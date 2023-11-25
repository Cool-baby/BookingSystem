package com.hao.user.domain.vo;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 登录表单
 * @date 2023-11-25 19:52:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {

    // 用户名ID
    @NotNull(message = "用户名不能为空")
    private String userID;

    // 用户密码
    @NotNull(message = "密码不能为空")
    private String userPassword;

}
