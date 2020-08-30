package com.czy1344.usrvservice.service;

import com.czy1344.usrvservice.entity.UsrOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author czy1344
 * @since 2020-08-07
 */
public interface UsrOrderService extends IService<UsrOrder> {

    String saveOrder(String courseId, String memberIdByJwtToken);

    /**
     * 查询订单支付信息
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    boolean getStatus(String courseId, String memberIdByJwtToken);
}
