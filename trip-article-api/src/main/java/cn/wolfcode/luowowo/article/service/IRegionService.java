package cn.wolfcode.luowowo.article.service;


import cn.wolfcode.luowowo.article.domain.Region;
import cn.wolfcode.luowowo.article.query.RegionQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;


public interface IRegionService {

    PageInfo query(RegionQuery qo);


    void saveOrUpdate(Region region);


    void changeHotValue(Long id, boolean hot);

    void delete(Long id);


    List<Region> queryHotRegion();
}
