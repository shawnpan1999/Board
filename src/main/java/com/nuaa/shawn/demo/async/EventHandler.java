package com.nuaa.shawn.demo.async;

import java.util.List;

public interface EventHandler {
    void doHandle(EventModel model);
    List<EventType> getSupportEventTypes();    //记录需要处理的EventType
}
