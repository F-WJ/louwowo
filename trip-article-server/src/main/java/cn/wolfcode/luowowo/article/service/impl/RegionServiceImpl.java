package cn.wolfcode.luowowo.article.service.impl;


import cn.wolfcode.luowowo.article.mapper.RegionMapper;
import cn.wolfcode.luowowo.article.domain.Region;

import cn.wolfcode.luowowo.article.query.RegionQuery;
import cn.wolfcode.luowowo.article.service.IRegionService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class RegionServiceImpl implements IRegionService {

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public PageInfo query(RegionQuery qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        return new PageInfo(regionMapper.selectForList(qo));
    }

    @Override
    public void saveOrUpdate(Region region) {
        if(region.getId() == null){
            regionMapper.insert(region);
        }else{
            regionMapper.updateByPrimaryKey(region);
        }

    }

    @Override
    public void changeHotValue(Long id, boolean hot) {
        regionMapper.updateHotValue(id, hot);
    }

    @Override
    public void delete(Long id) {
        regionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Region> queryHotRegion() {
        return regionMapper.selectHotRegion();
    }
}
