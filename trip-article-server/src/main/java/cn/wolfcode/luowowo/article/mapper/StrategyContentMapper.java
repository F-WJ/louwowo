package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.StrategyContent;

import java.util.List;

public interface StrategyContentMapper {
    int insert(StrategyContent record);

    List<StrategyContent> selectAll();

    StrategyContent selectByPrimaryKey(Long id);

    void updateByPrimaryKey(StrategyContent strategyContent);

}