package com.nuaa.shawn.demo;

import com.nuaa.shawn.demo.dao.LoginTicketDAO;
import com.nuaa.shawn.demo.dao.NewsDAO;
import com.nuaa.shawn.demo.dao.UserDAO;
import com.nuaa.shawn.demo.model.LoginTicket;
import com.nuaa.shawn.demo.model.News;
import com.nuaa.shawn.demo.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@Sql("/init-schema.sql")    //表示跑之前先执行一下写好的 sql 文件（把数据库创建起来）
public class InitDatabaseTests {
    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Test
    public void initData() {
        Random random = new Random();
        for (int i = 0; i < 11; ++i) {
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*5*i);
            LoginTicket ticket = new LoginTicket();
            ticket.setStatus(0);
            ticket.setUserId(i+1);
            ticket.setExpired(date);
            ticket.setTicket(String.format("TICKET%d", i+1));
            loginTicketDAO.addTicket(ticket);

            loginTicketDAO.updateStatus(ticket.getTicket(), 2);
        }

        Assert.assertEquals(1, loginTicketDAO.selectByTicket("TICKET1").getUserId());
        Assert.assertEquals(2, loginTicketDAO.selectByTicket("TICKET1").getStatus());

    }
}
