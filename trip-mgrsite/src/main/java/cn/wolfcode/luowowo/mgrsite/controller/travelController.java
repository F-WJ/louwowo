package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.article.domain.StrategyContent;
import cn.wolfcode.luowowo.article.domain.StrategyDetail;
import cn.wolfcode.luowowo.article.domain.TravelContent;
import cn.wolfcode.luowowo.article.query.StrategyDetailQuery;
import cn.wolfcode.luowowo.article.query.TravelQuery;
import cn.wolfcode.luowowo.article.service.*;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import cn.wolfcode.luowowo.mgrsite.util.JsonResult;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("travel")
public class travelController {

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

    @Reference
    private ITravelService travelService;


    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") TravelQuery qo){

        //pageInfo
        PageInfo pageInfo = travelService.query(qo);
        model.addAttribute("pageInfo", pageInfo);

        return "travel/list";
    }



    @RequestMapping("/changeState")
    @ResponseBody
    public Object changeState(Long id, int state){

        travelService.changeState(id, state);

        return new AjaxResult();
    }

    @RequestMapping("/getContentById")
    @ResponseBody
    public JsonResult getContentById(Long id){

        TravelContent content = travelService.getContent(id);
        JsonResult result = new JsonResult();
        result.setContent(content.getContent());
        return result;
    }



}
