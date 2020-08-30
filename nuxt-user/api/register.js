import request from '@/utils/request'

export default {
  // 手机验证码
  sendCode(phone) {
    return request({
      url: `/eduusrv/msm/send/${phone}`,
      method: 'get'
    })
  },
  // 注册
  registerMember(registerVo) {
    return request({
      url: `/eduusrv/member/register`,
      method: 'post',
      data: registerVo
    })
  }

}
