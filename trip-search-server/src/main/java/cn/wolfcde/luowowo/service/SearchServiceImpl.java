package cn.wolfcde.luowowo.service;

import cn.wolfcode.luowowo.search.query.SearchQueryObject;
import cn.wolfcode.luowowo.search.service.ISearchService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Override
    public <T> Page<T> searchWithHighlight(String index, String type, Class<T> clz, SearchQueryObject qo, String... fields) {
        String preTags = "<span style='color:red;'>";
        String postTags = "</span>";
        HighlightBuilder.Field[] fs = new HighlightBuilder.Field[fields.length];

        //声明需求给哪几个字段高亮显示
        for(int i = 0; i < fs.length; i++){
            fs[i] = new HighlightBuilder.Field(fields[i])
                    .preTags(preTags)
                    .postTags(postTags);
        }
        NativeSearchQueryBuilder searchQuery = new NativeSearchQueryBuilder();

        //设置索引跟类型
        searchQuery.withIndices(index).withTypes(type);
        //设置查询条件: 多个字段查询
        searchQuery.withQuery(QueryBuilders.multiMatchQuery(qo.getKeyword(), fields));
        //设置分页数据
        searchQuery.withPageable(qo.getPageableWithoutSort());
        //设置高亮显示字段
        searchQuery.withHighlightFields(fs);

        //封装查询结果数据
        //参数1: 条件
        //参数2:结果封装对象泛型
        //参数3:结果封装逻辑
        return elasticsearchTemplate.queryForPage(searchQuery.build(), clz, new SearchResultMapper() {

            //结果处理逻辑
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clz, Pageable pageable) {
                List<T>  list = new ArrayList<>();
                SearchHits hits = response.getHits();
                SearchHit[] searchHits = hits.getHits();
                for (SearchHit searchHit : searchHits) {
                    //仅仅是将结果封装成功指定的泛型对象, 此时查询字段没有高亮效果
                    T t = JSON.parseObject(searchHit.getSourceAsString(), clz);

                    //将查询出来的具有高亮效果的字段, 替换原来字段值
                    highlightFieldsCopy(t, searchHit.getHighlightFields(), fields);
                    list.add(t);
                }
                AggregatedPage<T> result = new AggregatedPageImpl<T>((List<T>) list, pageable,
                        response.getHits().getTotalHits());
                return result;
            }
        });
    }

    private <T> void highlightFieldsCopy(T t, Map<String, HighlightField> map, String ...fields){

        Map<String, String> mm = new HashMap<>();

        for (String field : fields) {
            //高亮显示字段
            HighlightField hf = map.get(field);
            if (hf != null) {
                // 高亮显示字段返回结果是数组类型,所以,此时将数组拼接一个完整的字符串
                Text[] fragments = hf.fragments();
                String str = "";
                for (Text text : fragments) {
                    str += text;
                }
                mm.put(field, str);
            }
        }
        try {
            //替换
            BeanUtils.copyProperties(t, mm);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
