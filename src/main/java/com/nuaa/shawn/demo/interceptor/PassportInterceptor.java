package com.nuaa.shawn.demo.interceptor;

import com.nuaa.shawn.demo.dao.LoginTicketDAO;
import com.nuaa.shawn.demo.dao.UserDAO;
import com.nuaa.shawn.demo.model.HostHolder;
import com.nuaa.shawn.demo.model.LoginTicket;
import com.nuaa.shawn.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/*【拦截器】在用户访问页面时检测用户 ticket*/
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    LoginTicketDAO loginTicketDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    HostHolder hostHolder;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie: httpServletRequest.getCookies()) {
                if (cookie.getName() == "ticket") {    //如果检测到含有 ticket，赋值给上面的 ticket 变量
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        //经过查找，验证一下这个 ticket 是否有效
        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.selectByTicket("ticket");    //通过 DAO 在数据库中取出 ticket 实体
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;    //无效机票
            }

            //有效机票，把这个用户保存(hostHolder 里面的 users 变量是 static 的，我们在开始时保存用户，结束时清除用户的目的是：
            //有一个全局的 users 变量指代当前登录的用户，让前端 Controller 也能获取到这个用户的信息)
            User user = userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null && hostHolder.getUser() != null) {
            modelAndView.addObject("user", hostHolder.getUser());    //向前端添加变量(可以直接在页面上 $user 了)
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();    //结束时清除刚才保存的用户
    }
}
