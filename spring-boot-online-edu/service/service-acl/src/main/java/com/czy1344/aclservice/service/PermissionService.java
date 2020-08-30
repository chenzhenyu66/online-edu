package com.czy1344.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.czy1344.aclservice.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> queryAllMenu();

    void removePermissionById(String id);

    void grantPermissionToRole(String roleId, String[] permissionId);

    void grantPermissionToRoleRecursion(String roleId, String[] permissionId);

    List<Permission> selectRoleAllMenu(String roleId);

    List<String> selectPermissionValueByUserId(String userId);

    List<JSONObject> selectPermissionByUserId(String userId);
}
