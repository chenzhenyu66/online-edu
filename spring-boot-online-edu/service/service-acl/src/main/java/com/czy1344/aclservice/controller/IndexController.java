package com.czy1344.aclservice.controller;


import com.alibaba.fastjson.JSONObject;
import com.czy1344.aclservice.service.IndexService;
import com.czy1344.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户登录时处理token，
 * 查找获取权限菜单
 * 退出处理
 *
 * 与spring security 对接的地方
 */
@RestController
@RequestMapping("/admin/acl/index")
public class IndexController {
    @Autowired
    private IndexService indexService;

    /**
     *
     * @return
     */
    @GetMapping("info")
    public Result info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return Result.success().data(userInfo);
    }

    /*
     * 获取菜单
     * @return
     */
    @GetMapping("menu")
    public Result getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return Result.success().data("permissionList", permissionList);
    }

    @PostMapping("logout")
    public Result logout(){
        return Result.success();
    }
}
