package org.myoranges.sotwo.admin.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.myoranges.sotwo.db.domain.SotwoTopic;
import org.myoranges.sotwo.db.service.SotwoTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/topic")
public class TopicController {
    private final Log logger = LogFactory.getLog(TopicController.class);

    @Autowired
    private SotwoTopicService topicService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String title, String subtitle,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        List<SotwoTopic> topicList = topicService.querySelective(title, subtitle, page, limit, sort, order);
        int total = topicService.countSelective(title, subtitle, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", topicList);

        return ResponseUtil.ok(data);
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody SotwoTopic topic){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        topicService.add(topic);
        return ResponseUtil.ok(topic);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, Integer id){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }

        if(id == null){
            return ResponseUtil.badArgument();
        }

        SotwoTopic brand = topicService.findById(id);
        return ResponseUtil.ok(brand);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody SotwoTopic topic){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        topicService.updateById(topic);
        return ResponseUtil.ok(topic);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody SotwoTopic topic){
        if(adminId == null){
            return ResponseUtil.unlogin();
        }
        topicService.deleteById(topic.getId());
        return ResponseUtil.ok();
    }

}
