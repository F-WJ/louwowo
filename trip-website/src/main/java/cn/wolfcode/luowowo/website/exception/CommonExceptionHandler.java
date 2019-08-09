package cn.wolfcode.luowowo.website.exception;

import cn.wolfcode.luowowo.common.exception.LoginException;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public void logicExp(Exception e, HttpServletResponse resp) throws IOException {
        e.printStackTrace();
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(new AjaxResult(e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    public void runTimeExp(Exception e, HttpServletResponse resp) throws IOException {
        e.printStackTrace();
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(new AjaxResult("系统出现未知异常，请联系客服。")));
    }
}
