package com.nuaa.shawn.demo.async;

import java.util.HashMap;
import java.util.Map;

/** 记录一次事件 */
public class EventModel {
    private EventType type;    //事件类型(枚举类)
    private int actorId;       //事件的触发者
    private int entityId;      //事件对象ID
    private int entityType;    //事件对象类型
    private int entityOwnerId; //事件对象拥有者ID (比如用于触发事件后发送通知的对象)
    private Map<String, String> exts = new HashMap<>();    //保存事件的相关信息(Map几乎能保存任何数据)

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel() {

    }

    public EventModel(EventType type) {
        this.type = type;
    }

    public String getExt(String name) {
        return exts.get(name);
    }

    public EventModel setExt(String name, String value) {
        exts.put(name, value);
        return this;
    }

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }
}
