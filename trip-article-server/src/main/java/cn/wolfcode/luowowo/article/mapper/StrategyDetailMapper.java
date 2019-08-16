package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.StrategyDetail;
import cn.wolfcode.luowowo.article.query.StrategyDetailQuery;
import cn.wolfcode.luowowo.article.query.StrategyQuery;
import cn.wolfcode.luowowo.article.service.vo.newStrategyStatisVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StrategyDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StrategyDetail record);

    StrategyDetail selectByPrimaryKey(Long id);

    List<StrategyDetail> selectAll();

    int updateByPrimaryKey(StrategyDetail record);

    List<StrategyDetail> selectForList(StrategyDetailQuery qo);

    void updateState(@Param("id") Long id, @Param("state") int state);

    void insertRelation(@Param("detailId") Long detailId, @Param("tagId") Long tagId);

    void deleteRelation(Long id);

    List<StrategyDetail> selectDetailTop3(Long id);


    List<StrategyDetail> getDetailByTag(StrategyQuery qo);

    StrategyDetail getByCatalogId(Long catalogId);

    void updateCommentNumById(Long detailId);

    void updateStatisData(@Param("vo") newStrategyStatisVO vo);
}