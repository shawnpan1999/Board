package com.nuaa.shawn.demo.controller;

import com.nuaa.shawn.demo.service.UserService;
import com.nuaa.shawn.demo.util.DemoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    UserService userService;

    @RequestMapping(path = {"/reg"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String reg(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "remember", defaultValue = "0") int rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.register(username, password);    //map 是 service 层处理注册信息返回的结果
            if (map.containsKey("ticket")) {
                //登陆成功 配置 cookie
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");    //设置 cookie 的有效范围：全站有效
                if (rememberme > 0) {    //如果没有记住登陆状态，则不设置时间，默认浏览器关闭就没了
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return DemoUtil.getJSONString(0, "注册成功");
            } else {
                return DemoUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("注册异常" + e.getMessage());
            return DemoUtil.getJSONString(1, "注册异常");
        }
    }

    @RequestMapping(path = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String login(Model model, @RequestParam("username") String username,
                      @RequestParam("password") String password,
                      @RequestParam(value = "remember", defaultValue = "0") int rememberme,
                      HttpServletResponse response) {
        try {
            Map<String, Object> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
                cookie.setPath("/");
                if (rememberme > 0) {
                    cookie.setMaxAge(3600*24*5);
                }
                response.addCookie(cookie);
                return DemoUtil.getJSONString(0, "登录成功");
            } else {
                return DemoUtil.getJSONString(1, map);
            }

        } catch (Exception e) {
            logger.error("登录异常" + e.getMessage());
            return DemoUtil.getJSONString(1, "登录异常");
        }
    }

    @RequestMapping(path = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logout(@CookieValue("ticket") String ticket) {    //这里可以直接从注解中获得 Cookie 值
        userService.logout(ticket);
        return "redirect:/";
    }
}
