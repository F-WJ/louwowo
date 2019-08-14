package cn.wolfcode.luowowo.comment.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 攻略评论
 */
@Setter
@Getter
@Document("strategy_comment")   //在MongoDB中文档名称(集合名)
public class StrategyComment implements Serializable {
    @Id  //文档id
    private String id;  //mongodb默认的是id
    private Long detailId;  //攻略(明细)id
    private String detailTitle; //游记标题

    private Long userId;    //用户id
    private String username;  //用户名
    private String city;
    private int level;
    private String headUrl;     //头像

    private Date createTime;    //创建时间
    private String content;      //评论内容
    private int thumbupnum;     //点赞数
    private List<Long> thumbuplist = new ArrayList<>();  //评论点赞人的id
}
