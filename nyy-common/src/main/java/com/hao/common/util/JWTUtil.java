package com.hao.common.util;


import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

/**
 * @author Hao
 * @program: HDSpringBootDemo
 * @description: JWT工具类
 * @date 2022-10-31 09:23:31
 */
/*
* 包含三个工具：
*   1、生成token；
*   2、通过token获取UserID；
*   3、验证token。
* */
public class JWTUtil {

    // 时间毫秒数，token有效期（Token过期时间设置为10小时）
    private static long time = 1000*60*60*10;
    // 签名
    private static final String signature = "NYY_System";

    /**
     * 创建Token
     * @param userID 用户工号
     * @return Token
     */
    public static String createToken(String userID){
        JwtBuilder jwtBuilder = Jwts.builder();//构建JWT对象
        String jwtToken = jwtBuilder
                // Header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                // payload
                .setSubject(userID)
                // 设置有效期（毫秒单位）
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())
                // signature
                .signWith(SignatureAlgorithm.HS256, signature)
                // compact拼接三部分header、payload、signature
                .compact();
        return jwtToken;
    }

    /**
     * 通过Token获取用户UserID
     * @param token
     * @return UserID
     */
    public static String getUserIDByToken(String token){
        if(token == null){
            return null;
        }
        try {
            JwtParser jwtParser = Jwts.parser();
            Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
            return claimsJws.getBody().getSubject();
        } catch (Exception e) {//抛异常，说明token失效
            return null;
        }
    }

    /**
     * 检测Token是否有效
     * @param token
     * @return Boolean
     */
    public static Boolean checkToken(String token){
        if(token == null){
            return false;
        }
        try {
            JwtParser jwtParser = Jwts.parser();
            Jws<Claims> claimsJws = jwtParser.setSigningKey(signature).parseClaimsJws(token);
            return true;
        }catch (Exception e){
            System.out.println("token失效");
            return false;
        }
    }
}
