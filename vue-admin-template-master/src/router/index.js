import Vue from 'vue'
import Router from 'vue-router'

// in development-env not use lazy-loading, because lazy-loading too many pages will cause webpack hot update too slow. so only in production use lazy-loading;
// detail: https://panjiachen.github.io/vue-element-admin-site/#/lazy-loading

Vue.use(Router)

/* Layout */
import Layout from '../views/layout/Layout'

/**
* hidden: true                   if `hidden:true` will not show in the sidebar(default is false)
* alwaysShow: true               if set true, will always show the root menu, whatever its child routes length
*                                if not set alwaysShow, only more than one route under the children
*                                it will becomes nested mode, otherwise not show the root menu
* redirect: noredirect           if `redirect:noredirect` will no redirect in the breadcrumb
* name:'router-name'             the name is used by <keep-alive> (must set!!!)
* meta : {
    title: 'title'               the name show in submenu and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar,
  }
**/
export const constantRouterMap = [
  { path: '/login', component: () => import('@/views/login/index'), hidden: true },
  { path: '/404', component: () => import('@/views/404'), hidden: true },
  {
    path: '/teacher',
    component: Layout,
    redirect: '/teacher/table',
    name: '讲师管理',
    meta: { title: '讲师管理', icon: 'example' },
    children: [
      {
        path: 'add',
        name: '添加讲师',
        component: () => import('@/views/edu/teacher/add'),
        meta: { title: '添加讲师', icon: 'table' }
      },
      {
        path: 'table',
        name: '讲师管理',
        component: () => import('@/views/edu/teacher/managerment'),
        meta: { title: '讲师管理', icon: 'tree' }
      },
      {
        path: 'edit/:id',
        name: 'EduTeacherEdit',
        component: () => import('@/views/edu/teacher/add'),
        meta: { title: '编辑讲师', noCache: true },
        hidden: true
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    name: 'Dashboard',
    hidden: true,
    children: [{
      path: 'dashboard',
      component: () => import('@/views/dashboard/index')
    }]
  },

  {
    path: '/subject',
    component: Layout,
    redirect: '/subject/table',
    name: '课程分类管理',
    meta: { title: '课程分类管理', icon: 'form' },
    children: [
      {
        path: 'list',
        name: '课程分类',
        component: () => import('@/views/edu/subject/list'),
        meta: { title: '课程分类', icon: 'tree' }
      },
      {
        path: 'add',
        name: '添加分类',
        component: () => import('@/views/edu/subject/add'),
        meta: { title: '添加分类', icon: 'table' }
      }
    ]
  },
  {
    path: '/course',
    component: Layout,
    redirect: '/course/table',
    name: '课程管理',
    meta: { title: '课程管理', icon: 'form' },
    children: [
      {
        path: 'list',
        name: '课程列表',
        component: () => import('@/views/edu/course/list'),
        meta: { title: '课程列表', icon: 'tree' }
      },
      {
        path: 'edit',
        name: '编辑课程信息',
        component: () => import('@/views/edu/course/edit'),
        meta: { title: '编辑课程信息', icon: 'table' }
      },
      {
        path: 'edit/:id',
        name: 'info',
        component: () => import('@/views/edu/course/edit'),
        meta: { title: '课程修改', noCache: true },
        hidden: true
      },
      {
        path: 'chapter/:id',
        name: 'chapter',
        component: () => import('@/views/edu/course/chapter'),
        meta: { title: '章节管理', noCache: true },
        hidden: true
      },
      {
        path: 'publish/:id',
        name: 'publish',
        component: () => import('@/views/edu/course/publish'),
        meta: { title: '发布课程', noCache: true },
        hidden: true
      }
    ]
  },
  {
    path: '/statistics',
    component: Layout,
    name: '数据统计',
    meta: { title: '数据统计', icon: 'form' },
    children: [
      {
        path: 'add',
        name: '添加数据',
        component: () => import('@/views/statistics/add'),
        meta: { title: '添加数据', icon: 'nested' }
      },
      {
        path: 'show',
        name: '数据可视化',
        component: () => import('@/views/statistics/show'),
        meta: { title: '数据可视化', icon: 'table' }
      }
    ]
  },

  {
    path: '/permission',
    component: Layout,
    name: '权限管理',
    meta: {
      title: '权限管理',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu',
        component: () => import('@/views/acl/menu/list'), // Parent router-view
        name: '菜单管理',
        meta: { title: '菜单管理' }
      },
      {
        path: 'user/list',
        name: '用户管理',
        component: () => import('@/views/acl/user/list'),
        meta: { title: '用户管理' }
      },
      {
        path: 'role/list',
        name: '角色管理',
        component: () => import('@/views/acl/role/list'),
        meta: { title: '角色管理' }
      },
      {
        path: 'role/form',
        name: '角色添加',
        component: () => import('@/views/acl/role/form'),
        meta: { title: '角色添加' },
        hidden: true
      },
      {
        path: 'role/update/:id',
        name: '角色修改',
        component: () => import('@/views/acl/role/form'),
        meta: { title: '角色修改' },
        hidden: true
      },
      {
        path: 'role/distribution/:id',
        name: '角色权限',
        component: () => import('@/views/acl/role/roleForm'),
        meta: { title: '角色权限' },
        hidden: true
      },
      {
        path: 'user/add',
        name: '用户添加',
        component: () => import('@/views/acl/user/form'),
        meta: { title: '用户添加' },
        hidden: true
      },
      {
        path: 'user/update/:id',
        name: '用户修改',
        component: () => import('@/views/acl/user/form'),
        meta: { title: '用户修改' },
        hidden: true
      },
      {
        path: 'user/role/:id',
        name: '用户角色',
        component: () => import('@/views/acl/user/roleForm'),
        meta: { title: '用户角色' },
        hidden: true
      }
    ]
  },

  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://github.com/chenzhenyu66',
        meta: { title: '我的git', icon: 'link' }
      }
    ]
  },

  { path: '*', redirect: '/404', hidden: true }
]

export default new Router({
  base: '/vue-admin/',
  // mode: 'history', //后端支持可开
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
