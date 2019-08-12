package cn.wolfcode.luowowo.article.query;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Document("travelDay")
public class travelDay {
    @Id //文档的id使用ObjectId类型来封装，并且贴上@Id注解
    private ObjectId _id;
    private Long id;
    private Integer min;
    private Integer max;

}
