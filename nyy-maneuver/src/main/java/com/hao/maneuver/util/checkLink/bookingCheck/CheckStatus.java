package com.hao.maneuver.util.checkLink.bookingCheck;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 检验状态枚举类
 * @date 2023-11-18 17:37:44
 */
public enum CheckStatus {

    SUCCESS("Checks passed"),
    EXPIRED("Activity expired"),
    NO_MANEUVER("No Maneuver"),
    NO_MANEUVER_SEGMENT("No Maneuver Segment"),
    BOOKED("Already booked"),
    VOLUME_LESS("Insufficient space available"),
    ERROR("Other Error");


    private final String description;

    CheckStatus(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
}
