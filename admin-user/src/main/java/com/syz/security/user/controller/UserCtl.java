package com.syz.security.user.controller;

import com.syz.security.common.rest.BaseController;
import com.syz.security.user.entity.BaseUser;
import com.syz.security.user.service.BaseUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserCtl extends BaseController<BaseUserService,BaseUser>{
  /*  @Value("${language.en}")
    private String language;*/

}
