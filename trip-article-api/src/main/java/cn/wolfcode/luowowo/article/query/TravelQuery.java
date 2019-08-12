package cn.wolfcode.luowowo.article.query;

import cn.wolfcode.luowowo.common.query.QueryObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 游记查询条件封装对象
 */
@Setter
@Getter
public class TravelQuery extends QueryObject {
    private Long destId = -1L;
    private  int state = -1; //游记状态
    private int orderType = -1; //排序类型
    private int travelTimeType = -1;  //出发时间(月份)
    private int dayType = -1;   //旅游天数类型
    private int perExpendType = -1;  //人均消费类型

    //排序
    public String getOrderBy(){
        if(orderType == 1){
            return " t.createTime desc "; //最新
        }else if(orderType == 2){
            return " t.viewnum desc";  //最热
        }
        return "t.createTime desc";
    }




}
