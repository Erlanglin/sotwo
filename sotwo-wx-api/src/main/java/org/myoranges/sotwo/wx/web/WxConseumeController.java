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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private SotwoProductService productService;
    @Autowired
    private SotwoIssueService goodsIssueService;
    @Autowired
    private SotwoGoodsAttributeService goodsAttributeService;
    @Autowired
    private SotwoBrandService brandService;
    @Autowired
    private SotwoCommentService commentService;
    @Autowired
    private SotwoUserService userService;
    @Autowired
    private SotwoCollectService collectService;
    @Autowired
    private SotwoFootprintService footprintService;
    @Autowired
    private SotwoCategoryService categoryService;
    @Autowired
    private SotwoSearchHistoryService searchHistoryService;
    @Autowired
    private SotwoCouponService apiCouponService;
    @Autowired
    private SotwoCartService cartService;
    @Autowired
    private SotwoGoodsSpecificationService goodsSpecificationService;

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
    public Object logList(Integer consumeCategoryId,
                          Integer payUserId,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size,
                          String sort, String order) {


        //查询列表数据
        List<SotwoConsumeLog> consumeLogsList = consumeLogService.querySelective(consumeCategoryId, payUserId, page, size, sort, order);
        int total = consumeLogService.countSeletive(consumeCategoryId, payUserId, page, size, sort, order);


        Map<String, Object> data = new HashMap<>();
        data.put("consumeLogsList", consumeLogsList);
        data.put("count", total);
        return ResponseUtil.ok(data);
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
    public Object infoList(Integer consumeLogId, Integer userId,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "10") Integer size,
                           String sort, String order) {


        //查询列表数据
        List<SotwoConsumeInfo> consumeInfosList = consumeInfoService.querySelective(consumeLogId, userId, page, size, sort, order);
        int total = consumeInfoService.countSeletive(consumeLogId, userId, page, size, sort, order);

        Map<String, Object> data = new HashMap<>();
        data.put("consumeLogsList", consumeInfosList);
        data.put("count", total);
        return ResponseUtil.ok(data);
    }


}