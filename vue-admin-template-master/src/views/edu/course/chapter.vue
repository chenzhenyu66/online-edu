<template>
  <div class="custom-tree-container" style="padding: 40px">
    <el-steps :active="page">
      <el-step title="基本信息填写" icon="el-icon-edit"/>
      <el-step title="课程大纲" icon="el-icon-upload"/>
      <el-step title="提交审核" icon="el-icon-picture"/>
    </el-steps>
    <el-button type="text" @click="showDialog(null)">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
      <li
        v-for="chapter in chapterNestedList"
        :key="chapter.id">
        <p>
          {{ chapter.sort }}：
          {{ chapter.title }}
          <span class="acts">
            <el-button type="text" @click="showVideoDialog(chapter.id,null)">添加课时</el-button>
            <el-button type="text" @click="showDialog(chapter.id)">编辑</el-button>
            <el-button type="text" @click="deleteChapter(chapter.id)">删除</el-button>
          </span>
        </p>
        <ul class="chanpterList videoList">
          <li
            v-for="video in chapter.children"
            :key="video.id">
            <p>{{ chapter.sort }}.{{ video.sort }}：{{ video.title }}
              <span class="acts">
                <el-button type="text" @click="showVideoDialog(chapter.id,video.id)">编辑</el-button>
                <el-button type="text" @click="deleteVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传文件">
          <el-upload
            ref="upload"
            :on-success="handleVodUploadSuccess"
            :before-remove="beforeVodRemove"
            :on-remove="handleVodRemove"
            :file-list="fileList"
            :action="BASE_URL+'/eduvod/upload'"
            class="upload-demo">
            <el-button slot="trigger" size="small" type="primary">选取视频</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdateVideo()">确 定</el-button>
      </div>
    </el-dialog>

    <el-button style="margin-top: 12px;" @click="previous">上一步</el-button>
    <el-button style="margin-top: 12px;" @click="next">下一步</el-button>
  </div>
</template>
<script>
import chapter from '@/api/course/chapter'

