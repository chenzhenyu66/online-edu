package com.czy1344.commonutils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 2020/7/25 16:00
 *
 * @author czy1344
 * 说明：用于返回页面统一结果
 */
@Data
public class Result {
    private Boolean success;
    private Integer code;
    private String message;
    private Map<String,Object> data=new HashMap<>();

    private Result(){}

    public static Result success(){
        Result result = new Result();
        result.setCode(ResultCode.SUCCESS);
        result.setSuccess(true);
        result.setMessage("成功");
        return result;
    }
    public static Result error(){
        Result result = new Result();
        result.setCode(ResultCode.ERROR);
        result.setSuccess(false);
        result.setMessage("失败");
        return result;
    }

    /**
     *  链式编程
     */
    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public Result message(String message){
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code){
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }


    interface ResultCode {
        Integer SUCCESS = 200;
        Integer ERROR = 400;
    }
}
