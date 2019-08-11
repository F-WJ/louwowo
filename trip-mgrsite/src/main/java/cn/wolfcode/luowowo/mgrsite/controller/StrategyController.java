package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.article.domain.Strategy;
import cn.wolfcode.luowowo.article.query.StrategyQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("strategy")
public class StrategyController {

    @Reference
    private IStrategyService strategyService;

    @Reference
    private IDestinationService destinationService;




    //查询大攻略数据
    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo")StrategyQuery qo){
        model.addAttribute("pageInfo", strategyService.query(qo));

        return "strategy/list";
    }


    //根据等级查询景点数据
    @RequestMapping("/getDestByDeep")
    @ResponseBody
    public Object getDestByDeep(int deep){
        return destinationService.queryDestByDeep(deep);
    }


    //添加
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Strategy  strategy){

        strategyService.saveOrUpdate(strategy);

        return new AjaxResult();
    }

}
