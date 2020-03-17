package com.nuaa.shawn.demo.async;

import com.alibaba.fastjson.JSON;
import com.nuaa.shawn.demo.util.JedisAdapter;
import com.nuaa.shawn.demo.util.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 事件消费者 用于分配各种 Handler 来处理队列中的事件*/
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {    //初始化的bean，在初始化完成后会自动调用 afterPropertiesSet 方法
    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);
    private Map<EventType, List<EventHandler>> config = new HashMap<>();    //路由:建立起 EventType 和多个 EventHandler 的映射
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
        //在初始化完成之后，还需要定义我们的路由表，即一个 Event 过来我们要知道怎么为其分配不同的 Handler 去执行
        //要定义路由表，首先就要去遍历所有的 Handler
        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);    //把现在上下文中所有 EventHandler 的实现类找出来

        /*TODO: 这一部分应该还有更好的方法，例如把EventType变成接口，在那边定义每个type需要执行的handler就可以了 */
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {    //Entry 遍历这个得到的 Map
                List<EventType> eventTypes = entry.getValue().getSupportEventTypes();   //查找这个遍历到的 EventHandler 中定义的 eventTypes，
                                                                                        //要把这些 type 加入到 config 中
                for (EventType type : eventTypes) {
                    if (!config.containsKey(type)) {
                        config.put(type, new ArrayList<EventHandler>());                //如果这个事件 type 还没有被注册，那么就当场 put 一个
                    }
                    // 注册每个事件的处理函数
                    config.get(type).add(entry.getValue());
                }
            }
        }
        /*TODO END.*/
        //接下来开线程去处理队列中的 Event
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    String key = RedisKeyUtil.getEventQueueKey();
                    List<String> messages = jedisAdapter.brpop(0, key);    //向右弹出，没元素了就阻塞
                    //（brpop 弹出返回一个含有两个元素的列表，第一个元素是被弹出元素所属的 key ，第二个元素是被弹出元素的值）
                    for (String message : messages) {
                        if (message.equals(key)) {
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(message, EventModel.class);    //转换成 EventModel
                        if (!config.containsKey(eventModel.getType())) {    //如果 cinfig 没有，则报错
                            logger.error("不能识别的事件");
                            continue;
                        }
                        for (EventHandler handler : config.get(eventModel.getType())) {    //对每个 handler 发布任务
                            handler.doHandle(eventModel);
                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
