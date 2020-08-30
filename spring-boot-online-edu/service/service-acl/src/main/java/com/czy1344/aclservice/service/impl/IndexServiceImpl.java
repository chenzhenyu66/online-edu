package com.czy1344.aclservice.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.czy1344.aclservice.entity.Role;
import com.czy1344.aclservice.entity.User;
import com.czy1344.aclservice.service.IndexService;
import com.czy1344.aclservice.service.PermissionService;
import com.czy1344.aclservice.service.RoleService;
import com.czy1344.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class IndexServiceImpl implements IndexService {

    private final UserService userService;
    private final PermissionService permissionService;
    private final RoleService roleService;
    private final RedisTemplate redisTemplate;
    @Autowired
    public IndexServiceImpl(UserService userService,
                            PermissionService permissionService,RoleService roleService,RedisTemplate redisTemplate) {
        this.userService = userService;
        this.permissionService = permissionService;
        this.roleService = roleService;
        this.redisTemplate = redisTemplate;
    }


    @Override
    public Map<String, Object> getUserInfo(String username) {
        Map<String, Object> result = new HashMap<>();
        User user = userService.selectByUsername(username);
        if (null == user) {
            //throw new GuliException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }

        //根据用户id获取角色
        List<Role> roleList = roleService.selectRoleByUserId(user.getId());
        // 将 List<Role> 转换为只有  RoleName的  List<String>
        List<String> roleNameList = roleList.stream().map(item -> item.getRoleName()).collect(Collectors.toList());
        if(roleNameList.size() == 0) {
            //前端框架必须返回一个角色，否则报错，如果没有角色，返回一个空角色
            roleNameList.add("游客");
        }

        //根据用户id获取操作权限值
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        redisTemplate.opsForValue().set(username, permissionValueList);

        result.put("name", user.getUsername());
        result.put("avatar", "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1236311331,334986194&fm=26&gp=0.jpg");
        result.put("roles", roleNameList);
        result.put("permissionValueList", permissionValueList);

        return result;
    }

    @Override
    public List<JSONObject> getMenu(String username) {
        User user = userService.selectByUsername(username);

        //根据用户id获取用户菜单权限
        List<JSONObject> permissionList = permissionService.selectPermissionByUserId(user.getId());
        return permissionList;

    }
}
