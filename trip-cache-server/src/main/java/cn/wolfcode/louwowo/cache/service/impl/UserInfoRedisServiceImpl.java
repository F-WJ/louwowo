package cn.wolfcode.louwowo.cache.service.impl;

import cn.wolfcode.luowowo.cache.service.IUserInfoRedisService;
import cn.wolfcode.luowowo.cache.util.RedisKeys;
import cn.wolfcode.luowowo.common.util.Consts;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoRedisServiceImpl implements IUserInfoRedisService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void saveVerifyCode(String phone) {
        //通过uuid获取验证码
        String verifyCode = UUID.randomUUID().toString().substring(0, 4);
        //保存验证码到redis上，并设置时间限制
//        redisTemplate.opsForValue().set(key, verifyCode, Consts.VERIFY_CODE_VAI_TIME * 60, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(RedisKeys.VERIFY_CODE.join(phone), verifyCode, Consts.VERIFY_CODE_VAI_TIME * 60, TimeUnit.SECONDS);
        System.out.println(verifyCode);

    }

    @Override
    public String getVerifyCode(String phone) {
        String value = redisTemplate.opsForValue().get(RedisKeys.VERIFY_CODE.join(phone));
        return value;
    }

    @Override
    public void saveTokenAndUserInfo(String token, UserInfo userInfo) {
        redisTemplate.opsForValue().set(RedisKeys.LOGIN_TOKEN_USERINFO.join(token), JSON.toJSONString(userInfo), Consts.USER_INFO_TOKEN_VAI_TIME * 60, TimeUnit.SECONDS);
    }

    @Override
    public UserInfo getUserInfoToken(String token) {
        String value = redisTemplate.opsForValue().get(RedisKeys.LOGIN_TOKEN_USERINFO.join(token));
        if(value == null){
            return null;
        }
        UserInfo userInfo = JSON.parseObject(value, UserInfo.class);
        return userInfo;
    }
}
