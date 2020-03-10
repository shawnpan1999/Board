package com.nuaa.shawn.demo.service;

import com.nuaa.shawn.demo.dao.MessageDAO;
import com.nuaa.shawn.demo.model.Message;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    public List<Message> getLatestMessage(int offset, int limit) {
        return messageDAO.selectByOffset(offset, limit);
    }


    public Map<String, Object> addMessage(int authorId, String text) {
        Map<String, Object> map = new HashMap<String, Object>();    //这里考虑用一个映射来返回注册的结果
        if (authorId == 0) {
            map.put("msgerr", "您还未登录！");
            return map;
        }
        if (StringUtils.isBlank(text)) {    //apache 给的包，判断字符串是不是空
            map.put("msgerr", "留言信息不能为空！");
            return map;
        }
        Message message = new Message();
        message.setAuthorId(authorId);
        Date date = new Date();
        message.setCreatedDate(date);
        message.setText(text);

        messageDAO.addMessage(message);

        map.put("success", "1");    //注册成功给他发一个机票 自动登录
        return map;
    }
}
