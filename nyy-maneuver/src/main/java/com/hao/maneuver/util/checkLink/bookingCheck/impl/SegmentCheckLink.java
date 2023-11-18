package com.hao.maneuver.util.checkLink.bookingCheck.impl;

import com.hao.maneuver.domain.po.ManeuverSegment;
import com.hao.maneuver.domain.vo.BookingInfo;
import com.hao.maneuver.service.IManeuverSegmentService;
import com.hao.maneuver.util.checkLink.bookingCheck.CheckInfo;
import com.hao.maneuver.util.checkLink.bookingCheck.CheckLink;
import com.hao.maneuver.util.checkLink.bookingCheck.CheckStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 检查链3：分段有效性检查
 * @date 2023-11-18 17:51:36
 */
@Slf4j
public class SegmentCheckLink extends CheckLink {

    private final IManeuverSegmentService maneuverSegmentService;

    public SegmentCheckLink(String checkListName, IManeuverSegmentService maneuverSegmentService) {
        super(checkListName);
        this.maneuverSegmentService = maneuverSegmentService;
    }

    @Override
    public CheckInfo doCheck(String userId, BookingInfo bookingInfo) {

        LocalDateTime nowTime = LocalDateTime.now();
        ManeuverSegment maneuverSegment = maneuverSegmentService.getManeuverSegment(bookingInfo.getManeuverId(), bookingInfo.getSegmentId());
        if(maneuverSegment == null){
            return new CheckInfo(CheckStatus.NO_MANEUVER_SEGMENT, "没有此时间段的活动！");
        }
        if(maneuverSegment.getEndTime().isBefore(nowTime)){
            return new CheckInfo(CheckStatus.EXPIRED, "此阶段的活动已过期！");
        }

        log.info(getCheckListName() + " pass");

        CheckLink next = super.next();
        if (next == null) {
            return new CheckInfo(CheckStatus.SUCCESS, "校验通过！");
        }

        return next.doCheck(userId, bookingInfo);
    }
}
