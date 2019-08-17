package cn.wolfcode.luowowo.website.controller;


import cn.wolfcode.luowowo.article.domain.Destination;
import cn.wolfcode.luowowo.article.domain.StrategyDetail;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import cn.wolfcode.luowowo.article.service.IStrategyTagService;
import cn.wolfcode.luowowo.search.service.IStrategySearchService;
import cn.wolfcode.luowowo.search.service.IUserInfoSearchService;
import cn.wolfcode.luowowo.search.template.StrategyTemplate;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
public class DataController {

    @Reference
    private IStrategyDetailService strategyDetailService;

    @Reference
    private IDestinationService destinationService;

    @Reference
    private IStrategySearchService strategySearchService;

    @Reference
    private IUserInfoSearchService userInfoSearchService;

    @Reference
    private IStrategyTagService strategyTagService;

    @RequestMapping("/dataInit")
    @ResponseBody
    public Object add(Model model) {

        ///攻略----------------------------------------------------------------------------
        List<StrategyDetail> all = strategyDetailService.list();
        for (StrategyDetail detail : all) {
            StrategyTemplate t = new StrategyTemplate();

            t.setId(detail.getId());
            t.setTitle(detail.getTitle());
            t.setSubTitle(detail.getSubTitle());
            t.setDestId(detail.getDest().getId());
            t.setDestName(detail.getDest().getName());
            t.setThemeId(detail.getTheme().getId());
            t.setThemeName(detail.getTheme().getName());
            t.setSummary(detail.getSummary());
            t.setCreateTime(detail.getCreateTime());
            t.setViewnum(detail.getViewnum());
            t.setFavornum(detail.getFavornum());
            t.setReplynum(detail.getReplynum());
            t.setThumbsupnum(detail.getThumbsupnum());

            t.setCoverUrl(detail.getCoverUrl());

            String tags = strategyTagService.getTags(detail.getId());
            List<String> list = Arrays.asList(tags.split(","));
            t.setTags(list);

            Destination dest = destinationService.getCountry(detail.getDest().getId());

            t.setCountryId(dest.getId());
            t.setCountryName(dest.getName());

            dest = destinationService.getProvince(detail.getDest().getId());
            if(dest != null){
                t.setProvinceId(dest.getId());
                t.setProvinceName(dest.getName());
            }

            strategySearchService.saveOrUpdate(t);
        }



        return "ok";
    }
}
