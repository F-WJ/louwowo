package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.StrategyCommend;
import cn.wolfcode.luowowo.article.query.StrategyCommendQuery;

import java.util.List;

public interface StrategyCommendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StrategyCommend record);

    StrategyCommend selectByPrimaryKey(Long id);

    List<StrategyCommend> selectAll();

    int updateByPrimaryKey(StrategyCommend record);

    List<StrategyCommend> selectForList(StrategyCommendQuery qo);

    List<StrategyCommend> selectCommendTop5();
}