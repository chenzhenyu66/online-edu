package com.czy1344.aclservice.controller;


import com.czy1344.aclservice.entity.Permission;
import com.czy1344.aclservice.service.PermissionService;
import com.czy1344.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 */
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 递归
     * 查询所有菜单
     * @return
     */
    @GetMapping("/menu")
    public Result indexAllPermission() {
        List<Permission> list = permissionService.queryAllMenu();
        return Result.success().data("item", list);
    }

    /**
     * 递归删除菜单
     */
    @DeleteMapping("/menu/{id}")
    public Result removePermissionById(@PathVariable("id") String id) {
        permissionService.removePermissionById(id);
        return Result.success();
    }

    /**
     * 给角色授予权限
     */
    @PostMapping("/grantPermission")
    public Result grantPermissionToRole(String roleId, String[] permissionId) {
        permissionService.grantPermissionToRole(roleId, permissionId);
        return Result.success();
    }

    /**
     * 递归给角色授予权限
     */
    @PostMapping("/grantPermissionRecursion")
    public Result grantPermissionToRoleRecursion(String roleId, String[] permissionId) {
        permissionService.grantPermissionToRoleRecursion(roleId, permissionId);
        return Result.success();
    }

    /**
     * 根据角色获取菜单
     * @param roleId
     * @return
     */
    @GetMapping("/selectPermission/{roleId}")
    public Result toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectRoleAllMenu(roleId);
        return Result.success().data("children", list);
    }

    /**
     * 新增菜单
     * @param permission
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.success();
    }

    /**
     * 修改菜单
     * @param permission
     * @return
     */
    @PutMapping("update")
    public Result updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.success();
    }
}

