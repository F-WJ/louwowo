package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.Strategy;
import cn.wolfcode.luowowo.article.query.StrategyQuery;

import java.util.List;

public interface StrategyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Strategy record);

    Strategy selectByPrimaryKey(Long id);

    List<Strategy> selectAll();

    int updateByPrimaryKey(Strategy record);

    List<Strategy> selectForList(StrategyQuery qo);


}