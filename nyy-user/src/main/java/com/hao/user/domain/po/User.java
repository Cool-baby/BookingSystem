package com.hao.user.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 用户
 * @date 2023-11-09 16:06:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long id;

    // 用户ID（唯一）
    private String userId;

    // 用户名
    private String userName;

    // 用户密码
    private String userPassword;

    // 用户手机号
    private String phone;

    // 用户地址
    private String userAddress;

    // 用户身份（admin、offer、apply）
    private String userRole;

    // 用户性别(0女1男)
    private int gender;
}
