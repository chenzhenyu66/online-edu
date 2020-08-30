package com.czy1344.eduservice.service;

import com.czy1344.eduservice.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czy1344.eduservice.entity.vo.StatisticsDailyVo;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author czy1344
 * @since 2020-08-09
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {


    void dailyStatistics(String day);

    Map<String,Object> StatisticsToAdmin(int size);
}
