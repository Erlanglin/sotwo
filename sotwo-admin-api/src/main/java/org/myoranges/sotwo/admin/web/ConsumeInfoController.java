package org.myoranges.sotwo.admin.web;

import com.github.pagehelper.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.myoranges.sotwo.db.domain.SotwoConsumeInfo;
import org.myoranges.sotwo.db.domain.SotwoConsumeLog;
import org.myoranges.sotwo.db.service.SotwoConsumeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/consumeInfo")
public class ConsumeInfoController {
    private final Log logger = LogFactory.getLog(ConsumeInfoController.class);

    @Autowired
    private SotwoConsumeInfoService consumeInfoService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       int consumeLogId, int userId,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order) {
/*        if(adminId == null){
            return ResponseUtil.fail401();
        }*/
        logger.debug(adminId);

        List<SotwoConsumeInfo> consumeInfoList = consumeInfoService.querySelective(consumeLogId, userId, page, limit, sort, order);
        int total = consumeInfoService.countSeletive(consumeLogId, userId, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", consumeInfoList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/consumeLogId")
    public Object consumeInfoname(int consumeLogId) {
        if (consumeLogId == 0) {
            return ResponseUtil.fail402();
        }

        int total = consumeInfoService.countSeletive(consumeLogId, null, null, null, null, null);
        if (total == 0) {
            return ResponseUtil.ok("不存在");
        }
        return ResponseUtil.ok("已存在");
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        if (id == null) {
            return ResponseUtil.badArgument();
        }

        SotwoConsumeInfo consumeInfo = consumeInfoService.findById(id);
        return ResponseUtil.ok(consumeInfo);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody SotwoConsumeInfo consumeInfo) {
        logger.debug(consumeInfo);

        consumeInfoService.add(consumeInfo);
        return ResponseUtil.ok(consumeInfo);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody SotwoConsumeInfo consumeInfo) {
        logger.debug(consumeInfo);

        consumeInfoService.update(consumeInfo);
        return ResponseUtil.ok(consumeInfo);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody SotwoConsumeInfo consumeInfo) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        consumeInfoService.deleteById(consumeInfo.getId());
        return ResponseUtil.ok();
    }


}
