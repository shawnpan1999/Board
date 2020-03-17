package com.nuaa.shawn.demo.async;

import com.alibaba.fastjson.JSONObject;
import com.nuaa.shawn.demo.util.JedisAdapter;
import com.nuaa.shawn.demo.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/* EventProducer 只做一件事情，就是把任务塞到队列里 */
@Service
public class EventProducer {
    @Autowired
    JedisAdapter jedisAdapter;

    public boolean fireEvent(EventModel model) {    //接收一个事件，并发射到队列中
        try {
            //把事件推到 redis List 中
            jedisAdapter.lpush(RedisKeyUtil.getEventQueueKey(), JSONObject.toJSONString(model));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
