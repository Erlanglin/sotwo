package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.myoranges.sotwo.db.domain.SotwoCart;
import org.myoranges.sotwo.db.service.SotwoCartService;
import org.myoranges.sotwo.db.service.SotwoGoodsService;
import org.myoranges.sotwo.db.service.SotwoProductService;
import org.myoranges.sotwo.db.service.SotwoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/cart")
public class CartController {
    private final Log logger = LogFactory.getLog(CartController.class);

    @Autowired
    private SotwoCartService cartService;
    @Autowired
    private SotwoUserService userService;
    @Autowired
    private SotwoGoodsService goodsService;
    @Autowired
    private SotwoProductService productService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       Integer userId, Integer goodsId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.fail401();
        }
        List<SotwoCart> cartList = cartService.querySelective(userId, goodsId, page, limit, sort, order);
        int total = cartService.countSelective(userId, goodsId, page, limit, sort, order);

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", cartList);

        return ResponseUtil.ok(data);
    }

    /*
     * 目前的逻辑不支持管理员创建
     */
    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody SotwoCart cart){
        if(adminId == null){
            return ResponseUtil.fail401();
        }

        return ResponseUtil.fail501();
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.fail401();
        }

        SotwoCart cart = cartService.findById(id);
        return ResponseUtil.ok(cart);
    }

    /*
     * 目前的逻辑不支持管理员创建
     */
    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody SotwoCart cart){
        if(adminId == null){
            return ResponseUtil.fail401();
        }
        return ResponseUtil.fail501();
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody SotwoCart cart){
        if(adminId == null){
            return ResponseUtil.fail401();
        }
        cartService.deleteById(cart.getId());
        return ResponseUtil.ok();
    }

}
