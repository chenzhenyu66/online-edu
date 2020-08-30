package com.czy1344.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy1344.commonutils.Result;
import com.czy1344.eduservice.client.StsClient;
import com.czy1344.eduservice.entity.StatisticsDaily;
import com.czy1344.eduservice.entity.vo.StatisticsDailyVo;
import com.czy1344.eduservice.mapper.StatisticsDailyMapper;
import com.czy1344.eduservice.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czy1344.eduservice.utils.DateUtil;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author czy1344
 * @since 2020-08-09
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private StsClient stsClient;


    @Override
    public void dailyStatistics(String day) {
        // 如果有这天的数据，我们删除当天数据，插入最新数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        StatisticsDaily daily = new StatisticsDaily();
        Result result = stsClient.registerCount(day);
        Integer count = (Integer) result.getData().get("countRegister");
        daily.setDateCalculated(day); // 日期
        daily.setRegisterNum(count); // 注册数
        // 以下数据用数据模拟实现
        daily.setCourseNum(RandomUtils.nextInt(10, 50));  // 上传课程数
        daily.setLoginNum(RandomUtils.nextInt(100, 300));  // 登录数
        daily.setVideoViewNum(RandomUtils.nextInt(100, 300)); //观看视频数
        baseMapper.insert(daily);
    }

    @Override
    public Map<String, Object> StatisticsToAdmin(int size) {
        Map<String, Object> map = new HashMap<>();

        String[] days = new String[size];
        Integer[] registerNum = new Integer[size];
        Integer[] loginNum = new Integer[size];
        Integer[] videoViewNum = new Integer[size];
        Integer[] courseNum = new Integer[size];

        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("date_calculated");
        wrapper.last("limit "+size);
        List<StatisticsDaily> list = baseMapper.selectList(wrapper);

        for (int i = list.size() - 1, j = 0; i > -1; i--, j++) {
            StatisticsDaily daily = list.get(i);

            days[j] = daily.getDateCalculated();
            registerNum[j] = daily.getRegisterNum();
            loginNum[j] = daily.getLoginNum();
            videoViewNum[j] = daily.getVideoViewNum();
            courseNum[j] = daily.getCourseNum();
        }
        map.put("days",days);
        map.put("registerNum",registerNum);
        map.put("loginNum",loginNum);
        map.put("videoViewNum",videoViewNum);
        map.put("courseNum",courseNum);

        return map;
    }
}
