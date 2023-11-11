package com.hao.maneuver.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 预约信息
 * @date 2023-11-09 17:48:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingInfo {

    private String maneuverId;

    private Long segmentId;
}
