package com.czy1344.usrvservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy1344.commonutils.JwtUtils;
import com.czy1344.commonutils.Result;
import com.czy1344.usrvservice.entity.UsrOrder;
import com.czy1344.usrvservice.service.UsrOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author czy1344
 * @since 2020-08-07
 */
@RestController
@RequestMapping("/eduusrv/order")
public class UsrOrderController {
    @Autowired
    private UsrOrderService usrOrderService;

    /**
     * 生成订单信息
     *
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("createOrder/{courseId}")
    public Result save(@PathVariable String courseId, HttpServletRequest request) {
        String orderId = usrOrderService.saveOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return Result.success().data("orderId", orderId);
    }

    /**
     * 根据id获取订单信息接口
     *
     * @param orderNo
     * @return
     */
    @GetMapping("getOrder/{orderNo}")
    public Result get(@PathVariable String orderNo) {
        QueryWrapper<UsrOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);

        UsrOrder order = usrOrderService.getOne(wrapper);
        return Result.success().data("item", order);
    }

    /**
     * 查询订单支付状态
     */
    @GetMapping("getOrderStatus/{courseId}")
    public Result getStatus(@PathVariable String courseId, HttpServletRequest request) {
        boolean status = usrOrderService.getStatus(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return Result.success().data("status",status);
    }

}

