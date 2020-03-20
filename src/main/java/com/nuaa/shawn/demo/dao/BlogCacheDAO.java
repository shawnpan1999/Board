package com.nuaa.shawn.demo.dao;

import com.alibaba.fastjson.JSONObject;
import com.nuaa.shawn.demo.model.Blog;
import com.nuaa.shawn.demo.util.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BlogCacheDAO {

    public static String BLOGLIST_KEY = "blogList";    //存储首页留言列表的 redis key
    @Autowired
    JedisAdapter jedisAdapter;

    public List<Blog> getBlogsByOffset(int offset, int limit) {
        List<String> stringList = jedisAdapter.hvals(BLOGLIST_KEY);
        limit = Math.min(limit, stringList.size());
        List<Blog> blogList = new ArrayList<>();
        for (int i = offset; i < limit; i++) {
            blogList.add(JSONObject.parseObject(stringList.get(i), Blog.class));
        }
        return blogList;
    }
}
