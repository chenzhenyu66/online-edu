import request from '@/utils/request'

export default {
  // 首页老师信息
  getTeacherToList(){
    return request({
      url: `/eduservice/frontteacher/teacher`,
      method: 'get'
    })
  },
  // 老师页信息展示 , 并分页
  pageListTeacher(current,limit) {
    return request({
      url: `/eduservice/frontteacher/teachers/${current}/${limit}`,
      method: 'get'
    })
  },
  // 获取讲师信息
  getTeacherAndCourseInfo(id){
    return request({
      url: `/eduservice/frontteacher/teacher/${id}`,
      method: 'get'
    })
  }
}
