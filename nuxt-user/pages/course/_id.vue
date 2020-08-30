<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="#" title class="c-999 fsize14">首页</a>
        \
        <a href="#" title class="c-999 fsize14">课程列表</a>
        \
        <span class="c-333 fsize14">{{courseList.subjectLevelOne}}：{{courseList.subjectLevelTwo}}</span>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 357px;">
          <section class="p-h-video-box" id="videoPlay">
            <img :src="courseList.cover" :alt="courseList.title" style="height: 360px" class="dis c-v-pic">
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{ courseList.title }}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格</span>
              <b class="c-yellow" style="font-size:24px;">￥{{courseList.price}}</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲： {{courseList.teacherName}}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"></em>
                <a class="c-fff vam" title="收藏" href="#">收藏</a>
              </span>
            </section>
            <section class="c-attr-mt">
              <a href="#" title="立即观看" class="comm-btn c-btn-3" v-show="this.status == true">立即观看</a>
              <a href="#" @click="createOrders()" title="立即购买" class="comm-btn c-btn-3" v-show="this.status == false">立即购买</a>
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数量</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseList.buyCount}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseList.lessonNum}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseList.viewCount}}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>
              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <p v-html="courseList.description">
                        {{ courseList.description }}
                      </p>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <!-- 文件目录 -->
                          <li class="lh-menu-stair" v-for="chapter in chapterList" :key="chapter">
                            <a href="javascript: void(0)" :title="chapter.title" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"></em>{{ chapter.title }}
                            </a>
                            <ol class="lh-menu-ol" style="display: block;">
                              <li class="lh-menu-second ml30" v-for="child in chapter.children" :key="child.id">
                                <a href="javascript: void(0)" :title="chapter.title"
                                   @click="sendMessage(child.videoSourceId,child.id)">
                                  <span class="fr">
                                    <i class="free-icon vam mr10">免费试听</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{ child.title }}
                                </a>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <!-- /课程大纲 -->
              </article>
            </div>
          </section>
        </article>
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li>
                    <div class="u-face">
                      <a href="#">
                        <img :src="courseList.avatar" width="50" height="50" alt>
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" href="#">{{ courseList.teacherName }}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{courseList.intro}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
  </div>
</template>

<script>
  import course from "@/api/course";
  import order from "@/api/order";
  export default {

    data() {
      return {
        courseList: {},
        chapterList: [],
        status: ''
      }
    },
    created() {
      this.selectInfoWebById()
      this.selectChapterInfoById()
      this.getOrderStatus()
    },
    methods: {
      // 获取订单状态
      getOrderStatus(){
        order.getOrderStatus(this.$route.params.id).then(response => {
          this.status = response.data.status
          // console.log(this.status)
        })
      },
      // 创建订单信息
      createOrders(){
        order.saveOrder(this.$route.params.id)
          .then(response => {
            this.$router.push({ path:'/order/'+ response.data.orderId })
          })
      },
      // 页面信息加载
      selectInfoWebById() {
        course.selectInfoWebById(this.$route.params.id)
          .then(response => {
            this.courseList = response.data.item
            // console.log(response.data.item)
          })
      },
      // 页面章节信息加载
      selectChapterInfoById() {
        course.selectChapterInfoById(this.$route.params.id)
          .then(response => {
            this.chapterList = response.data.items
            // console.log(response.data.item)
          })
      },
      // 播放页面发送数据
      sendMessage(videoSourceId, id) {
        // this.$router.push({path: '/player/' + videoSourceId, query: {courseId: this.courseList.id, childId: id}});

        const route = this.$router.resolve({
          path: '/player/' + videoSourceId,
          query: {
            courseId: this.courseList.id,
            childId: id
          }
        });
        window.open(route.href, '_blank');
      }
    }
  };
</script>
