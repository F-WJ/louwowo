package cn.wolfcode.luowowo.article.service.impl;

import cn.wolfcode.luowowo.article.domain.TravelCommend;
import cn.wolfcode.luowowo.article.mapper.TravelCommendMapper;
import cn.wolfcode.luowowo.article.query.TravelCommendQuery;
import cn.wolfcode.luowowo.article.service.ITravelCommendService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class TravelCommendServiceImpl implements ITravelCommendService {

    @Autowired
    private TravelCommendMapper travelCommendMapper;

    @Override
    public PageInfo query(TravelCommendQuery qo) {

        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());


        return new PageInfo(travelCommendMapper.selectForList(qo));
    }

    @Override
    public void saveOrUpdate(TravelCommend travelCommend) {
        if(travelCommend.getId() == null){
            travelCommendMapper.insert(travelCommend);
        }else{
            travelCommendMapper.updateByPrimaryKey(travelCommend);
        }
    }

    @Override
    public List<TravelCommend> queryCommendTop5() {
        return travelCommendMapper.selectCommendTop5();
    }
}
