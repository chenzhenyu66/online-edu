<template>
  <div class="app-container" style="padding: 40px">
    <el-steps :active="page">
      <el-step title="基本信息填写" icon="el-icon-edit"/>
      <el-step title="课程大纲" icon="el-icon-upload"/>
      <el-step title="提交审核" icon="el-icon-picture"/>
    </el-steps>
    <el-card class="box-card">
      <img :src="items.cover" style="float: left;height: 300px">
      <div style="float: left;padding-top: 25px;padding-left: 30px;padding-bottom: 25px">
        <div class="text item">
          <b>课程基本信息</b>
        </div><br>
        <div class="text item">
          标题：{{ items.title }}
        </div><br>
        <div class="text item">
          课程描述：<div style="padding-left: 25px" v-html="items.description"/>
        </div><br>
        <div class="text item">
          讲师：{{ items.name }}
        </div><br>
        <div class="text item">
          课程分类：{{ items.subjectParentTitle }}：{{ items.subjectTitle }}
        </div><br>
        <div class="text item">
          课时：{{ items.lessonNum }}
        </div><br>
        <div class="text item">
          价格：{{ items.price }}元
        </div>
      </div>
    </el-card>
    <el-button style="margin-top: 12px;" @click="previous">上一步</el-button>
    <el-button style="margin-top: 12px;" @click="next">发布课程</el-button>
  </div>
</template>

<script>
import course from '@/api/course/course'

export default {
  data() {
    return {
      items: {
        id: '',

        title: '', // 课程名称

        description: '', // 课程描述

        name: '', // 讲师名称

        subjectTitle: '', // 二级分类

        subjectParentTitle: '', // 一级分类

        cover: '', // 封面

        lessonNum: 0, // 课时

        price: 0.0 // 价钱
      },
      page: 3
    }
  },
  created() {
    this.findCourseForPublishById(this.$route.params.id)
  },
  methods: {
    next() {
      console.log(this.items.id)
      course.publishCourseById(this.items.id).then(response => {
        this.$message({
          showClose: true,
          message: '课程发布成功',
          type: 'success'
        })
      })
      this.$router.push({ path: '/course/list/' })
    },
    previous() {
      this.$router.push({ path: '/course/chapter/' + this.items.id })
    },
    findCourseForPublishById(courseId) {
      course.findCourseForPublishById(courseId)
        .then(response => {
          // console.log(response.data)
          // console.log(this.items)
          this.items = response.data.item
          this.items.id = courseId
        })
    }
  }
}
</script>

<style scoped>

</style>
