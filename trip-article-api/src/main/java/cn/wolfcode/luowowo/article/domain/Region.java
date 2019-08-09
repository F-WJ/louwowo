package cn.wolfcode.luowowo.article.domain;


import cn.wolfcode.luowowo.common.domain.BaseDomain;
import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 区域
 */
@Setter
@Getter
public class Region extends BaseDomain {
    public static final boolean STATE_HOT = true;
    public static final boolean STATE_NORMAL = false;

    private String name;        //地区名
    private String sn;          //地区编码
    private String ref;  //关联的id，多个用,分开
    private Long[] refIds;
    private boolean ishot = STATE_NORMAL;         //是否为热点
    private int sequence;   //序号
    private String info;  //简介

    //添加编辑时，前端传入是long数组， 数据库保存的是id拼接的字符串
    public String getRef(){
        //id1,id2,id3
        return StringUtils.join(refIds, ",");
    }
    //从数据库拿出的id拼接字符串转换成数组
    public Long[] getRefIds(){
        Long[] ids = null;
        if(StringUtils.isNotBlank(ref)){
            String[] strs = ref.split(",");
            ids = new Long[strs.length];
            for (int i = 0; i <strs.length ; i++) {
                ids[i] =  Long.parseLong(strs[i]);
            }
        }
        return ids;
    }
    public String getJsonString(){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        map.put("sn",sn);
        map.put("ref",ref);
        map.put("refIds",getRefIds());
        map.put("ishot",ishot);
        map.put("sequence",sequence);
        map.put("info",info);
        return JSON.toJSONString(map);
    }
}