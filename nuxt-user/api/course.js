import request from '@/utils/request'

export default {
  // 拿到首页的课程信息
  getCourseToList(){
    return request({
      url: `/eduservice/frontcourse/course`,
      method: 'get'
    })
  },
  // 拿到课程页的信息
  getAllCourseToPage(current,limit,conditionInfo){
    return request({
      url: `/eduservice/frontcourse/courses/${current}/${limit}`,
      method: 'post',
      data: conditionInfo
    })
  },
  // 拿到课程界面的分类信息
  getSubjectAsList() {
    return request({
      url: `/eduservice/frontcourse/subject`,
      method: 'get'
    })
  },
  // 课程详情数据显示
  selectInfoWebById(id){
    return request({
      url: `/eduservice/frontcourse/course/${id}`,
      method: 'get'
    })
  },
  selectChapterInfoById(id){
    return request({
      url: `/eduservice/frontcourse/chapter/${id}`,
      method: 'get'
    })
  }

}
