package cn.wolfcode.luowowo.common.exception;


/**
 * 给用户看的异常:
 *  一些系统异常的封装
 *
 */
public class LoginException extends  RuntimeException {
    public LoginException(String message) {
        super(message);
    }
}
