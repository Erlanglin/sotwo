package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.db.domain.sotwoOrder;
import org.myoranges.sotwo.db.service.sotwoOrderService;
import org.myoranges.sotwo.db.util.OrderUtil;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/order")
public class OrderController {
    private final Log logger = LogFactory.getLog(OrderController.class);

    @Autowired
    private sotwoOrderService orderService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       Integer userId, String orderSn,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.fail401();
        }
        List<sotwoOrder> orderList = orderService.querySelective(userId, orderSn, page, limit, sort, order);
        int total = orderService.countSelective(userId, orderSn, page, limit, sort, order);

        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", orderList);

        return ResponseUtil.ok(data);
    }

    /*
     * 目前的逻辑不支持管理员创建
     */
    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody sotwoOrder order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.unsupport();
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.fail401();
        }

        sotwoOrder order = orderService.findById(id);
        return ResponseUtil.ok(order);
    }

    /*
     * 目前仅仅支持管理员设置发货相关的信息
     */
    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody sotwoOrder order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        Integer orderId = order.getId();
        if(orderId == null){
            return ResponseUtil.badArgument();
        }

        sotwoOrder sotwoOrder = orderService.findById(orderId);
        if(sotwoOrder == null){
            return ResponseUtil.badArgumentValue();
        }

        if(OrderUtil.isPayStatus(sotwoOrder) || OrderUtil.isShipStatus(sotwoOrder)){
            sotwoOrder newOrder = new sotwoOrder();
            newOrder.setId(orderId);
            newOrder.setShipChannel(order.getShipChannel());
            newOrder.setShipSn(order.getOrderSn());
            newOrder.setShipStartTime(order.getShipStartTime());
            newOrder.setShipEndTime(order.getShipEndTime());
            newOrder.setOrderStatus(OrderUtil.STATUS_SHIP);
            orderService.update(newOrder);
        }
        else {
            return ResponseUtil.badArgumentValue();
        }

        sotwoOrder = orderService.findById(orderId);
        return ResponseUtil.ok(sotwoOrder);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody sotwoOrder order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.unsupport();
    }

}
