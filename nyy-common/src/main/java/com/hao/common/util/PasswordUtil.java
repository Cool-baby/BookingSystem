package com.hao.common.util;

import cn.hutool.crypto.digest.DigestUtil;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 密码生成器
 * @date 2023-11-09 16:51:45
 */
public class PasswordUtil {

    /**
     * 将密码使用MD5工具类进行加密操作
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String pwdEncrypt(String password){
        return DigestUtil.md5Hex(password);
    }

    /**
     * 判断输入的密码是否和数据库中的密码一致
     * @param password 输入密码
     * @param passwordInSQL 数据库密码
     * @return boolean
     */
    public static boolean pwdEqual(String password, String passwordInSQL){
        return passwordInSQL.equals(pwdEncrypt(password));
    }
}
