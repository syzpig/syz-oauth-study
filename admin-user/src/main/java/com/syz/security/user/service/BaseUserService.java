package com.syz.security.user.service;

import com.syz.security.common.biz.BaseBiz;
import com.syz.security.user.entity.BaseUser;
import com.syz.security.user.mapper.BaseUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BaseUserService extends BaseBiz<BaseUserMapper,BaseUser>{
}
