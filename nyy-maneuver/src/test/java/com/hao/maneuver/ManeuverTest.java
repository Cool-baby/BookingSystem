package com.hao.maneuver;

import cn.hutool.core.util.IdUtil;
import com.hao.maneuver.domain.dto.ManeuverDTO;
import com.hao.maneuver.domain.po.Maneuver;
import com.hao.maneuver.service.IManeuverService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


/**
 * @author Hao
 * @program: nengyuyue
 * @description: Maneuver测试
 * @date 2023-11-09 11:59:47
 */
@SpringBootTest
@Slf4j
public class ManeuverTest {

    @Autowired
    private IManeuverService maneuverService;

    // 新建一个活动
    @Test
    void saveManeuver(){
        String uuid = IdUtil.simpleUUID();

        LocalDateTime startTime = LocalDateTime.of(2023, 11, 19, 7, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 11, 19, 23, 0, 0);
        Maneuver maneuver = new Maneuver(null, uuid, "南区男001浴室预约", "洗澡预约", startTime, endTime, 6400L, "男");
        maneuverService.saveManeuver(maneuver);
    }

    @Test
    void getManeuverDTOTest(){
        ManeuverDTO maneuverDTO = maneuverService.getManeuverDTO("4f0b77be65db4250ad4df2fa339ad6fd");
        System.out.println(maneuverDTO);
    }

    @Test
    void getNowTime(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);
        LocalDateTime startTime = LocalDateTime.of(2023, 11, 9, 7, 0, 0);
        LocalDateTime endTime = LocalDateTime.of(2023, 11, 9, 22, 0, 0);
        System.out.println(now.isAfter(startTime));
        System.out.println(now.isBefore(startTime));
        System.out.println(now.isAfter(endTime));
        System.out.println(now.isBefore(endTime));
    }
}
