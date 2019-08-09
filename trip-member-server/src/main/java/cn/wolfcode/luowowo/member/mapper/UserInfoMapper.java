package cn.wolfcode.luowowo.member.mapper;

import cn.wolfcode.luowowo.member.domain.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    List<UserInfo> selectAll();

    int updateByPrimaryKey(UserInfo record);

    Boolean IsExistByPhone(String phone);

    UserInfo selectUserInfo(@Param("username") String username, @Param("password") String password);
}