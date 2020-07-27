package com.fly.vue.deploy.vue;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 张攀钦
 * 首页访问
 */
@Controller
public class IndexController {
    @RequestMapping(value = {"/test", "/test"})
    public String index() {
        return "forward:/test/index.html";
    }
}
