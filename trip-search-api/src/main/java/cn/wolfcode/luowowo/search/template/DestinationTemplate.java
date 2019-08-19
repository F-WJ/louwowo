package cn.wolfcode.luowowo.search.template;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName="luowowo_destination",type="destination")
@Getter
@Setter
public class DestinationTemplate implements Serializable {


    public static final String INDEX_NAME = "luowowo_destination";
    public static final String TYPE_NAME = "destination";
    @Id
    //@Field 每个文档的字段配置（类型、是否分词、是否存储、分词器 ）
    @Field(store=true, index = false,type = FieldType.Long)
    private Long id;  //攻略id

    @Field(index=true,store=true,type = FieldType.Keyword)
    private String name;
    @Field(index=true,analyzer="ik_max_word",store=true,searchAnalyzer="ik_max_word",type = FieldType.Text)
    private String info;

    @Field(index=true,store=true,type = FieldType.Keyword)
    private String coverUrl;
}