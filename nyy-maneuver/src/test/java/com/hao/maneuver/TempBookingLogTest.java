package com.hao.maneuver;

import com.hao.common.domain.other.RedisKey;
import com.hao.maneuver.util.redis.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: TempBookingLog测试
 * @date 2023-11-10 15:54:04
 */
@SpringBootTest
public class TempBookingLogTest {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private RedisCache redisCache;

    @Test
    void saveLogToRedis(){
        String key = RedisKey.MANEUVER_SEGMENT_LOG_KEY + "ee62e1c7da964c5fa1f425b65b4bfe59";
        String userId = "20230112";
        // redisTemplate.opsForSet().add(key, userId);

        System.out.println(redisCache.hasValueInSet(key, "20230112"));
        System.out.println(redisTemplate.opsForSet().isMember(key, "20230113"));
    }

}
