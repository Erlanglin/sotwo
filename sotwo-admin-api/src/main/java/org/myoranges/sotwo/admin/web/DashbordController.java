package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.myoranges.sotwo.db.service.SotwoGoodsService;
import org.myoranges.sotwo.db.service.SotwoOrderService;
import org.myoranges.sotwo.db.service.SotwoProductService;
import org.myoranges.sotwo.db.service.SotwoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/dashboard")
public class DashbordController {
    private final Log logger = LogFactory.getLog(DashbordController.class);

    @Autowired
    private SotwoUserService userService;
    @Autowired
    private SotwoGoodsService goodsService;
    @Autowired
    private SotwoProductService productService;
    @Autowired
    private SotwoOrderService orderService;

    @GetMapping("")
    public Object info(@LoginAdmin Integer adminId){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        int userTotal = userService.count();
        int goodsTotal = goodsService.count();
        int productTotal = productService.count();
        int orderTotal = orderService.count();
        Map<String, Integer> data = new HashMap<>();
        data.put("userTotal", userTotal);
        data.put("goodsTotal", goodsTotal);
        data.put("productTotal", productTotal);
        data.put("orderTotal", orderTotal);

        return ResponseUtil.ok(data);
    }

}
