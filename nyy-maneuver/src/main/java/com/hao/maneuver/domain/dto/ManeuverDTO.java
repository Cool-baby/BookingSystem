package com.hao.maneuver.domain.dto;

import com.hao.maneuver.domain.po.Maneuver;
import com.hao.maneuver.domain.po.ManeuverSegment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 活动信息DTO
 * @date 2023-11-09 11:53:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManeuverDTO {

    // 活动信息
    private Maneuver maneuver;

    // 分段信息
    private List<ManeuverSegment> maneuverSegmentList;
}
