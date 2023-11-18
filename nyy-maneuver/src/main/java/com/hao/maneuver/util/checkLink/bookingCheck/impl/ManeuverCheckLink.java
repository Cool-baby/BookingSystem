package com.hao.maneuver.util.checkLink.bookingCheck.impl;

import com.hao.maneuver.domain.po.Maneuver;
import com.hao.maneuver.domain.vo.BookingInfo;
import com.hao.maneuver.service.IManeuverService;
import com.hao.maneuver.util.checkLink.bookingCheck.CheckInfo;
import com.hao.maneuver.util.checkLink.bookingCheck.CheckLink;
import com.hao.maneuver.util.checkLink.bookingCheck.CheckStatus;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 检查链1：活动有效性检查
 * @date 2023-11-18 16:25:52
 */
@Slf4j
public class ManeuverCheckLink extends CheckLink {

    private final IManeuverService maneuverService;

    public ManeuverCheckLink(String checkListName, IManeuverService maneuverService) {
        super(checkListName);
        this.maneuverService = maneuverService;
    }

    @Override
    public CheckInfo doCheck(String userId, BookingInfo bookingInfo) {

        Maneuver maneuver = maneuverService.getManeuver(bookingInfo.getManeuverId());
        if(maneuver == null){
            return new CheckInfo(CheckStatus.NO_MANEUVER, "没有此活动！");
        }
        LocalDateTime nowTime = LocalDateTime.now();
        if(!nowTime.isBefore(maneuver.getEndTime())){
            return new CheckInfo(CheckStatus.EXPIRED, "活动已过期！");
        }

        log.info(getCheckListName() + " pass");

        CheckLink next = super.next();
        if (next == null) {
            return new CheckInfo(CheckStatus.SUCCESS, "校验通过！");
        }

        return next.doCheck(userId, bookingInfo);
    }
}
