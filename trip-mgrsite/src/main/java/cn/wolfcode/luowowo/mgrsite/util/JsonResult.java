package cn.wolfcode.luowowo.mgrsite.util;

import cn.wolfcode.luowowo.article.domain.TravelContent;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JsonResult {

    
    private String content;
    private String msg;

    private boolean success = true;




    public JsonResult(){
    }

    public JsonResult(String msg){
        this.success = false;
        this.msg = msg;
    }



}