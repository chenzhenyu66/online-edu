import request from '@/utils/request'

export default {
  //生成订单信息
  saveOrder(courseId){
    return request({
      url: `/eduusrv/order/createOrder/${courseId}`,
      method: 'post'
    })
  },
  // 根据id获取订单信息接口
  getOrderInfo(orderNo){
    return request({
      url: `/eduusrv/order/getOrder/${orderNo}`,
      method: 'get'
    })
  },
  // 生成订单日志
  createPayInfo(orderNo,price){
    return  request({
      url: `/eduusrv/paylog/createPayInfo/${orderNo}/${price}`,
      method: 'post'
    })
  },
  // 获取订单状态方法
  getOrderStatus(courseId){
    return request({
      url: `/eduusrv/order/getOrderStatus/${courseId}`,
      method: 'get'
    })
  }
}
