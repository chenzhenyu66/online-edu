<template>
  <div class="in-wrap">

    <!-- 公共头引入 -->
    <header id="header">
      <el-backtop></el-backtop>
      <section class="container">
        <h1 id="logo">
          <a href="#" title="在线教育项目">
            <h1>在线教育项目</h1>
          </a>
        </h1>

        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/course" tag="li" active-class="current">
              <a>课程</a>
            </router-link>
            <router-link to="/teacher" tag="li" active-class="current">
              <a>名师</a>
            </router-link>
            <router-link to="/article" tag="li" active-class="current">
              <a>文章</a>
            </router-link>
            <router-link to="/qa" tag="li" active-class="current">
              <a>问答</a>
            </router-link>
          </ul>
          <!-- / nav -->
          <ul class="h-r-login">
            <li v-show="!member.id" id="no-login">
              <a href="/login" title="登录">
                <em class="icon18 login-icon">&nbsp;</em>
                <span class="vam ml5">登录</span>
              </a>
              |
              <a href="/register" title="注册">
                <span class="vam ml5">注册</span>
              </a>
            </li>
            <li class="mr10 undis" id="is-login-one">
              <a href="#" title="消息" id="headerMsgCountId">
                <em class="icon18 news-icon">&nbsp;</em>
              </a>
              <q class="red-point" style="display: none">&nbsp;</q>
            </li>
            <li v-show="member.id" class="h-r-user" id="is-login-two">
              <a href="#" title="头像">
                <img
                  :src="member.avatar"
                  width="30"
                  height="30"
                  class="vam picImg"
                  alt="头像"
                />
                <span class="vam disIb" id="userName" style="padding-bottom: 3px">{{ member.nickname }}</span>
              </a>
              <a href="javascript:void(0);" title="退出" @click="logout()" class="ml5">退出</a>
            </li>
            <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>
          <aside class="h-r-search">
            <form action="#" method="post">
              <label class="h-r-s-box">
                <input type="text" placeholder="输入你想学的课程" name="queryCourse.courseName" value>
                <button type="submit" class="s-btn">
                  <em class="icon18">&nbsp;</em>
                </button>
              </label>
            </form>
          </aside>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>
    <!-- /公共头引入 -->
    <nuxt/>
    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="https://github.com/chenzhenyu66" title="我的git" target="_blank">git地址</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="#" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <a href="#" title="资源下载" target="_blank">资源下载</a>|
                <span>此项目仅用于学习，不用于任何商业目的</span>
                <span>Email：1344877587@qq.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>https://github.com/chenzhenyu66</span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="~/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>

    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
  import '~/assets/css/reset.css'
  import '~/assets/css/theme.css'
  import '~/assets/css/global.css'
  import '~/assets/css/web.css'
  import '~/assets/css/base.css'
  import '~/assets/css/activity_tab.css'
  import '~/assets/css/bottom_rec.css'
  import '~/assets/css/nice_select.css'
  import '~/assets/css/order.css'
  import '~/assets/css/swiper-3.3.1.min.css'
  import "~/assets/css/pages-weixinpay.css"
  import cookie from 'js-cookie'
export default {
  data() {
    return {
      member: {
        id: '',
        mobile: '',
        nickname: '',
        avatar: '',
        age: '',
        sex: ''
      },
      token: ''
    }
  },
  created() {
    //this.getUserInfo()
  },
  mounted() {
    this.getUserInfo()
  },
  methods:{
    getUserInfo(){
      let userInfo = cookie.get("edu_member")
      if(userInfo){
        this.member = JSON.parse(userInfo)
        console.log(this.member)
      }
    },
    logout() {
      this.$confirm('确定要退出吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 清除cookie
        cookie.set('edu_member', "")
        cookie.set('edu_token', "")
        //  cookie.set('edu_token', "",{domain: '120.26.77.168',path: '/'})
        //  cookie.set('edu_member', "",{domain: '120.26.77.168',path: '/'})
        window.location.href = "/"
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消退出'
        });
      });

    }

  }
}
</script>
