package cn.wolfcode.luowowo.search.service;

import cn.wolfcode.luowowo.search.template.UserInfoTemplate;

import java.util.List;

public interface IUserInfoSearchService {



    void saveOrUpdate(UserInfoTemplate template);

    List<UserInfoTemplate> findByDestName(String name);
}
