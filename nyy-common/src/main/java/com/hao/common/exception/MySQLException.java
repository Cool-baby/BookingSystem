package com.hao.common.exception;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 自定义Mysql操作异常
 * @date 2023-11-09 20:04:19
 */
public class MySQLException extends RuntimeException{

    // 默认构造器
    public MySQLException(){

    }

    // 带有详细参数构造器
    public MySQLException(String msg){
        super(msg);
    }
}
