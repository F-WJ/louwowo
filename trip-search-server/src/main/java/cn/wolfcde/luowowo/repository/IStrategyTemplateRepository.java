package cn.wolfcde.luowowo.repository;


import cn.wolfcode.luowowo.search.template.StrategyTemplate;
import cn.wolfcode.luowowo.search.template.UserInfoTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStrategyTemplateRepository extends ElasticsearchRepository<StrategyTemplate, Long> {


}
