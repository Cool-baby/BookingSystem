package com.hao.common.domain.dto;

import com.hao.common.domain.other.Code;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 返回结果类
 * @date 2023-11-09 12:42:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private Integer code;
    private String msg;
    private Object data;

    /**
     * 成功回执（带Message）
     * @param message 信息
     * @return Result
     */
    public static Result success(String message){
        return new Result(Code.SUCCESS, message, null);
    }

    /**
     * 错误回执（带Message）
     * @param message 信息
     * @return Result
     */
    public static Result error(String message){
        return new Result(Code.ERROR, message, null);
    }

    /**
     * 失败回执（带Message）
     * @param message 信息
     * @return Result
     */
    public static Result filed(String message){
        return new Result(Code.FAILED, message, null);
    }

    /**
     * 成功回执（带Message 和 Data）
     * @param message 信息
     * @param data 数据
     * @return Result
     */
    public static Result success(String message, Object data){
        return new Result(Code.SUCCESS, message, data);
    }

    /**
     * token失效回执
     * @return Result
     */
    public static Result tokenInvalid(){
        return new Result(Code.TOKEN_INVALID, "Token无效!!", null);
    }

    /**
     * 无权限操作回执
     * @return Result
     */
    public static Result noPermission(){
        return new Result(Code.NO_PERMISSION, "无权限的操作", null);
    }

    /**
     * 系统错误回执
     * @return Result
     */
    public static Result systemError(){
        return new Result(Code.SYSTEM_ERROR, "系统错误", null);
    }

    /**
     * 系统错误回执
     * @return Result
     */
    public static Result systemError(Object o){
        return new Result(Code.SYSTEM_ERROR, "系统错误", o);
    }
}
