package com.czy1344.eduservice.controller;


import com.czy1344.commonutils.Result;
import com.czy1344.eduservice.client.StsClient;
import com.czy1344.eduservice.entity.vo.StatisticsDailyVo;
import com.czy1344.eduservice.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author czy1344
 * @since 2020-08-09
 */
@RestController
@RequestMapping("/eduservice/statistics")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService dailyService;

    /**
     * 生成每日数据
     * @param day
     * @return
     */

    @GetMapping("/statistics/{day}")
    public Result dailyStatistics(@PathVariable String day){
        dailyService.dailyStatistics(day);

        return Result.success();
    }

    /**
     * 获取近5天的数据，返回给后台显示
     */
    @GetMapping("/statisticsToAdmin/{size}")
    public Result StatisticsToAdmin(@PathVariable("size") Integer size){
        Map<String,Object> map = dailyService.StatisticsToAdmin(size);
        return Result.success().data(map);
    }

}

