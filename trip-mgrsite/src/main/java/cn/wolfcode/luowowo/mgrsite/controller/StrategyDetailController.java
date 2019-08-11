package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.article.domain.StrategyContent;
import cn.wolfcode.luowowo.article.domain.StrategyDetail;
import cn.wolfcode.luowowo.article.query.StrategyDetailQuery;
import cn.wolfcode.luowowo.article.service.*;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("strategyDetail")
public class StrategyDetailController {

    @Reference
    private IStrategyDetailService strategyDetailService;

    @Reference
    private IDestinationService destinationService;

    @Reference
    private IStrategyService strategyService;

    @Reference
    private IStrategyThemeService strategyThemeService;

    @Reference
    private IStrategyCatalogService strategyCatalogService;

    @Reference
    private  IStrategyTagService strategyTagService;


    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo")StrategyDetailQuery qo){

        //pageInfo

        model.addAttribute("pageInfo", strategyDetailService.query(qo));

        return "strategyDetail/list";
    }


    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StrategyDetail  strategyDetail, String tags){

        strategyDetailService.saveOrUpdate(strategyDetail, tags);

        return new AjaxResult();
    }

    @RequestMapping("/changeState")
    @ResponseBody
    public Object changeState(Long id, int state){

        strategyDetailService.changeState(id, state);

        return new AjaxResult();
    }

    @RequestMapping("/input")
    public String input(Model model, Long id){
        //回显
        //strategyDetail
        if(id != null){
            StrategyDetail detail = strategyDetailService.get(id);
            StrategyContent content = strategyDetailService.getContent(id);
            detail.setStrategyContent(content);
            model.addAttribute("strategyDetail", detail);


            //标签回显: tag1,tag2,tag3
            model.addAttribute("tags", strategyTagService.getTags(id));
        }
        //strategies
        model.addAttribute("strategies", strategyService.list());

        //themes
        model.addAttribute("themes", strategyThemeService.list());

        return "strategyDetail/input";
    }


    @RequestMapping("/getCatalogByStrategyId")
    @ResponseBody
    public Object getCatalogByStrategyId(Long strategyId){
       return strategyCatalogService.queryCatalogByStrategyId(strategyId);
    }

}
