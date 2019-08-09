package cn.wolfcode.luowowo.cache.util;

import lombok.Getter;

import java.awt.event.ItemEvent;

@Getter
public enum  RedisKeys {
    //验证码(大小转换ctrl+x)
    VERIFY_CODE("verify_code"), LOGIN_TOKEN_USERINFO("Login_Token_UserInfo");
//    Login_Token_UserInfo("Login_Token_UserInfo");

    private String prefix;
    private RedisKeys(String prefix){
        this.prefix = prefix;
    }


    public String join(String... keys){
        StringBuilder stringBuilder = new StringBuilder(10);
        stringBuilder.append(prefix);
        for(String key : keys){
            stringBuilder.append(":").append(key);
        }
        return stringBuilder.toString();
    }



}
