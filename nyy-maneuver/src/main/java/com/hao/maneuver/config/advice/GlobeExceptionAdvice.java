package com.hao.maneuver.config.advice;

import com.hao.common.domain.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Hao
 * @program: nengyuyue
 * @description: 全局异常捕获
 * @date 2023-11-09 15:54:47
 */
@RestControllerAdvice
@Slf4j
public class GlobeExceptionAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Result runtimeException(RuntimeException runtimeException){
        runtimeException.printStackTrace();
        return Result.systemError(runtimeException);
    }
}
