package org.myoranges.sotwo.os.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.myoranges.sotwo.core.util.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/os/index")
public class OsIndexController {
    private final Log logger = LogFactory.getLog(OsIndexController.class);

    @RequestMapping("/index")
    public Object index(){
        return ResponseUtil.ok("hello world, this is os service");
    }

}
