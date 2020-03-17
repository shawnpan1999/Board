package com.nuaa.shawn.demo.controller;

import com.nuaa.shawn.demo.model.Blog;
import com.nuaa.shawn.demo.model.EntityType;
import com.nuaa.shawn.demo.model.HostHolder;
import com.nuaa.shawn.demo.model.ViewObject;
import com.nuaa.shawn.demo.service.BlogService;
import com.nuaa.shawn.demo.service.LikeService;
import com.nuaa.shawn.demo.service.UserService;
import com.nuaa.shawn.demo.util.DemoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    //Controller 通过 service 调用 DAO
    @Autowired
    UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    LikeService likeService;

    private List<ViewObject> getBlog(int offset, int limit) {
        List<Blog> blogList = blogService.getLatestBlog(offset, limit);
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;

        List<ViewObject> vos = new ArrayList<>();
        for (Blog blog : blogList) {
            ViewObject vo = new ViewObject();
            vo.set("blog", blog);    //存入 blog 对象
            vo.set("author", userService.getUser(blog.getAuthorId()));    //存入作者 user 对象
            if (localUserId != 0) {
                vo.set("likestatus", likeService.getLikeStatus(localUserId, EntityType.ENTITY_BLOG, blog.getId()));
            } else {
                vo.set("likestatus", 0);
            }
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String board(Model model) {
        model.addAttribute("vos", getBlog(0, 25));
        return "board";
    }

    @RequestMapping(path = {"/addblog"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String addBlog(Model model, @RequestParam("authorId") int authorId,
                          @RequestParam("text") String text,
                          HttpServletResponse response) {
        try {
            Map<String, Object> map = blogService.addBlog(authorId, text);    //map 是 service 层处理注册信息返回的结果
            if (map.containsKey("success")) {
                return DemoUtil.getJSONString(0, "留言成功");
            } else {
                return DemoUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("留言异常" + e.getMessage());
            return DemoUtil.getJSONString(1, "留言异常");
        }
    }
}
