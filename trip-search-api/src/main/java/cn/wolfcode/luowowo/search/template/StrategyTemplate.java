package cn.wolfcode.luowowo.search.template;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Document(indexName="luowowo_strategy",type="strategy")
@Getter
@Setter
public class StrategyTemplate implements Serializable {
    public static final String INDEX_NAME = "luowowo_strategy";
    public static final String TYPE_NAME = "strategy";
    @Id
    //@Field 每个文档的字段配置（类型、是否分词、是否存储、分词器 ）
    @Field(store=true, index = true,type = FieldType.Long)
    private Long id;  //攻略id

    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_max_word",type = FieldType.Text)
    private String title;  //攻略标题

    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_max_word",type = FieldType.Text)
    private String subTitle;    //攻略副标题

    @Field(store=true, index = true,type = FieldType.Long)
    private Long destId;      //目的地Id

    @Field(index=true,store=true,type = FieldType.Keyword)
    private String destName;    //目的地名称

    @Field(store=true, index = true,type = FieldType.Long)
    private Long countryId;   //国家id(通过目的id关联而来)
    @Field(index=true,store=true,type = FieldType.Keyword)
    private String countryName;


    @Field(store=true, index = true,type = FieldType.Long)
    private Long provinceId;  //省份id(通过目的id关联而来)
    @Field(index=true,store=true,type = FieldType.Keyword)
    private String provinceName;


    @Field(store=true, index = true,type = FieldType.Long)
    private Long themeId;     //主题id
    @Field(index=true,store=true,type = FieldType.Keyword)
    private String themeName;   //主题名


    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_max_word",type = FieldType.Text)
    private String summary;     //概要

    @Field(index=true,store=true,type = FieldType.Keyword)
    private String coverUrl;     //封面

    @Field(pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis", type=FieldType.Date, format = DateFormat.custom)
    private Date createTime;   //创建时间




    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_max_word",type = FieldType.Text)
    private List<String> tags;        //标签集合

    @Field(store=true, index = true,type = FieldType.Integer)
    private int viewnum;
    @Field(store=true, index = true,type = FieldType.Integer)
    private int replynum;
    @Field(store=true, index = true,type = FieldType.Integer)
    private int favornum;
    @Field(store=true, index = true,type = FieldType.Integer)
    private int thumbsupnum; //点赞

}