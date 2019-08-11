package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.Region;
import cn.wolfcode.luowowo.article.query.RegionQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RegionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Region record);

    Region selectByPrimaryKey(Long id);

    List<Region> selectAll();

    int updateByPrimaryKey(Region record);


    List<Region> selectForList(RegionQuery qo);

    void updateHotValue(@Param("id") Long id, @Param("hot") boolean hot);

    List<Region> selectHotRegion();
}