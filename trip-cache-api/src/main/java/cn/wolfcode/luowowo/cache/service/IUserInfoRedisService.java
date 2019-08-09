package cn.wolfcode.luowowo.cache.service;

import cn.wolfcode.luowowo.member.domain.UserInfo;

public interface IUserInfoRedisService {


    /**
     * 保存验证码
     * @param phone
     */
    void saveVerifyCode(String phone);


    String getVerifyCode(String phone);

    void saveTokenAndUserInfo(String token, UserInfo userInfo);

    UserInfo getUserInfoToken(String token);
}
