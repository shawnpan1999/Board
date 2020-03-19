package com.nuaa.shawn.demo;

import com.alibaba.fastjson.JSONObject;
import com.nuaa.shawn.demo.model.Blog;
import com.nuaa.shawn.demo.service.BlogService;
import com.nuaa.shawn.demo.util.JedisAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DemoApplication.class)
//@Sql("/init-schema.sql")    //跑之前先执行一下写好的 sql 文件（初始化数据库）
public class InitDatabaseTests {
    @Autowired
    JedisAdapter jedisAdapter;
    @Autowired
    BlogService blogService;

    @Test
    public void initHomepageBlogCache() {
        Map<String, String> blogMap = new HashMap<String, String>();
        List<Blog> blogList = blogService.getLatestBlog(0, 10);
        for (Blog blog : blogList) {
            blogMap.put(String.valueOf(blog.getId()), JSONObject.toJSONString(blog));
        }
        jedisAdapter.hmset(JedisAdapter.REDIS_BLOGLIST_KEY, blogMap);
        System.out.println("Done.");
    }
}
