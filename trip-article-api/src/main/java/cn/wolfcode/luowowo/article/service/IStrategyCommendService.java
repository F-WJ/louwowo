package cn.wolfcode.luowowo.article.service;

import cn.wolfcode.luowowo.article.domain.StrategyCommend;
import cn.wolfcode.luowowo.article.query.StrategyCommendQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 攻略推荐服务
 */
public interface IStrategyCommendService {

    /**
     * 分页
     * @param qo
     * @return
     */
    PageInfo query(StrategyCommendQuery qo);

    /**
     * 添加跟编辑
     * @param strategyCommend
     */
    void saveOrUpdate(StrategyCommend strategyCommend);

    /**
     * 查询前5条件
     * @return
     */
    List<StrategyCommend> queryCommendTop5();
}
