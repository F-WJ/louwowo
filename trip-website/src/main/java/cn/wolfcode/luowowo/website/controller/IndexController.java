package cn.wolfcode.luowowo.website.controller;

import cn.wolfcode.luowowo.cache.service.IUserInfoRedisService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import cn.wolfcode.luowowo.common.util.Consts;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import cn.wolfcode.luowowo.member.service.IUserInfoService;
import cn.wolfcode.luowowo.website.annotation.RequireLogin;
import cn.wolfcode.luowowo.website.annotation.UserParam;
import cn.wolfcode.luowowo.website.util.CookieUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Controller
public class IndexController {


    @Reference
    private IUserInfoRedisService userInfoRedisService;

    @RequireLogin
    @RequestMapping("")
    public String index(HttpServletRequest request){
        String token = CookieUtil.getToken(request);
        UserInfo userInfo = userInfoRedisService.getUserInfoToken(token);
        if(userInfo != null){
            request.getSession().setAttribute("userInfo", userInfo);
        }
        return "/index/index";
    }

    //对象注入
    @RequestMapping("/userInfo")
    @ResponseBody
    public Object userInfo(HttpServletRequest request, @UserParam UserInfo userInfo){
        return userInfo;
    }

    @RequestMapping("/userInfo1")
    @ResponseBody
    public Object userInfo1(HttpServletRequest request, UserInfo userInfo){
        return userInfo;
    }


}
