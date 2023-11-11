package com.hao.maneuver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.maneuver.domain.dto.ManeuverDTO;
import com.hao.maneuver.domain.po.Maneuver;

import java.util.List;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: Maneuver Service
 * @date 2023-11-09 12:00:58
 */
public interface IManeuverService extends IService<Maneuver> {

    /**
     * 新建活动并预热
     * @param maneuver 新活动
     * @return boolean
     */
    boolean newManeuver(Maneuver maneuver);

    /**
     * 获取全部的Maneuver
     * @return List<Maneuver>
     */
    List<Maneuver> getAllManeuver();

    /**
     * 获取指定的活动
     * @param maneuverId maneuverId
     * @return Maneuver
     */
    Maneuver getManeuver(String maneuverId);

    /**
     * 通过maneuverId获取ManeuverDTO
     * @param maneuverId maneuverId
     * @return ManeuverDTO
     */
    ManeuverDTO getManeuverDTO(String maneuverId);
}
