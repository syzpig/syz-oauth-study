package com.syz.security.transaction.servicea.servicea.service;

import com.syz.security.transaction.servicea.servicea.annotation.SYZTransaction;
import com.syz.security.transaction.servicea.servicea.dao.DemoDao;
import org.springframework.stereotype.Service;

import java.beans.Transient;

@Service
public class DemoService {
    private DemoDao demoDao;

    @Transient
    @SYZTransaction(isStart = true)//加了他就代表的开始
    public void test(){

    }
}
