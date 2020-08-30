package com.czy1344.servicebase.handler;

import com.czy1344.commonutils.ExceptionUtil;
import com.czy1344.commonutils.Result;
import com.czy1344.servicebase.exception.OnlineEduException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 2020/7/25 21:15
 *
 * @author czy1344
 * 说明：
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    //全局异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message(e.getMessage());
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message(e.getMessage());
    }

    //自定义异常处理
    @ExceptionHandler(OnlineEduException.class)
    @ResponseBody
    public Result error(OnlineEduException e) {
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
}
