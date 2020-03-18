package com.nuaa.shawn.demo.async.handler;

import com.nuaa.shawn.demo.async.EventHandler;
import com.nuaa.shawn.demo.async.EventModel;
import com.nuaa.shawn.demo.async.EventType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class LikeHandler implements EventHandler {

    @Override
    public void doHandle(EventModel model) {
        // TODO: DO YOUR HANDLE
        System.out.println("LikeHandler done!");
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
