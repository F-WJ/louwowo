package cn.wolfcode.luowowo.common.util;


import cn.wolfcode.luowowo.common.exception.LoginException;
import sun.rmi.runtime.Log;

public class AssertUtil {


    /**
     * 判断传入value值是否有值
     * @param value
     * @param msg
     * @return
     */
    public static void hasLength(String value, String msg) {
        if(value== null || "".equals(value.trim())){
            throw new LoginException(msg);
        }
    }

}
