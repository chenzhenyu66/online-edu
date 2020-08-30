import request from '@/utils/request'

export default {
  dailyStatistics(day) {
    return request({
      url: `/eduservice/statistics/statistics/${day}`,
      method: 'get'
    })
  },
  statisticsForAdmin(size) {
    return request({
      url: `/eduservice/statistics/statisticsToAdmin/${size}`,
      method: 'get'
    })
  }
}
