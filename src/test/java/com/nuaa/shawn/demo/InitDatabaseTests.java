package com.nuaa.shawn.demo;

import com.nuaa.shawn.demo.dao.LoginTicketDAO;
import com.nuaa.shawn.demo.model.LoginTicket;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@Sql("/init-schema.sql")    //表示跑之前先执行一下写好的 sql 文件（把数据库创建起来）
public class InitDatabaseTests {

    @Test
    public void initData() {
        System.out.println("Done.");
    }
}
