import request from '@/utils/request'
export default {
  findCoursesForList(page, size) {
    return request({
      url: `/eduservice/courseInfos/${page}/${size}`,
      method: 'get'
    })
  },
  deleteCourseInfoInList(courseId) {
    return request({
      url: `/eduservice/courseInfo/${courseId}`,
      method: 'delete'
    })
  }
}
