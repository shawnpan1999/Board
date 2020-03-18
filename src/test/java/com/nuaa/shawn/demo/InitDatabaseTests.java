package com.nuaa.shawn.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
@Sql("/init-schema.sql")    //跑之前先执行一下写好的 sql 文件（初始化数据库）
public class InitDatabaseTests {

    @Test
    public void initData() {
        System.out.println("Done.");
    }
}
