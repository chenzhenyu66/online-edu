package com.czy1344.usrvservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.czy1344.commonutils.vo.CourseInfoVo;
import com.czy1344.usrvservice.client.EduClient;
import com.czy1344.usrvservice.entity.UsrOrder;
import com.czy1344.usrvservice.entity.UsrvMember;
import com.czy1344.usrvservice.mapper.UsrOrderMapper;
import com.czy1344.usrvservice.service.UsrOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czy1344.usrvservice.service.UsrvMemberService;
import com.czy1344.usrvservice.utils.OrderNoUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author czy1344
 * @since 2020-08-07
 */
@Service
public class UsrOrderServiceImpl extends ServiceImpl<UsrOrderMapper, UsrOrder> implements UsrOrderService {

    @Autowired
    private UsrvMemberService usrvMemberService;

    @Autowired
    private UsrOrderService usrOrderService;

    @Autowired
    private EduClient eduClient;

    @Override
    public String saveOrder(String courseId, String memberIdByJwtToken) {
        // 先查询是否已经有了订单信息,有的话直接返回即可
        QueryWrapper<UsrOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberIdByJwtToken);
        wrapper.select("order_no");
        UsrOrder one = usrOrderService.getOne(wrapper);
        if (one != null){
            return one.getOrderNo();
        }

        // ---------------------------------------------------------------
        // 获得课程信息
        CourseInfoVo info = eduClient.selectCourseInfoToUsr(courseId);
        // 获得用户信息
        UsrvMember member = usrvMemberService.getById(memberIdByJwtToken);

        // 创建订单
        UsrOrder order = new UsrOrder();
        // ----------------------------课程信息----------------------------
        BeanUtils.copyProperties(info, order);
        // ----------------------------用户信息----------------------------
        order.setMobile(member.getMobile());
        // 会员id
        order.setMemberId(member.getId());
        order.setNickname(member.getNickname());
        // ----------------------------订单信息----------------------------
        // 订单号
        order.setOrderNo(OrderNoUtil.getOrderNo());
        //支付类型
        order.setPayType(1);
        //支付状态
        order.setStatus(0);
        usrOrderService.save(order);
        return order.getOrderNo();
    }

    /**
     * 查询订单支付状态
     *
     * @param courseId
     * @param memberIdByJwtToken
     * @return
     */
    @Override
    public boolean getStatus(String courseId, String memberIdByJwtToken) {
        QueryWrapper<UsrOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberIdByJwtToken);
        wrapper.select("status");
        UsrOrder one = usrOrderService.getOne(wrapper);
        // System.out.println(one);
        if (one == null || one.getStatus() == 0)
            return false;
        return true;
    }
}
