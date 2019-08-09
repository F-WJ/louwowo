package cn.wolfcode.luowowo.common.query;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class QueryObject  implements Serializable{
    private int currentPage=1; //当前页
    private int pageSize=10;   //每页显示记录数


    private String keyword;

    public String getKeyword(){
        if(keyword == null || "".equals(keyword.trim())){
            return null;
        }
        return  keyword;
    }
}
