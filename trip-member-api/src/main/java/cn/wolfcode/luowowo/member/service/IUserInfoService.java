package cn.wolfcode.luowowo.member.service;



import cn.wolfcode.luowowo.common.exception.LoginException;
import cn.wolfcode.luowowo.member.domain.UserInfo;

import java.util.List;


public interface IUserInfoService {

    /**
     * 得到用户信息
     * @param id 用户id
     * @return 用户信息
     */
    UserInfo get(Long id);

    /**
     * 判断数据库里是否存在手机号码
     * @param phone  电话
     * @return
     */
    Boolean IsExistByPhone(String phone);

    /**
     * 保存到用户信息
     * @param phone
     * @param nickname
     * @param password
     * @param rpassword
     * @param verifyCode
     */
    void saveUserInfo(String phone, String nickname, String password, String rpassword, String verifyCode) throws LoginException;

    String userLogin(String username, String password) throws LoginException;


    List<UserInfo> list();

}
