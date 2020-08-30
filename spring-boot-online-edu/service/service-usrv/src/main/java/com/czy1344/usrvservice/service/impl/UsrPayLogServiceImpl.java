package com.czy1344.usrvservice.service.impl;

import com.czy1344.usrvservice.entity.UsrPayLog;
import com.czy1344.usrvservice.mapper.UsrPayLogMapper;
import com.czy1344.usrvservice.service.UsrPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author czy1344
 * @since 2020-08-07
 */
@Service
public class UsrPayLogServiceImpl extends ServiceImpl<UsrPayLogMapper, UsrPayLog> implements UsrPayLogService {

    @Override
    public Map createNative(String orderNo) {
        // 模拟微信支付接口
        System.out.println("正在生成微信支付二维码");
        return null;
    }
}
