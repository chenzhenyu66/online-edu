<template>
  <div align="center">
    <div v-if="hasPerm('daily.list')" id="main" style="width:900px;height:600px;"/>
  </div>
</template>

<script>
import statistics from '@/api/statistics/statistics'

export default {
  data() {
    return {
      days: [],
      registerNum: [],
      loginNum: [],
      videoViewNum: [],
      courseNum: []
    }
  },
  // days: [],
  // registerNum: [],
  // loginNum: [],
  // videoViewNum: [],
  // courseNum: []

  created() {
    // this.testA()
  },
  mounted() {
    // this.testA()
    this.initData()
  },
  methods: {
    initData() {
      statistics.statisticsForAdmin(7).then(response => {
        this.days = response.data.days
        this.registerNum = response.data.registerNum
        this.loginNum = response.data.loginNum
        this.videoViewNum = response.data.videoViewNum
        this.courseNum = response.data.courseNum
        this.testA()
      })
    },
    testA() {
      var echarts = require('echarts')

      // 基于准备好的dom，初始化echarts实例
      var myChart = echarts.init(document.getElementById('main'))

      var option = {
        title: {
          text: '网站数据堆叠图'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          data: ['注册人数', '登录人数', '每日播放量', '每日新增视频']
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: this.days
            // data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '注册人数',
            type: 'line',
            stack: '总量',
            areaStyle: {},
            data: this.registerNum
            // data: [120, 132, 101, 134, 90, 230, 210]
          },
          {
            name: '登录人数',
            type: 'line',
            stack: '总量',
            areaStyle: {},
            data: this.loginNum
            // data: [220, 182, 191, 234, 290, 330, 310]
          },
          {
            name: '每日播放量',
            type: 'line',
            stack: '总量',
            areaStyle: {},
            data: this.videoViewNum
            // data: [150, 232, 201, 154, 190, 330, 410]
          },
          {
            name: '每日新增视频',
            type: 'line',
            stack: '总量',
            areaStyle: {},
            data: this.courseNum
          }
        ]
      }
      // 绘制图表
      myChart.setOption(option)
    }
  }
}
</script>

<style scoped>

</style>
