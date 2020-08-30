import request from '@/utils/request'

export default {
  selectAllChapterInfo(courseId) {
    return request({
      url: `/eduservice/chapters/${courseId}`,
      method: 'get'
    })
  },
  addChapterInfo(eduChapter) {
    return request({
      url: `/eduservice/chapter`,
      method: 'post',
      data: eduChapter
    })
  },
  deleteChapter(id) {
    return request({
      url: `/eduservice/chapter/${id}`,
      method: 'delete'
    })
  },
  updateChapter(eduChapter) {
    return request({
      url: `/eduservice/chapter`,
      method: 'put',
      data: eduChapter
    })
  },
  addVideoInfo(eduVideo) {
    return request({
      url: `/eduservice/video`,
      method: 'post',
      data: eduVideo
    })
  },
  // 更新视频信息
  updateVideoInfo(eduVideo) {
    return request({
      url: `/eduservice/video`,
      method: 'put',
      data: eduVideo
    })
  },
  // 删除Video的信息
  deleteVideoInfo(videoId) {
    return request({
      url: `/eduservice/video/${videoId}`,
      method: 'delete'
    })
  },
  // 删除VoD的视频
  deleteVideoVoDInfo(videoId) {
    return request({
      url: `/eduvod/video/${videoId}`,
      method: 'delete'
    })
  },
  // 拿到章节信息
  selectOneChapter(chapterId) {
    return request({
      url: `/eduservice/chapter/${chapterId}`,
      method: 'get'
    })
  },
  // 拿到视频信息
  selectOneVideo(videoId) {
    return request({
      url: `/eduservice/video/${videoId}`,
      method: 'get'
    })
  }
}
