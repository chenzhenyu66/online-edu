import request from '@/utils/request'

export default{
  addCommentInfo(commentInfo){
    return request({
      url: `/eduusrv/comment/add/comment`,
      method: 'post',
      data:commentInfo
    })
  },
  listComment(current,limit,videoId){
    return request({
      url: `/eduusrv/comment/comments/${current}/${limit}/${videoId}`,
      method: 'get',
    })

  }
}
