package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.db.domain.sotwoFootprint;
import org.myoranges.sotwo.db.service.sotwoFootprintService;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/footprint")
public class FootprintController {
    private final Log logger = LogFactory.getLog(FootprintController.class);

    @Autowired
    private sotwoFootprintService footprintService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String userId, String goodsId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<sotwoFootprint> footprintList = footprintService.querySelective(userId, goodsId, page, limit, sort, order);
        int total = footprintService.countSelective(userId, goodsId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", footprintList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody sotwoFootprint footprint){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.unsupport();
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        if(id == null){
            return ResponseUtil.badArgument();
        }

        sotwoFootprint footprint = footprintService.findById(id);
        return ResponseUtil.ok(footprint);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody sotwoFootprint footprint){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        footprintService.updateById(footprint);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody sotwoFootprint footprint){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        footprintService.deleteById(footprint.getId());
        return ResponseUtil.ok();
    }

}
