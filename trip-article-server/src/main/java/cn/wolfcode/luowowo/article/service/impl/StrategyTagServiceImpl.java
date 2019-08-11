package cn.wolfcode.luowowo.article.service.impl;

import cn.wolfcode.luowowo.article.domain.StrategyTag;
import cn.wolfcode.luowowo.article.mapper.StrategyTagMapper;
import cn.wolfcode.luowowo.article.query.StrategyTagQuery;
import cn.wolfcode.luowowo.article.service.IStrategyTagService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class StrategyTagServiceImpl implements IStrategyTagService {

    @Autowired
    private StrategyTagMapper strategyTagMapper;


    @Override
    public PageInfo query(StrategyTagQuery qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        return new PageInfo(strategyTagMapper.selectForList(qo));
    }

    @Override
    public void saveOrUpdate(StrategyTag strategyTag) {
        if(strategyTag.getId() == null){
            strategyTagMapper.insert(strategyTag);
        }else{
            strategyTagMapper.updateByPrimaryKey(strategyTag);
        }
    }

    @Override
    public List<StrategyTag> list() {
        return strategyTagMapper.selectAll();
    }

    @Override
    public String getTags(Long id) {
        return strategyTagMapper.selectTagString(id);
    }
}
