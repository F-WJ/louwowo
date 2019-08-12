package cn.wolfcode.luowowo.article.query;

import cn.wolfcode.luowowo.common.query.QueryObject;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class DestinationQuery extends QueryObject {

    private Long parentId = -1L;

    private int ishot = -1;

    private Long destId;
    private Long catalogId;


}
