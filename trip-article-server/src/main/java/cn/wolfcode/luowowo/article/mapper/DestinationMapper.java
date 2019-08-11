package cn.wolfcode.luowowo.article.mapper;

import cn.wolfcode.luowowo.article.domain.Destination;
import cn.wolfcode.luowowo.article.query.DestinationQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DestinationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Destination record);

    Destination selectByPrimaryKey(Long id);

    List<Destination> selectAll();

    int updateByPrimaryKey(Destination record);

    List<Destination> selectForList(DestinationQuery qo);

    List<Destination> selectDestByDeep(int deep);

    List<Destination> selectDestByIds(Long[] refIds);

    void updateHotValue(@Param("id") Long id, @Param("hot") boolean hot);

    void updateInfo(@Param("id") Long id, @Param("info") String info);


    List<Destination> selectDestsByParendId(long parendId);
}