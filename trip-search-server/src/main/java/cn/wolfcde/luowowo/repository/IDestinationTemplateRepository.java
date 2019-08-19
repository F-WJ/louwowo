package cn.wolfcde.luowowo.repository;

import cn.wolfcode.luowowo.search.template.DestinationTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 操作es 持久化接口
 */
@Repository
public interface IDestinationTemplateRepository extends ElasticsearchRepository<DestinationTemplate, Long> {

    DestinationTemplate findByName(String keyword);
}
