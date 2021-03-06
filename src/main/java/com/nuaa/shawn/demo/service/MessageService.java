package com.nuaa.shawn.demo.service;

import com.nuaa.shawn.demo.dao.MessageDAO;
import com.nuaa.shawn.demo.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;
    public int addMessage(Message message) {
        return messageDAO.addMessage(message);
    }

    public List<Message> getConversationDetail(String conversationId, int offset, int limit) {
        return messageDAO.getConversationDetail(conversationId, offset, limit);
    }

    public List<Message> getMessageList(int userId, int offset, int limit) {
        return messageDAO.getMessageList(userId, offset, limit);
    }

    public int getMessageUnreadCount(int userId, int type, String conversationId) {
        return messageDAO.getMessageUnreadCount(userId, type, conversationId);
    }
}
