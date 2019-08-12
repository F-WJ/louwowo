package cn.wolfcode.luowowo.website.controller;

import cn.wolfcode.luowowo.article.domain.*;
import cn.wolfcode.luowowo.article.query.DestinationQuery;
import cn.wolfcode.luowowo.article.query.TravelQuery;
import cn.wolfcode.luowowo.article.service.*;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @Reference
    private IStrategyCatalogService strategyCatalogService;

    @Reference
    private IStrategyDetailService strategyDetailService;

    @Reference
    private ITravelService travelService;



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

        //导航栏（吐司）
        model.addAttribute("toasts", toasts);
        //删除最后一个
        Destination destination = toasts.remove(toasts.size() - 1);
        model.addAttribute("dest", destination);

        //攻略目录和攻略文章标题
        //catalogs
        List<StrategyCatalog> strategyCatalogs = strategyCatalogService.queryCatalogByDestId(id);
        model.addAttribute("catalogs", strategyCatalogs);


        //strategyDetails（关联攻略，点击量最高的三篇）
        List<StrategyDetail> detailTop3 = strategyDetailService.getDetailTop3(id);
        model.addAttribute("strategyDetails", detailTop3);

        return "destination/guide";
    }

    @RequestMapping("/surveyPage")
    public String survey(Model model,@ModelAttribute("qo") DestinationQuery qo){

        List<Destination> toasts = destinationService.getToasts(qo.getDestId());
        //导航栏（吐司）
        model.addAttribute("toasts", toasts);
        //删除最后一个
        Destination destination = toasts.remove(toasts.size() - 1);
        model.addAttribute("dest", destination);



        return "destination/survey";
    }

    @RequestMapping("/survey")
    public String surveyTpl(Model model, @ModelAttribute("qo") DestinationQuery qo){
        //catalogs
        List<StrategyCatalog> catalogs = strategyCatalogService.queryCatalogByDestId(qo.getDestId());
        model.addAttribute("catalogs", catalogs);

        //catalog
        StrategyCatalog catalog = strategyCatalogService.get(qo.getCatalogId());
        model.addAttribute("catalog", catalog);

        //detail
        StrategyDetail detail = strategyDetailService.getByCatalogId(qo.getCatalogId());
        if(detail != null) {
            StrategyContent content = strategyDetailService.getContent(detail.getId());
            detail.setStrategyContent(content);
            model.addAttribute("detail", detail);
        }


        return "destination/surveyTpl";
    }


    @RequestMapping("/travels")
    public String travels(Model model,TravelQuery qo){

        //某个城市已发布游记文章
        PageInfo<Travel> travels = travelService.selectTravelByState(qo, Travel.STATE_RELEASE);
        model.addAttribute("pageInfo", travels);
        return "destination/travelTpl";
    }

//    @RequestMapping("getHotDestByRegionId")
//    public







}
