package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.article.domain.StrategyCatalog;
import cn.wolfcode.luowowo.article.query.StrategyCatalogQuery;
import cn.wolfcode.luowowo.article.service.IStrategyCatalogService;
import cn.wolfcode.luowowo.article.service.IStrategyService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("strategyCatalog")
public class StrategyCatalogController {

    @Reference
    private IStrategyCatalogService strategyCatalogService;

    @Reference
    private IStrategyService strategyService;


    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo")StrategyCatalogQuery qo){

        //pageInfo

        model.addAttribute("pageInfo", strategyCatalogService.query(qo));

        //strategies
        model.addAttribute("strategies", strategyService.list());


        return "strategyCatalog/list";
    }




    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StrategyCatalog  strategyCatalog){

        strategyCatalogService.saveOrUpdate(strategyCatalog);

        return new AjaxResult();
    }

}
