<template>
  <div class="app-container" style="padding: 15px">
    <el-steps :active="page">
      <el-step title="基本信息填写" icon="el-icon-edit"/>
      <el-step title="课程大纲" icon="el-icon-upload"/>
      <el-step title="提交审核" icon="el-icon-picture"/>
    </el-steps>
    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。"/>
      </el-form-item>

      <el-form-item label="选择讲师">
        <el-select v-model="courseInfo.teacherId" filterable placeholder="请选择">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.name"
            :value="item.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="课程分类">
        <el-select v-model="courseInfo.subjectParentId" filterable placeholder="请选择" @change="findChildren">
          <el-option
            v-for="item in subjectParentOptions"
            :key="item.value"
            :label="item.title"
            :value="item.id"
          />
        </el-select>
        <el-select ref="childSelect" v-model="courseInfo.subjectId" placeholder="请选择">
          <el-option
            v-for="item in subjectChildrenOptions"
            :key="item.value"
            :label="item.title"
            :value="item.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数" style="width: 23%"/>
      </el-form-item>

      <!-- <el-form-item label="简介" prop="description">
        <el-input v-model="courseInfo.description" type="textarea" style="width: 80%"/>
      </el-form-item>-->

      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>

      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API+'/eduoss/file'"
          class="avatar-uploader">
          <img :src="courseInfo.cover" style="height: 300px">
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格" >
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元" style="width: 25%"/> 元
      </el-form-item>

      <el-form-item>
        <el-button v-if="hasPerm('course.save')" style="margin-top: 12px;" @click="addCourseInfo">下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import course from '@/api/course/course'
import subject from '@/api/subject/subject'
import Tinymce from '@/components/Tinymce'

export default {
  components: { Tinymce },
  data() {
    return {
      courseInfo: {
        id: '',
        title: null,
        subjectId: '',
        teacherId: '',
        subjectParentId: '', // 一级目录
        lessonNum: 0,
        description: '',
        cover: '',
        price: 0
      },
      page: 1,
      options: [],
      subjectParentOptions: [],
      subjectChildrenOptions: [],
      BASE_API: process.env.BASE_API
    }
  },
  watch: {
    $route(to, from) {
      // console.log('watch $route')
      this.init()
    }
  },
  created() {
    // 无论回显还是添加都需要茶道teacher和subject
    this.listTeacher()
    this.listParentSubject()
    // 回显数据
    this.init()
  },
  methods: {
    handleAvatarSuccess(res, file) {
      console.log(res)// 上传响应
      console.log(URL.createObjectURL(file.raw))// base64编码
      this.courseInfo.cover = res.data.url
    },

    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 4

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 4MB!')
      }
      return isJPG && isLt2M
    },
    findChildren(id) {
      for (let i = 0; i < this.subjectParentOptions.length; i++) {
        if (this.subjectParentOptions[i].id === id) {
          this.subjectChildrenOptions = this.subjectParentOptions[i].children
          this.courseInfo.subjectId = ''
          break
        }
      }
    },
    listParentSubject() {
      subject.getSubjects().then(response => {
        this.subjectParentOptions = response.data.items
      })
    },
    findCourseById() {
      course.findCourseById(this.$route.params.id)
        .then(response => {
          this.courseInfo = response.data.item
          for (let i = 0; i < this.subjectParentOptions.length; i++) {
            if (this.subjectParentOptions[i].id === this.courseInfo.subjectParentId) {
              this.subjectChildrenOptions = this.subjectParentOptions[i].children
              break
            }
          }
        })
    },
    listTeacher() {
      course.listTeacher()
        .then(response => {
          this.options = response.data.items
        })
    },
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.findCourseById(this.$route.params.id)
      } else {
        this.courseInfo = {
          id: '',
          title: null,
          subjectId: '',
          teacherId: '', // 二级目录
          subjectParentId: '', // 一级目录
          lessonNum: 0,
          description: '',
          cover: 'https://edu-online1024.oss-cn-beijing.aliyuncs.com/2020/08/02/dfe31d1c-fd86-4921-90f3-a93ae04344e21.jpg',
          price: 0
        }
      }
    },
    addCourseInfo() {
      if (this.$route.params && this.$route.params.id) {
        // 更新
        this.courseInfo.id = this.$route.params.id
        course.updateCourseInfo(this.courseInfo)
          .then(response => {
            // 跳转
            this.$router.push({ path: '/course/chapter/' + response.data.id })
          })
      } else {
        // 添加
        course.addCourseInfo(this.courseInfo)
          .then(response => {
            this.$message({
              showClose: true,
              message: '插入数据库成功',
              type: 'success'
            })
            // 跳转
            this.$router.push({ path: '/course/chapter/' + response.data.id })
          })
      }
    }
  }
}
</script>

<style scoped>

</style>
