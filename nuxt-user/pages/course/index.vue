<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a title="全部" href="#" @click.prevent="getAllCourse()">全部</a>
                </li>
                <li v-for="(subject,index) in subjectList" :key="index" :class="{current:oneIndex==index}">
                  <a :title="subject.title" href="#" @click.prevent="getChildren(subject,index)">{{ subject.title }}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li v-for="(child,index) in subjectChildren" :key="index" :class="{current:twoIndex==index}">
                  <a title="child.title" href="#" @click.prevent="childrenCondition(child,index)">{{ child.title }}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange':condition.buyCountSort == 1}">
                <a title="销量" href="javascript:void(0);" @click="searchBuyCount()">销量
                  <span v-if="condition.buyCountSort == 2">↓</span>
                  <span v-if="condition.buyCountSort == 1">↑</span>
                </a>
              </li>
              <li :class="{'current bg-orange':condition.gmtModifiedSort == 1}">
                <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">最新
                  <span v-if="condition.gmtModifiedSort == 2">↓</span>
                  <span v-if="condition.gmtModifiedSort == 1">↑</span>
                </a>
              </li>
              <li :class="{'current bg-orange':condition.priceSort == 1}">
                <a title="价格" href="javascript:void(0);" @click="searchPrice()">价格&nbsp;
                  <span v-if="condition.priceSort == 2">↓</span>
                  <span v-if="condition.priceSort == 1">↑</span>
                </a>

              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-if="data.total == 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article class="comm-course-list">
            <ul class="of" id="bna">
              <li v-for="course in data.courses" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover" class="img-responsive" :alt="course.title" style="height: 180px">
                    <div class="cc-mask">
                      <a :href="'/course/'+course.id" :title="course.title" class="comm-btn c-btn-1">{{ course.title }}</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+course.id" :title="course.title" class="course-title fsize18 c-333">{{ course.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fr jgTag bg-green">
                      <i class="c-fff fsize12 f-fA" v-if="course.price == 0">免费</i>
                      <i class="c-fff fsize12 f-fA" v-if="course.price > 0">{{course.price}} 元</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">9634人学习</i>
                      |
                      <i class="c-999 f-fA">9634评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)">首</a>
            <a
              :class="{undisable: !data.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(data.current - 1)">&lt;</a>
            <a
              v-for="page in data.pages"
              :key="page"
              :class="{current: data.current == page, undisable: data.current == page}"
              :title="'第'+page+'页'"
              href="#"
              @click.prevent="gotoPage(page)">{{ page }}</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(data.current + 1)">&gt;</a>
            <a
              :class="{undisable: !data.hasNext}"
              href="#"
              title="末页"
              @click.prevent="gotoPage(data.pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
  import course from "@/api/course";

  export default {

    data() {
      return {
        data: [],
        subjectList: [],
        subjectChildren: [],
        oneIndex:-1,
        twoIndex:-1,
        condition: {
          subjectParentId: '',
          subjectId: '',
          priceSort: 0,
          gmtModifiedSort:0,
          buyCountSort: 0
        }
      }
    },
    created() {
      this.init()
      this.initSubject()
    },
    methods: {
      // 初始化课程数据
      init() {
        course.getAllCourseToPage(1, 8, this.condition)
          .then(response => {
            this.data = response.data.items
            // console.log(response.data.items)
          })
      },
      // 初始化课程分类信息
      initSubject() {
        course.getSubjectAsList()
          .then(response => {
            this.subjectList = response.data.items
            // console.log(this.subjectList)
          })
      },
      // 跳转界面
      gotoPage(page) {
        course.getAllCourseToPage(page, 8, this.condition)
          .then(response => {
            this.data = response.data.items
          })
      },
      // 做跳转，加存储信息拿到课程子信息
      getChildren(subject,index) {
        
        // 清空子条件
        this.condition.subjectId = ''
        this.twoIndex = -1
        // 设置父条件
        this.oneIndex = index
        this.condition.subjectParentId = subject.id

        this.subjectChildren = subject.children
        this.init()
      },
      // 全部课程信息
      getAllCourse() {
        this.condition = {
          subjectParentId: '',
          subjectId: '',
          priceSort: 0,
          gmtModifiedSort: 0,
          buyCountSort: 0
        }
        this.init()
      },
      // 更改subject的子条件
      childrenCondition(child,index) {
        console.log("1" + this.condition.priceSort)
        this.twoIndex = index
        this.condition.subjectId = child.id
        this.init()
      },
      // 条件排序
      searchBuyCount(){
        this.condition.priceSort = 0
        this.condition.gmtModifiedSort = 0
        this.condition.buyCountSort === 1?this.condition.buyCountSort=2:this.condition.buyCountSort = 1
        this.init()
      },
      searchGmtCreate(){
        this.condition.priceSort = 0
        this.condition.buyCountSort = 0
        this.condition.gmtModifiedSort === 1?this.condition.gmtModifiedSort=2:this.condition.gmtModifiedSort = 1
        this.init()
      },
      searchPrice(){
        this.condition.gmtModifiedSort = 0
        this.condition.buyCountSort = 0
        this.condition.priceSort === 1?this.condition.priceSort=2:this.condition.priceSort = 1
        this.init()
      }
    }
  };
</script>
