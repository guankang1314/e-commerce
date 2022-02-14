package com.imooc.ecommerce.service;

import com.imooc.ecommerce.filter.AccessContext;
import com.imooc.ecommerce.vo.LoginUserInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qingtian
 * @version 1.0
 * @description: 测试用例基类，用于填充用户信息
 * @date 2022/1/17 23:37
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class BaseTest {

    protected final LoginUserInfo loginUserInfo = new LoginUserInfo(
            10L,"moocguankang@163.com"
    );

    @Before
    public void init() {
        AccessContext.setLoginUserInfo(loginUserInfo);
    }

    @After
    public void destroy() {
        AccessContext.clearLoginUserInfo();
    }
}
