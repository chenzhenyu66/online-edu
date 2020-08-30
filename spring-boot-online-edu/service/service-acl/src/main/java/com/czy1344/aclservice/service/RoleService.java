package com.czy1344.aclservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.czy1344.aclservice.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 */
public interface RoleService extends IService<Role> {


    Map<String, Object> findRoleByUserId(String userId);

    void saveUserRoleRelationShip(String userId, String[] roleId);

    /**
     * 通过userId拿到所有的 对应的角色
     * @param id
     * @return
     */
    List<Role> selectRoleByUserId(String id);
}
