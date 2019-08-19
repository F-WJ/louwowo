package cn.wolfcde.luowowo.repository;


import cn.wolfcode.luowowo.search.template.StrategyTemplate;
import cn.wolfcode.luowowo.search.template.UserInfoTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IStrategyTemplateRepository extends ElasticsearchRepository<StrategyTemplate, Long> {


    List<StrategyTemplate> findByDestName(String name);
}
