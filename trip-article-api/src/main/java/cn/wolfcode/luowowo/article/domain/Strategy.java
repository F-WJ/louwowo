package cn.wolfcode.luowowo.article.domain;

import cn.wolfcode.luowowo.common.domain.BaseDomain;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 大攻略
 */
@Getter
@Setter
public class Strategy extends BaseDomain {
    public static final int STATE_NORMAL = 0; //正常
    public static final int STATE_COMMEND= 1;   //推荐
    private Destination dest; //绑定目的地

    private String title;  //标题
    private String subTitle; //副标题
    private String coverUrl; //封面图片
    private int state = STATE_NORMAL;  //状态
    private int sequence;  //排序

    public String getStateDisplay(){
        return state == STATE_NORMAL ? "正常" : "热门";
    }
    public String getJsonString(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("title",title);
        map.put("subTitle",subTitle);
        map.put("coverUrl",coverUrl);
        if(dest != null){
            map.put("destName", dest.getName());
            map.put("destId", dest.getId());
        }
        map.put("state",state);
        map.put("sequence",sequence);
        return JSON.toJSONString(map);
    }
}