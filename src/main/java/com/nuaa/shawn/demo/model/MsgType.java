package com.nuaa.shawn.demo.model;

public enum MsgType {
    /** 使用枚举类来保存 Message 的类型 */
    LIKE(0),    //被点赞
    COMMENT(1), //被评论
    DIALOG(2);  //收到对话(私信)

    private int value;
    MsgType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
