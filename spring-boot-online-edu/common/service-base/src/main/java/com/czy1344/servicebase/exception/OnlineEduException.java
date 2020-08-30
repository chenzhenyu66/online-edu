package com.czy1344.servicebase.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2020/7/26 9:49
 *
 * @author czy1344
 * 说明：自定义异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OnlineEduException extends RuntimeException{
    public static final Integer SUCCESS = 200;
    public static final Integer ERROR = 400;

    private Integer code;

    private String msg;
}
