package cn.wolfcde.luowowo.service;

import cn.wolfcde.luowowo.repository.IUserInfoTemplateRepository;
import cn.wolfcode.luowowo.search.service.IUserInfoSearchService;
import cn.wolfcode.luowowo.search.template.UserInfoTemplate;
import com.alibaba.dubbo.config.annotation.Service;
import org.elasticsearch.client.ElasticsearchClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.List;


@Service
public class UserInfoSearchServiceImpl implements IUserInfoSearchService {


    @Autowired
    private IUserInfoTemplateRepository userInfoTemplateRepository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private ElasticsearchClient elasticsearchClient;




    @Override
    public void saveOrUpdate(UserInfoTemplate template) {
        userInfoTemplateRepository.save(template);
    }

    @Override
    public List<UserInfoTemplate> findByDestName(String name) {
        return userInfoTemplateRepository.findByDestName(name);
    }
}
