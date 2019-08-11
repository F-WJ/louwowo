package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.article.domain.Destination;
import cn.wolfcode.luowowo.article.domain.Region;
import cn.wolfcode.luowowo.article.query.RegionQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IRegionService;
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

    @Reference
    private IDestinationService destionationService;



    @RequestMapping("/list")
    public String list(Model model, @ModelAttribute("qo") RegionQuery qo){
        //共享数据
        model.addAttribute("pageInfo", regionService.query(qo));



        return "region/list";
    }

    @RequestMapping("/getDestByDeep")
    @ResponseBody
    public List<Destination> getDestByDeep(Integer deep){
        List<Destination> list =  destionationService.queryDestByDeep(deep);
        return list;
    }

    @RequestMapping("/saveOrUpdate")
    public void saveOrUpdate(Region region){
        regionService.saveOrUpdate(region);
    }

    @RequestMapping("/getDestByRegionId")
    @ResponseBody
    public List<Destination> getDestByRegionId(Long rid){
        List<Destination> destinations = destionationService.queryDestByRegionId(rid);
        return destinations;
    }


}
