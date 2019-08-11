package cn.wolfcode.luowowo.article.service.impl;

import cn.wolfcode.luowowo.article.domain.*;
import cn.wolfcode.luowowo.article.mapper.StrategyContentMapper;
import cn.wolfcode.luowowo.article.mapper.StrategyDetailMapper;
import cn.wolfcode.luowowo.article.mapper.StrategyMapper;
import cn.wolfcode.luowowo.article.mapper.StrategyTagMapper;
import cn.wolfcode.luowowo.article.query.StrategyDetailQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class StrategyDetailServiceImpl implements IStrategyDetailService {

    @Autowired
    private StrategyDetailMapper strategyDetailMapper;
    @Autowired
    private StrategyContentMapper strategyContentMapper;

    @Autowired
    private IDestinationService destinationService;

    @Autowired
    private StrategyMapper strategyMapper;

    @Autowired
    private StrategyTagMapper strategyTagMapper;


    @Override
    public PageInfo query(StrategyDetailQuery qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        return new PageInfo(strategyDetailMapper.selectForList(qo));
    }

    @Override
    public void saveOrUpdate(StrategyDetail detail, String tags) {
        //关联目的地
        //大攻略
        Strategy strategy = strategyMapper.selectByPrimaryKey(detail.getStrategy().getId());
        //目的地
        Destination dest = strategy.getDest();
        detail.setDest(dest);
        //作者
//        detail.setAuthor(null);
        //摘要:summary:
        //1: 运营人人自己添加
        //2: 截取文章中前200个字
        String content = detail.getStrategyContent().getContent();
        if(content.length() >= 200){
            detail.setSummary(content.substring(0, 200));
        }else{
            detail.setSummary(content);
        }
//        //是否为海外: isabroad
//        boolean ret = destinationService.isAbroad(dest.getId());
//        detail.setIsabroad(ret);
        if(detail.getId() == null){
            //创建: createTime
            detail.setCreateTime(new Date());
            strategyDetailMapper.insert(detail);
            //添加内容
            StrategyContent strategyContent = detail.getStrategyContent();
            strategyContent.setId(detail.getId());
            strategyContentMapper.insert(strategyContent);
        }else{
            //更新
            strategyDetailMapper.updateByPrimaryKey(detail);
            StrategyContent strategyContent = detail.getStrategyContent();
            strategyContent.setId(detail.getId());
            strategyContentMapper.updateByPrimaryKey(strategyContent);
        }


        //处理标签


        //删除所以标签关系, 再行添加或更新动作
        strategyDetailMapper.deleteRelation(detail.getId());

        //tags: tag1, tag2, tag3
        String[] tagsArr = tags.split(",");
        if(tagsArr != null && tagsArr.length > 0){

            //优化点: 能不能排除重复的标签?
            for (String s : tagsArr) {
                StrategyTag strategyTag = new StrategyTag();
                strategyTag.setName(s);
                strategyTag.setState(StrategyTag.STATE_NORMAL);
                strategyTagMapper.insert(strategyTag);

                //维护中间表
                strategyDetailMapper.insertRelation(detail.getId(), strategyTag.getId());
            }
        }

    }

    @Override
    public List<StrategyDetail> list() {
        return strategyDetailMapper.selectAll();
    }

    @Override
    public void changeState(Long id, int state) {
        strategyDetailMapper.updateState(id, state);
    }

    @Override
    public StrategyDetail get(Long id) {
        return strategyDetailMapper.selectByPrimaryKey(id);
    }

    @Override
    public StrategyContent getContent(Long id) {
        return strategyContentMapper.selectByPrimaryKey(id);
    }


    @Override
    public List<StrategyDetail> getDetailTop3(Long id) {
        return strategyDetailMapper.selectDetailTop3(id);
    }

//    @Override
//    public void updateStatisData(StrategyPersistenceStatisVO vo) {
//        strategyDetailMapper.updateStatisData(vo);
//    }
}
