package cn.wolfcode.luowowo.search.service;


import cn.wolfcode.luowowo.search.query.SearchQueryObject;
import cn.wolfcode.luowowo.search.template.TravelTemplate;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 游记搜索服务
 */
public interface ITravelSearchService {



    /**
     * 保存
     * @param template
     */
    void saveOrUpdate(TravelTemplate template);


    List<TravelTemplate> findByDestName(String name);
}
