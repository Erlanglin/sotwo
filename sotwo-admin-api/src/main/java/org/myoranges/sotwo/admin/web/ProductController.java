package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.db.domain.sotwoGoods;
import org.myoranges.sotwo.db.domain.sotwoProduct;
import org.myoranges.sotwo.db.service.sotwoGoodsService;
import org.myoranges.sotwo.db.service.sotwoGoodsSpecificationService;
import org.myoranges.sotwo.db.service.sotwoProductService;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/product")
public class ProductController {
    private final Log logger = LogFactory.getLog(ProductController.class);

    @Autowired
    private sotwoProductService productService;
    @Autowired
    private sotwoGoodsService goodsService;
    @Autowired
    private sotwoGoodsSpecificationService goodsSpecificationService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       Integer goodsId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<sotwoProduct> productList = productService.querySelective(goodsId, page, limit, sort, order);
        int total = productService.countSelective(goodsId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", productList);

        return ResponseUtil.ok(data);
    }

    /**
     *
     * @param adminId
     * @param sotwoProduct
     * @return
     */
    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody sotwoProduct sotwoProduct){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        Integer goodsId = sotwoProduct.getGoodsId();
        if(goodsId == null){
            return ResponseUtil.badArgument();
        }

        sotwoGoods goods = goodsService.findById(goodsId);
        if(goods == null){
            return ResponseUtil.badArgumentValue();
        }

        List<sotwoProduct> productList = productService.queryByGid(goodsId);
        if(productList.size() != 0){
            return ResponseUtil.badArgumentValue();
        }

        Integer[] goodsSpecificationIds = goodsSpecificationService.queryIdsByGid(goodsId);
        if(goodsSpecificationIds.length == 0) {
            return ResponseUtil.serious();
        }

        sotwoProduct product = new sotwoProduct();
        product.setGoodsId(goodsId);
        product.setGoodsNumber(0);
        product.setRetailPrice(new BigDecimal(0.00));
        product.setGoodsSpecificationIds(goodsSpecificationIds);
        productService.add(product);

        return ResponseUtil.ok();
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        if(id == null){
            return ResponseUtil.badArgument();
        }

        sotwoProduct product = productService.findById(id);
        return ResponseUtil.ok(product);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody sotwoProduct product){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        productService.updateById(product);
        return ResponseUtil.ok(product);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody sotwoProduct product){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        productService.deleteById(product.getId());
        return ResponseUtil.ok();
    }

}
