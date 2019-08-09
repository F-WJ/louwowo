package cn.wolfcode.luowowo.common.util;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class AjaxResult {

    private String msg;

    private boolean success = true;


    public AjaxResult(){
    }

    public AjaxResult(String msg){
        this.success = false;
        this.msg = msg;
    }



}
