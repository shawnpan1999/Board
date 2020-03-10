package com.nuaa.shawn.demo.controller;

import com.nuaa.shawn.demo.model.Message;
import com.nuaa.shawn.demo.model.ViewObject;
import com.nuaa.shawn.demo.service.MessageService;
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
    MessageService messageService;

    private List<ViewObject> getMessage(int offset, int limit) {
        List<Message> messageList = messageService.getLatestMessage(offset, limit);

        List<ViewObject> vos = new ArrayList<>();
        for (Message message : messageList) {
            ViewObject vo = new ViewObject();
            vo.set("message", message);    //存入 message 对象
            vo.set("author", userService.getUser(message.getAuthorId()));    //存入作者 user 对象
            vos.add(vo);
        }
        return vos;
    }

    @RequestMapping(path = {"/addmsg"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String addMessage(Model model, @RequestParam("authorId") int authorId,
                      @RequestParam("text") String text,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = messageService.addMessage(authorId, text);    //map 是 service 层处理注册信息返回的结果
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

    @RequestMapping(path = {"/"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String board(Model model) {
        model.addAttribute("vos", getMessage(0, 25));
        return "board";
    }
}
