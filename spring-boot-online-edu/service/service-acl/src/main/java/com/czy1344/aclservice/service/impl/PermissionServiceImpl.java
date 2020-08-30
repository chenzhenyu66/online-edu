package com.czy1344.aclservice.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.czy1344.aclservice.entity.Permission;
import com.czy1344.aclservice.entity.RolePermission;
import com.czy1344.aclservice.entity.User;
import com.czy1344.aclservice.helper.MemuHelper;
import com.czy1344.aclservice.helper.PermissionHelper;
import com.czy1344.aclservice.mapper.PermissionMapper;
import com.czy1344.aclservice.service.PermissionService;
import com.czy1344.aclservice.service.RolePermissionService;
import com.czy1344.aclservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 权限 服务实现类
 * </p>
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserService userService;

    /**
     * 查询所有菜单
     * 并按层级返回
     *
     * @return
     */
    @Override
    public List<Permission> queryAllMenu() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        List<Permission> permissions = baseMapper.selectList(wrapper);

        List<Permission> result = buildPermission(permissions);

        return result;
    }

    private List<Permission> buildPermission(List<Permission> permissions) {

        List<Permission> target = new ArrayList<>();

        for (Permission p : permissions) {
            if ("0".equals(p.getPid())) {
                p.setLevel(1);
                target.add(selectChildren(p, permissions));
            }
        }
        return target;
    }

    private Permission selectChildren(Permission p, List<Permission> permissions) {

        List<Permission> list = new ArrayList<>();

        for (Permission pChild : permissions) {
            if (p.getId().equals(pChild.getPid())) {
                int level = p.getLevel();
                pChild.setLevel(level + 1);

                list.add(selectChildren(pChild, permissions));
            }
        }

        p.setChildren(list);
        return p;
    }


    /**
     * 递归删除某一菜单
     */
    @Override
    public void removePermissionById(String id) {
        List<String> list = new ArrayList<>();
        list.add(id);

        selectChildrenById(id, list);
        baseMapper.deleteBatchIds(list);
    }


    private void selectChildrenById(String id, List<String> list) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", id);
        wrapper.select("id");
        List<Permission> childList = baseMapper.selectList(wrapper);

        // Lamda 表达式
        childList.stream().forEach(item -> {
            list.add(item.getId());
            selectChildrenById(item.getId(), list);
        });
    }


    /**
     * 给角色分配权限
     *
     * @param roleId       角色id
     * @param permissionId 权限数组
     */
    @Override
    public void grantPermissionToRole(String roleId, String[] permissionId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        rolePermissionService.remove(wrapper);
        //删除再添加对io操作有点多，不推荐
        /*QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        wrapper.select("permission_id");
        List<RolePermission> rolePermissions = rolePermissionService.list(wrapper);

        List<String> collect = rolePermissions.stream().map(rp -> rp.getPermissionId()).collect(Collectors.toList());*/

        List<RolePermission> list = new ArrayList<>();

        for (String pmId : permissionId) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(pmId);
            list.add(rolePermission);
        }
        if (list.size() != 0)
            rolePermissionService.saveBatch(list);
    }

    /**
     * 递归给角色授予权限
     *
     * @param roleId
     * @param permissionId
     */
    @Override
    public void grantPermissionToRoleRecursion(String roleId, String[] permissionId) {
        List<String> list = new ArrayList<>();
        List<RolePermission> listRole = new ArrayList<>();

        for (String pmId : permissionId) {
            list.add(pmId);

            selectChildrenById(pmId, list);
        }

        for (String pid : list) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(pid);
            listRole.add(rolePermission);
        }

        rolePermissionService.saveBatch(listRole);
    }

    /**
     * 根据角色获取菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> selectRoleAllMenu(String roleId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        wrapper.select("permission_id");
        List<RolePermission> list = rolePermissionService.list(wrapper);

        List<String> ids = new ArrayList<>();
        for (RolePermission rolePermission : list) {
            ids.add(rolePermission.getPermissionId());
        }
        List<Permission> permissions = null;

        if (ids.size() != 0) {
            permissions = baseMapper.selectBatchIds(ids);
        }

        return permissions;
    }

    /**
     * 根据角色获取菜单(用于给spring security使用)
     * 如果是管理员，授予所有权限
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> selectPermissionValueByUserId(String userId) {
        List<String> permissions = null;
        if (isSysAdmin(userId)) {
            permissions = baseMapper.selectAdminPermission();
        } else {
            permissions = baseMapper.selectRoleMenu(userId);
        }
        return permissions;
    }

    /*
    将用户权限信息转为json，有一说一，写的是个jb
     */
    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if (this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);

        List<JSONObject> result = MemuHelper.bulid(permissionList);

        return result;
    }

    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if (null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

}
