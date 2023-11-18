package com.hao.maneuver.util.checkLink.bookingCheck.impl;

import com.hao.maneuver.domain.vo.BookingInfo;
import com.hao.maneuver.util.checkLink.bookingCheck.CheckInfo;
import com.hao.maneuver.util.checkLink.bookingCheck.CheckLink;
import com.hao.maneuver.util.checkLink.bookingCheck.CheckStatus;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 检查链2：规则检查
 * @date 2023-11-18 17:48:47
 */
@Slf4j
public class RuleCheckLink extends CheckLink {

    public RuleCheckLink(String checkListName) {
        super(checkListName);
    }

    @Override
    public CheckInfo doCheck(String userId, BookingInfo bookingInfo) {

        // TODO 规则校验
        log.info(getCheckListName() + " pass");

        CheckLink next = super.next();
        if (next == null) {
            return new CheckInfo(CheckStatus.SUCCESS, "校验通过！");
        }

        return next.doCheck(userId, bookingInfo);
    }
}
