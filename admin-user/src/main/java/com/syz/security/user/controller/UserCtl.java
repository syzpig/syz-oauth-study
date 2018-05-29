package com.syz.security.user.controller;

import com.syz.security.common.rest.BaseController;
import com.syz.security.user.entity.BaseUser;
import com.syz.security.user.service.BaseUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("test1")
public class UserCtl extends BaseController<BaseUserService,BaseUser>{
    @Autowired
   private BaseUserService baseUserService;

    @Value("${language.en}")
    private String language;

    @RequestMapping("aa")
    public List<BaseUser> get(){
        return baseUserService.selectListAll();
    }
}
