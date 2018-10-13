package org.myoranges.sotwo.wx.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.myoranges.sotwo.db.domain.SotwoConsumeInfo;
import org.myoranges.sotwo.db.domain.SotwoConsumeLog;
import org.myoranges.sotwo.db.model.SotwoConsumeModel;
import org.myoranges.sotwo.db.service.SotwoConsumeInfoService;
import org.myoranges.sotwo.db.service.SotwoConsumeLogService;
import org.myoranges.sotwo.db.service.SotwoUserService;
import org.myoranges.sotwo.db.util.ConsumeUtil;
import org.myoranges.sotwo.db.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "ConseumeController", description = "消费管理接口")
@RequestMapping("/wx/consume")
public class WxConseumeController {

    @Autowired
    private SotwoConsumeLogService consumeLogService;
    @Autowired
    private SotwoConsumeInfoService consumeInfoService;
    @Autowired
    private SotwoUserService userService;

    /**
     * 根据条件搜素消费记录
     *
     * @param page  分页页数
     * @param size  分页大小
     * @param sort  排序方式
     * @param order 排序类型，顺序或者降序
     * @return 根据条件搜素的商品详情
     */
    @GetMapping("logList")
    @ApiOperation(value = "根据条件搜素消费记录", notes = "")
    public Object logList(SotwoConsumeLog sotwoConsumeLog,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size,
                          String sort, String order) {
        //查询列表数据
        List<SotwoConsumeInfo> consumeInfos = null;
        if (sotwoConsumeLog.getStatus() == 3)
            sotwoConsumeLog.setStatus(null);

        List<Map<String, Object>> consumeLogLists = new ArrayList<>();
        List<SotwoConsumeLog> consumeLogsList = consumeLogService.querySelective(sotwoConsumeLog, page, size, sort, order);
        for(SotwoConsumeLog consumeLog: consumeLogsList){
            Map<String, Object> consumeLogList = new HashMap<>();
            consumeInfos  = consumeInfoService.queryByConsumeLogId(consumeLog.getId());
            consumeLogList.put("consumeLog", consumeLog);
            consumeLogList.put("consumeInfo",consumeInfos);
            consumeLogList.put("billStatus",ConsumeUtil.billStatusText(consumeLog.getStatus()));
            consumeLogList.put("payStatus",ConsumeUtil.payStatusText(consumeInfos.get));
            consumeLogLists.add(consumeLogList);
        }
        int total = consumeLogService.countSeletive(sotwoConsumeLog, page, size, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("consumeLogList", consumeLogLists);
        data.put("count", total);
        return ResponseUtil.ok(data);
    }

    /**
     * 添加消费记录（记账）
     *
     * @return 根据条件搜素的商品详情
     */
    @PostMapping("/addConsumeLog")
    @ApiOperation(value = "添加消费记录（记账）", notes = "")
    public Object list(@RequestBody SotwoConsumeModel sotwoConsumeModel) {
        sotwoConsumeModel.getConsumeLog().setRegTime(LocalDateTime.now());
        int addNum = consumeLogService.add(sotwoConsumeModel.getConsumeLog());
        for (SotwoConsumeInfo consumeInfo : sotwoConsumeModel.getSotwoConsumeInfos()) {
            consumeInfo.setConsumeLogId(sotwoConsumeModel.getConsumeLog().getId());
            if (consumeInfo.getPrice() > 0) {
                consumeInfo.setPayStatus(1);
                consumeInfo.setStatus(1);
                consumeInfo.setHandlerTime(LocalDateTime.now());
            }
            consumeInfoService.add(consumeInfo);
        }
        if (addNum == 0) {
            return ResponseUtil.fail(-1, "添加账单失败");
        }
        return ResponseUtil.ok();
    }


    /**
     * 根据条件搜素消费详情
     *
     * @param page  分页页数
     * @param size  分页大小
     * @param sort  排序方式
     * @param order 排序类型，顺序或者降序
     * @return 根据条件搜素的商品详情
     */

    @GetMapping("infoList")
    @ApiOperation(value = "根据条件搜素消费详情", notes = "")
    public Object infoList(SotwoConsumeInfo sotwoConsumeInfo,
                           String startTime, String endTime,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                           String sort, String order) {

        //查询列表数据
        List<SotwoConsumeInfo> consumeInfosList = consumeInfoService.querySelective(sotwoConsumeInfo, startTime, endTime, page, size, sort, order);
        int total = consumeInfoService.countSeletive(sotwoConsumeInfo, page, size, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("consumeLogsList", consumeInfosList);
        data.put("count", total);
        return ResponseUtil.ok(data);
    }


    /**
     * 修改账单结算状态（结账）
     */

    @GetMapping("/updateStatus")
    @ApiOperation(value = "修改账单结算状态(结账)", notes = "")
    public Object updateStatus(@RequestParam Integer consumeLogId, @RequestParam Integer userId) {

        int statusSum = 0;
        int updateNum = consumeInfoService.updateStatus(consumeLogId, userId);
        List<SotwoConsumeInfo> consumeInfos = consumeInfoService.queryByConsumeLogId(consumeLogId);
        for (SotwoConsumeInfo consumeInfoTwo : consumeInfos) {
            statusSum = statusSum + consumeInfoTwo.getStatus();
        }
        if (statusSum == 5) {
            SotwoConsumeLog consumeLog = new SotwoConsumeLog();
            consumeLog.setId(consumeLogId);
            consumeLog.setStatus(1);
            consumeLog.setHandlerTime(LocalDateTime.now());
            int updateLogStatusNum = consumeLogService.update(consumeLog);
            if (updateNum != 0 && updateLogStatusNum != 0)
                return ResponseUtil.fail(0, "个人结算完成并且整个账单结算完成");
            else if (updateNum != 0)
                return ResponseUtil.fail(0, "个人结算完成");
            else
                return ResponseUtil.fail(-1, "结算出现问题，请再次确认后再次结算");
        }
        if (updateNum != 1)
            return ResponseUtil.fail(-1, "结算出现问题，请再次确认后再次结算");
        return ResponseUtil.fail(0, "个人结算完成");
    }

}