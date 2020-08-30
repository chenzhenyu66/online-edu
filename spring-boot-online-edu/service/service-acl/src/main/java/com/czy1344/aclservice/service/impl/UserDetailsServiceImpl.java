package com.czy1344.aclservice.service.impl;


import com.czy1344.aclservice.entity.User;
import com.czy1344.aclservice.service.PermissionService;
import com.czy1344.aclservice.service.UserService;
import com.czy1344.serurity.entity.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 自定义userDetailsService - 认证用户详情
 * </p>
 *
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final PermissionService permissionService;
    @Autowired
    public UserDetailsServiceImpl(UserService userService, PermissionService permissionService) {
        this.userService = userService;
        this.permissionService = permissionService;
    }

    /**
     * 查询username的用户，并查询权限列表
     * 将权限列表交给  spring security
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 根据用户名查询用户
        User user = userService.selectByUsername(username);

        com.czy1344.serurity.entity.User safeUser = new com.czy1344.serurity.entity.User();
        BeanUtils.copyProperties(user,safeUser);

        // 查询权限列表
        List<String> authorities = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser(safeUser);

        securityUser.setPermissionValueList(authorities);

        return securityUser;
    }
}
