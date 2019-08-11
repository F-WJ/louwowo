package cn.wolfcode.luowowo.article.domain;

import cn.wolfcode.luowowo.common.domain.BaseDomain;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 攻略
 */
@Setter
@Getter
public class StrategyDetail extends BaseDomain {

    public static final boolean  ABROAD_NO = false;  //国内
    public static final boolean  ABROAD_YES = true;  //国外

    public static final int STATE_NORMAL = 0;  //正常
    public static final int STATE_PUBLISH = 1; //发布

    private Destination dest;  //关联的目的地

    private StrategyTheme theme; //关联主题

    private Strategy strategy;   //攻略

    private StrategyCatalog catalog;  //关联的分类

//    private LoginInfo author; //作者

    private String title;  //标题

    private String subTitle; //副标题

    private String summary;  //内容摘要

    private String coverUrl;  //封面

    private Date createTime;  //创建时间

    private boolean isabroad = ABROAD_NO;  //是否是国外

    private int viewnum;  //点击数

    private int replynum;  //攻略评论数

    private int favornum; //收藏数

    private int sharenum; //分享数

    private int thumbsupnum; //点赞个数

    private int state = STATE_NORMAL;  //状态

    private StrategyContent strategyContent; //攻略内容

    public String getStateDisplay(){
        return state == STATE_PUBLISH ? "发布" :"正常";
    }
}