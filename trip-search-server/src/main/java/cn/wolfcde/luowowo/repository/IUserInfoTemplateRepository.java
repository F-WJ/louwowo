package cn.wolfcde.luowowo.repository;


import cn.wolfcode.luowowo.search.template.UserInfoTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserInfoTemplateRepository extends ElasticsearchRepository<UserInfoTemplate, Long> {


}
