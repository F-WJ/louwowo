package cn.wolfcode.luowowo.article.service.impl;

import cn.wolfcode.luowowo.article.domain.Travel;
import cn.wolfcode.luowowo.article.domain.TravelContent;
import cn.wolfcode.luowowo.article.mapper.TravelContentMapper;
import cn.wolfcode.luowowo.article.mapper.TravelMapper;
import cn.wolfcode.luowowo.article.query.TravelQuery;
import cn.wolfcode.luowowo.article.query.travelDay;
import cn.wolfcode.luowowo.article.query.travelPreExpend;
import cn.wolfcode.luowowo.article.query.travelTime;
import cn.wolfcode.luowowo.article.service.ITravelService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

@Service
public class TravelServiceImpl implements ITravelService {

    @Autowired
    private TravelMapper travelMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private TravelContentMapper travelContentMapper;


    @Override
    public PageInfo query(TravelQuery qo) {
        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize());
        return new PageInfo(travelMapper.selectForList(qo));
    }

    @Override
    public Long saveOrUpdate(Travel travel) {
        if(travel.getId() == null){
            //创建时间
            travel.setCreateTime(new Date());
            //更新时间
            travel.setLastUpdateTime(new Date());
            travelMapper.insert(travel);
            //保存文章
            TravelContent travelContent = travel.getTravelContent();
            travelContent.setId(travel.getId());
            travelContentMapper.insert(travelContent);
        }else{
            //更新时间
            travel.setLastUpdateTime(new Date());
            travelMapper.updateByPrimaryKey(travel);
            //保存文章
            TravelContent travelContent = travel.getTravelContent();
            travelContent.setId(travel.getId());
            travelContentMapper.insert(travelContent);
        }
        return travel.getId();
    }

    @Override
    public List<Travel> list() {
        return travelMapper.selectAll();
    }

    @Override
    public Travel get(Long sid) {
        return travelMapper.selectByPrimaryKey(sid);
    }

    public Query query(int type){
        // 构建限制条件
        Criteria criteria = Criteria.where("id").is(type);
        // 创建查询对象
        Query query = new Query();
        // 添加限制条件
        query.addCriteria(criteria);
        return query;
    }

    @Override
    public PageInfo<Travel> selectTravelByState(TravelQuery qo, int state) {
        int travelTimeType = qo.getTravelTimeType();
        //mongodb查询
        List<travelTime> travelTimes = mongoTemplate.find(query(travelTimeType), travelTime.class, "travelTime");
        travelTime travelTime = travelTimes.get(0);

        int dayType = qo.getDayType();
        //mongodb查询
        List<travelDay> travelDays = mongoTemplate.find(query(dayType), travelDay.class, "travelDay");
        travelDay travelDay = travelDays.get(0);

        int perExpendType = qo.getPerExpendType();
        //mongodb查询
        List<travelPreExpend> travelPreExpends = mongoTemplate.find(query(perExpendType), travelPreExpend.class, "travelPreExpend");
        travelPreExpend travelPreExpend = travelPreExpends.get(0);


        PageHelper.startPage(qo.getCurrentPage(), qo.getPageSize(), qo.getOrderBy());
        return new PageInfo<Travel>(travelMapper.selectTravelByState(qo, state, travelTime, travelDay, travelPreExpend ));
    }

    @Override
    public TravelContent getContent(Long id) {
        return travelMapper.getContent(id);
    }

    @Override
    public List<Travel> getDetailTop3(Long id) {
        return travelMapper.getDetailTop3(id);
    }

    @Override
    public void changeState(Long id, int state) {
        travelMapper.changeState(id, state);
    }
}
