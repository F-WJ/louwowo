package cn.wolfcode.luowowo.article.service;


import cn.wolfcode.luowowo.article.domain.StrategyTag;
import cn.wolfcode.luowowo.article.query.StrategyTagQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 攻略标签服务
 */
public interface IStrategyTagService {

    /**
     * 分页查询
     * @param qo
     * @return
     */
    PageInfo query(StrategyTagQuery qo);


    /**
     * 添加/更新
     * @param strategyTag
     */
    void saveOrUpdate(StrategyTag strategyTag);

    /**
     * 查询所有
     * @return
     */
    List<StrategyTag> list();

    /**
     * 获取攻略明细标签字符串
     * @param id
     * @return
     */
    String getTags(Long id);
}
