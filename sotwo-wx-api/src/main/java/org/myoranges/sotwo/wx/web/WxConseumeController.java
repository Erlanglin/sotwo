package org.myoranges.sotwo.wx.web;

import com.mysql.jdbc.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.myoranges.sotwo.db.domain.*;
import org.myoranges.sotwo.db.service.*;
import org.myoranges.sotwo.db.util.SortUtil;
import org.myoranges.sotwo.wx.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "WxConseumeController", description = "消费管理接口")
@RequestMapping("/wx/consume")
public class WxConseumeController {
    private final Log logger = LogFactory.getLog(WxConseumeController.class);

    @Autowired
    private SotwoConsumeLogService consumeLogService;
    @Autowired
    private SotwoConsumeInfoService consumeInfoService;
    @Autowired
    private SotwoUserService userService;

    /**
     * 根据条件搜素消费记录
     * <p>
     * 1. 这里的前五个参数都是可选的，甚至都是空
     * 2. 用户是可选登录，如果登录，则记录用户的搜索关键字
     *
     * @param page  分页页数
     * @param size  分页大小
     * @param sort  排序方式
     * @param order 排序类型，顺序或者降序
     * @return 根据条件搜素的商品详情
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * goodsList: xxx,
     * filterCategoryList: xxx,
     * count: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("logList")
    @ApiOperation(value = "根据条件搜素消费记录", notes = "")
    public Object logList(SotwoConsumeLog sotwoConsumeLog,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size,
                          String sort, String order) {


        //查询列表数据
        List<SotwoConsumeLog> consumeLogsList = consumeLogService.querySelective(sotwoConsumeLog, page, size, sort, order);
        int total = consumeLogService.countSeletive(sotwoConsumeLog, page, size, sort, order);


        Map<String, Object> data = new HashMap<>();
        data.put("consumeLogsList", consumeLogsList);
        data.put("count", total);
        return ResponseUtil.ok(data);
    }


    /**
     * 消费者列表（目前就是用户列表）
     *
     * @param page  分页页数
     * @param limit 分页大小
     * @param sort  排序方式
     * @param order 排序类型，顺序或者降序
     * @return 根据条件搜素的商品详情
     */
    @GetMapping("/userList")
    @ApiOperation(value = "消费者列表（目前就是用户列表）", notes = "")
    public Object list(
            String username, String mobile,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            String sort, String order) {
        List<SotwoUser> userList = userService.querySelective(username, mobile, page, limit, sort, order);
        int total = userService.countSeletive(username, mobile, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", userList);
        return ResponseUtil.ok(data);
    }


    /**
     * 添加消费记录（记账）
     *
     * @return 根据条件搜素的商品详情
     */
    @PostMapping("/addConsumeLog")
    @ApiOperation(value = "添加消费记录（记账）", notes = "")
    public Object list(@RequestBody SotwoConsumeLog consumeLog,
                       @RequestBody List<SotwoConsumeInfo> sotwoConsumeInfos) {
        int addNum = consumeLogService.add(consumeLog);
        for (SotwoConsumeInfo consumeInfo : sotwoConsumeInfos){
            consumeInfo.setConsumeLogId(consumeLog.getId());
            consumeInfoService.add(consumeInfo);
        }
        if (addNum == 0) {
            return ResponseUtil.fail(-1, "添加账单失败");
        }
        return ResponseUtil.ok();
    }


    /**
     * 根据条件搜素消费详情
     * <p>
     * 1. 这里的前五个参数都是可选的，甚至都是空
     * 2. 用户是可选登录，如果登录，则记录用户的搜索关键字
     *
     * @param page  分页页数
     * @param size  分页大小
     * @param sort  排序方式
     * @param order 排序类型，顺序或者降序
     * @return 根据条件搜素的商品详情
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * goodsList: xxx,
     * filterCategoryList: xxx,
     * count: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */

    @GetMapping("infoList")
    @ApiOperation(value = "根据条件搜素消费详情", notes = "")
    public Object infoList(SotwoConsumeInfo sotwoConsumeInfo,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                           String sort, String order) {

        //查询列表数据
        List<SotwoConsumeInfo> consumeInfosList = consumeInfoService.querySelective(sotwoConsumeInfo, page, size, sort, order);
        int total = consumeInfoService.countSeletive(sotwoConsumeInfo, page, size, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("consumeLogsList", consumeInfosList);
        data.put("count", total);
        return ResponseUtil.ok(data);
    }
}