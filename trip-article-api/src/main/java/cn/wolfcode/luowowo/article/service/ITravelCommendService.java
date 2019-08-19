package cn.wolfcode.luowowo.article.service;

import cn.wolfcode.luowowo.article.domain.TravelCommend;
import cn.wolfcode.luowowo.article.query.TravelCommendQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 游记推荐服务
 */
public interface ITravelCommendService {

    /**
     * 分页
     * @param qo
     * @return
     */
    PageInfo query(TravelCommendQuery qo);

    /**
     * 添加跟编辑
     * @param travelCommend
     */
    void saveOrUpdate(TravelCommend travelCommend);

    /**
     * 查询前5条件
     * @return
     */
    List<TravelCommend> queryCommendTop5();
}
