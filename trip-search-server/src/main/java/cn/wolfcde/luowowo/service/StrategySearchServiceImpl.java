package cn.wolfcde.luowowo.service;

import cn.wolfcde.luowowo.repository.IStrategyTemplateRepository;
import cn.wolfcde.luowowo.repository.IUserInfoTemplateRepository;
import cn.wolfcode.luowowo.search.query.SearchQueryObject;
import cn.wolfcode.luowowo.search.service.IStrategySearchService;
import cn.wolfcode.luowowo.search.template.StrategyTemplate;
import cn.wolfcode.luowowo.search.vo.StatisVO;
import com.alibaba.dubbo.config.annotation.Service;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.bucket.composite.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.*;


@Service
public class StrategySearchServiceImpl implements IStrategySearchService {

    @Autowired
    private IStrategyTemplateRepository repository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ElasticsearchClient elasticsearchClient;



    @Override
    public void saveOrUpdate(StrategyTemplate template) {
        repository.save(template);
    }

    @Override
    public List<Map<String, Object>> getThemeCommend() {

        //创建存入数据
        List<Map<String, Object>> data = new ArrayList<>();

        //设置条件判断
        List<CompositeValuesSourceBuilder<?>> sources = new ArrayList<>();
        TermsValuesSourceBuilder idSource = new TermsValuesSourceBuilder("id");
        idSource.missingBucket(true);
        idSource.field("themeId");
        sources.add(idSource);

        TermsValuesSourceBuilder nameSource = new TermsValuesSourceBuilder("name");
        nameSource.missingBucket(true); // 忽略其他数据
        nameSource.field("themeName");
        sources.add(nameSource);

        CompositeAggregationBuilder aggBuilder = new CompositeAggregationBuilder("themeGroup", sources);//条件结合

        //执行es
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withIndices(StrategyTemplate.INDEX_NAME); //表名
        builder.withTypes(StrategyTemplate.TYPE_NAME); //查询类型
        builder.addAggregation(aggBuilder);

        //获取数据
        AggregatedPage<StrategyTemplate> page = (AggregatedPage<StrategyTemplate>) repository.search(builder.build());
        InternalComposite aggregation = page.getAggregations().get("themeGroup");

        //封装数据
        ArrayList<StatisVO> themes = new ArrayList<>();
        for (CompositeAggregation.Bucket bucket : aggregation.getBuckets()) {
            Map<String, Object> key = bucket.getKey();
            StatisVO vo = new StatisVO();
            vo.setId(Long.parseLong(key.get("id").toString()));
            vo.setName(key.get("name").toString());
            vo.setCount(bucket.getDocCount());
            themes.add(vo);

        }

        //排序(thteme)
        Collections.sort(themes, new Comparator<StatisVO>() {
            @Override
            public int compare(StatisVO o1, StatisVO o2) {
                return Integer.valueOf(o2.getCount() - o1.getCount() + "");
            }
        });

        //只回显10个结果
//        List<StatisVO> subThemes = themes.subList(0, 10);



        //根据主题id查询目的地
        for (StatisVO theme : themes) {
            List<StatisVO> dests = this.getDestByThemeid(theme.getId());
            HashMap<String, Object> map = new HashMap<>();
            map.put("theme", theme);
            map.put("dests", dests);
            data.add(map);
        }


        return data;
    }

    @Override
    public List<StatisVO> queryConditionGroup(int type) {
        String idField = "";
        String nameField = "";

        if(type == SearchQueryObject.CONDITION_TYPE_ABROAD){
          idField = "countryId";
          nameField = "countryName";
        }else if(type == SearchQueryObject.CONDITION_TYPE_UN_ABROAD){
            idField = "provinceId";
            nameField = "provinceName";
        }else if(type == SearchQueryObject.CONDITION_TYPE_THEME){
            idField = "themeId";
            nameField = "themeName";
        }

        return this.staticGroup(idField, nameField, "conditionGroup");
    }

    /**
     * 根据type查询攻略信息
     * @param qo
     * @return
     */
    @Override
    public Page query(SearchQueryObject qo) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if( qo.getType() == SearchQueryObject.CONDITION_TYPE_ABROAD){
            boolQuery.must(QueryBuilders.termQuery("countryId", qo.getTypeValue()));
        }else if(qo.getType() == SearchQueryObject.CONDITION_TYPE_UN_ABROAD){
            boolQuery.must(QueryBuilders.termQuery("provinceId", qo.getTypeValue()));
        }else if(qo.getType() == SearchQueryObject.CONDITION_TYPE_THEME){
            boolQuery.must(QueryBuilders.termQuery("themeId", qo.getTypeValue()));
        }

        return repository.search(boolQuery, qo.getPageable());
    }

    @Override
    public List<StrategyTemplate> findByDestName(String name) {
        return repository.findByDestName(name);
    }

    private List<StatisVO> staticGroup(String idField, String nameField, String conditionGroup) {
        //创建存入数据
        List<Map<String, Object>> data = new ArrayList<>();

        //设置条件判断
        List<CompositeValuesSourceBuilder<?>> sources = new ArrayList<>();
        TermsValuesSourceBuilder idSource = new TermsValuesSourceBuilder("id");
        idSource.missingBucket(true);
        idSource.field(idField);
        sources.add(idSource);

        TermsValuesSourceBuilder nameSource = new TermsValuesSourceBuilder("name");
        nameSource.missingBucket(true); // 忽略其他数据
        nameSource.field(nameField);
        sources.add(nameSource);

        CompositeAggregationBuilder aggBuilder = new CompositeAggregationBuilder(conditionGroup, sources);//条件结合

        //执行es
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withIndices(StrategyTemplate.INDEX_NAME); //表名
        builder.withTypes(StrategyTemplate.TYPE_NAME); //查询类型
        builder.addAggregation(aggBuilder);

        //获取数据
        AggregatedPage<StrategyTemplate> page = (AggregatedPage<StrategyTemplate>) repository.search(builder.build());
        InternalComposite aggregation = page.getAggregations().get(conditionGroup);

        //封装数据
        ArrayList<StatisVO> themes = new ArrayList<>();
        for (CompositeAggregation.Bucket bucket : aggregation.getBuckets()) {
            Map<String, Object> key = bucket.getKey();
            if(key.get("id") == null || key.get("name") == null){
                continue;
            }
            if("中国".equals(key.get("name").toString())){
                continue;
            }
            StatisVO vo = new StatisVO();
            vo.setId(Long.parseLong(key.get("id").toString()));
            vo.setName(key.get("name").toString());
            vo.setCount(bucket.getDocCount());
            themes.add(vo);

        }

        //排序(thteme)
        Collections.sort(themes, new Comparator<StatisVO>() {
            @Override
            public int compare(StatisVO o1, StatisVO o2) {
                return Integer.valueOf(o2.getCount() - o1.getCount() + "");
            }
        });
        return themes;
    }

    private List<StatisVO> getDestByThemeid(Long themeId) {
        //通过id查询结果
        Iterable<StrategyTemplate> iterable = repository.search(QueryBuilders.termQuery("themeId", themeId));

        //封装结果
        ArrayList<StatisVO> list = new ArrayList<>();
        ArrayList<String> temp = new ArrayList<>();
        iterable.forEach(st ->{
            StatisVO vo = new StatisVO();
            if(!temp.contains(st.getDestName())) {
                vo.setId(st.getDestId());
                vo.setName(st.getDestName());
                list.add(vo);
                temp.add(st.getDestName());
            }
        });

        return list;
    }
}
