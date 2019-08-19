package cn.wolfcode.luowowo.search.query;

import cn.wolfcode.luowowo.common.query.QueryObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


@Getter
@Setter
public class SearchQueryObject extends QueryObject {
    public static final int CONDITION_TYPE_ABROAD = 0;  //国外
    public static final int CONDITION_TYPE_UN_ABROAD = 1; //国内
    public static final int CONDITION_TYPE_THEME= 2; //主题


    public static final int SEARCH_TYPE_ALL= -1; //全部
    public static final int SEARCH_TYPE_DEST= 0; //目的地
    public static final int SEARCH_TYPE_STRATEGY=  1; //攻略
    public static final int SEARCH_TYPE_TRAVEL= 2; //游记
    public static final int SEARCH_TYPE_USER=  3; //用户







    private int type = -1; //攻略条件判断

    private Long typeValue = -1L; //目的地

    private String orderBy = "viewnum"; //排序要求


    public Pageable getPageable() {
        PageRequest of = PageRequest.of(super.getCurrentPage() - 1, super.getPageSize(), Sort.by(Sort.Direction.DESC, orderBy));

        return of;

    }

    public Pageable getPageableWithoutSort() {
        PageRequest of = PageRequest.of(super.getCurrentPage() - 1, super.getPageSize());


        return of;
    }
}
