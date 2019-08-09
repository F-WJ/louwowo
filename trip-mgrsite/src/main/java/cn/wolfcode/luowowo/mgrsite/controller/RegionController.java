package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.article.domain.Region;
import cn.wolfcode.luowowo.article.query.RegionQuery;
import cn.wolfcode.luowowo.article.service.IRegionService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/region")
public class RegionController {


    @Reference
    private IRegionService regionService;



    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") RegionQuery qo){
        //pageInfo
        model.addAttribute("pageInfo", regionService.query(qo));
        return "region/list";
    }


}
