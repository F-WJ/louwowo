package cn.wolfcode.luowowo.article.service;


import cn.wolfcode.luowowo.article.domain.Strategy;
import cn.wolfcode.luowowo.article.query.StrategyQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 大攻略服务
 */
public interface IStrategyService {

    /**
     * 分页查询
     * @param qo
     * @return
     */
    PageInfo query(StrategyQuery qo);


    /**
     * 添加/更新
     * @param strategy
     */
    void saveOrUpdate(Strategy strategy);

    /**
     * 查询所有
     * @return
     */
    List<Strategy> list();

    /**
     * 查单个
     * @param sid
     * @return
     */
    Strategy get(Long sid);
}
