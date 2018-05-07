package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.db.domain.sotwoGoods;
import org.myoranges.sotwo.db.service.sotwoGoodsService;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/goods")
public class GoodsController {
    private final Log logger = LogFactory.getLog(GoodsController.class);

    @Autowired
    private sotwoGoodsService goodsService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String goodsSn, String name,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<sotwoGoods> goodsList = goodsService.querySelective(goodsSn, name, page, limit, sort, order);
        int total = goodsService.countSelective(goodsSn, name, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", goodsList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody sotwoGoods goods){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        goodsService.add(goods);
        return ResponseUtil.ok(goods);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        if(id == null){
            return ResponseUtil.badArgument();
        }

        sotwoGoods goods = goodsService.findById(id);
        return ResponseUtil.ok(goods);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody sotwoGoods goods){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        goodsService.updateById(goods);
        return ResponseUtil.ok(goods);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody sotwoGoods goods){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        goodsService.deleteById(goods.getId());
        return ResponseUtil.ok();
    }

}
