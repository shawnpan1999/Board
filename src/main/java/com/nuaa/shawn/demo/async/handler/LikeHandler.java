package com.nuaa.shawn.demo.async.handler;

import com.nuaa.shawn.demo.async.EventHandler;
import com.nuaa.shawn.demo.async.EventModel;
import com.nuaa.shawn.demo.async.EventType;
import com.nuaa.shawn.demo.dao.BlogDAO;
import com.nuaa.shawn.demo.model.Blog;
import com.nuaa.shawn.demo.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {
    @Autowired
    BlogService blogService;
    @Override
    public void doHandle(EventModel model) {
        // TODO: DO YOUR HANDLE
        //向数据库中更新数据
        Blog blog = blogService.getById(model.getEntityId());
        blogService.updateLikeCount(model.getEntityId(), Integer.parseInt(model.getExt("likeCount")));
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
