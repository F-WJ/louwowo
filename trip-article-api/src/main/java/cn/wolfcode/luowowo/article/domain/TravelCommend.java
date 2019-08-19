package cn.wolfcode.luowowo.article.domain;

import cn.wolfcode.luowowo.common.domain.BaseDomain;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 游记推荐
 */
@Setter
@Getter
public class TravelCommend  extends BaseDomain {
    public static final int STATE_NORMAL = 0;   //正常
    public static final int STATE_DISABLE = 1;  //禁用

    private Long travelId;  //关联游记
    private String title;  //标题
    private String subTitle; //副标题
    private String coverUrl; //封面
    private int state = STATE_NORMAL; //状态
    private int sequence; //排序

    public String getJsonString(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("title",title);
        map.put("subTitle",subTitle);
        map.put("coverUrl",coverUrl);
        map.put("state",state);
        map.put("sequence",sequence);
        map.put("travelId",travelId);
        return JSON.toJSONString(map);
    }

    public String getStateDisplay(){
        return state == STATE_NORMAL?"正常":"禁用";
    }
}