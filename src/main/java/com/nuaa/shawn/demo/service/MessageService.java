package com.nuaa.shawn.demo.service;

import com.nuaa.shawn.demo.dao.MessageDAO;
import com.nuaa.shawn.demo.model.Message;
import com.nuaa.shawn.demo.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageDAO messageDAO;

    public List<Message> getLatestMessage(int offset, int limit) {
        return messageDAO.selectByOffset(offset, limit);
    }
}
