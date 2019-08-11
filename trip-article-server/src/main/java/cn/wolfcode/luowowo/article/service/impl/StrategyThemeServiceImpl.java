package cn.wolfcode.luowowo.article.service.impl;

import cn.wolfcode.luowowo.article.domain.StrategyTheme;
import cn.wolfcode.luowowo.article.mapper.StrategyThemeMapper;
import cn.wolfcode.luowowo.article.query.StrategyThemeQuery;
import cn.wolfcode.luowowo.article.service.IStrategyThemeService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class StrategyThemeServiceImpl implements IStrategyThemeService {

    @Autowired
    private StrategyThemeMapper strategyThemeMapper;


    @Override
    public PageInfo query(StrategyThemeQuery qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        return new PageInfo(strategyThemeMapper.selectForList(qo));
    }

    @Override
    public void saveOrUpdate(StrategyTheme strategyTheme) {
        if(strategyTheme.getId() == null){
            strategyThemeMapper.insert(strategyTheme);
        }else{
            strategyThemeMapper.updateByPrimaryKey(strategyTheme);
        }
    }

    @Override
    public List<StrategyTheme> list() {
        return strategyThemeMapper.selectAll();
    }
}
