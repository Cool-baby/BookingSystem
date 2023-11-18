package com.hao.maneuver;

import cn.hutool.json.JSONUtil;
import com.hao.common.domain.other.RedisKey;
import com.hao.maneuver.domain.dto.ManeuverDTO;
import com.hao.maneuver.domain.po.ManeuverSegment;
import com.hao.maneuver.service.IManeuverSegmentService;
import com.hao.maneuver.service.IManeuverService;
import com.hao.maneuver.util.redis.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Hao
 * @program: nengyuyue
 * @description:
 * @date 2023-11-09 22:15:29
 */
@SpringBootTest
@Slf4j
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IManeuverService maneuverService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private IManeuverSegmentService maneuverSegmentService;

    @Test
    void saveToRedis(){
        ManeuverDTO maneuverDTO = maneuverService.getManeuverDTO("92455dd271fc4f6f95e1d3a30c965dba");
        log.info(String.valueOf(maneuverDTO));
        String key = "nengyuye:maneuver:" + "92455dd271fc4f6f95e1d3a30c965dba";
        redisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(maneuverDTO));
    }

    @Test
    void getFromRedis(){
        String key = "nengyuye:maneuver:" + "92455dd271fc4f6f95e1d3965dba";
        ManeuverDTO maneuverDTO = JSONUtil.toBean(redisTemplate.opsForValue().get(key), ManeuverDTO.class);
        log.info(String.valueOf(maneuverDTO));
    }

    @Test
    void setHashToRedis(){
        String key = RedisKey.MANEUVER_SEGMENT_KEY + "ee62e1c7da964c5fa1f425b65b4bfe59:" + 10026;
        String jsonRedis = redisCache.getString(key);

        ManeuverSegment maneuverSegment = maneuverSegmentService.getManeuverSegment("ee62e1c7da964c5fa1f425b65b4bfe59", 10026L);

    }

    @Test
    void learnRedis(){
        String key =  "test:ee62e1c7da964c5fa1f425b65b4bfe59";
        ManeuverSegment maneuverSegment = maneuverSegmentService.getManeuverSegment("ee62e1c7da964c5fa1f425b65b4bfe59", 10026L);

        redisTemplate.opsForHash().put(key, maneuverSegment.getSegmentId().toString(), maneuverSegment.getFreeCapacity().toString());
    }

    @Test
    void saveToSet(){
        String key = "test";
        String value = "2022";

        Long add = redisTemplate.opsForSet().add(key, value);
        System.out.println(add);
        Long remove = redisTemplate.opsForSet().remove(key, value);
        System.out.println(remove);
    }


}
