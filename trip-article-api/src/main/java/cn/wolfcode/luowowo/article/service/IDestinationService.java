package cn.wolfcode.luowowo.article.service;



import cn.wolfcode.luowowo.article.domain.Destination;
import cn.wolfcode.luowowo.article.domain.Region;
import cn.wolfcode.luowowo.article.query.DestinationQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface IDestinationService {

    //查询
    PageInfo query(DestinationQuery qo);

    //查询分级
    List<Destination> queryDestByDeep(int deep);

    //添加
    void saveOrUpdate(Destination destination);


    List<Destination> getToasts(Long parentId);

    List<Destination> queryDestByRegionId(Long rid);

    Destination getCountry(Long id);

    Destination getProvince(Long id);
}
