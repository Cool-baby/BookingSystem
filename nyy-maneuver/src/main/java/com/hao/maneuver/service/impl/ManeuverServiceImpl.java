package com.hao.maneuver.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hao.common.domain.other.RedisKey;
import com.hao.maneuver.domain.dto.ManeuverDTO;
import com.hao.maneuver.domain.po.Maneuver;
import com.hao.maneuver.domain.po.ManeuverSegment;
import com.hao.maneuver.mapper.ManeuverMapper;
import com.hao.maneuver.service.IManeuverSegmentService;
import com.hao.maneuver.service.IManeuverService;
import com.hao.maneuver.util.redis.RedisCache;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: IManeuverService实现类
 * @date 2023-11-09 12:03:12
 */
@Service
@RequiredArgsConstructor
public class ManeuverServiceImpl extends ServiceImpl<ManeuverMapper, Maneuver> implements IManeuverService {

    private final IManeuverSegmentService maneuverSegmentService;
    private final RedisCache redisCache;

    // 新建活动并预热
    @Override
    public boolean saveManeuver(Maneuver maneuver) {

        // 校验数据
        if(maneuver == null || StrUtil.isBlank(maneuver.getManeuverId())){
            return false;
        }
        // 1、存入MySQL
        boolean flag = this.save(maneuver);

        // 2、存入Redis
        String key = RedisKey.MANEUVER_KEY + maneuver.getManeuverId();
        if(maneuver.getEndTime() != null){
            // 将过期时间设置在活动结束后一小时
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(now, maneuver.getEndTime());
            long hours = duration.toHours();
            redisCache.setString(key, JSONUtil.toJsonStr(maneuver), hours+1, TimeUnit.HOURS); // 动态时间
        }else {
            redisCache.setString(key, JSONUtil.toJsonStr(maneuver), 8L, TimeUnit.HOURS);
        }

        return flag;
    }

    // 获取全部的Maneuver
    @Override
    public List<Maneuver> getAllManeuver() {
        return this.list();
    }

    // 获取指定的活动
    @Override
    public Maneuver getManeuver(String maneuverId) {
        /*
        * 1、先读Redis缓存；
        * 2、如果Redis没有，就读出来放进Redis；
        * 3、返回。
        * */
        String key = RedisKey.MANEUVER_KEY + maneuverId;
        // 1、先读Redis缓存
        String jsonString = redisCache.getString(key);
        if(StrUtil.isNotBlank(jsonString)){
            return JSONUtil.toBean(jsonString, Maneuver.class);
        }

        // 2、如果Redis没有再读数据库
        Maneuver maneuverFromMySQL = this.lambdaQuery().eq(Maneuver::getManeuverId, maneuverId).one();

        // 3、写回Redis
        // TODO 防止数据库里也没用，出现缓存穿透问题
        if(maneuverFromMySQL != null){
            redisCache.setString(key, maneuverFromMySQL, 8L, TimeUnit.HOURS);
        }
        return maneuverFromMySQL;
    }

    // 通过maneuverId获取ManeuverDTO
    @Override
    public ManeuverDTO getManeuverDTO(String maneuverId) {
        // 1.1、先找maneuver
        Maneuver maneuver = getManeuver(maneuverId);
        // 1.2、再找先找maneuverSegment
        List<ManeuverSegment> allManeuverSegment = maneuverSegmentService.getAllManeuverSegment(maneuverId);

        return new ManeuverDTO(maneuver, allManeuverSegment);
    }
}
