import request from '@/utils/request'

export default {
  getSubjects() {
    return request({
      url: `/eduservice/subjects`,
      method: 'get'
    })
  },
  deleteSubjects() {
    return request({
      url: `/eduservice/subjects`,
      method: 'delete'
    })
  }
}
