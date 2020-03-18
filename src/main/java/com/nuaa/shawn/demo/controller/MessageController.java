package com.nuaa.shawn.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.nuaa.shawn.demo.model.*;
import com.nuaa.shawn.demo.service.MessageService;
import com.nuaa.shawn.demo.service.UserService;
import com.nuaa.shawn.demo.util.DemoUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nowcoder on 2016/7/9.
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;

    @RequestMapping(path = {"/msg/list"}, method = {RequestMethod.GET})
    public String getMessageList(Model model) {
        try {
//            int localUserId = hostHolder.getUser().getId();
//            List<ViewObject> conversations = new ArrayList<ViewObject>();
//            List<Message> conversationList = messageService.getMessageList(localUserId, 0, 10);
//            for (Message msg : conversationList) {
//                ViewObject vo = new ViewObject();
//                vo.set("conversation", msg);
//                int targetId = msg.getFromId() == localUserId ? msg.getToId() : msg.getFromId();
//                User user = userService.getUser(targetId);
//                vo.set("user", user);
//                vo.set("unread", messageService.getConvesationUnreadCount(localUserId, msg.getConversationId()));
//                conversations.add(vo);
//            }
//            model.addAttribute("conversations", conversations);
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> vos = new ArrayList<ViewObject>();
            List<Message> messageList = messageService.getMessageList(localUserId, 0, 10);  //这个limit功能还没实现
            for (Message msg : messageList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User fromUser = userService.getUser(msg.getFromId());
                vo.set("fromUser", fromUser);
                vo.set("unread", messageService.getMessageUnreadCount(localUserId, msg.getMsgType(), msg.getConversationId()));
                vos.add(vo);
            }
            model.addAttribute("msgs", vos);
        } catch (Exception e) {
            logger.error("获取消息列表失败" + e.getMessage());
        }
        return "letter";
    }

    /**
     * 消息详情，直接按 conversationId 查找，添加到 vo ，再把 vo 加到 model 即可
     * @param model model
     * @param conversationId 对话Id
     * @return 返回页面
     */
    @RequestMapping(path = {"/msg/detail"}, method = {RequestMethod.GET})
    public String conversationDetail(Model model, @Param("conversationId") String conversationId) {
        try {
            List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);
            List<ViewObject> messages = new ArrayList<>();
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("message", msg);
                User fromUser = userService.getUser(msg.getFromId());
                if (fromUser == null) {
                    continue;
                }
                vo.set("headUrl", fromUser.getHeadUrl());
                vo.set("userId", fromUser.getId());
                messages.add(vo);
            }
            model.addAttribute("messages", messages);
        } catch (Exception e) {
            logger.error("获取消息详情失败" + e.getMessage());
        }
        return "letterDetail";
    }


    @RequestMapping(path = {"/msg/addMessage"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("msgType") int msgType,
                             @RequestParam("fromId") int fromId,
                             @RequestParam("toId") int toId,
                             @RequestParam("content") String content) {
        try {
            Message msg = new Message();
            msg.setMsgType(msgType);
            msg.setContent(content);
            msg.setFromId(fromId);
            msg.setToId(toId);
            msg.setCreatedDate(new Date());
            msg.setConversationId(fromId < toId ? String.format("%d_%d", fromId, toId) : String.format("%d_%d", toId, fromId));
            messageService.addMessage(msg);
            return DemoUtil.getJSONString(0, String.valueOf(msg.getId()));
        } catch (Exception e) {
            logger.error("增加消息失败" + e.getMessage());
            return DemoUtil.getJSONString(1, "增加消息失败");
        }
    }
}
