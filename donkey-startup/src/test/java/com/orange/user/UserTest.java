package com.orange.user;


import com.orange.AppStart;
import com.orange.person.dao.UserBaseDao;
import com.orange.person.dao.UserConnectionDao;
import com.orange.person.domain.UserBase;
import com.orange.person.domain.UserConnection;
import com.orange.person.service.UserBaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes= AppStart.class)// 指定spring-boot的启动类

public class UserTest {
    @Autowired
    UserConnectionDao userConnectionDao;
    @Autowired
    UserBaseDao userBaseDao;

//    @Test
    public void add(){
        UserBase userBase = new UserBase();
        userBase.setNickname("lalalla");
        userBase.setUsername("12313");
        userBaseDao.save(userBase);
        UserConnection userConnection = new UserConnection();
        userConnection.setUserId(userBase.getUserId());
        userConnectionDao.save(userConnection);
    }
//    @Test
    public void zhuanhuan(){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, 1);// 24小时制
        date = cal.getTime();

        System.out.println("front:" + format.format(date)); //显示输入的日期
        String time_expire = format.format(date);
    }

}
