<template>
  <div class="app-container">
    <el-form ref="ruleForm" :model="teacher" :rules="rules" label-width="100px" class="demo-ruleForm">
      <el-form-item label="姓名" prop="name" placeholder="例如：张三">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="资历" prop="career" placeholder="资历,一句话说明讲师">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="排序">
        <el-input-number v-model="teacher.sort" :min="0" :max="10" controls-position="right"/>
      </el-form-item>
      <el-form-item label="头衔" prop="level">
        <el-select v-model="teacher.level" placeholder="请选择头衔">
          <el-option :value="levelNum.one" label="高级讲师"/>
          <el-option :value="levelNum.two" label="首席讲师"/>
        </el-select>
      </el-form-item>
      <el-form-item label="简介" prop="intro">
        <el-input v-model="teacher.intro" type="textarea"/>
      </el-form-item>
      <el-form-item label="上传头像">
        <el-upload
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          :on-preview="handlePreview"
          :limit="1"
          :file-list="picList"
          :show-file-list="true"
          :action= "base_api + '/eduoss/file'"
          class="avatar-uploader"
          list-type="picture-card"
          style="width: 35%">
          <i class="el-icon-plus"/>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-form-item>
      <el-form-item>
        <el-button v-if="hasPerm('teacher.add')":disabled="saveBtnDisabled" type="primary" @click="submitForm()">保存</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teachers from '@/api/teacher/teacher'

export default {
  data() {
    return {
      teacher: {
        id: null,
        name: '',
        sort: 0,
        level: '',
        career: '',
        intro: '',
        avatar: ''
      },
      picList: [],
      saveBtnDisabled: false,
      levelNum: {
        one: 1,
        two: 2
      },
      base_api: process.env.BASE_API,
      rules: {
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 1, max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        career: [
          { required: true, message: '请输入资历', trigger: 'blur' },
          { min: 5, max: 50, message: '长度在5个字符以上', trigger: 'blur' }
        ],
        level: [
          { required: true, message: '请选择头衔', trigger: 'blur' }
        ],
        desc: [
          { required: true, message: '请填简介说明', trigger: 'blur' }
        ]
      }
    }
  },
  // 监听路由
  watch: {
    $route(to, from) {
      // console.log('watch $route')
      this.init()
    }
  },
  // 在页面渲染之前进行的方法
  created() {
    this.init()
  },
  methods: {
    // 初始化界面，检测传来的参数中有没有id这个参数，如果有id这个参数，就回显数据
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.id = this.$route.params.id
        this.fetchTeacherById(this.id)
      } else {
        this.teacher = {}
      }
    },
    // 提交表单，如果没有id这个参数，我们就执行插入数据，如果有id这个参数，我们就执行更新数据
    submitForm() {
      if (this.id == null) {
        teachers.insertOne(this.teacher)
          .then(response => {
            this.saveBtnDisabled = true
            this.$message({
              showClose: true,
              message: '插入数据库成功',
              type: 'success'
            })
          }).catch(error => {
            this.$message({
              message: '错误' + error,
              type: 'error'
            })
          })
      } else {
        teachers.updateOne(this.teacher)
          .then(response => {
            this.saveBtnDisabled = true
            this.$message({
              showClose: true,
              message: '更新数据库成功',
              type: 'success'
            })
          }).catch(error => {
            this.$message({
              message: '错误' + error,
              type: 'error'
            })
          })
      }
    },
    // 清空表单
    resetForm(formName) {
      this.$refs[formName].resetFields()
      this.saveBtnDisabled = false
    },
    // 回显数据调用这个方法
    fetchTeacherById(id) {
      teachers.getTeacherById(id)
        .then(response => {
          this.teacher = response.data.item
          // 回显图片
          const obj = Object()
          obj.url = this.teacher.avatar
          this.picList.push(obj)
          // console.log(this.picList)
        })
        .catch(error => this.$message({
          type: 'error：' + error,
          message: '获取数据失败'
        }))
    },
    handleRemove(file) {
      console.log(file)
    },
    handleSuccess(str) {
      console.log(str)
      this.teacher.avatar = str.data.url
    },
    handlePreview(file) {
      // console.log(file)
    }
  }
}
</script>

<style>
</style>
