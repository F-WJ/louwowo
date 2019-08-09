package cn.wolfcode.luowowo.article.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StrategyPersistenceStatisVO implements Serializable{
    private Long strategyId;  //攻略id
    private int viewnum;  //点击数
    private int replynum;  //攻略评论数
    private int favornum; //收藏数
    private int sharenum; //分享数
    private int thumbsupnum; //点赞个数
}
