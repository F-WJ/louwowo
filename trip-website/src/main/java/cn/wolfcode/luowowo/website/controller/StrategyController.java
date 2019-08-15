package cn.wolfcode.luowowo.website.controller;


import cn.wolfcode.luowowo.article.domain.Destination;
import cn.wolfcode.luowowo.article.domain.StrategyContent;
import cn.wolfcode.luowowo.article.domain.StrategyDetail;
import cn.wolfcode.luowowo.article.domain.StrategyTag;
import cn.wolfcode.luowowo.article.query.StrategyQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import cn.wolfcode.luowowo.article.service.IStrategyTagService;
import cn.wolfcode.luowowo.cache.service.IStrategyDetailRedisService;
import cn.wolfcode.luowowo.cache.vo.StrategyStatisVO;
import cn.wolfcode.luowowo.comment.domain.StrategyComment;
import cn.wolfcode.luowowo.comment.query.StrategyCommentQuery;
import cn.wolfcode.luowowo.comment.service.IStrategyCommentService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import cn.wolfcode.luowowo.website.annotation.UserParam;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.soap.SOAPBinding;
import java.util.Date;
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

    @Reference
    private IStrategyCommentService strategyCommentService;

    @Reference
    private IStrategyDetailRedisService strategyDetailRedisService;


    //顶数（点赞）
    @RequestMapping("/strategyThumbup")
    @ResponseBody
    public AjaxResult strategyThumbup(Long sid, @UserParam UserInfo userInfo){
        AjaxResult ajaxResult = strategyDetailRedisService.saveThumbsupnum(sid, userInfo.getId());

        return ajaxResult;
    }




    //收藏数
    @RequestMapping("/favor")
    @ResponseBody
    public AjaxResult favor(Long sid, @UserParam UserInfo userInfo){

        AjaxResult ajaxResult = strategyDetailRedisService.saveFavornum(sid, userInfo.getId());

        return ajaxResult;
    }


    //评论
    @RequestMapping("/commentAdd")
    @ResponseBody
    public AjaxResult commentAdd(@ModelAttribute("vo") StrategyStatisVO vo,StrategyComment strategyComment, @UserParam UserInfo userInfo){

        //设置部分值
        //用户
        strategyComment.setUserId(userInfo.getId());
        strategyComment.setUsername(userInfo.getNickname());
        strategyComment.setHeadUrl(userInfo.getHeadImgUrl());
        strategyComment.setCity(userInfo.getCity());
        strategyComment.setLevel(userInfo.getLevel());
        //日期
        strategyComment.setCreateTime(new Date());
        //保存
        strategyCommentService.saveOrUpdate(strategyComment);
        //修改评论数
        AjaxResult result = new AjaxResult();
//        strategyDetailService.updateCommentNumById(strategyComment.getDetailId());
//        StrategyDetail strategyDetail = strategyDetailService.get(strategyComment.getDetailId());

        //使用redis保存评论数
        vo = strategyDetailRedisService.saveReplynum(strategyComment.getDetailId());
        result.setData(vo.getReplynum());


        return result;

    }

    //回显攻略评论
    @RequestMapping("/comment")
    public String comment(Model model, @ModelAttribute("qo") StrategyCommentQuery qo, @UserParam UserInfo userInfo){
        //根据攻略文章iD查询评论数据
        Page page = strategyCommentService.query(qo);
        model.addAttribute("page", page);
        model.addAttribute("userInfo", userInfo);
        return "strategy/commentTpl";
    }


    //评论点赞数
    @RequestMapping("/commentThumbUp")
    @ResponseBody
    public AjaxResult commentThumbUp(String toid, Long fromid){
        strategyCommentService.saveThumbupnum(toid, fromid);
        return new AjaxResult();
    }


    @RequestMapping("detail")
    public String detail(Model model, Long id, @UserParam UserInfo userInfo){
        //回显文章内容detail
        StrategyDetail detail = strategyDetailService.get(id);
        StrategyContent content = strategyDetailService.getContent(id);
        detail.setStrategyContent(content);
        model.addAttribute("detail", detail);
        model.addAttribute("userInfo", userInfo);



        //通过redis添加阅读数数
        StrategyStatisVO vo = strategyDetailRedisService.setViewNum(id);
        //回显阅读数数据
        model.addAttribute("vo", vo);

        //判断用户是否已经收藏
        if(userInfo != null) {
            boolean isFavor = strategyDetailRedisService.isFavorBy(id, userInfo.getId());
            model.addAttribute("isFavor", isFavor);
        }

        return "/strategy/detail";
    }

    @RequestMapping("")
    public String index(Model model){

        return "/strategy/index";
    }

    @RequestMapping("list")
    public String list(Model model, Long destId, @ModelAttribute("qo")StrategyQuery qo){

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
