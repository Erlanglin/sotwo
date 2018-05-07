package org.myoranges.sotwo.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.myoranges.sotwo.db.domain.*;
import org.myoranges.sotwo.db.service.*;
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
    private SotwoAdService adService;
    @Autowired
    private SotwoGoodsService goodsService;
    @Autowired
    private SotwoBrandService brandService;
    @Autowired
    private SotwoTopicService topicService;
    @Autowired
    private SotwoCategoryService categoryService;
    @Autowired
    private SotwoCartService cartService;

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

        List<SotwoAd> banner = adService.queryByApid(1);
        data.put("banner", banner);

        List<SotwoCategory> channel = categoryService.queryChannel();
        data.put("channel", channel);

        List<SotwoGoods> newGoods = goodsService.queryByNew(0, 4);
        data.put("newGoodsList", newGoods);

        List<SotwoGoods> hotGoods = goodsService.queryByHot(0, 3);
        data.put("hotGoodsList", hotGoods);

        List<SotwoBrand> brandList = brandService.query(0,4);
        data.put("brandList", brandList);

        List<SotwoTopic> topicList = topicService.queryList(0, 3);
        data.put("topicList", topicList);

        List<Map> categoryList = new ArrayList<>();
        List<SotwoCategory> catL1List = categoryService.queryL1WithoutRecommend(0, 6);
        for (SotwoCategory catL1 : catL1List) {
            List<SotwoCategory> catL2List = categoryService.queryByPid(catL1.getId());
            List<Integer> l2List = new ArrayList<>();
            for (SotwoCategory catL2 : catL2List) {
                l2List.add(catL2.getId());
            }

            List<SotwoGoods> categoryGoods = goodsService.queryByCategory(l2List, 0, 5);
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