package cn.wolfcode.luowowo.article.service.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 攻略redis中统计数据
 * 运用模块：
 *  1：数据统计(回复，点赞，收藏，分享，查看)
 *  2：排行数据查询
 */
@Getter
@Setter
public class newStrategyStatisVO implements Serializable {

    private boolean isabroad;
    private Long destId;
    private String destName;
    private String title;

    private Long strategyId;  //攻略id
    private int viewnum;  //点击数
    private int replynum;  //攻略评论数
    private int favornum; //收藏数
    private int sharenum; //分享数
    private int thumbsupnum; //点赞个数
}