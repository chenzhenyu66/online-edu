import request from '@/utils/request'

export default {
  addCourseInfo(courseInfoVo) {
    return request({
      url: `/eduservice/course`,
      method: 'post',
      data: courseInfoVo
    })
  },
  listTeacher() {
    return request({
      url: `/eduservice/teachers`,
      method: 'get'
    })
  },
  findCourseById(id) {
    return request({
      url: `/eduservice/course/${id}`,
      method: 'get'
    })
  },
  updateCourseInfo(courseInfoVo) {
    return request({
      url: `/eduservice/course/`,
      method: 'put',
      data: courseInfoVo
    })
  },
  findCourseForPublishById(courseId) {
    return request({
      url: `/eduservice/courseInfo/${courseId}`,
      method: 'get'
    })
  },
  publishCourseById(courseId) {
    return request({
      url: `/eduservice/coursePublish/${courseId}`,
      method: 'put'
    })
  }
}
