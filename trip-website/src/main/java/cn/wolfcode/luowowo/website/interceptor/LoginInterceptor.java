package cn.wolfcode.luowowo.website.interceptor;

import cn.wolfcode.luowowo.cache.service.IUserInfoRedisService;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import cn.wolfcode.luowowo.website.annotation.RequireLogin;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Reference
    private IUserInfoRedisService userInfoRedisService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            //获取当前请求的方法
            HandlerMethod method = (HandlerMethod) handler;
            //判断方法上是否贴有RequireLogin注解
            if(method.hasMethodAnnotation(RequireLogin.class)){
                //登录判断操作
                //获取当前所有cookie值
                Cookie[] cookies = request.getCookies();
                if(cookies == null){
                    response.sendRedirect("/login.html");
                    return false;
                }
                //获取名字为token的cookie
                for (Cookie cookie : cookies) {
                    if("token".equals(cookie.getName())){
                        String token = cookie.getValue();
                        System.err.println(token);
                        //获取redis的userInfo值
                        UserInfo userInfo = userInfoRedisService.getUserInfoToken(token);
                        if(userInfo == null){
                            //跳转到登录页面
                            response.sendRedirect("/login.html");
                            return false;
                        }

                    }
                }

            }
        }


        return true;
    }




}
