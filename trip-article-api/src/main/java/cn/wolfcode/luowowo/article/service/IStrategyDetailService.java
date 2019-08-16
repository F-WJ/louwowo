package cn.wolfcode.luowowo.article.service;





import cn.wolfcode.luowowo.article.domain.StrategyContent;
import cn.wolfcode.luowowo.article.domain.StrategyDetail;
import cn.wolfcode.luowowo.article.query.StrategyDetailQuery;
import cn.wolfcode.luowowo.article.query.StrategyQuery;

import cn.wolfcode.luowowo.article.service.vo.newStrategyStatisVO;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 攻略明细服务
 */
public interface IStrategyDetailService {

    /**
     * 分页查询
     * @param qo
     * @return
     */
    PageInfo query(StrategyDetailQuery qo);


    /**
     * 添加/更新
     * @param strategyDetail
     * @param tags
     */
    void saveOrUpdate(StrategyDetail strategyDetail, String tags);

    /**
     * 查询所有
     * @return
     */
    List<StrategyDetail> list();

    /**
     * 修改状态
     * @param id
     * @param state
     */
    void changeState(Long id, int state);

    /**
     * 查单个
     * @param id
     * @return
     */
    StrategyDetail get(Long id);

    /**
     * 查内容
     * @param id
     * @return
     */
    StrategyContent getContent(Long id);

    /**
     * 获取点击量最大的前3个
     * @return
     * @param id
     */
    List<StrategyDetail> getDetailTop3(Long id);


    PageInfo<StrategyDetail> getDetailByTag(StrategyQuery qo);

    StrategyDetail getByCatalogId(Long catalogId);


    void updateCommentNumById(Long detailId);


    void updateStatisData(newStrategyStatisVO vo);
}
