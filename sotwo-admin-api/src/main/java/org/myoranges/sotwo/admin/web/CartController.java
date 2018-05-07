package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.db.domain.sotwoCart;
import org.myoranges.sotwo.db.service.sotwoCartService;
import org.myoranges.sotwo.db.service.sotwoGoodsService;
import org.myoranges.sotwo.db.service.sotwoProductService;
import org.myoranges.sotwo.db.service.sotwoUserService;
import org.myoranges.sotwo.core.util.ResponseUtil;
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
    private sotwoCartService cartService;
    @Autowired
    private sotwoUserService userService;
    @Autowired
    private sotwoGoodsService goodsService;
    @Autowired
    private sotwoProductService productService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       Integer userId, Integer goodsId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.fail401();
        }
        List<sotwoCart> cartList = cartService.querySelective(userId, goodsId, page, limit, sort, order);
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
    public Object create(@LoginAdmin Integer adminId, @RequestBody sotwoCart cart){
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

        sotwoCart cart = cartService.findById(id);
        return ResponseUtil.ok(cart);
    }

    /*
     * 目前的逻辑不支持管理员创建
     */
    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody sotwoCart cart){
        if(adminId == null){
            return ResponseUtil.fail401();
        }
        return ResponseUtil.fail501();
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody sotwoCart cart){
        if(adminId == null){
            return ResponseUtil.fail401();
        }
        cartService.deleteById(cart.getId());
        return ResponseUtil.ok();
    }

}
