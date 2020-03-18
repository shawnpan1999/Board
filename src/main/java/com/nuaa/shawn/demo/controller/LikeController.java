package com.nuaa.shawn.demo.controller;

import com.nuaa.shawn.demo.async.EventModel;
import com.nuaa.shawn.demo.async.EventProducer;
import com.nuaa.shawn.demo.async.EventType;
import com.nuaa.shawn.demo.model.EntityType;
import com.nuaa.shawn.demo.model.Blog;
import com.nuaa.shawn.demo.model.HostHolder;
import com.nuaa.shawn.demo.service.LikeService;
import com.nuaa.shawn.demo.service.BlogService;
import com.nuaa.shawn.demo.util.DemoUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    BlogService blogService;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/like"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String like(@Param("blogId") int blogId) {
        long likeCount = likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_BLOG, blogId);
        // 更新喜欢数
        Blog blog = blogService.getById(blogId);
        blogService.updateLikeCount(blogId, (int) likeCount);   //更新喜欢数据
        /** 这边是一个骚操作，eventProducer每个setter方法都返回了自己(return this;)，因此可以一直.一直.来set整个eventProducer */
        //记录现场(EventModel)，发射到队列中
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                                .setEntityOwnerId(blog.getAuthorId())
                                .setActorId(hostHolder.getUser().getId())
                                .setEntityId(blogId)
                                .setEntityType(EntityType.ENTITY_BLOG));
        /** 这样就可以避免写大量(各种参数组合的)构造函数了 */
        return DemoUtil.getJSONString(0, String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String dislike(@Param("blogId") int blogId) {
        long likeCount = likeService.dislike(hostHolder.getUser().getId(), EntityType.ENTITY_BLOG, blogId);
        // 更新喜欢数
//        Blog blog = blogService.getById(newsId);
        blogService.updateLikeCount(blogId, (int) likeCount);   //更新喜欢数据
//        eventProducer.fireEvent(new EventModel(EventType.LIKE)
//                .setEntityOwnerId(blogs.getAutohrId())
//                .setActorId(hostHolder.getUser().getId()).setEntityId(newsId));
        return DemoUtil.getJSONString(0, String.valueOf(likeCount));
    }
}
