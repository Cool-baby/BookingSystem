package com.hao.maneuver.controller;

import com.hao.common.domain.dto.Result;
import com.hao.maneuver.domain.dto.ManeuverDTO;
import com.hao.maneuver.domain.po.Maneuver;
import com.hao.maneuver.domain.vo.BookingInfo;
import com.hao.maneuver.service.IBookingService;
import com.hao.maneuver.service.IManeuverService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: Maneuver控制类
 * @date 2023-11-09 12:40:47
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/maneuvers")
public class ManeuverController {

    private final IManeuverService maneuverService;
    private final IBookingService bookingService;

    @GetMapping()
    public Result getAllManeuver(){
        List<Maneuver> maneuverList = maneuverService.getAllManeuver();

        if(maneuverList.isEmpty()){
            return Result.filed("不存在此活动！");
        }
        return Result.success("OK", maneuverList);
    }

    @GetMapping("/{maneuverId}")
    public Result getManeuverDTO(@PathVariable String maneuverId){
        ManeuverDTO maneuverDTO = maneuverService.getManeuverDTO(maneuverId);

        return Result.success("OK", maneuverDTO);
    }

    @PostMapping("/booking")
    public Result bookingManeuver(HttpServletRequest httpServletRequest, @RequestBody BookingInfo bookingInfo){

        return bookingService.bookingManeuver(httpServletRequest, bookingInfo);
    }

    @PostMapping("/cancel")
    public Result cancelBooking(HttpServletRequest httpServletRequest, @RequestBody BookingInfo bookingInfo){

        return bookingService.cancelBooking(httpServletRequest, bookingInfo);
    }
}
