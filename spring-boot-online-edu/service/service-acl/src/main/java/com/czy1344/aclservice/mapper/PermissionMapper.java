package com.czy1344.aclservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.czy1344.aclservice.entity.Permission;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 拿到 admin的 type 为 2 的所有权限
     * @return
     */
    List<String> selectAdminPermission();

    /**
     * 拿到其他用户操作权限
     * @param userId
     * @return
     */
    List<String> selectRoleMenu(String userId);

    /**
     * 拿到用户权限通过用户 id
     * @param userId
     * @return
     */
    List<Permission> selectPermissionByUserId(String userId);
}
