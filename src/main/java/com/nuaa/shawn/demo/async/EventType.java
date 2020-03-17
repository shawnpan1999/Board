package com.nuaa.shawn.demo.async;

public enum EventType {
    /** 使用枚举类来保存可能出现的事件 */
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    MAIL(3);

    private int value;
    EventType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
