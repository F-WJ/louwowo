package cn.wolfcode.luowowo.comment.service;

import cn.wolfcode.luowowo.comment.domain.TravelComment;


import cn.wolfcode.luowowo.comment.query.TravelCommentQuery;
import org.springframework.data.domain.Page;

public interface ITravelCommentService {
    void saveOrUpdate(TravelComment travelComment);

    Page query(TravelCommentQuery qo);

}
