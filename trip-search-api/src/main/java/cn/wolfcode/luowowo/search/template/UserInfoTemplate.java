package cn.wolfcode.luowowo.search.template;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName="luowowo_userinfo",type="userinfo")
@Getter
@Setter
public class UserInfoTemplate implements Serializable {

    public static final String INDEX_NAME = "luowowo_userinfo";
    public static final String TYPE_NAME = "userinfo";

    @Id
    //@Field 每个文档的字段配置（类型、是否分词、是否存储、分词器 ）
    @Field(store=true, index = true,type = FieldType.Long)
    private Long id;  //攻略id

    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_max_word",type = FieldType.Text)
    private String nickname;
    @Field(index=true,store=true,type = FieldType.Keyword)
    private String destName;
    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_max_word",type = FieldType.Text)
    private String info;

    @Field(store=true, index = true,type = FieldType.Integer)
    private int travelnum;
    @Field(store=true, index = true,type = FieldType.Integer)
    private int replynum;
    @Field(store=true, index = true,type = FieldType.Integer)
    private int fansnum;
    @Field(store=true, index = true,type = FieldType.Integer)
    private int level;
    @Field(index=true,store=true,type = FieldType.Keyword)
    private String headUrl;

}
