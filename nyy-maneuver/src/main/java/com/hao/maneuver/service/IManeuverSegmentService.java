package com.hao.maneuver.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hao.maneuver.domain.po.ManeuverSegment;

import java.util.List;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: ManeuverSegmentService
 * @date 2023-11-09 12:01:42
 */
public interface IManeuverSegmentService extends IService<ManeuverSegment> {

    /**
     * 给Maneuver添加分段信息，并进行预热
     * @param maneuverId 目标活动ID
     * @param maneuverSegmentList 分段信息
     * @return boolean
     */
    boolean addSegmentForManeuver(String maneuverId, List<ManeuverSegment> maneuverSegmentList);

    /**
     * 通过活动ID和分段ID获取分段活动信息
     * @param maneuverId 活动ID
     * @param segmentId 分段ID
     * @return ManeuverSegment
     */
    ManeuverSegment getManeuverSegment(String maneuverId, Long segmentId);

    /**
     * 通过maneuverId获取其全部的分段信息
     * @param maneuverId 活动ID
     * @return List<ManeuverSegment>
     */
    List<ManeuverSegment> getAllManeuverSegment(String maneuverId);

    /**
     * 从Redis中读出最新的可用容量
     * @param maneuverId 活动ID
     * @param segmentId 分段ID
     * @return 最新可用容量
     */
    Long getNewFreeCapacity(String maneuverId, Long segmentId);
}
