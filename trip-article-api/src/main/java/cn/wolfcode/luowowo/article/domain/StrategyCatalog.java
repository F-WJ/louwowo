package cn.wolfcode.luowowo.article.domain;

import cn.wolfcode.luowowo.common.domain.BaseDomain;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 攻略分类： 从属于某个目的地
 */
@Setter
@Getter
public class StrategyCatalog extends BaseDomain {

    public static final int STATE_NORMAL = 0;  //显示
    public static final int STATE_DISABLE = 1;  //禁用

    private Strategy strategy;  //管理
    private Long destId;  //目的地
    private int state = STATE_NORMAL;  //状态
    private String name;  //分类名
    private int sequence; //排序

    //当前分类下, 所以明细
    private List<StrategyDetail> details = new ArrayList<>();

    public String getStateDisplay(){
        return state == STATE_NORMAL ? "正常" : "禁用";
    }

    public String getJsonString(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);

        if(strategy != null){
            map.put("strategyId",strategy.getId());
            map.put("strategySubTitle",strategy.getSubTitle());
        }

        map.put("state",state);
        map.put("sequence",sequence);
        return JSON.toJSONString(map);
    }
}