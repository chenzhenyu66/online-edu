<template>
  <div class="app-container" style="margin-left: 40px">
    <el-input
      v-model="filterText"
      placeholder="输入关键字进行过滤"/>

    <el-tree
      v-if="hasPerm('subject.list')"
      ref="tree"
      :data="data"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all/>

  </div>
</template>

<script>
import subject from '@/api/subject/subject'

export default {

  data() {
    return {
      filterText: '',
      data: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      }
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    }
  },
  created() {
    this.getSubjects()
    console.log(this.data)
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    },
    getSubjects() {
      subject.getSubjects()
        .then(response => {
          this.data = response.data.items
        }).catch()
    }
  }
}
</script>

