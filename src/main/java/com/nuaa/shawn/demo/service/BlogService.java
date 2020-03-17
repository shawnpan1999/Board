package com.nuaa.shawn.demo.service;

import com.nuaa.shawn.demo.dao.BlogDAO;
import com.nuaa.shawn.demo.model.Blog;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogService {
    @Autowired
    private BlogDAO blogDAO;

    public List<Blog> getLatestBlog(int offset, int limit) {
        return blogDAO.selectByOffset(offset, limit);
    }

    public Blog getById(int newsId) {
        return blogDAO.getById(newsId);
    }

    public Map<String, Object> addBlog(int authorId, String text) {
        Map<String, Object> map = new HashMap<String, Object>();    //这里考虑用一个映射来返回注册的结果
        if (authorId == 0) {
            map.put("msgerr", "您还未登录！");
            return map;
        }
        if (StringUtils.isBlank(text)) {    //apache 给的包，判断字符串是不是空
            map.put("msgerr", "留言信息不能为空！");
            return map;
        }
        Blog blog = new Blog();
        blog.setAuthorId(authorId);
        Date date = new Date();
        blog.setCreatedDate(date);
        blog.setText(text);
        blog.setCommentCount(0);
        blog.setLikeCount(0);

        blogDAO.addBlog(blog);

        map.put("success", "1");
        return map;
    }

    public int updateCommentCount(int id, int count) {
        return blogDAO.updateCommentCount(id, count);
    }

    public int updateLikeCount(int id, int count) {
        return blogDAO.updateLikeCount(id, count);
    }
}
