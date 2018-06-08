<template>
  <div class="app-container calendar-list-container">

    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input clearable class="filter-item" style="width: 200px;" placeholder="请输入消费记录Id" v-model="listQuery.consumeLogId">
      </el-input>
      <el-input clearable class="filter-item" style="width: 200px;" placeholder="请输入消费者Id" v-model="listQuery.userId">
      </el-input>
      <el-button class="filter-item" type="primary" v-waves icon="el-icon-search" @click="handleFilter">查找</el-button>
      <el-button class="filter-item" type="primary" @click="handleCreate" icon="el-icon-edit">添加</el-button>
      <el-button class="filter-item" type="primary" :loading="downloadLoading" v-waves icon="el-icon-download" @click="handleDownload">导出</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table size="small" :data="list" v-loading="listLoading" element-loading-text="正在查询中。。。" border fit highlight-current-row>



      <el-table-column align="center" width="100px" label="消费信息Id" prop="id" sortable>
      </el-table-column>
      
      <el-table-column align="center" min-width="100px" label="消费记录Id" prop="consumeLogId">
      </el-table-column>

      <el-table-column align="center" min-width="100px" label="用户Id" prop="userId">
      </el-table-column>

      <el-table-column align="center" min-width="100px" label="是否付款" prop="payStatus">
              <template slot-scope="scope">
          <el-tag :type="scope.row.payStatus ? 'success' : 'error' ">{{scope.row.payStatus ? '是' : '否'}}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" min-width="100px" label="消费费用" prop="price">
      </el-table-column>

      <el-table-column align="center" min-width="100px" label="是否结清" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status ? 'success' : 'error' ">{{scope.row.status ? '是' : '否'}}</el-tag>
        </template>
      </el-table-column> 

      <el-table-column align="center" min-width="100px" label="结清时间" prop="handlerTime">
      </el-table-column>          

      <el-table-column align="center" label="操作" width="250" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini"  @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="listQuery.page"
        :page-sizes="[10,20,30,50]" :page-size="listQuery.limit" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </div>

    <el-tooltip placement="top" content="返回顶部">
      <back-to-top :visibilityHeight="100" ></back-to-top>
    </el-tooltip>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form :rules="rules" ref="dataForm" :model="dataForm" status-icon label-position="left" label-width="100px" style='width: 400px; margin-left:50px;'>
        <el-form-item label="消费记录Id" prop="consumeLogId">
          <el-input v-model="dataForm.consumeLogId"></el-input>
        </el-form-item>        
        <el-form-item label="用户Id" prop="userId">
          <el-input v-model="dataForm.userId"></el-input>
        </el-form-item>
        <el-form-item label="是否付款" prop="payStatus">
          <el-select v-model="dataForm.payStatus" placeholder="请选择">
            <el-option label="是" :value="true">
            </el-option>
            <el-option label="否" :value="false">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="消费费用" prop="price">
          <el-input v-model="dataForm.price"></el-input>
        </el-form-item>            
        <el-form-item label="是否结清" prop="status">
          <el-select v-model="dataForm.status" placeholder="请选择">
            <el-option label="是" :value="true">
            </el-option>
            <el-option label="否" :value="false">
            </el-option>
          </el-select>
        </el-form-item>   
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div> 
</el-dialog>
  </div>
</template>

<style>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 200px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
  }
  .el-dialog {
    width: 800px;
  }

</style>

<script>
import { listConsumeInfo, createConsumeInfo, updateConsumeInfo, deleteConsumeInfo } from '@/api/consumeInfo'
import waves from '@/directive/waves' // 水波纹指令
import BackToTop from '@/components/BackToTop'
import Tinymce from '@/components/Tinymce'

export default {
  name: 'ConsumeInfo',
  components: { BackToTop, Tinymce },
  directives: { waves },
  data() {
    return {
      list: undefined,
      total: undefined,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        consumeLogId: undefined,
        userId: undefined,
        sort: '+id'
      },
      dataForm: {
        id: undefined,
        consumeLogId: undefined,
        userId: undefined,
        payStatus: false,
        price: undefined,
        status: false,
        handlerTime: undefined
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑',
        create: '创建'
      },
      rules: {
        consumeLogId: [{ required: true, message: '消费记录Id不能为空', trigger: 'blur' }],
        userId: [{ required: true, message: '用户Id不能为空', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      listConsumeInfo(this.listQuery).then(response => {
        this.list = response.data.data.items
        this.total = response.data.data.total
        this.listLoading = false
      }).catch(() => {
        this.list = []
        this.total = 0
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    handleSizeChange(val) {
      this.listQuery.limit = val
      this.getList()
    },
    handleCurrentChange(val) {
      this.listQuery.page = val
      this.getList()
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        consumeLogId: undefined,
        userId: undefined,
        payStatus: true,
        price: undefined,
        status: false,
        handlerTime: undefined
      }
    },
    filterLevel(value, row) {
      return row.level === value
    },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          createConsumeInfo(this.dataForm).then(response => {
            this.list.unshift(response.data.data)
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '创建成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          updateConsumeInfo(this.dataForm).then(() => {
            for (const v of this.list) {
              if (v.id === this.dataForm.id) {
                const index = this.list.indexOf(v)
                this.list.splice(index, 1, this.dataForm)
                break
              }
            }
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row) {
      deleteConsumeInfo(row).then(response => {
        this.$notify({
          title: '成功',
          message: '删除成功',
          type: 'success',
          duration: 2000
        })
        const index = this.list.indexOf(row)
        this.list.splice(index, 1)
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['消费信息Id', '消费记录Id', '用户Id', '是否付款', '消费费用', '是否结清', '结清时间']
        const filterVal = ['id', 'consumeLogId', 'userId', 'payStatus', 'price', 'status', 'handlerTime']
        excel.export_json_to_excel2(tHeader, this.list, filterVal, '消费账单信息')
        this.downloadLoading = false
      })
    }
  }
}
</script>