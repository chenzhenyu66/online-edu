<template>
  <div class="dashboard-container">
    <el-card class="box-card">
      <div style="float: left">
        <img :src="avatar" height="230px">
      </div>
      <div style="float: left;margin-left: 20px;margin-top: 20px">
        <div class="dashboard-text">账号：{{ name }}</div>
        <div class="dashboard-text">您的身份为：<span v-for="role in roles" :key="role">{{ role }}</span></div>
        <br>
        <div class="dashboard-text">欢迎来到在线教育后台管理系统</div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name',
      'roles',
      'avatar'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .dashboard {
    &-container {
      margin: 30px;
    }

    &-text {
      font-size: 30px;
      line-height: 46px;
    }
  }
</style>
