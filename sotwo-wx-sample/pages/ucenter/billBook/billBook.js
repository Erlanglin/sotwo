var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');

Page({
  data:{
    consumeLogList: [],
    consumeLog: {},
    consumeInfo: [],
    showType: 3,
    userId: 1
  },
  onLoad:function(options){
    // 页面初始化 options为页面跳转所带来的参数
    this.getConsumeList();
  },
  
  getConsumeList(){
    let that = this;
    util.request(api.BillList, { status: that.data.showType,userId:that.data.userId}).then(function (res) {
      if (res.errno === 0) {
        console.log(res.data);
        that.setData({
          count: res.data.count,
          billList: res.data.billList
        });
      }
    });
  },
  switchTab: function (event) {
    let showType = event.currentTarget.dataset.index;
    this.setData({
      showType: showType
    });
    this.getConsumeList();
  },
  onReady:function(){
    // 页面渲染完成
  },
  onShow:function(){
    // 页面显示
  },
  onHide:function(){
    // 页面隐藏
  },
  onUnload:function(){
    // 页面关闭
  }
})