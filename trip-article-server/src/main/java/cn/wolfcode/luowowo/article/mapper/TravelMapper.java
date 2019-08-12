package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.Travel;
import cn.wolfcode.luowowo.article.domain.TravelContent;
import cn.wolfcode.luowowo.article.query.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Travel record);

    Travel selectByPrimaryKey(Long id);

    List<Travel> selectAll();

    int updateByPrimaryKey(Travel record);

    List<Travel> selectForList(TravelQuery qo);

    List<Travel> selectTravelByState(@Param("qo") TravelQuery qo, @Param("state") int state, @Param("travelTime") travelTime travelTime, @Param("travelDay") travelDay travelDay, @Param("travelPreExpend") travelPreExpend travelPreExpend);

    TravelContent getContent(Long id);

    List<Travel> getDetailTop3(Long id);

    void changeState(@Param("id") Long id, @Param("state") int state);
}