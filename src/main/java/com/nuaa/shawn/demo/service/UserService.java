package com.nuaa.shawn.demo.service;

import com.nuaa.shawn.demo.dao.LoginTicketDAO;
import com.nuaa.shawn.demo.dao.UserDAO;
import com.nuaa.shawn.demo.model.LoginTicket;
import com.nuaa.shawn.demo.model.User;
import com.nuaa.shawn.demo.util.DemoUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();    //这里考虑用一个映射来返回注册的结果
        if (StringUtils.isBlank(username)) {    //apache 给的包，判断字符串是不是空
            map.put("msgname", "用户名不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空！");
            return map;
        }
        /*上面这样的验证虽然前后端都需要做(安全性)，因为很多攻击可以直接调用我们的函数*/
        User user = userDAO.selectByName(username);
        if (user != null) {    //判断用户名是否被注册了
            map.put("msgname", "用户名已经被注册！");
            return map;
        }
        user = new User();
        user.setName(username);

        /*【密码加强】
        * 直接使用明文密码不可取
        * 密码转成 md5 也不够安全
        * 密码后插入几位随机 UUID(盐) 一起转成 md5 比较好*/
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        String head = String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000));
        user.setHeadUrl(head);
        user.setPassword(DemoUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);

        map.put("ticket", addLoginTicket(user.getId()));    //注册成功给他发一个机票 自动登录
        return map;
    }

    public Map<String, Object> login(String username, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(username)) {
            map.put("msgname", "用户名不能为空！");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msgpwd", "密码不能为空！");
            return map;
        }
        User user = userDAO.selectByName(username);
        if (user == null) {    //判断用户名存在
            map.put("msgname", "用户名不存在！");
            return map;
        }

        if (!DemoUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msgpwd", "用户名密码不匹配");
            return map;
        }

        map.put("ticket", addLoginTicket(user.getId()));
        return map;
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));    //把随机的 UUID 横杠去掉
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }
}