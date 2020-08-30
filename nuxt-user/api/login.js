import request from '@/utils/request'

export default {
  // 登录
  toLogin(loginInfo){
    return request({
      url: `/eduusrv/member/login`,
      method: 'post',
      data: loginInfo
    })
  },
  // 根据token获取用户登录信息
  getUserInfo(){
    return request({
      url: `/eduusrv/member/getUserInfo`,
      method: 'get'
    })
  }
}
