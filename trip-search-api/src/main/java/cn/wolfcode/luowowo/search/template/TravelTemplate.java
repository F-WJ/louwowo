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

@Document(indexName="luowowo_travel",type="travel")
@Getter
@Setter
public class TravelTemplate implements Serializable {
    public static final String INDEX_NAME = "luowowo_travel";
    public static final String TYPE_NAME = "travel";

    //@Field 每个文档的字段配置（类型、是否分词、是否存储、分词器 ）
    @Id
    @Field(store = true, index = true, type = FieldType.Long)
    private Long id;  //游记id

    @Field(pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis", type=FieldType.Date, format = DateFormat.custom)
    private Date createTime;  //创建时间
    @Field(store=true, index = true,type = FieldType.Long)
    private Long destId;  //游记地点
    @Field(index=true,store=true,type = FieldType.Keyword)
    private String destName; //游记地点名称
    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_max_word",type = FieldType.Text)
    private String title;  //游记标题
    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_max_word",type = FieldType.Text)
    private String summary; //游记简介
    @Field(store=true, index = true,type = FieldType.Integer)
    private int viewnum;  //游记查看数
    @Field(store=true, index = true,type = FieldType.Long)

    private Long authorId;  //作者id
    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_max_word",type = FieldType.Text)
    private String authorName; //作者名
    @Field(store=true, index = true,type = FieldType.Integer)
    private int replynum;  //回复数
    @Field(index=true,store=true,type = FieldType.Keyword)
    private String coverUrl; //封面

}