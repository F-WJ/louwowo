package cn.wolfcode.luowowo.website.controller;


import cn.wolfcode.luowowo.article.domain.Destination;
import cn.wolfcode.luowowo.article.domain.StrategyContent;
import cn.wolfcode.luowowo.article.domain.StrategyDetail;
import cn.wolfcode.luowowo.article.domain.StrategyTag;
import cn.wolfcode.luowowo.article.query.StrategyQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import cn.wolfcode.luowowo.article.service.IStrategyService;
import cn.wolfcode.luowowo.article.service.IStrategyTagService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.IdentityHashMap;
import java.util.List;

@Controller
@RequestMapping("/strategy")
public class StrategyController {

    @Reference
    private IStrategyDetailService strategyDetailService;

    @Reference
    private IDestinationService destinationService;

    @Reference
    private IStrategyTagService strategyTagService;


    @RequestMapping("detail")
    public String detail(Model model, Long id){
        //回显文章内容detail
        StrategyDetail detail = strategyDetailService.get(id);
        StrategyContent content = strategyDetailService.getContent(id);
        detail.setStrategyContent(content);
        model.addAttribute("detail", detail);
        //
        return "/strategy/detail";
    }

    @RequestMapping("")
    public String index(Model model){
        //回显文章内容detail
//        model.addAttribute("detail", detail);
        //
        return "/strategy/index";
    }

    @RequestMapping("list")
    public String list(Model model, Long destId, @ModelAttribute("qo")StrategyQuery qo){
        //回显文章内容detail
//        model.addAttribute("detail", detail);

        //toasts
        List<Destination> toasts = destinationService.getToasts(destId);
        //导航栏（吐司）
        model.addAttribute("toasts", toasts);
        Destination destination = toasts.remove(toasts.size() - 1);
        model.addAttribute("dest", destination);
        //tags
        List<StrategyTag> tags = strategyTagService.list();
        model.addAttribute("tags", tags);

        //pageInfo
        PageInfo pageInfo = strategyDetailService.getDetailByTag(qo);
        model.addAttribute("pageInfo", pageInfo);


        return "/strategy/list";
    }
}
