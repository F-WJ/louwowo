package cn.wolfcode.luowowo.website.util;

import cn.wolfcode.luowowo.common.util.Consts;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String name, String value) {
        Cookie c = new Cookie(name, value);
        c.setPath("/");
        c.setMaxAge(Integer.valueOf((Consts.USER_INFO_TOKEN_VAI_TIME * 60) + ""));
        response.addCookie(c);
    }


    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
