package cn.wolfcode.luowowo.comment.service.impl;

import cn.wolfcode.luowowo.comment.domain.TravelComment;
import cn.wolfcode.luowowo.comment.query.TravelCommentQuery;
import cn.wolfcode.luowowo.comment.repository.ITravelCommentRepository;
import cn.wolfcode.luowowo.comment.service.ITravelCommentService;
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
public class TravelCommentServiceImpl implements ITravelCommentService {

    @Autowired
    private ITravelCommentRepository travelCommentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void saveOrUpdate(TravelComment travelComment) {
        //通过id判断
        if(travelComment.getType() == 1){
            //关联评论
            Optional<TravelComment> op = travelCommentRepository.findById(travelComment.getRefComment().getId());
            travelComment.setRefComment(op.get());
        }
        mongoTemplate.save(travelComment);
    }

    @Override
    public Page query(TravelCommentQuery qo) {
        //查询
        Query query = new Query();

        if(qo.getTravelId() != -1){
            query.addCriteria(Criteria.where("travelId").is(qo.getTravelId()));
        }


        //数据总数
        long count = mongoTemplate.count(query, TravelComment.class);

        if(count == 0){
            return Page.empty();
        }
        //查询
        List<TravelComment> travelComments = mongoTemplate.find(query, TravelComment.class);

        PageRequest pageRequest = PageRequest.of(qo.getCurrentPage() - 1, qo.getPageSize(), Sort.Direction.DESC, "createTime");

        //每页数据，分页， 总数
        PageImpl page = new PageImpl(travelComments, pageRequest, count);
        return page;
    }


}
