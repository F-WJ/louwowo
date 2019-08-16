package cn.wolfcode.luowowo.cache.util;

import lombok.Getter;

import java.awt.event.ItemEvent;

@Getter
public enum  RedisKeys {
    //验证码(大小转换ctrl+x)
    VERIFY_CODE("verify_code"), LOGIN_TOKEN_USERINFO("Login_Token_UserInfo"),
    STRATEGY_DETAIL_VO("Strategy_Detail_Vo"),
    FAVORNUM_USERINFO_ID("favornum_userinfo_id"),
    THUMBSUPNUM_USERINFO_ID("thumbsupnum_userinfo_id"),
    TRAVEL_DETAIL_VO("travel_detail_vo"),
    FAVORNUM_TRAVEL_USERINFO_ID("favornum_travel_userinfo_id"),
    THUMBSUPNUM_TRAVEL_USERINFO_ID("thumbsupnum_travel_userinfo_id"),
    ABROADCDS_TOP10_DETAIL_ID("abroadCds_top10_detail_id"),
    UNABROADCDS_TOP10_DETAIL_ID("unabroadCds_top10_detail_id"),
    HOTCDS_TOP10_DETAIL_ID("hotCds_top10_detail_id");



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
