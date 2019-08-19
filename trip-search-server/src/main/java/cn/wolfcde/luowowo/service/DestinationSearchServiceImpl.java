package cn.wolfcde.luowowo.service;

import cn.wolfcde.luowowo.repository.IDestinationTemplateRepository;
import cn.wolfcode.luowowo.search.service.IDestinationSearchService;
import cn.wolfcode.luowowo.search.template.DestinationTemplate;
import com.alibaba.dubbo.config.annotation.Service;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DestinationSearchServiceImpl implements IDestinationSearchService {

    @Autowired
    private IDestinationTemplateRepository repository;

    @Autowired
    private ElasticsearchTemplate template;

    @Autowired
    private TransportClient client;



    @Override
    public void saveOrUpdate(DestinationTemplate template) {
        repository.save(template);
    }

    @Override
    public DestinationTemplate findByName(String keyword) {

        return repository.findByName(keyword);
    }


}
