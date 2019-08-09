package cn.wolfcode.luowowo.website.param;

import cn.wolfcode.luowowo.cache.service.IUserInfoRedisService;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import cn.wolfcode.luowowo.website.annotation.UserParam;
import cn.wolfcode.luowowo.website.util.CookieUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

//用户对象注入
@Component
public class UserInfoArgumentResolver implements HandlerMethodArgumentResolver {

    @Reference
    private IUserInfoRedisService userInfoRedisService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //检测是否该对象
        return parameter.getParameterType() == UserInfo.class && parameter.hasParameterAnnotation(UserParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //获取当前对象
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = CookieUtil.getToken(request);
        System.err.println(token);
        //获取当前用户信息
        UserInfo userInfo = userInfoRedisService.getUserInfoToken(token);
        return userInfo;
    }
}