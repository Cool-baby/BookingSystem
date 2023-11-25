package com.hao.maneuver.service;

import com.hao.common.domain.dto.Result;
import com.hao.maneuver.domain.vo.BookingInfo;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: BookingService
 * @date 2023-11-09 18:01:01
 */
public interface IBookingService {

    /**
     * 预约活动服务
     * @param bookingInfo 预约目标
     * @return Result 结果
     */
    Result bookingManeuver(BookingInfo bookingInfo);

    /**
     * 取消预约
     * @param bookingInfo 预约目标
     * @return Result 结果
     */
    Result cancelBooking(BookingInfo bookingInfo);
}
