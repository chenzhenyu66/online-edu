<template>
  <div class="app-container">
    <el-form :inline="true" :model="queryTeacher" class="demo-form-inline" >
      <el-form-item>
        <el-input v-model="queryTeacher.name" size="small" placeholder="姓名"/>
      </el-form-item>
      <el-form-item>
        <el-select v-model="queryTeacher.level" size="small" placeholder="头衔">
          <el-option label="高级讲师" value="1"/>
          <el-option label="首席讲师" value="2"/>
          <el-option label="所有" value="null"/>
        </el-select>
      </el-form-item>
      <el-form-item label="时间">
        <el-date-picker
          v-model="queryTeacher.begin"
          size="small"
          type="datetime"
          placeholder="选择开始时间"
          value-fromat="yyyy-MM-dd HH:mm:ss"
          defalut-time="00:00:00"/>
      </el-form-item>
      <el-form-item>
        <el-date-picker
          v-model="queryTeacher.end"
          size="small"
          type="datetime"
          placeholder="选择开始时间"
          value-fromat="yyyy-MM-dd HH:mm:ss"
          defalut-time="00:00:00"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" @click="getList()">查询</el-button>
        <el-button type="primary" size="mini" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-if="hasPerm('teacher.list')"
      :data="list"
      border
      style="width: 100%">
      <el-table-column
        prop="id"
        label="员工编号"
        width="180"/>
      <el-table-column
        fixed
        prop="name"
        label="姓名"
        width="100"/>
      <el-table-column
        prop="intro"
        label="介绍"
        width="580"/>
      <el-table-column
        prop="career"
        label="资历"
        width="200"/>
      <el-table-column
        label="头衔"
        width="100">
        <template slot-scope="scope">
          {{ scope.row.level===1?'高级讲师':'首席讲师' }}
        </template>
      </el-table-column>
      <el-table-column
        prop="gmtCreate"
        label="创建时间"
        width="100"/>
      <el-table-column
        fixed="right"
        label="操作"
        width="100">
        <template slot-scope="scope">
          <el-button v-if="hasPerm('teacher.remove')" type="text" size="small" @click="deleteTeacherById(scope.row.id)">删除</el-button>
          <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button v-if="hasPerm('teacher.update')" type="text" size="small">编辑</el-button>
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
import teacher from '@/api/teacher/teacher'

export default {
  name: 'Management',
  // 定义变量和初始值
  data: function() {
    return {
      list: null,
      page: 1,
      limit: 10,
      total: 0,
      queryTeacher: {
        name: '',
        level: null,
        begin: '',
        end: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.page = page
      teacher.getTeacherListPage(this.page, this.limit, this.queryTeacher)
        .then(response => {
          // console.log(response)
          this.list = response.data.rows
          this.total = response.data.total
          console.log(this.list)
          console.log(this.total)
        })
        .catch(error => {
          console.log(error)
        })
    },
    resetData() {
      this.queryTeacher = {}
      this.getList()
    },
    deleteTeacherById(id) {
      this.$confirm('此操作将删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const _this = this
        teacher.deleteTeacher(id)
          .then(response => {
            this.getList()
            _this.$message({
              type: 'success',
              message: '删除成功!'
            })
          }).catch(error => console.log(error))
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
