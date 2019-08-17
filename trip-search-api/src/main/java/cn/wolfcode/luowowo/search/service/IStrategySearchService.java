package cn.wolfcode.luowowo.search.service;

import cn.wolfcode.luowowo.search.template.StrategyTemplate;
import cn.wolfcode.luowowo.search.vo.StatisVO;

import java.util.List;
import java.util.Map;

public interface IStrategySearchService {



    void saveOrUpdate(StrategyTemplate template);

    List<Map<String, Object>> getThemeCommend();


    List<StatisVO> queryConditionGroup(int conditionTypeUnAbroad);

}
