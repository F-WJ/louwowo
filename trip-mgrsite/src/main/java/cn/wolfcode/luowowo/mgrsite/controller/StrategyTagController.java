package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.article.domain.StrategyTag;
import cn.wolfcode.luowowo.article.query.StrategyTagQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyTagService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("strategyTag")
public class StrategyTagController {

    @Reference
    private IStrategyTagService strategyTagService;

    @Reference
    private IDestinationService destinationService;




    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo")StrategyTagQuery qo){

        //pageInfo

        model.addAttribute("pageInfo", strategyTagService.query(qo));

        return "strategyTag/list";
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StrategyTag  strategyTag){

        strategyTagService.saveOrUpdate(strategyTag);

        return new AjaxResult();
    }

}
