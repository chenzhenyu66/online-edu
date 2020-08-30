<template>
  <div>
    <!-- 阿里云视频播放器样式 -->
    <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.8/skins/default/aliplayer-min.css"/>
    <!-- 阿里云视频播放器脚本 -->
    <script type="text/javascript" charset="utf-8"
            src="https://g.alicdn.com/de/prismplayer/2.8.8/aliplayer-min.js"></script>
    <!-- 定义播放器dom -->
    <div id="J_prismPlayer"/>


    <h2 style="margin: 30px">评论</h2>

    <el-divider></el-divider>
    <div class="infinite-list-wrapper" style="overflow:auto">
      <el-form style="margin-left: 30px;margin-right: 30px">
        <el-form-item>
          <el-input type="textarea" v-model="message.content"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="sendComment()">发表评论</el-button>
        </el-form-item>
      </el-form>
      <el-divider>其他评论</el-divider>
    </div>
    <div>
      <ul>
        <li v-for="comment in commentList" :key="comment.id" class="list-item">
          <section>
            <div class="item">
              <div class="photo">
                <img :src="comment.avatar" style="height: 80px">
              </div>
              <div class="text">
                <div class="title">
                  <div>{{comment.nickname}}</div>
                  <div>{{comment.gmtCreate}}</div>
                </div>
                <div class="content">
                  {{comment.content}}
                </div>
              </div>
            </div>
          </section>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
  import vod from '@/api/vod'
  import comment from "@/api/comment"
  import course from "@/api/course"
  import cookie from 'js-cookie'

  export default {

    layout: 'default',//应用video布局
    asyncData({params, error}) {
      return vod.getPlayAuth(params.id).then(response => {
        // console.log(response.data.data)
        return {
          vid: params.id,
          playAuth: response.data.playAuth,
          commentList: [],
          courseInfo: {},
          message: {
            content: ''
          }
        }
      })
    },
    created() {
    },
    methods: {
      // 初始化评论
      init() {
        comment.listComment(1, 10, this.$route.query.childId)
          .then(response => {
            this.commentList = response.data.items
          })
      },
      sendComment() {
        if (cookie.get('edu_member') === '') {
          this.$message({
            message: '亲，你还没有登录捏~',
            type: 'warning'
          });
        }
        // console.log(this.$route.query.courseId)
        // console.log(this.$route.query.childId)
        course.selectInfoWebById(this.$route.query.courseId)
          .then(response => {
            this.courseInfo = response.data.item
            this.message.courseId = this.courseInfo.id
            this.message.teacherId = this.courseInfo.teacherId
            this.message.videoId = this.$route.query.childId

            let loginInfo = JSON.parse(cookie.get('edu_member'))
            console.log(loginInfo)
            this.message.memberId = loginInfo.id
            this.message.nickname = loginInfo.nickname
            this.message.avatar = loginInfo.avatar
            // console.log(this.message)
            comment.addCommentInfo(this.message)
              .then(response => {
                this.$message({
                  message: '发表成功',
                  type: 'success'
                });
                this.init()
              })
          })

      },
      async initVideo() {
        new Aliplayer({
          id: 'J_prismPlayer',
          vid: this.vid, // 视频id
          playauth: this.playAuth, // 播放凭证
          //encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
          width: '100%',
          height: '500px',
          // 以下可选设置
          //cover: '', // 封面
          qualitySort: 'asc', // 清晰度排序

          mediaType: 'video', // 返回音频还是视频
          autoplay: false, // 自动播放
          isLive: false, // 直播
          rePlay: false, // 循环播放
          preload: true,
          controlBarVisibility: 'hover', // 控制条的显示方式：鼠标悬停
          useH5Prism: true, // 播放器类型：html5
        }, function (player) {
          // console.log('播放器创建成功')
        })
      }
    },
    // data() {
    //   return {
    //     count: 10,
    //     videoInfo:{}
    //   }
    // },
    // created() {
    //   this.getPlayAuthTo()
    // },
    // methods: {
    //   getPlayAuthTo() {
    //     this.videoInfo.id = this.$route.params.id
    //
    //     vod.getPlayAuthToPage(this.videoInfo.id)
    //       .then(response => {
    //         this.videoInfo.playAuth = response.data.playAuth
    //       })
    //   }
    // },
    mounted() {
      this.initVideo()
      this.init()
    }
  }
</script>

<style scoped>
  .item {
    width: 600px;
    display: flex;
    margin-bottom: 20px;
  }

  .photo {
    height: 60px;
    width: 60px;
    background-color: #666;
    border-radius: 15px;
    margin-right: 20px;
  }

  .text {
    flex: 1;
    padding: 20px;
    background-color: #d7d7d7;
    border-radius: 15px;
  }

  .title {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
  }

  .content {
    text-indent: 2em;
  }
</style>
