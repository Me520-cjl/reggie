package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义业务异常类
 */
@Slf4j
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }

}