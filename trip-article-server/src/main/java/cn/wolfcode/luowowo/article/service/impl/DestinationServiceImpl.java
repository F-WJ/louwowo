package cn.wolfcode.luowowo.article.service.impl;


import cn.wolfcode.luowowo.article.domain.Region;
import cn.wolfcode.luowowo.article.mapper.DestinationMapper;
import cn.wolfcode.luowowo.article.mapper.RegionMapper;
import cn.wolfcode.luowowo.article.domain.Destination;
import cn.wolfcode.luowowo.article.query.DestinationQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DestinationServiceImpl implements IDestinationService {

    @Autowired
    private DestinationMapper destinationMapper;

    @Autowired
    private RegionMapper regionMapper;

    @Override
    public PageInfo query(DestinationQuery qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        return new PageInfo(destinationMapper.selectForList(qo));
    }

    @Override
    public List<Destination> queryDestByDeep(int deep) {
        return destinationMapper.selectDestByDeep(deep);
    }

    @Override
    public void saveOrUpdate(Destination destination) {


    }

    @Override
    public List<Destination> getToasts(Long parentId) {

        List<Destination> list = new ArrayList<>();
        createToasts(parentId, list);
        //顺序反转
        Collections.reverse(list);
        return list;
    }

    @Override
    public List<Destination> queryDestByRegionId(Long rid) {
        if(rid == -1) {
            //查询国内所有景点
            return destinationMapper.selectDestsByParendId(1L);
        }
        //安装rid获取景点
        Region region = regionMapper.selectByPrimaryKey(rid);
        Long[] refIds = region.getRefIds();
        return destinationMapper.selectDestByIds(refIds);

    }

    private void createToasts(Long parentId, List<Destination> list){
        //1.根据前台传递过来的id值，获取地区
        Destination destination = destinationMapper.selectByPrimaryKey(parentId);
        if(destination == null){
            return;
        }
        list.add(destination);
        //2.判断是否为空，如果不为空继续调用createToasts，知道为null为止
        if(destination.getParent()  != null){
            createToasts(destination.getParent().getId(), list);
        }
    }


}
