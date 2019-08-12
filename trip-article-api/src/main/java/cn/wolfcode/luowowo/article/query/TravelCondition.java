package cn.wolfcode.luowowo.article.query;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * 游记条件
 */
@Setter
@Getter
public class TravelCondition {

    private int min;
    private int max;


    public TravelCondition(int min, int max){
        this.min = min;
        this.max = max;
    }

    public static final Map TRAVEL_TIME; //旅游出发时间月份

    public static final Map TRAVEL_DAYS; //旅游天数

    public static final Map TRAVEL_PRE_EXPENDS; //旅游人均消费

    static{
        //旅游出发月份
        TRAVEL_TIME = new HashMap<>();
        TRAVEL_TIME.put(-1, new TravelCondition(1, 12));
        TRAVEL_TIME.put(1, new TravelCondition(1, 2));
        TRAVEL_TIME.put(2, new TravelCondition(3, 4));
        TRAVEL_TIME.put(3, new TravelCondition(5, 6));
        TRAVEL_TIME.put(4, new TravelCondition(7, 8));
        TRAVEL_TIME.put(5, new TravelCondition(9, 10));
        TRAVEL_TIME.put(6, new TravelCondition(11, 12));

        //旅游天数
        TRAVEL_DAYS = new HashMap();
        TRAVEL_DAYS.put(-1, new TravelCondition(-1, Integer.MAX_VALUE));
        TRAVEL_DAYS.put(1, new TravelCondition(0, 3));
        TRAVEL_DAYS.put(2, new TravelCondition(4, 7));
        TRAVEL_DAYS.put(3, new TravelCondition(8, 14));
        TRAVEL_DAYS.put(4, new TravelCondition(15,Integer.MAX_VALUE));

        TRAVEL_PRE_EXPENDS = new HashMap();
        TRAVEL_PRE_EXPENDS.put(-1, new TravelCondition(-1, Integer.MAX_VALUE));
        TRAVEL_PRE_EXPENDS.put(1, new TravelCondition(1, 999));
        TRAVEL_PRE_EXPENDS.put(2, new TravelCondition(1000, 6000));
        TRAVEL_PRE_EXPENDS.put(3, new TravelCondition(6001, 200000));
        TRAVEL_PRE_EXPENDS.put(4, new TravelCondition(200001,Integer.MAX_VALUE));
    }








}
