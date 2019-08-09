package cn.wolfcode.luowowo.website;


import cn.wolfcode.luowowo.website.interceptor.LoginInterceptor;
import cn.wolfcode.luowowo.website.param.UserInfoArgumentResolver;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
@EnableDubbo
public class webSite implements WebMvcConfigurer {
    public static void main(String[] args) {
        SpringApplication.run(webSite.class, args);
    }


    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private UserInfoArgumentResolver userInfoArgumentResolver;

    //注册自定义对象注入
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userInfoArgumentResolver);
    }

    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器
        registry.addInterceptor(loginInterceptor)
                // 对哪些资源起过滤作用
                .addPathPatterns("/**")
                // 对哪些资源起排除作用
                .excludePathPatterns("/static.html")
                .excludePathPatterns("/login.html")
                .excludePathPatterns("/regist.html")
                .excludePathPatterns("/userRegist")
                .excludePathPatterns("/sendVerifyCode")
                .excludePathPatterns("/imges/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/styles/**");
    }
}

