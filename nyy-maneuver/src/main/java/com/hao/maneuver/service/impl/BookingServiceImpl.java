package com.hao.maneuver.service.impl;

import cn.hutool.core.util.StrUtil;
import com.hao.common.domain.dto.Result;
import com.hao.common.domain.other.RedisKey;
import com.hao.log.domain.po.TempBookingLog;
import com.hao.maneuver.domain.po.Maneuver;
import com.hao.maneuver.domain.po.ManeuverSegment;
import com.hao.maneuver.domain.vo.BookingInfo;
import com.hao.maneuver.service.IBookingService;
import com.hao.maneuver.service.IManeuverSegmentService;
import com.hao.maneuver.service.IManeuverService;
import com.hao.maneuver.util.redis.RedisCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 预约实现类
 * @date 2023-11-09 18:01:15
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements IBookingService {

    private final IManeuverService maneuverService;
    private final IManeuverSegmentService maneuverSegmentService;
    private final RedisCache redisCache;
    private final RocketMQTemplate rocketMQTemplate;

    // 预约活动服务
    @Override
    @Transactional
    public Result bookingManeuver(HttpServletRequest httpServletRequest, BookingInfo bookingInfo) {

        // 前置校验
        if(bookingInfo == null || StrUtil.isBlank(bookingInfo.getManeuverId())){
            return Result.filed("请求信息错误！");
        }
        String userId = httpServletRequest.getHeader("userId"); // 这里简单的用请求头的userId作为身份，没有使用token
        if(StrUtil.isBlank(userId)){
            return Result.filed("请先登录！");
        }

        // 获取当前时间
        LocalDateTime nowTime = LocalDateTime.now();
        /*
        * 预约服务流程
        *   1、检验是否有此活动 && 当前时间在规定时间内；
        *   2、判断用户是否有资格参加此活动；
        *   3、检验是否有此活动的分段 && 当前时间是否已超过最大时间 && 检验此分段活动是否有剩余容量；
        *   4、检验用户是否已经预约过此活动；
        *   5、扣减容量；（考虑高并发）
        *   6、记录或更新日志
        * */
        // 1、检验是否有此活动 && 当前时间在规定时间内
        Maneuver maneuver = maneuverService.getManeuver(bookingInfo.getManeuverId());
        if(maneuver == null){
            return Result.filed("没有此活动！");
        }
        if(!(nowTime.isAfter(maneuver.getStartTime()) && nowTime.isBefore(maneuver.getEndTime()))){
            return Result.filed("活动已过期！");
        }

        // 2、TODO 判断用户是否有资格参加此活动

        // 3、检验是否有此活动的分段 && 当前时间是否已超过最大时间
        ManeuverSegment maneuverSegment = maneuverSegmentService.getManeuverSegment(bookingInfo.getManeuverId(), bookingInfo.getSegmentId());
        if(maneuverSegment == null){
            return Result.filed("没有此时间段的活动！");
        }
        if(maneuverSegment.getEndTime().isBefore(nowTime)){
            return Result.filed("此阶段的活动已过期！");
        }

        // 4、检验用户是否已经预约过此活动，直接往Redis的Set中存，能存进去说明还没预约，存不进去说明已经预约了
        String segmentLogKey = RedisKey.MANEUVER_SEGMENT_LOG_KEY + bookingInfo.getManeuverId();
        Long saveLog = redisCache.addSet(segmentLogKey, userId);
        if(saveLog == 0){
            return Result.filed("已经预约过！");
        }

        // 5、扣减容量（直接扣Redis，扣完判断是否小于0，如果扣完之后小于0，再加回去）
        String bookingKey = RedisKey.MANEUVER_BOOKING_KEY + bookingInfo.getManeuverId();
        Long tryBooking = redisCache.incrementOrDecrementHash(bookingKey, String.valueOf(bookingInfo.getSegmentId()), -1L);
        if(tryBooking < 0){
            redisCache.incrementOrDecrementHash(bookingKey, String.valueOf(bookingInfo.getSegmentId()), 1L); // 把扣的库存再加回去
            redisCache.removeValueInSet(segmentLogKey, userId); // 再把此用户从已预约的名单中踢出去
            return Result.filed("可用容量不足！");
        }

        // 6、使用RocketMQ保证写回MySQL
        TempBookingLog tempBookingLog = new TempBookingLog(null, userId, bookingInfo.getManeuverId(), bookingInfo.getSegmentId(), nowTime, 0);
        rocketMQTemplate.syncSend("addTempBookingLog1", tempBookingLog);

        return Result.success("OK", tempBookingLog);
    }

    // 取消预约
    @Override
    @Transactional
    public Result cancelBooking(HttpServletRequest httpServletRequest, BookingInfo bookingInfo) {

        if(bookingInfo == null || StrUtil.isBlank(bookingInfo.getManeuverId())){
            return Result.filed("请求信息错误！");
        }
        String userId = httpServletRequest.getHeader("userId"); // 这里简单的用请求头的userId作为身份，没有使用token
        if(StrUtil.isBlank(userId)){
            return Result.filed("请先登录！");
        }

        /*
        * 取消预约流程
        *   1、读取用户是否有预约，没有预约怎么取消；
        *   2、取消预约，删除日志；
        *   3、释放容量。（考虑并发）
        * */
        // 1、判断用户是否有预约
        String logKey = RedisKey.MANEUVER_SEGMENT_LOG_KEY + bookingInfo.getManeuverId();
        Long removeFlag = redisCache.removeValueInSet(logKey, userId); // 直接删
        if(removeFlag == 0){
            // TODO 这个地方是否还要判断用户是否预约了此时间段，万一时间段是错误的怎么办？
            return Result.filed("您还未预约");
        }

        // 2、使用MQ异步删除数据库日志
        TempBookingLog tempBookingLog = new TempBookingLog(null, userId, bookingInfo.getManeuverId(),null, null, 1);
        rocketMQTemplate.syncSend("addTempBookingLog1", tempBookingLog);

        // 3、释放容量
        String bookingKey = RedisKey.MANEUVER_BOOKING_KEY + bookingInfo.getManeuverId();
        redisCache.incrementOrDecrementHash(bookingKey, String.valueOf(bookingInfo.getSegmentId()), 1L);

        return Result.success("OK");
    }
}
