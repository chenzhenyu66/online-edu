package com.czy1344.usrvservice.service;

import com.czy1344.usrvservice.entity.UsrvMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czy1344.usrvservice.entity.vo.LoginVo;
import com.czy1344.usrvservice.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author czy1344
 * @since 2020-08-05
 */
public interface UsrvMemberService extends IService<UsrvMember> {

    void toRegister(RegisterVo registerVo);

    String toLogin(LoginVo loginVo);

    Integer countRegisterByDay(String day);
}
