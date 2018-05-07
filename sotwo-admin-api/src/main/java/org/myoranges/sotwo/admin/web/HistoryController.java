package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.myoranges.sotwo.db.domain.SotwoSearchHistory;
import org.myoranges.sotwo.db.service.SotwoSearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/history")
public class HistoryController {
    private final Log logger = LogFactory.getLog(HistoryController.class);

    @Autowired
    private SotwoSearchHistoryService searchHistoryService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String userId, String keyword,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<SotwoSearchHistory> footprintList = searchHistoryService.querySelective(userId, keyword, page, limit, sort, order);
        int total = searchHistoryService.countSelective(userId, keyword, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", footprintList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody SotwoSearchHistory history){
        if(adminId == null){
            return ResponseUtil.fail401();
        }
        return ResponseUtil.fail501();
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        if(id == null){
            return ResponseUtil.badArgument();
        }

        SotwoSearchHistory history = searchHistoryService.findById(id);
        return ResponseUtil.ok(history);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody SotwoSearchHistory history){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        searchHistoryService.updateById(history);
        return ResponseUtil.ok();
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody SotwoSearchHistory history){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        searchHistoryService.deleteById(history.getId());
        return ResponseUtil.ok();
    }

}
