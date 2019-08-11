package cn.wolfcode.luowowo.article.query;

import cn.wolfcode.luowowo.common.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

/**
 * 明细
 * 查询条件封装对象
 */
@Setter
@Getter
public class StrategyDetailQuery extends QueryObject {

    private Long destId = -1L;

    private Long tagId = -1L;


}
