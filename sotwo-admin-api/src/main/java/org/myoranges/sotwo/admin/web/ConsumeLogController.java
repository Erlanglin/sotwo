package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.myoranges.sotwo.db.domain.SotwoConsumeLog;
import org.myoranges.sotwo.db.service.SotwoConsumeLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/consumeLog")
public class ConsumeLogController {
    private final Log logger = LogFactory.getLog(ConsumeLogController.class);

    @Autowired
    private SotwoConsumeLogService consumeLogService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       SotwoConsumeLog sotwoConsumeLog,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order) {
        if (adminId == null) {
            return ResponseUtil.fail401();
        }
        List<SotwoConsumeLog> consumeLogList = consumeLogService.querySelective(sotwoConsumeLog, page, limit, sort, order);
        int total = consumeLogService.countSeletive(sotwoConsumeLog, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", consumeLogList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        SotwoConsumeLog consumeLog = consumeLogService.findById(id);
        return ResponseUtil.ok(consumeLog);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody SotwoConsumeLog consumeLog) {
        logger.debug(consumeLog);
        consumeLogService.add(consumeLog);
        return ResponseUtil.ok(consumeLog);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody SotwoConsumeLog consumeLog) {
        logger.debug(consumeLog);
        consumeLogService.update(consumeLog);
        return ResponseUtil.ok(consumeLog);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody SotwoConsumeLog consumeLog) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        consumeLogService.deleteById(consumeLog.getId());
        return ResponseUtil.ok();
    }

}