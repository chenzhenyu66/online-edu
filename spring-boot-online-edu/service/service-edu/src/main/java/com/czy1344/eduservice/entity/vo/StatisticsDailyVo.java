package com.czy1344.eduservice.entity.vo;


import lombok.Data;

/**
 * 2020/8/10 9:12
 *
 * @author czy1344
 * 说明：
 */
@Data
public class StatisticsDailyVo {

    private String[] days;

    private Integer[] registerNum;

    private Integer[] loginNum;

    private Integer[] videoViewNum;

    private Integer[] courseNum;
}
