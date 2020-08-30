package com.czy1344.aclservice.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.czy1344.aclservice.entity.Role;
import com.czy1344.aclservice.service.RoleService;
import com.czy1344.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 */
@RestController
@RequestMapping("/admin/acl/role")

public class RoleController {
    @Autowired
    private RoleService roleService;
    /**
     *角色分页条件查询显示
     */
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page,
                        @PathVariable Long limit,
                        Role role){

        Page<Role> pageParam = new Page<>(page, limit);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();

        if(!StringUtils.isEmpty(role.getRoleName())) {
            wrapper.like("role_name",role.getRoleName());
        }

        roleService.page(pageParam,wrapper);
        return Result.success().data("items",pageParam.getRecords()).data("total",pageParam.getTotal());
    }

    /**
     * 获取角色信息
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        Role role = roleService.getById(id);
        return Result.success().data("item", role);
    }

    /**
     * 新增角色
     * @param role
     * @return
     */
    @PostMapping("save")
    public Result save(@RequestBody Role role) {
        roleService.save(role);
        return Result.success();
    }

    /**
     * 修改角色
     * @param role
     * @return
     */
    @PutMapping("update")
    public Result updateById(@RequestBody Role role) {
        roleService.updateById(role);
        return Result.success();
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        roleService.removeById(id);
        return Result.success();
    }

    /**
     * 根据列表删除
     * @param idList
     * @return
     */
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        roleService.removeByIds(idList);
        return Result.success();
    }

    /**
     * 根据角色获取权限列表
     */

}

