<template>
  <div class="app-container">
    条件查询部分
    <el-table
      :data="list"
      border
      style="width: 100%">
      <el-table-column
        prop="id"
        label="课程编号"
        width="180"/>
      <el-table-column
        fixed
        prop="title"
        label="课程名称"
        width="100"/>
      <el-table-column
        sortable
        prop="name"
        label="讲师"
        width="100"/>
      <el-table-column
        label="课程分类"
        width="200">
        <template slot-scope="scope">
          <span>{{ scope.row.subjectParentTitle }}</span>
          <span>：{{ scope.row.subjectTitle }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="发布状态"
        width="100"/>
      <el-table-column
        sortable
        prop="price"
        label="价格"
        width="100"/>
      <el-table-column
        sortable
        prop="lessonNum"
        label="课时"
        width="100"/>
      <el-table-column
        sortable
        prop="gmtCreate"
        label="创建时间"
        width="180"/>
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button v-if="hasPerm('course.update')" type="text" size="small" @click="deleteCourseById(scope.row.id)">删除</el-button>
          <router-link :to="'/course/edit/'+scope.row.id">
            <el-button v-if="hasPerm('course.update')" type="text" size="small" >编辑</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      background
      layout="total, prev, pager, next, jumper"
      align="center"
      @current-change="getList"/>
  </div>
</template>

<script>
import managerment from '@/api/course/managerment'
export default {
  data() {
    return {
      list: [],
      page: 1,
      limit: 10,
      total: 0
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      managerment.findCoursesForList(page, this.limit).then(response => {
        this.list = response.data.items
        this.total = response.data.total
      })
    },
    deleteCourseById(courseId) {
      this.$confirm('是否确认删除？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确定',
        cancelButtonText: '放弃删除'
      }).then(() => {
        managerment.deleteCourseInfoInList(courseId).then(response => {
          this.getList()
        })
      }).catch(action => {
        this.$message({
          type: 'info',
          message: action === 'cancel'
            ? '已放弃删除'
            : '已取消操作'
        })
      })
    }
  }
}
</script>

