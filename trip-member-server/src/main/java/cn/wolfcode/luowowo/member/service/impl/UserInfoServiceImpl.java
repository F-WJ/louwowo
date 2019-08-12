package cn.wolfcode.luowowo.member.service.impl;

import cn.wolfcode.luowowo.cache.service.IUserInfoRedisService;
import cn.wolfcode.luowowo.common.exception.LoginException;
import cn.wolfcode.luowowo.common.util.AssertUtil;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import cn.wolfcode.luowowo.member.mapper.UserInfoMapper;
import cn.wolfcode.luowowo.member.service.IUserInfoService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;


@Service
public class UserInfoServiceImpl implements IUserInfoService {


    @Autowired
    private UserInfoMapper userInfoMapper;

    @Reference
    private IUserInfoRedisService userInfoRedisService;

    @Override
    public UserInfo get(Long id) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(id);
        return userInfo;
    }

    @Override
    public Boolean IsExistByPhone(String phone) {
        return userInfoMapper.IsExistByPhone(phone);
    }

    @Override
    public void saveUserInfo(String phone, String nickname, String password, String rpassword, String verifyCode) {
       //校验参数是否为空
        AssertUtil.hasLength(phone, "手机不能为空");
        AssertUtil.hasLength(nickname, "昵称不能空");
        AssertUtil.hasLength(password, "密码不能为空");
        AssertUtil.hasLength(rpassword, "密码不能为空");
        AssertUtil.hasLength(verifyCode, "验证码不能为空");
       //校验手机号码是否存在
        if(userInfoMapper.IsExistByPhone(phone)){
            throw new LoginException("手机已存在");
        }
       //校验密码是否相等
        if(!password.equals(rpassword)){
            throw new LoginException("两次密码不一样，请重新输入");
        }
        //校验验证码是否相等
        String code = userInfoRedisService.getVerifyCode(phone);
        if(!verifyCode.equalsIgnoreCase(code)){
            throw new LoginException("验证码无效");
        }
        System.out.println(code);
        // 密码加密
//        password = password + phone;
//        String md5DigestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());


        //注册
        UserInfo userInfo = new UserInfo();
        userInfo.setPhone(phone);
        userInfo.setNickname(nickname);
        userInfo.setPassword(password);
        userInfo.setState(UserInfo.STATE_NORMAL);
        userInfo.setHeadImgUrl("/images/default.jpg");
        userInfoMapper.insert(userInfo);

    }

    @Override
    public String userLogin(String username, String password) {
        //判断账号和密是否为空
        AssertUtil.hasLength(username, "账号不能为空");
        AssertUtil.hasLength(password, "密码不能为空");
        //查询用户是否存在
        UserInfo userInfo = userInfoMapper.selectUserInfo(username, password);
        if(userInfo == null){
            throw new LoginException("用户不再存在");
        }
        //通过uuid设置一个唯一的token值
        String token = UUID.randomUUID().toString().replace("-", "");
        //保存token值（30分钟限制）
        userInfoRedisService.saveTokenAndUserInfo(token, userInfo);

        //每验证一次增加30分钟

        return token;
    }
}
