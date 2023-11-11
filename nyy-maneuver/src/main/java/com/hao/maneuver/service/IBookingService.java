package com.hao.maneuver.service;

import com.hao.common.domain.dto.Result;
import com.hao.maneuver.domain.vo.BookingInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: BookingService
 * @date 2023-11-09 18:01:01
 */
public interface IBookingService {

    /**
     * 预约活动服务
     * @param httpServletRequest 请求头
     * @param bookingInfo 预约目标
     * @return Result 结果
     */
    Result bookingManeuver(HttpServletRequest httpServletRequest, BookingInfo bookingInfo);

    /**
     * 取消预约
     * @param httpServletRequest 请求头
     * @param bookingInfo 预约目标
     * @return Result 结果
     */
    Result cancelBooking(HttpServletRequest httpServletRequest, BookingInfo bookingInfo);
}
