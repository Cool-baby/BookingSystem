package com.hao.maneuver.util.checkLink.bookingCheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 校验结果信息
 * @date 2023-11-18 16:17:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckInfo {

    // 状态码
    private CheckStatus status;
    // 提示信息
    private String info;

}
