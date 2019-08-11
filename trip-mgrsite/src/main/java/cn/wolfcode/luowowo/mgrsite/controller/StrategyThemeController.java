package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.article.domain.StrategyTheme;
import cn.wolfcode.luowowo.article.query.StrategyThemeQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyThemeService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("strategyTheme")
public class StrategyThemeController {

    @Reference
    private IStrategyThemeService strategyThemeService;

    @Reference
    private IDestinationService destinationService;




    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo")StrategyThemeQuery qo){

        //pageInfo

        model.addAttribute("pageInfo", strategyThemeService.query(qo));

        return "strategyTheme/list";
    }


    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StrategyTheme  strategyTheme){

        strategyThemeService.saveOrUpdate(strategyTheme);

        return new AjaxResult();
    }

}
