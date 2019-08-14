package cn.wolfcode.luowowo.comment.service.impl;

import cn.wolfcode.luowowo.comment.domain.StrategyComment;
import cn.wolfcode.luowowo.comment.query.StrategyCommentQuery;
import cn.wolfcode.luowowo.comment.repository.IStrategyCommentRepository;
import cn.wolfcode.luowowo.comment.service.IStrategyCommentService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;


@Service
public class StrategyCommentServiceImpl implements IStrategyCommentService {

    @Autowired
    private IStrategyCommentRepository strategyCommentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void saveOrUpdate(StrategyComment strategyComment) {
        mongoTemplate.save(strategyComment);
    }

    @Override
    public Page query(StrategyCommentQuery qo) {
        //查询
        Query query = new Query();
        if(qo.getDetailId() != -1){
            query.addCriteria(Criteria.where("detailId").is(qo.getDetailId()));
        }
        //数据总数
        long count = mongoTemplate.count(query, StrategyComment.class);

        if(count == 0){
            return Page.empty();
        }
        //查询
        List<StrategyComment> strategyComments = mongoTemplate.find(query, StrategyComment.class);

        PageRequest pageRequest = PageRequest.of(qo.getCurrentPage() - 1, qo.getPageSize(), Sort.Direction.DESC, "createTime");

        //每页数据，分页， 总数
        PageImpl page = new PageImpl(strategyComments, pageRequest, count);
        return page;
    }

    @Override
    public void saveThumbupnum(String toid, Long fromid) {
        Optional<StrategyComment> op = strategyCommentRepository.findById(toid);
        StrategyComment strategyComment = op.get();
        List<Long> thumbuplist = strategyComment.getThumbuplist();
        //判断是否已经点赞过
        if(thumbuplist.contains(fromid)){
            //取消点赞
            strategyComment.setThumbupnum(strategyComment.getThumbupnum() - 1);
            thumbuplist.remove(fromid);
            strategyCommentRepository.save(strategyComment);
        }else{
            //点赞
            strategyComment.setThumbupnum(strategyComment.getThumbupnum() + 1);
            thumbuplist.add(fromid);
            strategyCommentRepository.save(strategyComment);
        }
    }
}
