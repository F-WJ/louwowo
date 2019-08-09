package cn.wolfcode.luowowo.website.controller;

import cn.wolfcode.luowowo.cache.service.IUserInfoRedisService;
import cn.wolfcode.luowowo.common.exception.LoginException;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import cn.wolfcode.luowowo.common.util.Consts;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import cn.wolfcode.luowowo.member.service.IUserInfoService;

import com.alibaba.dubbo.config.annotation.Reference;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Reference
    private IUserInfoService userInfoService;

    @Reference
    private IUserInfoRedisService userInfoRedisService;


    @RequestMapping("/hello")
    @ResponseBody
    public Object hello(){
        UserInfo userInfo = userInfoService.get(6L);
        return userInfo;
    }

    @RequestMapping("checkPhone")
    @ResponseBody
    public Boolean checkPhone(String phone){

        Boolean result =  userInfoService.IsExistByPhone(phone);

        return !result;
    }

    @RequestMapping("/sendVerifyCode")
    @ResponseBody
    public AjaxResult sendVerifyCode(String phone){
        AjaxResult result = new AjaxResult();
        try {
            userInfoRedisService.saveVerifyCode(phone);
        }catch (Exception e){
            e.printStackTrace();
            result = new AjaxResult("验证码发送失败");

        }
        return result;
    }

    @RequestMapping("/userRegist")
    @ResponseBody
    public AjaxResult userRegist(String phone, String nickname, String password, String rpassword,  String verifyCode){
        userInfoService.saveUserInfo(phone, nickname, password, rpassword, verifyCode);
        return new AjaxResult();
    }

    @RequestMapping("/userLogin")
    @ResponseBody
    public AjaxResult userLogin(HttpServletResponse response, String username, String password){
        String token = userInfoService.userLogin(username, password);
        //设置到cookie
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setMaxAge(Consts.USER_INFO_TOKEN_VAI_TIME * 60);
        //发送
        response.addCookie(cookie);
        return new AjaxResult();
    }


}
