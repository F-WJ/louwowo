package cn.wolfcode.luowowo.article.query;

import cn.wolfcode.luowowo.common.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 目的地
 * 查询条件封装对象
 */
@Setter
@Getter
public class StrategyCatalogQuery extends QueryObject {

    private Long destId;
    private Long catalogId;

}
