package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.db.domain.sotwoRegion;
import org.myoranges.sotwo.db.service.sotwoRegionService;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/region")
public class RegionController {
    private final Log logger = LogFactory.getLog(RegionController.class);

    @Autowired
    private sotwoRegionService regionService;

    @GetMapping("/clist")
    public Object clist(@LoginAdmin Integer adminId, Integer id) {
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        List<sotwoRegion> regionList = regionService.queryByPid(id);

        return ResponseUtil.ok(regionList);
    }

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String name, Integer code,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<sotwoRegion> regionList = regionService.querySelective(name, code, page, limit, sort, order);
        int total = regionService.countSelective(name, code, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", regionList);

        return ResponseUtil.ok(data);
    }
}
