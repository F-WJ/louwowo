package cn.wolfcode.luowowo.article.service;


import cn.wolfcode.luowowo.article.domain.Travel;
import cn.wolfcode.luowowo.article.domain.TravelContent;
import cn.wolfcode.luowowo.article.query.TravelQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 游记信息
 */
public interface ITravelService {

    /**
     * 分页查询
     * @param qo
     * @return
     */
    PageInfo query(TravelQuery qo);


    /**
     * 添加/更新
     * @param
     * @return
     */
    Long saveOrUpdate(Travel travel);

    /**
     * 查询所有
     * @return
     */
    List<Travel> list();

    /**
     * 查单个
     * @param sid
     * @return
     */
    Travel get(Long sid);

    PageInfo<Travel> selectTravelByState(TravelQuery qo, int state);

    TravelContent getContent(Long id);

    List<Travel> getDetailTop3(Long id);

    void changeState(Long id, int state);
}