export default {
  data() {
    return {
      chapter: {// 章节对象
        title: '',
        sort: 0,
        courseId: ''
      },
      video: {
        title: '',
        sort: 0,
        courseId: '',
        chapterId: '',
        isFree: false,
        videoSourceId: '',
        videoOriginalName: ''
      },
      courseId: '',
      chapterNestedList: [],
      fileList: [],
      page: 2,
      dialogChapterFormVisible: false,
      dialogVideoFormVisible: false,
      BASE_URL: process.env.BASE_API
    }
  },
  created() {
    this.init()
    // console.log(this.chapterNestedList)
  },
  methods: {
    // 删除视频
    handleVodRemove() {
      chapter.deleteVideoVoDInfo(this.video.videoSourceId).then(response => {
        this.video.videoSourceId = ''
        this.video.videoOriginalName = ''
        this.fileList = []
      })
    },
    // 上传视频
    submitUpload() {
      this.$refs.upload.submit()
    },
    // 处理删除视频之前
    beforeVodRemove() {
      return this.$confirm(`确定移除视频？`)
    },
    // 上传视频成功
    handleVodUploadSuccess(response, file, fileList) {
      this.$message({
        showClose: true,
        message: '上传视频成功',
        type: 'success'
      })
      this.video.videoSourceId = response.data.videoId
      this.video.videoOriginalName = file.name
    },
    // 删除课时信息
    deleteVideo(videoId) {
      this.$confirm('是否确认删除？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '放弃删除'
      }).then(() => {
        chapter.deleteVideoInfo(videoId)
          .then(response => {
            this.$message({
              showClose: true,
              message: '删除数据成功',
              type: 'success'
            })
            this.fetchChapterNestedListByCourseId() // 刷新列表
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
    // 更新课时信息
    updateVideoData() {
      chapter.updateVideoInfo(this.video).then(response => {
        this.$message({
          showClose: true,
          message: '数据更新成功',
          type: 'success'
        })
        this.dialogVideoFormVisible = false
        this.fetchChapterNestedListByCourseId() // 刷新列表
        this.video.title = ''
        this.video.sort = 0
      })
    },
    // 增加课时信息
    saveVideoData() {
      chapter.addVideoInfo(this.video).then(response => {
        this.$message({
          showClose: true,
          message: '数据插入成功',
          type: 'success'
        })
        this.dialogVideoFormVisible = false
        this.fetchChapterNestedListByCourseId() // 刷新列表
        this.video.title = ''
        this.video.sort = 0
      })
    },
    // 删除章节信息
    deleteChapter(id) {
      this.$confirm('是否确认删除？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '放弃删除'
      }).then(() => {
        chapter.deleteChapter(id)
          .then(response => {
            this.$message({
              showClose: true,
              message: '删除数据成功',
              type: 'success'
            })
            this.fetchChapterNestedListByCourseId() // 刷新列表
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
    saveData() {
      this.chapter.courseId = this.courseId
      chapter.addChapterInfo(this.chapter).then(response => {
        this.$message({
          showClose: true,
          message: '插入数据成功',
          type: 'success'
        })
        this.dialogChapterFormVisible = false // 如果保存成功则关闭对话框
        this.fetchChapterNestedListByCourseId() // 刷新列表
        this.chapter.title = '' // 重置章节标题
        this.chapter.sort = 0 // 重置章节标题
      })
    },
    updateData() {
      this.chapter.courseId = this.courseId
      chapter.updateChapter(this.chapter).then(response => {
        this.$message({
          showClose: true,
          message: '更新数据成功',
          type: 'success'
        })
        this.dialogChapterFormVisible = false // 如果保存成功则关闭对话框
        this.fetchChapterNestedListByCourseId() // 刷新列表
        this.chapter.title = '' // 重置章节标题
        this.chapter.sort = 0 // 重置章节标题
      })
    },
    // 保存或者更新课时信息
    saveOrUpdateVideo() {
      if (!this.video.id) {
        this.saveVideoData()
      } else {
        this.updateVideoData()
      }
    },
    // 保存或者更新章节信息
    saveOrUpdate() {
      if (!this.chapter.id) {
        this.saveData()
      } else {
        this.updateData()
      }
    },
    // 弹出一个课时框
    showVideoDialog(chapterId, videoId) {
      this.dialogVideoFormVisible = true
      // this.video.courseId = this.courseId
      // this.video.chapterId = chapterId
      // 回显数据
      if (videoId != null) {
        chapter.selectOneVideo(videoId).then(response => {
          this.video = response.data.item
          console.log(this.video)
          if (this.video.videoOriginalName !== '') {
            this.fileList = [{ 'name': this.video.videoOriginalName }]
          }
          // console.log(this.video)
        })
      } else {
        this.video = {
          title: '',
          sort: 0,
          courseId: '',
          chapterId: '',
          isFree: false,
          videoSourceId: '',
          videoOriginalName: ''
        }
        this.fileList = []
      }
      this.video.courseId = this.courseId
      this.video.chapterId = chapterId
    },
    // 弹出一个章节框
    showDialog(chapterId) {
      this.dialogChapterFormVisible = true
      // chapter.addChapterInfo().then()
      // 如果是更新操作，chapterId为需要更新的id，如果为添加操作，chapterId为null
      if (chapterId != null) {
        chapter.selectOneChapter(chapterId).then(response => {
          this.chapter = response.data.item
        })
      } else {
        this.chapter = {// 章节对象
          title: '',
          sort: 0,
          courseId: ''
        }
      }
      this.chapter.id = chapterId
    },
    // 刷新数据
    fetchChapterNestedListByCourseId() {
      chapter.selectAllChapterInfo(this.courseId).then(response => {
        this.chapterNestedList = response.data.items
      })
    },
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.courseId = this.$route.params.id
        // 根据id获取课程基本信息
        this.fetchChapterNestedListByCourseId()
      }
    },
    next() {
      this.$router.push({ path: '/course/publish/' + this.$route.params.id })
    },
    previous() {
      this.$router.push({ path: '/course/edit/' + this.$route.params.id })
    }
  }
}
</script>

<style scoped>
  .chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }
  .chanpterList li{
    position: relative;
  }
  .chanpterList p{
    float: left;
    font-size: 19px;
    margin: 10px 0;
    padding: 10px;
    height: 60px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }
  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }

  .videoList{
    padding-left: 40px;
  }
  .videoList p{
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 20px;
    width: 100%;
    border: 1px dotted #DDD;
  }

</style>
