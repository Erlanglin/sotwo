package org.myoranges.sotwo.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.db.domain.*;
import org.myoranges.sotwo.db.service.*;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/home")
public class WxHomeController {
    private final Log logger = LogFactory.getLog(WxHomeController.class);

    @Autowired
    private sotwoAdService adService;
    @Autowired
    private sotwoGoodsService goodsService;
    @Autowired
    private sotwoBrandService brandService;
    @Autowired
    private sotwoTopicService topicService;
    @Autowired
    private sotwoCategoryService categoryService;
    @Autowired
    private sotwoCartService cartService;

    /**
     * app首页
     *
     * @return app首页相关信息
     *   成功则
     *  {
     *      errno: 0,
     *      errmsg: '成功',
     *      data:
     *          {
     *              banner: xxx,
     *              channel: xxx,
     *              newGoodsList: xxx,
     *              hotGoodsList: xxx,
     *              topicList: xxx,
     *              floorGoodsList: xxx
     *          }
     *  }
     *   失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("/index")
    public Object index() {
        Map<String, Object> data = new HashMap<>();

        List<sotwoAd> banner = adService.queryByApid(1);
        data.put("banner", banner);

        List<sotwoCategory> channel = categoryService.queryChannel();
        data.put("channel", channel);

        List<sotwoGoods> newGoods = goodsService.queryByNew(0, 4);
        data.put("newGoodsList", newGoods);

        List<sotwoGoods> hotGoods = goodsService.queryByHot(0, 3);
        data.put("hotGoodsList", hotGoods);

        List<sotwoBrand> brandList = brandService.query(0,4);
        data.put("brandList", brandList);

        List<sotwoTopic> topicList = topicService.queryList(0, 3);
        data.put("topicList", topicList);

        List<Map> categoryList = new ArrayList<>();
        List<sotwoCategory> catL1List = categoryService.queryL1WithoutRecommend(0, 6);
        for (sotwoCategory catL1 : catL1List) {
            List<sotwoCategory> catL2List = categoryService.queryByPid(catL1.getId());
            List<Integer> l2List = new ArrayList<>();
            for (sotwoCategory catL2 : catL2List) {
                l2List.add(catL2.getId());
            }

            List<sotwoGoods> categoryGoods = goodsService.queryByCategory(l2List, 0, 5);
            Map catGoods = new HashMap();
            catGoods.put("id", catL1.getId());
            catGoods.put("name", catL1.getName());
            catGoods.put("goodsList", categoryGoods);
            categoryList.add(catGoods);
        }
        data.put("floorGoodsList", categoryList);

        return ResponseUtil.ok(data);
    }
}