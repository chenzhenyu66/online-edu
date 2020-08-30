package com.czy1344.aclservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czy1344.aclservice.entity.Role;
import com.czy1344.aclservice.entity.UserRole;
import com.czy1344.aclservice.mapper.RoleMapper;
import com.czy1344.aclservice.service.RoleService;

import com.czy1344.aclservice.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 *  服务实现类
 * </p>
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 将所有角色    和    用户id对应的角色存入到map中返回
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> findRoleByUserId(String userId) {

        //查询所有的角色
        List<Role> allRolesList =baseMapper.selectList(null);

        //根据用户id，查询用户拥有的角色id
        List<UserRole> existUserRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", userId).select("role_id"));

        List<String> existRoleList = existUserRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());

        //对角色进行分类
        List<Role> assignRoles = new ArrayList<Role>();
        for (Role role : allRolesList) {
            //已分配
            if(existRoleList.contains(role.getId())) {
                assignRoles.add(role);
            }
        }

        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("assignRoles", assignRoles);
        roleMap.put("allRolesList", allRolesList);

        return roleMap;
    }

    /**
     * 根据用户分配角色
     * @param userId
     * @param roleId
     */
    @Override
    public void saveUserRoleRelationShip(String userId, String[] roleId) {
        // 先删除所有的用户id对应的角色
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        userRoleService.remove(wrapper);

        List<UserRole> userRoleList = new ArrayList<>();
        // 遍历加入
        for (String s : roleId) {
            if (StringUtils.isEmpty(s)) continue;
            UserRole role = new UserRole();
            role.setRoleId(s);
            role.setUserId(userId);
            userRoleList.add(role);
        }

        userRoleService.saveBatch(userRoleList);
    }

    @Override
    public List<Role> selectRoleByUserId(String id) {
        //根据用户id拥有的角色id
        List<UserRole> userRoleList = userRoleService.list(new QueryWrapper<UserRole>().eq("user_id", id).select("role_id"));
        List<String> roleIdList = userRoleList.stream().map(item -> item.getRoleId()).collect(Collectors.toList());

        List<Role> roleList = new ArrayList<>();
        if(roleIdList.size() > 0) {
            roleList = baseMapper.selectBatchIds(roleIdList);
        }
        return roleList;
    }
}
