import request from '@/utils/request'

export default {
  getTeacherListPage(current, limit, queryTeacher) {
    return request({
      // url: '/eduservice/teacherCondition/{current}/{limit}',
      url: `/eduservice/teacherCondition/${current}/${limit}`,
      method: 'post',
      // 传输结果为json
      data: queryTeacher
    })
  },
  deleteTeacher(id) {
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'delete'
    })
  },
  insertOne(teacher) {
    return request({
      url: `/eduservice/teacher`,
      method: 'post',
      data: teacher
    })
  },
  updateOne(teacher) {
    return request({
      url: `/eduservice/teacher`,
      method: 'put',
      data: teacher
    })
  },
  getTeacherById(id) {
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'get',
      data: id
    })
  },
  deleteOssFile(url) {
    return request({
      url: `/eduoss/file/delete`,
      method: 'post',
      data: url
    })
  }
}
