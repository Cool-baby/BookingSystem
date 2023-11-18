package com.hao.maneuver.util.checkLink.bookingCheck;

import com.hao.maneuver.domain.vo.BookingInfo;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 检查链抽象类
 * @date 2023-11-18 16:19:26
 */
public abstract class CheckLink {

    // 名称
    private final String checkListName;

    // 下一检查链
    private CheckLink next;

    public CheckLink(String checkListName) {
        this.checkListName = checkListName;
    }

    public CheckLink next(){
        return next;
    }

    /**
     * 检查方法
     * @param userId 用户ID
     * @param bookingInfo 预约信息
     * @return CheckInfo
     */
    public abstract CheckInfo doCheck(String userId, BookingInfo bookingInfo);

    /**
     * 添加下一检查链
     * @param next 下一检查链
     * @return CheckLink
     */
    public CheckLink appendNext(CheckLink next){
        this.next = next;
        return this;
    }

    /**
     * 获取名称
     * @return String
     */
    public String getCheckListName(){
        return checkListName;
    }
}
