package cn.wolfcode.luowowo.article.service;


import cn.wolfcode.luowowo.article.domain.StrategyTheme;
import cn.wolfcode.luowowo.article.query.StrategyThemeQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 攻略主题服务
 */
public interface IStrategyThemeService {

    /**
     * 分页查询
     * @param qo
     * @return
     */
    PageInfo query(StrategyThemeQuery qo);


    /**
     * 添加/更新
     * @param strategyTheme
     */
    void saveOrUpdate(StrategyTheme strategyTheme);

    /**
     * 查询所有
     * @return
     */
    List<StrategyTheme> list();
}
