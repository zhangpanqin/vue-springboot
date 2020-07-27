package com.fly.vue.deploy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张攀钦
 * @date 2020-07-27-19:40
 */
@RestController
public class TestController {
    @GetMapping("/demo22")
    public String test1() {
        return "test";
    }
}
