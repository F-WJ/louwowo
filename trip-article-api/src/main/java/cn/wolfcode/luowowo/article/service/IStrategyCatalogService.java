package cn.wolfcode.luowowo.article.service;


import cn.wolfcode.luowowo.article.domain.StrategyCatalog;
import cn.wolfcode.luowowo.article.query.StrategyCatalogQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 攻略分类服务
 */
public interface IStrategyCatalogService {

    /**
     * 分页查询
     * @param qo
     * @return
     */
    PageInfo query(StrategyCatalogQuery qo);


    /**
     * 添加/更新
     * @param strategyCatalog
     */
    void saveOrUpdate(StrategyCatalog strategyCatalog);

    /**
     * 查询某个攻略下分类集合
     * @param strategyId
     * @return
     */
    List<StrategyCatalog> queryCatalogByStrategyId(Long strategyId);

    /**
     * 查询攻略分类
     * @param id
     * @return
     */
    List<StrategyCatalog> queryCatalogByDestId(Long id);

    /**
     * 查单个
     * @param id
     * @return
     */
    StrategyCatalog get(Long id);
}
