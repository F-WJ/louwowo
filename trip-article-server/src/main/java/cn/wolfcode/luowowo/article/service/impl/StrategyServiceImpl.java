package cn.wolfcode.luowowo.article.service.impl;

import cn.wolfcode.luowowo.article.domain.Strategy;
import cn.wolfcode.luowowo.article.mapper.StrategyMapper;
import cn.wolfcode.luowowo.article.query.StrategyQuery;
import cn.wolfcode.luowowo.article.service.IStrategyService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class StrategyServiceImpl implements IStrategyService {

    @Autowired
    private StrategyMapper strategyMapper;


    @Override
    public PageInfo query(StrategyQuery qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        return new PageInfo(strategyMapper.selectForList(qo));
    }

    @Override
    public void saveOrUpdate(Strategy strategy) {
        if(strategy.getId() == null){
            strategyMapper.insert(strategy);
        }else{
            strategyMapper.updateByPrimaryKey(strategy);
        }
    }

    @Override
    public List<Strategy> list() {
        return strategyMapper.selectAll();
    }

    @Override
    public Strategy get(Long sid) {
        return strategyMapper.selectByPrimaryKey(sid);
    }
}
