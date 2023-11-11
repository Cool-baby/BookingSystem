package com.hao.maneuver;

import cn.hutool.core.util.IdUtil;
import com.hao.common.domain.other.RedisKey;
import com.hao.maneuver.domain.dto.ManeuverDTO;
import com.hao.maneuver.domain.po.Maneuver;
import com.hao.maneuver.domain.po.ManeuverSegment;
import com.hao.maneuver.service.IManeuverSegmentService;
import com.hao.maneuver.service.IManeuverService;
import com.hao.maneuver.util.redis.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: ManeuverSegmentTest
 * @date 2023-11-09 12:16:16
 */
@SpringBootTest
public class ManeuverSegmentTest {

    @Autowired
    private IManeuverSegmentService maneuverSegmentService;
    @Autowired
    private IManeuverService maneuverService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    // 存入数据并预热
    @Test
    void saveManeuverSegment(){

        String maneuverId = "7d7b63a896754161b05bf1aa50d079e1";

        LocalDateTime startTime = LocalDateTime.of(2023, 11, 11, 7, 0, 0);

        long allCapacity = 6400L;
        int allSegment = 32;

        List<ManeuverSegment> maneuverSegmentList = new ArrayList<>();
        for(int i=0; i<allSegment; i++){
            long segmentId = 10000L;
            segmentId += i;
            Long capacity = allCapacity / allSegment;
            LocalDateTime temp = startTime.plusMinutes(30);

            ManeuverSegment maneuverSegment = new ManeuverSegment(null, maneuverId, segmentId, startTime, temp, capacity, capacity);
            maneuverSegmentList.add(maneuverSegment);
            startTime = temp;
        }

        maneuverSegmentService.addSegmentForManeuver(maneuverId, maneuverSegmentList);
    }

    // 缓存预热
    @Test
    void saveToRedis(){
        String key = "nengyuyue:maneuver:booking:";
        String maneuverId = "ee62e1c7da964c5fa1f425b65b4bfe59";

        String tempKey = key + maneuverId;
        ManeuverDTO maneuverDTO = maneuverService.getManeuverDTO(maneuverId);

        for(ManeuverSegment maneuverSegment:maneuverDTO.getManeuverSegmentList()){
            redisTemplate.opsForHash().put(tempKey, maneuverSegment.getSegmentId().toString(), maneuverSegment.getFreeCapacity().toString());
        }
    }

    @Test
    void getManeuverSegmentTest(){
        String maneuverId = "ee62e1c7da964c5fa1f425b65b4bfe59";
        Long segmentId = 10009L;

        ManeuverSegment maneuverSegment = maneuverSegmentService.getManeuverSegment(maneuverId, segmentId);

        System.out.println(maneuverSegment);
    }
}
