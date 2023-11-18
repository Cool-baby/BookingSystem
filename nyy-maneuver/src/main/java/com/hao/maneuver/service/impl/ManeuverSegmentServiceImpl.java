package com.hao.maneuver.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.common.domain.other.RedisKey;
import com.hao.maneuver.domain.po.ManeuverSegment;
import com.hao.maneuver.mapper.ManeuverSegmentMapper;
import com.hao.maneuver.service.IManeuverSegmentService;
import com.hao.maneuver.util.redis.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: ManeuverSegmentService实现类
 * @date 2023-11-09 12:02:10
 */
@Service
@RequiredArgsConstructor
public class ManeuverSegmentServiceImpl extends ServiceImpl<ManeuverSegmentMapper, ManeuverSegment> implements IManeuverSegmentService {

    private final RedisCache redisCache;

    // 给Maneuver添加分段信息，并进行预热
    @Override
    @Transactional
    public boolean saveSegmentForManeuver(String maneuverId, List<ManeuverSegment> maneuverSegmentList) {

        // 1、批量存入数据库
        boolean flag = this.saveBatch(maneuverSegmentList);

        // 2、预热数据
        for(ManeuverSegment maneuverSegment:maneuverSegmentList){
            // 2.1、存入到nengyuyue:maneuver:segment中
            String segmentKey = RedisKey.MANEUVER_SEGMENT_KEY + maneuverId;
            redisCache.putToHash(segmentKey, String.valueOf(maneuverSegment.getSegmentId()), maneuverSegment);
            // 2.2、存入到nengyuyue:maneuver:booking中
            String bookingKey = RedisKey.MANEUVER_BOOKING_KEY + maneuverId;
            redisCache.putToHash(bookingKey, String.valueOf(maneuverSegment.getSegmentId()), maneuverSegment.getFreeCapacity().toString());
        }

        return flag;
    }

    // 通过活动ID和分段ID获取分段活动信息
    @Override
    public ManeuverSegment getManeuverSegment(String maneuverId, Long segmentId) {
        // 1、先读Redis
        String key = RedisKey.MANEUVER_SEGMENT_KEY + maneuverId;
        ManeuverSegment maneuverSegmentFromRedis = JSONUtil.toBean((String) redisCache.getHashValue(key, String.valueOf(segmentId)), ManeuverSegment.class);
        if(maneuverSegmentFromRedis != null && StrUtil.isNotBlank(maneuverSegmentFromRedis.getManeuverId())){
            return maneuverSegmentFromRedis;
        }

        // 2、如果没有，去MySQL中找
        // TODO 第一个到达的请求去找，防止缓存击穿
        ManeuverSegment maneuverSegmentFromMySQL = this.lambdaQuery()
                .eq(ManeuverSegment::getManeuverId, maneuverId)
                .eq(ManeuverSegment::getSegmentId, segmentId)
                .one();

        // 3、写回Redis
        // TODO 防止MySQL中也没用，会缓存穿透
        if(maneuverSegmentFromMySQL != null){
            redisCache.putToHash(key, String.valueOf(maneuverSegmentFromMySQL.getSegmentId()), maneuverSegmentFromMySQL);
        }

        return maneuverSegmentFromMySQL;
    }

    // 通过maneuverId获取其全部的分段信息
    @Override
    public List<ManeuverSegment> getAllManeuverSegment(String maneuverId) {
        List<ManeuverSegment> maneuverSegmentListFromRedis = new ArrayList<>();
        // 1、先去读Redis
        String segmentKey = RedisKey.MANEUVER_SEGMENT_KEY + maneuverId;
        String bookingKey = RedisKey.MANEUVER_BOOKING_KEY + maneuverId;
        // 2、获取redis中的键值对
        Map<Object, Object> segmentEntries = redisCache.getHashAllEntry(segmentKey);
        Map<Object, Object> bookingEntries = redisCache.getHashAllEntry(bookingKey);

        if (!segmentEntries.isEmpty() && !bookingEntries.isEmpty()) {
            segmentEntries.forEach((k, v) -> {
                ManeuverSegment maneuverSegment = JSONUtil.toBean((String) v, ManeuverSegment.class);
                Long freeCapacity = Long.parseLong((String) bookingEntries.get(k));
                maneuverSegment.setFreeCapacity(freeCapacity);
                maneuverSegmentListFromRedis.add(maneuverSegment);
            });
            maneuverSegmentListFromRedis.sort(Comparator.comparingLong(ManeuverSegment::getSegmentId));
            return maneuverSegmentListFromRedis;
        }
        // 2、如果没有，去MySQL读取
        List<ManeuverSegment> maneuverSegmentListFromMySQL = this.lambdaQuery().eq(ManeuverSegment::getManeuverId, maneuverId).list();

        // 3、写回Redis
        for(ManeuverSegment maneuverSegment:maneuverSegmentListFromMySQL){
            redisCache.putToHash(segmentKey, String.valueOf(maneuverSegment.getSegmentId()), maneuverSegment);
        }

        return maneuverSegmentListFromMySQL;
    }

    // 从Redis中读出最新的可用容量
    @Override
    public Long getNewFreeCapacity(String maneuverId, Long segmentId) {
        String key = RedisKey.MANEUVER_BOOKING_KEY + maneuverId;
        // TODO 这里一定要提前预热数据
        Object objectFromRedis = redisCache.getHashValue(key, String.valueOf(segmentId));
        return Long.parseLong(objectFromRedis == null ? "0" : String.valueOf(objectFromRedis)); // 如果获取数据为空，则置为0
    }

}
