package cn.wolfcode.luowowo.search.vo;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class StatisVO implements Serializable {
    private Long id;
    private String name;
    private Long count;

}


