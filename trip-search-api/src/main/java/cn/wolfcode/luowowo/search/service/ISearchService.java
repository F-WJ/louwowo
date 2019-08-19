package cn.wolfcode.luowowo.search.service;

import cn.wolfcode.luowowo.search.query.SearchQueryObject;
import org.springframework.data.domain.Page;

/**
 * 工具服务
 */
public interface ISearchService {

    /**
     * 高量查询
     * @param index  索引
     * @param type   类型
     * @param clz    泛型
     * @param qo     条件
     * @param fields 参与搜索的字段
     * @param <T>    进行转换泛型
     * @return
     */
     <T> Page<T> searchWithHighlight(String index, String type, Class<T> clz, SearchQueryObject qo, String... fields);
}
