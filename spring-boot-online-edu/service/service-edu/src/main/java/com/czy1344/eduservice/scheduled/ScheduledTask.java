package com.czy1344.eduservice.scheduled;

import com.czy1344.eduservice.service.StatisticsDailyService;
import com.czy1344.eduservice.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 2020/8/9 20:09
 *
 * @author czy1344
 * 说明：定时任务
 */
@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService dailyService;

   /* @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("hello world!");
    }*/

    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        dailyService.dailyStatistics(day);
    }
}
