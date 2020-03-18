package com.nuaa.shawn.demo.model;

import java.util.Date;

public class Message {
    private int id;                   //消息id
    private int msgType;              //消息类型：LIKE 被点赞、COMMENT 被评论、DIALOG 收到私信
    private int fromId;               //消息来自
    private int toId;                 //消息发送至
    private String content;           //消息内容
    private Date createdDate;         //消息日期
    private int hasRead;              //是否已读(接收方已读)
    private String conversationId;    //仅在 type==DIALOG 时有效；用于标记同一个私信对话，命名规范是 id1_id2 ，为from和to两个id连接，较小者排在前面

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msg_type) {
        this.msgType = msg_type;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public String getConversationId() {
        if (fromId < toId) {
            return String.format("%d_%d", fromId, toId);
        }
        return String.format("%d_%d", toId, fromId);
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

}
