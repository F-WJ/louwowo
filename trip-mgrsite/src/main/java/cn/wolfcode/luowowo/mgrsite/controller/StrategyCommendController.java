package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.article.domain.StrategyCommend;
import cn.wolfcode.luowowo.article.query.StrategyCommendQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyCommendService;
import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("strategyCommend")
public class StrategyCommendController {

    @Reference
    private IStrategyCommendService strategyCommendService;

    @Reference
    private IDestinationService destinationService;

    @Reference
    private IStrategyDetailService strategyDetailService;




    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo")StrategyCommendQuery qo){

        //pageInfo

        model.addAttribute("pageInfo", strategyCommendService.query(qo));

        model.addAttribute("details", strategyDetailService.list());

        return "strategyCommend/list";
    }


    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StrategyCommend strategyCommend){

        strategyCommendService.saveOrUpdate(strategyCommend);

        return new AjaxResult();
    }

}
