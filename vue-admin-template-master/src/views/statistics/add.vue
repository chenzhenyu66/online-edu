<template>
  <el-form ref="form" label-width="80px">
    <el-form-item label="日期" style="margin-top: 20px">
      <el-col :span="11">
        <el-date-picker v-model="day" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width: 100%;"/>
      </el-col>
    </el-form-item>
    <el-form-item>
      <el-button v-if="hasPerm('daily.add')" type="primary" @click="onCreate()" >立即生成</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import statistics from '@/api/statistics/statistics'
export default {
  data() {
    return {
      day: ''
    }
  },
  created() {
  },
  methods: {
    onCreate() {
      statistics.dailyStatistics(this.day).then(response => {
        if (response.code === 200) {
          this.$message({
            showClose: true,
            message: '生成数据成功',
            type: 'success'
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
