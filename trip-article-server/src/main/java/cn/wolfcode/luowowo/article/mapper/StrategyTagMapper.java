package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.StrategyTag;
import cn.wolfcode.luowowo.article.query.StrategyTagQuery;

import java.util.List;

public interface StrategyTagMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StrategyTag record);

    StrategyTag selectByPrimaryKey(Long id);

    List<StrategyTag> selectAll();

    int updateByPrimaryKey(StrategyTag record);

    List<StrategyTag> selectForList(StrategyTagQuery qo);

    String selectTagString(Long id);
}