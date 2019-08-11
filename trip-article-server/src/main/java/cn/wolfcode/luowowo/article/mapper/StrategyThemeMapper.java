package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.StrategyTheme;
import cn.wolfcode.luowowo.article.query.StrategyThemeQuery;

import java.util.List;

public interface StrategyThemeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StrategyTheme record);

    StrategyTheme selectByPrimaryKey(Long id);

    List<StrategyTheme> selectAll();

    int updateByPrimaryKey(StrategyTheme record);

    List<StrategyTheme> selectForList(StrategyThemeQuery qo);
}