package cn.wolfcde.luowowo.service;

import cn.wolfcde.luowowo.repository.ITravelTemplateRepository;
import cn.wolfcode.luowowo.search.query.SearchQueryObject;
import cn.wolfcode.luowowo.search.service.ITravelSearchService;
import cn.wolfcode.luowowo.search.template.TravelTemplate;
import com.alibaba.dubbo.config.annotation.Service;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.List;

@Service
public class TravelSearchServiceImpl implements ITravelSearchService {

    @Autowired
    private ITravelTemplateRepository repository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private TransportClient client;



    @Override
    public void saveOrUpdate(TravelTemplate template) {
        repository.save(template);
    }

    @Override
    public List<TravelTemplate> findByDestName(String name) {
        return repository.findByDestName(name);
    }
}
