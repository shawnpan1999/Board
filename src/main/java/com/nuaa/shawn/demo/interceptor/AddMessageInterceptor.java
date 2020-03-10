package com.nuaa.shawn.demo.interceptor;

import com.nuaa.shawn.demo.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*添加留言的拦截器，只注册在 /addmsg 页面，用于拦截未登录用户添加留言
* */

@Component
public class AddMessageInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (hostHolder.getUser() == null) {    //拦截未登录用户
            httpServletResponse.sendRedirect("/board");
            return false;
        } else if (hostHolder.getUser().getId() != Integer.parseInt(httpServletRequest.getParameter("authorId"))) {    //拦截不符合当前登录用户
            httpServletResponse.sendRedirect("/board");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
