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
 * 目的地(行政地区：国家/省份/城市)
 */
@Setter
@Getter
public class Destination extends BaseDomain {

    public static final boolean STATE_HOT = true;
    public static final boolean STATE_NORMAL = false;

    private String name;        //名称
    private String english;  //英文名
    private String pinyin;      //中文拼音
    private Destination parent; //上级目的地
    private Region region;  //所属区域， 仅国家有
    private boolean ishot = STATE_NORMAL;         //是否为热点
    private String info;    //简介
    private int clicknum;      //点击次数
    private int deep;
    private String coverUrl;

    public String getJsonString(){
        Map<String, Object> map = new HashMap<>();
        map.put("info",info);
        map.put("id",id);
        return JSON.toJSONString(map);
    }
    public String getHotDisplay(){
        return ishot ? "是":"否";
    }


    //子地区
    private List<Destination> children = new ArrayList<>();
}