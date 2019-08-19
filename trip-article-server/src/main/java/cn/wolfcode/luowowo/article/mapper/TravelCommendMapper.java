package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.TravelCommend;
import cn.wolfcode.luowowo.article.query.TravelCommendQuery;

import java.util.List;

public interface TravelCommendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TravelCommend record);

    TravelCommend selectByPrimaryKey(Long id);

    List<TravelCommend> selectAll();

    int updateByPrimaryKey(TravelCommend record);

    List<TravelCommend> selectForList(TravelCommendQuery qo);

    List<TravelCommend> selectCommendTop5();
}