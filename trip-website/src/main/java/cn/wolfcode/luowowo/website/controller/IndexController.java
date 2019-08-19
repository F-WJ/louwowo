package cn.wolfcode.luowowo.website.controller;


import cn.wolfcode.luowowo.article.domain.TravelCommend;
import cn.wolfcode.luowowo.article.query.TravelQuery;
import cn.wolfcode.luowowo.article.service.ITravelCommendService;
import cn.wolfcode.luowowo.article.service.ITravelService;
import cn.wolfcode.luowowo.cache.service.IUserInfoRedisService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import cn.wolfcode.luowowo.common.util.Consts;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import cn.wolfcode.luowowo.member.service.IUserInfoService;
import cn.wolfcode.luowowo.search.query.SearchQueryObject;
import cn.wolfcode.luowowo.search.service.*;
import cn.wolfcode.luowowo.search.template.DestinationTemplate;
import cn.wolfcode.luowowo.search.template.StrategyTemplate;
import cn.wolfcode.luowowo.search.template.TravelTemplate;
import cn.wolfcode.luowowo.search.template.UserInfoTemplate;
import cn.wolfcode.luowowo.search.vo.SearchResultVO;
import cn.wolfcode.luowowo.website.annotation.RequireLogin;
import cn.wolfcode.luowowo.website.annotation.UserParam;
import cn.wolfcode.luowowo.website.util.CookieUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.List;

@Controller
public class IndexController {


    @Reference
    private IUserInfoRedisService userInfoRedisService;

    @Reference
    private ITravelCommendService travelCommendService;

    @Reference
    private ITravelService travelService;

    @Reference
    private IDestinationSearchService destinationSearchService;

    @Reference
    private IStrategySearchService strategySearchService;

    @Reference
    private ITravelSearchService travelSearchService;

    @Reference
    private IUserInfoSearchService userInfoSearchService;

    @Reference
    private ISearchService searchService;





    //热门游记
    @RequestMapping("index/travelPage")
    public String travelPage(Model model, @ModelAttribute("qo") TravelQuery qo){

        model.addAttribute("pageInfo", travelService.query(qo));

        return "/index/travelPageTpl";
    }


    //站内搜索
    @RequestMapping("q")
    public String q(Model model, @ModelAttribute("qo")SearchQueryObject qo){
        //根据type判断搜索类型
        switch(qo.getType()){
            case SearchQueryObject.SEARCH_TYPE_DEST: return destSearch(model, qo);
            case SearchQueryObject.SEARCH_TYPE_STRATEGY: return strategySearch(model, qo);
            case SearchQueryObject.SEARCH_TYPE_TRAVEL: return travelSearch(model, qo);
            case SearchQueryObject.SEARCH_TYPE_USER: return userSearch(model, qo);
            default: return allSearch(model, qo);
        }

    }

    private String allSearch(Model model, SearchQueryObject qo) {
        Page<UserInfoTemplate> users = searchService.searchWithHighlight(UserInfoTemplate.INDEX_NAME, UserInfoTemplate.TYPE_NAME, UserInfoTemplate.class, qo, "nickname", "destName");


        Page<TravelTemplate> travels = searchService.searchWithHighlight(TravelTemplate.INDEX_NAME, TravelTemplate.TYPE_NAME, TravelTemplate.class, qo, "title", "summary");


        Page<StrategyTemplate> strategys = searchService.searchWithHighlight(StrategyTemplate.INDEX_NAME, StrategyTemplate.TYPE_NAME, StrategyTemplate.class, qo, "title", "subTitle", "summary");

        Page<DestinationTemplate> dests = searchService.searchWithHighlight(DestinationTemplate.INDEX_NAME, DestinationTemplate.TYPE_NAME, DestinationTemplate.class, qo, "name", "info");

        SearchResultVO data = new SearchResultVO();

        data.setUsers(users.getContent());
        data.setTravels(travels.getContent());
        data.setStrategys(strategys.getContent());
        data.setDests(dests.getContent());

        data.setTotal(dests.getTotalElements() + users.getTotalElements() + strategys.getTotalElements() + travels.getTotalElements());

        model.addAttribute("data", data);



        return "index/searchAll";
    }

    private String userSearch(Model model, SearchQueryObject qo) {

        Page<UserInfoTemplate> page = searchService.searchWithHighlight(UserInfoTemplate.INDEX_NAME, UserInfoTemplate.TYPE_NAME, UserInfoTemplate.class, qo, "nickname", "destName");
        model.addAttribute("page", page);

        return "index/searchUser";
    }

    private String travelSearch(Model model, SearchQueryObject qo) {

        Page<TravelTemplate> page = searchService.searchWithHighlight(TravelTemplate.INDEX_NAME, TravelTemplate.TYPE_NAME, TravelTemplate.class, qo, "title", "summary");
        model.addAttribute("page", page);

        return "index/searchTravel";
    }

    private String strategySearch(Model model, SearchQueryObject qo) {

        Page<StrategyTemplate> page = searchService.searchWithHighlight(StrategyTemplate.INDEX_NAME, StrategyTemplate.TYPE_NAME, StrategyTemplate.class, qo, "title", "subTitle", "summary");
        model.addAttribute("page", page);


        return "index/searchStrategy";
    }

    private String destSearch(Model model, SearchQueryObject qo) {
        //查询目的地对象
        DestinationTemplate dest = destinationSearchService.findByName(qo.getKeyword());

        if(dest != null){
            long total = 0;
            //查询目的地的所有攻略
            List<StrategyTemplate>  sts= strategySearchService.findByDestName(dest.getName());
            total += sts.size();
            if(sts.size() > 5){
                sts = sts.subList(0, 5);
            }
            //查询目的地的所有游记
            List<TravelTemplate>  ts= travelSearchService.findByDestName(dest.getName());
            total += ts.size();
            if(sts.size() > 5){
                sts = sts.subList(0, 5);
            }

            //查询目的地的所有用户
            List<UserInfoTemplate>  us= userInfoSearchService.findByDestName(dest.getName());
            total += us.size();
            if(sts.size() > 5){
                sts = sts.subList(0, 5);
            }

            SearchResultVO data = new SearchResultVO();
            data.setStrategys(sts);
            data.setTravels(ts);
            data.setUsers(us);
            data.setTotal(total);

            model.addAttribute("data", data);


        }





        model.addAttribute("dest", dest);

        return "index/searchDest";
    }


    //    @RequireLogin
    @RequestMapping("")
    public String index(Model model, HttpServletRequest request){
        String token = CookieUtil.getToken(request);
        UserInfo userInfo = userInfoRedisService.getUserInfoToken(token);
        if(userInfo != null){
            request.getSession().setAttribute("userInfo", userInfo);
        }

        //轮播图
        List<TravelCommend> tcs = travelCommendService.queryCommendTop5();
        model.addAttribute("tcs", tcs);

        //旅游攻略推荐
        TravelCommend sc = travelCommendService.queryCommendTop5().get(1);
        model.addAttribute("sc", sc);


        return "/index/index";
    }

    //对象注入
    @RequestMapping("/userInfo")
    @ResponseBody
    public Object userInfo(HttpServletRequest request, @UserParam UserInfo userInfo){
        return userInfo;
    }

    @RequestMapping("/userInfo1")
    @ResponseBody
    public Object userInfo1(HttpServletRequest request, UserInfo userInfo){
        return userInfo;
    }


}
