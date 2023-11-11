package com.hao.maneuver.util.converter;

import com.hao.maneuver.domain.dto.ManeuverDTO;
import com.hao.maneuver.domain.po.Maneuver;
import com.hao.maneuver.domain.po.ManeuverSegment;

import java.util.List;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: Maneuver转换为ManeuverDTO
 * @date 2023-11-09 11:55:33
 */
public class Maneuver2ManeuverDTO {

    public static ManeuverDTO maneuver2ManeuverDTO(Maneuver maneuver, List<ManeuverSegment> maneuverSegmentList){
        if(maneuver != null){
            return new ManeuverDTO(maneuver, maneuverSegmentList);
        }

        return null;
    }
}
