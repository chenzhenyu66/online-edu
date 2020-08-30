package com.czy1344.usrvservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy1344.commonutils.Result;
import com.czy1344.usrvservice.entity.UsrOrder;
import com.czy1344.usrvservice.entity.UsrPayLog;
import com.czy1344.usrvservice.service.UsrOrderService;
import com.czy1344.usrvservice.service.UsrPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author czy1344
 * @since 2020-08-07
 */
@RestController
@RequestMapping("/eduusrv/paylog")
public class UsrPayLogController {

    @Autowired
    private UsrOrderService usrOrderService;
    @Autowired
    private UsrPayLogService usrPayLogService;
    /**
     * 生成二维码接口，这里不进行实现，因为需要公众号等商户   条件，没有这些东西，不实现了
     */
    @GetMapping("/createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo) {
        Map map = usrPayLogService.createNative(orderNo);

        return Result.success().data(map);
    }

    /**
     * 假设已经支付成功，我们生成订单信息
     * 并且将订单支付状态设置为      已支付
     */
    @PostMapping("createPayInfo/{orderNo}/{price}")
    public Result createPayInfo(@PathVariable("orderNo") String orderNo,
                                @PathVariable("price") String price){
        // 模拟订单日志信息
        UsrPayLog payLog = new UsrPayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setPayType(1);
        payLog.setTradeState("success");
        payLog.setTotalFee(new BigDecimal(price));
        usrPayLogService.save(payLog);

        // 将支付信息设置为已支付
        UsrOrder order = new UsrOrder();
        order.setStatus(1);
        QueryWrapper<UsrOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        usrOrderService.update(order,wrapper);
        return Result.success();
    }
}

