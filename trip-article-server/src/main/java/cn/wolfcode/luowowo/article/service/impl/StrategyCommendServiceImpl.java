package cn.wolfcode.luowowo.article.service.impl;

import cn.wolfcode.luowowo.article.domain.StrategyCommend;
import cn.wolfcode.luowowo.article.mapper.StrategyCommendMapper;
import cn.wolfcode.luowowo.article.query.StrategyCommendQuery;
import cn.wolfcode.luowowo.article.service.IStrategyCommendService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class StrategyCommendServiceImpl implements IStrategyCommendService {

    @Autowired
    private StrategyCommendMapper strategyCommendMapper;

    @Override
    public PageInfo query(StrategyCommendQuery qo) {

        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());


        return new PageInfo(strategyCommendMapper.selectForList(qo));
    }

    @Override
    public void saveOrUpdate(StrategyCommend strategyCommend) {
        if(strategyCommend.getId() == null){
            strategyCommendMapper.insert(strategyCommend);
        }else{
            strategyCommendMapper.updateByPrimaryKey(strategyCommend);
        }
    }

    @Override
    public List<StrategyCommend> queryCommendTop5() {
        return strategyCommendMapper.selectCommendTop5();
    }
}
