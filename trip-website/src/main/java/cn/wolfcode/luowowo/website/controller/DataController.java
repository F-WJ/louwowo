package cn.wolfcode.luowowo.website.controller;


import cn.wolfcode.luowowo.article.domain.Destination;
import cn.wolfcode.luowowo.article.domain.StrategyDetail;
import cn.wolfcode.luowowo.article.domain.Travel;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import cn.wolfcode.luowowo.article.service.IStrategyTagService;
import cn.wolfcode.luowowo.article.service.ITravelService;
import cn.wolfcode.luowowo.cache.vo.StrategyStatisVO;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import cn.wolfcode.luowowo.member.service.IUserInfoService;
import cn.wolfcode.luowowo.search.service.IDestinationSearchService;
import cn.wolfcode.luowowo.search.service.IStrategySearchService;
import cn.wolfcode.luowowo.search.service.ITravelSearchService;
import cn.wolfcode.luowowo.search.service.IUserInfoSearchService;
import cn.wolfcode.luowowo.search.template.DestinationTemplate;
import cn.wolfcode.luowowo.search.template.StrategyTemplate;
import cn.wolfcode.luowowo.search.template.TravelTemplate;
import cn.wolfcode.luowowo.search.template.UserInfoTemplate;
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

    @Reference
    private ITravelService travelService;

    @Reference
    private ITravelSearchService travelSearchService;

    @Reference
    private IUserInfoService userInfoService;

    @Reference
    private IDestinationSearchService destinationSearchService;


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

        ///游记----------------------------------------------------------------------------
        List<Travel> ts = travelService.list();

        for (Travel t : ts) {
            TravelTemplate tt = new TravelTemplate();
            tt.setId(t.getId());
            tt.setAuthorId(t.getAuthor().getId());
            tt.setAuthorName(t.getAuthor().getNickname());
            tt.setCoverUrl(t.getCoverUrl());
            tt.setCreateTime(t.getCreateTime());
            tt.setDestId(t.getDest().getId());
            tt.setDestName(t.getDest().getName());
            tt.setReplynum(t.getReplynum());
            tt.setSummary(t.getSummary());
            tt.setTitle(t.getTitle());
            tt.setViewnum(t.getViewnum());
            travelSearchService.saveOrUpdate(tt);
        }

        //用户----------------------------------------------------------------------------
        List<UserInfo> us = userInfoService.list();
        for (UserInfo u : us) {
            UserInfoTemplate tt = new UserInfoTemplate();
            tt.setId(u.getId());
            tt.setDestName(u.getCity());
            tt.setFansnum(11);
            tt.setHeadUrl(u.getHeadImgUrl());
            tt.setInfo(u.getInfo());
            tt.setLevel(u.getLevel());
            tt.setNickname(u.getNickname());
            tt.setReplynum(10);
            tt.setTravelnum(2);
            userInfoSearchService.saveOrUpdate(tt);
        }

        //目的地----------------------------------------------------------------------------
        //List<Destination> ds = destinationService.queryDestByDeep(3);
        List<Destination> ds = destinationService.list();

        for (Destination d : ds) {

            DestinationTemplate tt = new DestinationTemplate();

            tt.setId(d.getId());
            tt.setCoverUrl(d.getCoverUrl());
            tt.setName(d.getName());
            tt.setInfo(d.getInfo());
            destinationSearchService.saveOrUpdate(tt);
        }




        return "ok";
    }
}
