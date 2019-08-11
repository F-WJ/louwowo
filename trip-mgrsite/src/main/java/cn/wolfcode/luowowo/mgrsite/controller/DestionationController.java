package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.article.query.DestinationQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IRegionService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/destination")
public class DestionationController {


    @Reference
    private IRegionService regionService;

    @Reference
    private IDestinationService destionationService;





    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") DestinationQuery qo){
        //共享数据
        model.addAttribute("pageInfo", destionationService.query(qo));

        //回显目录数据
        model.addAttribute("toasts", destionationService.getToasts(qo.getParentId()));
        return "destination/list";
    }







}
