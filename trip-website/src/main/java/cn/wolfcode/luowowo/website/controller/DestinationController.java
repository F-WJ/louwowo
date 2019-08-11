package cn.wolfcode.luowowo.website.controller;

import cn.wolfcode.luowowo.article.domain.Destination;
import cn.wolfcode.luowowo.article.domain.Region;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IRegionService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.xml.ws.soap.Addressing;
import java.util.List;

@Controller
@RequestMapping("/destination")
public class DestinationController {


    @Reference
    private IDestinationService destinationService;

    @Reference
    private IRegionService regionService;



    //首页
    @RequestMapping("")
    public String index(Model model){

        List<Region> hotRegions = regionService.queryHotRegion();
        model.addAttribute("hotRegions", hotRegions);
        return "/destination/index";
    }



    @RequestMapping("/getHotDestByRegionId")
    public  String getHotDestByRegionId(Model model , Long regionId){



        model.addAttribute("regionId", regionId);
        List<Destination> list = destinationService.queryDestByRegionId(regionId);
        int size = list.size();
        //左边数据
        model.addAttribute("leftDests",list.subList(0, size / 2 + 1));
        //右边数据
        model.addAttribute("rightDests", list.subList(size / 2 + 1, size));

        return "destination/hotdestTpl";
    }

    @RequestMapping("/guide")
    public String guide(Model model, Long id){

        List<Destination> toasts = destinationService.getToasts(id);

        model.addAttribute("toasts", toasts);
        //删除最后一个
        Destination destination = toasts.remove(toasts.size() - 1);
        model.addAttribute("dest", destination);
        return "destination/guide";
    }

//    @RequestMapping("getHotDestByRegionId")
//    public







}
