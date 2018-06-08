package org.myoranges.sotwo.admin.web;

import com.github.pagehelper.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.admin.annotation.LoginAdmin;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.myoranges.sotwo.db.domain.SotwoUser;
import org.myoranges.sotwo.db.service.SotwoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class UserController {
    private final Log logger = LogFactory.getLog(UserController.class);

    @Autowired
    private SotwoUserService userService;

    @GetMapping("/list")
    public Object list(String username, String mobile,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                       String sort, String order){

        List<SotwoUser> userList = userService.querySelective(username, mobile, page, limit, sort, order);
        int total = userService.countSeletive(username, mobile, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", userList);

        return ResponseUtil.ok(data);
    }

    @GetMapping("/username")
    public Object username(String username){
        if(StringUtil.isEmpty(username)){
            return ResponseUtil.fail402();
        }

        int total = userService.countSeletive(username, null, null, null, null, null);
        if(total == 0){
            return ResponseUtil.ok("不存在");
        }
        return ResponseUtil.ok("已存在");
    }


    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody SotwoUser user){
        logger.debug(user);

        userService.add(user);
        return ResponseUtil.ok(user);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody SotwoUser user){
        logger.debug(user);

        userService.update(user);
        return ResponseUtil.ok(user);
    }
}
