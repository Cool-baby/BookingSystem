package com.hao.maneuver;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.hao.common.domain.other.RedisKey;
import com.hao.maneuver.domain.dto.ManeuverDTO;
import com.hao.maneuver.domain.po.ManeuverSegment;
import com.hao.maneuver.service.IManeuverSegmentService;
import com.hao.maneuver.service.IManeuverService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.jws.Oneway;
import java.time.LocalDateTime;
import java.util.*;

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

        String maneuverId = "003e7863a42b418299e08d5d4a3bff30";

        LocalDateTime startTime = LocalDateTime.of(2023, 11, 19, 7, 0, 0);

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

        maneuverSegmentService.saveSegmentForManeuver(maneuverId, maneuverSegmentList);
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

    @Test
    void getManeuverDTO(){
        String maneuverId = "003e7863a42b418299e08d5d4a3bff30";
        List<ManeuverSegment> maneuverSegmentList = new ArrayList<>();

        String segmentKey = RedisKey.MANEUVER_SEGMENT_KEY + maneuverId;
        String bookingKey = RedisKey.MANEUVER_BOOKING_KEY + maneuverId;

        Map<Object, Object> segmentEntries = redisTemplate.opsForHash().entries(segmentKey);
        Map<Object, Object> bookingEntries = redisTemplate.opsForHash().entries(bookingKey);

        segmentEntries.forEach((k, v) -> {
            ManeuverSegment maneuverSegment = JSONUtil.toBean((String) v, ManeuverSegment.class);
            Long freeCapacity = Long.parseLong((String) bookingEntries.get(k));
            maneuverSegment.setFreeCapacity(freeCapacity);
            maneuverSegmentList.add(maneuverSegment);
        });

        Collections.sort(maneuverSegmentList, Comparator.comparing(ManeuverSegment::getSegmentId));
        System.out.println(maneuverSegmentList);
    }

    @Test
    void testInnClass() throws InterruptedException {

        Integer number = 1;
        List<Integer> linkedList = new LinkedList<>();
        List<Integer> arrayList = new ArrayList<>();

        Thread thread = new Thread(() -> {
            linkedList.add(number);
            arrayList.add(number);
        });

        thread.start();
        thread.join();

        System.out.println(linkedList);
        System.out.println(arrayList);
    }
}
