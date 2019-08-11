package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.StrategyCatalog;
import cn.wolfcode.luowowo.article.query.StrategyCatalogQuery;

import java.util.List;

public interface StrategyCatalogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StrategyCatalog record);

    StrategyCatalog selectByPrimaryKey(Long id);

    List<StrategyCatalog> selectAll();

    int updateByPrimaryKey(StrategyCatalog record);

    List<StrategyCatalog> selectForList(StrategyCatalogQuery qo);

    List<StrategyCatalog> selectCatalogByStrategyId(Long strategyId);

    List<StrategyCatalog> selectCatalogByDestId(Long id);
}