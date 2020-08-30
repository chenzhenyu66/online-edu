<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="OSS_PATH">点击下载模版</a>
        </el-tag>
      </el-form-item>
      <el-form-item label="上传文件">
        <el-upload
          ref="upload"
          :on-success="handleSuccess"
          :on-remove="handleRemove"
          :file-list="fileList"
          :auto-upload="false"
          :action="BASE_URL+'/eduservice/uploadSubject'"
          accept="application/vnd.ms-excel"
          class="upload-demo">
          <el-button v-if="hasPerm('subject.import')" slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button v-if="hasPerm('subject.import')" style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-form-item>
      <el-form-item v-if="hasPerm('subject.removeAll')" label="删除所有">
        <el-button type="danger" icon="el-icon-delete" circle @click="deleteAll()"/>
      </el-form-item>
    </el-form>
    <!--<el-upload
      ref="upload"
      :on-remove="handleRemove"
      :on-success="handleSuccess"
      :before-remove="beforeRemove"
      :limit="3"
      :on-exceed="handleExceed"
      :file-list="fileList"
      :action="BASE_URL+'/eduservice/uploadSubject'"
      :auto-upload="false"
      class="upload-demo"
      multiple>
      <el-button size="medium" type="primary">点击上传</el-button>
      <div slot="tip" class="el-upload__tip">只能上传xls,xlsx文件</div>
    </el-upload>
    <el-button size="medium" type="primary" @click="submitUpload()">开始上传</el-button>-->
  </div>
</template>

<script>
import subject from '@/api/subject/subject'
export default {
  data() {
    return {
      OSS_PATH: 'https://edu-online1024.oss-cn-beijing.aliyuncs.com/2020/07/29/5305dab4-dade-4d11-974c-09e2099374f4%E6%A8%A1%E6%9D%BF.xls',
      BASE_URL: process.env.BASE_API,
      fileList: []
    }
  },
  methods: {
    downloadFile() {
    },
    deleteAll() {
      this.$confirm('该操作比较危险，是否确认删除？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '放弃删除'
      }).then(() => {
        subject.deleteSubjects().then(response => {
          this.$message({
            message: '删除成功',
            type: 'success'
          })
        })
      }).catch(action => {
        this.$message({
          type: 'info',
          message: action === 'cancel'
            ? '已放弃删除'
            : '已取消操作'
        })
      })
    },
    submitUpload() {
      this.$refs.upload.submit()
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handleSuccess(file) {
      this.$message({
        message: '上传成功',
        type: 'success'
      })
      this.$router.push({ path: '/subject/list' })
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    }
  }
}

</script>

