package cn.wolfcode.luowowo.comment.service;

import cn.wolfcode.luowowo.comment.domain.StrategyComment;
import cn.wolfcode.luowowo.comment.query.StrategyCommentQuery;
import org.springframework.data.domain.Page;

public interface IStrategyCommentService {
    void saveOrUpdate(StrategyComment strategyComment);

    Page query(StrategyCommentQuery qo);

    void saveThumbupnum(String toid, Long fromid);
}
