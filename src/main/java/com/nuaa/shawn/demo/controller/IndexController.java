package com.nuaa.shawn.demo.controller;

import com.nuaa.shawn.demo.model.User;
import com.nuaa.shawn.demo.service.ToutiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

//注解 （Java 通过注解来表明这个类是用来干嘛的）
//@Controller
public class IndexController {
    /*【映射请求路径】*/
//  @RequestMapping("/")    //RequestMapping 表示访问某个地址就用下面这个函数来处理
    @RequestMapping({"/", "/index"})    //在源码中可以了解到，括号中的参数其实是字符串数组，然后赋给源码中的 path 变量，因此可以通过数组指定多个地址
    @ResponseBody           //ResponseBody 不使用视图解析器，直接返回return的内容。（表示我返回的东西是一个 Body）
    public String index() {
        return "Hello World";
    }
    /*【页面携带参数】*/
//  例如 "http://localhost:8080/profile/100/abc?username=psw&password=123" 这样的网址
    @RequestMapping("/profile/{userId}/{groupId}")    //域名映射这里规定了 userId 和 groupId 是变量，需要在方法参数里声明
    //同时在 ? 后面的是 URL 变量，也需要在方法参数中声明 @RequestParam 请求参数
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "username", defaultValue = "") String username,
                          @RequestParam(value = "password", defaultValue = "0") int password) {
        //一般在这里调用 service 层
        //DAO 层
        return String.format("%d %s %s %d", userId, groupId, username, password);
    }
    /*【返回模板页面 templates】*/
//    我们现在不返回字符串了，返回一个可以已经事先写好的 vm 模板试一下
    @RequestMapping(value = {"/vm"})
    public String news(Model model) {    //SpringBoot 会自动将 Model 实例化为 BindingAwareModelMap 对象
        Map<Integer, String> map = new HashMap<Integer, String>();
        List<String> colors = Arrays.asList("RED", "GREEN", "BLUE");
        for (int i = 0; i < 5; i++) {
            map.put(i, Integer.toString(i*i));
        }
        model.addAttribute("colors", colors);
        model.addAttribute("map", map);    //向模板传递名字为 value1 的参数；在页面可以用 $!{value1} 来获取这个参数
        model.addAttribute("user", new User("Jim"));
        return "news";    //也可以直接就 return "detail"; 指的都是同一个 vm 文件
    }

    /*【Request 和 Resonse】*/
    //request 从服务端获得数据
    @RequestMapping("/request")
    @ResponseBody
    public String request(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) + "<br>");
        }

        for (Cookie cookie : request.getCookies()) {
            sb.append("Cookie:");
            sb.append(cookie.getName());
            sb.append(":");
            sb.append(cookie.getValue());
            sb.append("<br>");
        }

        sb.append("getMethod:" + request.getMethod() + "<br>");
        sb.append("getPathInfo:" + request.getPathInfo() + "<br>");
        sb.append("getQueryString:" + request.getQueryString() + "<br>");
        sb.append("getRequestURI:" + request.getRequestURI() + "<br>");

        return sb.toString();

    }
    //response 向服务端写入数据
    @RequestMapping(value = {"/response"})
    @ResponseBody
    public String response(@CookieValue(value = "nowcoderid", defaultValue = "a") String nowcoderId,
                           @RequestParam(value = "key", defaultValue = "key") String key,
                           @RequestParam(value = "value", defaultValue = "value") String value,
                           HttpServletResponse response) {
        response.addCookie(new Cookie(key, value));
        response.addHeader(key, value);
        return "NowCoderId From Cookie:" + nowcoderId;
    }

    /*【重定向】*/
    //301:永久转移(跳转过一次后浏览器就会记住，下次就不会访问服务器数据)
    //302:临时转移
    @RequestMapping("/redirect/{code}")
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession session) {
        RedirectView red = new RedirectView("/", true);
        if (code == 301) {
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        /*【session 设置】*/
        session.setAttribute("name", "ZhangSan");
        return red;
    }

    /*【错误处理】*/
    @RequestMapping("/admin")
    @ResponseBody
    public String admin(@RequestParam(value = "key", required = false) String key) {
        if ("admin".equals(key)) {
            return "hello admin";
        }
        throw new IllegalArgumentException("Key 错误");
    }
    //捕获错误（spring 没有处理的 Exception 我们可以自己捕获下来处理）
    @ExceptionHandler()
    @ResponseBody
    public String error(IllegalArgumentException e) {
        return e.getMessage();
    }

    /*【IoC/DI 控制反转、依赖注入】*/
    @Autowired    //这样就能够找到另一个 @Service 的类自动实例化这个对象了（就不用 new ...）
    private ToutiaoService toutiaoService;
    @RequestMapping("/toutiao")
    @ResponseBody
    public String toutiao() {
        return toutiaoService.sayHello();
    }
}
