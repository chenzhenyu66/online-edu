package com.czy1344.aclservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.aclservice.entity.User;
import com.czy1344.aclservice.service.RoleService;
import com.czy1344.aclservice.service.UserService;
import com.czy1344.commonutils.MD5;
import com.czy1344.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 用户表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/admin/acl/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * 管理用户分页显示
     */
    @GetMapping("/{current}/{limit}")
    public Result index(@PathVariable Long current,
                        @PathVariable Long limit,
                        User user) {
        Page<User> page = new Page<>(current, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(user.getUsername())) {
            wrapper.like("username",user.getUsername());
        }
        userService.page(page,wrapper);

        return Result.success().data("items",page.getRecords()).data("total",page.getTotal());
    }

    /**
     * 增加用户
     */
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.save(user);
        return Result.success();
    }

    /**
     *删除用户
     */
    @DeleteMapping("/delete/{userId}")
    public Result delete(@PathVariable String userId){
        userService.removeById(userId);
        return Result.success();
    }

    /**
     * 批量删除用户
     */
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> list){
        userService.removeByIds(list);
        return Result.success();
    }

    /**
     * 修改用户数据
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user){
        user.setPassword(MD5.encrypt(user.getPassword()));
        userService.updateById(user);
        return Result.success();
    }
    /**
     * 获取用户信息
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id){
        User user = userService.getById(id);
        user.setPassword("");
        return Result.success().data("item",user);
    }

    /**
     * 获取角色数据
     */
    @GetMapping("/toAssign/{userId}")
    public Result doAssign(@PathVariable String userId){
        Map<String, Object> roleMap = roleService.findRoleByUserId(userId);
        return Result.success().data(roleMap);
    }

    /**
     * 根据用户分配角色
     */
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam String userId,
                           @RequestParam String[] roleId) {
        roleService.saveUserRoleRelationShip(userId,roleId);
        return Result.success();
    }
}

