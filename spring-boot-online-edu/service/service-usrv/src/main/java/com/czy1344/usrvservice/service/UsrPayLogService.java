package com.czy1344.usrvservice.service;

import com.czy1344.usrvservice.entity.UsrPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author czy1344
 * @since 2020-08-07
 */
public interface UsrPayLogService extends IService<UsrPayLog> {

    Map createNative(String orderNo);
}
