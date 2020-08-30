import request from '@/utils/request'

const api_name = '/admin/acl/permission'

export default {
  getNestedTreeList() {
    return request({
      url: `${api_name}/menu`,
      method: 'get'
    })
  },
  removeById(id) {
    return request({
      url: `${api_name}/menu/${id}`,
      method: 'delete'
    })
  },
  saveLevelOne(menu) {
    return request({
      url: `${api_name}/save`,
      method: 'post',
      data: menu
    })
  },
  update(menu) {
    return request({
      url: `${api_name}/update`,
      method: 'put',
      data: menu
    })
  },
  // 根据角色获取菜单
  toAssign(roleId) {
    return request({
      url: `${api_name}/selectPermission/${roleId}`,
      method: 'get'
    })
  },
  //  给角色授予权限
  doAssign(roleId, permissionId) {
    return request({
      url: `${api_name}/grantPermission`,
      method: 'post',
      params: { roleId, permissionId }
    })
  }
}
