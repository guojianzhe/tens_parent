package com.tensquare.base.controller;


import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//专门为controller创建的
//异常通知=>出现异常会执行
@ControllerAdvice
public class BaseExceptionHandler
{
    //描述该方法作为通知代码处理方法,并表示异常类型
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler(Exception e){
        e.printStackTrace();
        return new Result(StatusCode.ERROR,false,"出现异常了:"+e.getMessage());
    }
}
