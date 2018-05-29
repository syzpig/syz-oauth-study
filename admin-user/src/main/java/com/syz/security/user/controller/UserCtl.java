package com.syz.security.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserCtl {
    @Value("${language.en}")
    private String language;

    @RequestMapping("test")
    @ResponseBody
    public String test() throws InterruptedException {
        Thread.sleep(7000);
        return language;
    }
}
