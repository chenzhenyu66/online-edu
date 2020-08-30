package com.czy1344.usrvservice.controller;


import com.czy1344.commonutils.JwtUtils;
import com.czy1344.commonutils.Result;
import com.czy1344.usrvservice.entity.UsrvMember;
import com.czy1344.usrvservice.entity.vo.LoginVo;
import com.czy1344.usrvservice.entity.vo.RegisterVo;
import com.czy1344.usrvservice.service.UsrvMemberService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author czy1344
 * @since 2020-08-05
 */
@RestController
@RequestMapping("/eduusrv/member")
public class UsrvMemberController {
    @Autowired
    private UsrvMemberService usrvMemberService;

    /**
     * 注册
     * 注册信息包括，
     * 手机号，密码，验证码，用户名
     */
    @PostMapping("/register")
    public Result toRegister(@RequestBody RegisterVo registerVo) {
        usrvMemberService.toRegister(registerVo);

        return Result.success().message("恭喜，o(∩_∩)o 注册成功！");
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public Result toLogin(@RequestBody LoginVo loginVo){
        String token = usrvMemberService.toLogin(loginVo);
        return Result.success().data("token",token);
    }

    /**
     * 根据token 获取用户信息
     */
    @GetMapping("/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        // 用jwt工具拿到token信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库根据id拿到用户信息
        UsrvMember member = usrvMemberService.getById(memberId);

        return Result.success().data("userInfo",member);
    }

    /**
     * 统计每日注册人数
     */
    @GetMapping("register/{day}")
    public Result registerCount(@PathVariable String day){
        Integer count = usrvMemberService.countRegisterByDay(day);
        return Result.success().data("countRegister",count);
    }


}

