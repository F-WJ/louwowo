package cn.wolfcode.luowowo.website.controller;


import cn.wolfcode.luowowo.article.domain.*;
import cn.wolfcode.luowowo.article.query.TravelQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import cn.wolfcode.luowowo.article.service.ITravelService;
import cn.wolfcode.luowowo.cache.service.ITravelDetailRedisService;
import cn.wolfcode.luowowo.cache.vo.TravelStatisVO;
import cn.wolfcode.luowowo.comment.domain.TravelComment;
import cn.wolfcode.luowowo.comment.query.TravelCommentQuery;
import cn.wolfcode.luowowo.comment.service.ITravelCommentService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import cn.wolfcode.luowowo.website.annotation.UserParam;
import cn.wolfcode.luowowo.website.util.UMEditorUploader;
import cn.wolfcode.luowowo.website.util.UploadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.codec.json.AbstractJackson2Decoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/travel")
public class TravelController {


    @Reference
    private ITravelService travelService;

    @Reference
    private IDestinationService destinationService;

    @Reference
    private IStrategyDetailService strategyDetailService;

    @Reference
    private ITravelCommentService travelCommentService;

    @Reference
    private ITravelDetailRedisService travelDetailRedisService;








    //顶数（点赞）
    @RequestMapping("/strategyThumbup")
    @ResponseBody
    public AjaxResult strategyThumbup(Long tid, @UserParam UserInfo userInfo){
        AjaxResult ajaxResult = travelDetailRedisService.saveThumbsupnum(tid, userInfo.getId());

        return ajaxResult;
    }


    //收藏数
    @RequestMapping("/favor")
    @ResponseBody
    public AjaxResult favor(Long tid, @UserParam UserInfo userInfo){

        AjaxResult ajaxResult = travelDetailRedisService.saveFavornum(tid, userInfo.getId());

        return ajaxResult;
    }


    //评论
    @RequestMapping("/commentAdd")
    public String commentAdd(Long floor, Model model, TravelComment travelComment, @UserParam UserInfo userInfo){

        //设置部分值
        travelComment.setCity(userInfo.getCity());
        travelComment.setLevel(userInfo.getLevel());
        travelComment.setCreateTime(new Date());
        travelComment.setHeadUrl(userInfo.getHeadImgUrl());
        //保存
        travelCommentService.saveOrUpdate(travelComment);
        model.addAttribute("c", travelComment);
        model.addAttribute("floor", floor + 1);

        //使用redis保存评论数
        TravelStatisVO vo = travelDetailRedisService.saveReplynum(travelComment.getTravelId());
        model.addAttribute("vo", vo);

        return "travel/commentTpl";

    }









    @RequestMapping("")
    public String list(Model model, @ModelAttribute("qo") TravelQuery qo, @UserParam UserInfo userInfo){
        //展示已发布的游记
        PageInfo<Travel> pageInfo = travelService.selectTravelByState(qo, Travel.STATE_RELEASE);
        model.addAttribute("pageInfo", pageInfo);

        //回显最新的十条游记评论
        TravelCommentQuery travelCommentQuery = new TravelCommentQuery();
        travelCommentQuery.setCurrentPage(1);
        travelCommentQuery.setPageSize(Integer.MAX_VALUE);
        Page page = travelCommentService.query(travelCommentQuery);
        int size = page.getContent().size();
        List content = page.getContent();
        if(page.getContent().size() >= 10){
            content = content.subList(0, 10);
        }

        model.addAttribute("tcs", content);



        return "travel/list";
    }

    @RequestMapping("/input")
    public String input(Model model, Long id){
        //景点dests
        List<Destination> destinations = destinationService.queryDestByDeep(3);
        model.addAttribute("dests", destinations);
        if(id != null){
            //回显文章

            Travel travel = travelService.get(id);
            TravelContent content = travelService.getContent(id);
            travel.setTravelContent(content);
            model.addAttribute("tv", travel);


        }
        return "travel/input";
    }




    @RequestMapping("detail")
    public String detail(Model model, Long id, @UserParam UserInfo userInfo){
        //回显文章内容detail
        Travel detail = travelService.get(id);
        TravelContent content = travelService.getContent(id);
        detail.setTravelContent(content);
        model.addAttribute("detail", detail);
        //toasts
        List<Destination> toasts = destinationService.getToasts(detail.getDest().getId());
        //导航栏（吐司）
        model.addAttribute("toasts", toasts);

        //前三攻略信息
        List<StrategyDetail> detailTop3 = strategyDetailService.getDetailTop3(detail.getDest().getId());
        model.addAttribute("sds", detailTop3);


        //前三关于此景点游记
        List<Travel> ts = travelService.getDetailTop3(detail.getDest().getId());
        model.addAttribute("ts", ts);

        //回显当前用户
        model.addAttribute("userInfo", userInfo);

        //回显评论
        TravelCommentQuery travelCommentQuery = new TravelCommentQuery();
        travelCommentQuery.setTravelId(id);
        travelCommentQuery.setCurrentPage(1);
        travelCommentQuery.setPageSize(Integer.MAX_VALUE);
        Page page = travelCommentService.query(travelCommentQuery);
        model.addAttribute("list", page.getContent());


        //通过redis添加阅读数数
        TravelStatisVO vo = travelDetailRedisService.setViewNum(id);
        //回显阅读数数据
        model.addAttribute("vo", vo);

        //判断用户是否已经收藏
        if(userInfo != null) {
            boolean isFavor = travelDetailRedisService.isFavorBy(id, userInfo.getId());
            model.addAttribute("isFavor", isFavor);
        }

        return "/travel/detail";


    }


    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public AjaxResult saveOrUpdate(Travel travel){
        Long travelId = travelService.saveOrUpdate(travel);
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setData(travelId);
        return ajaxResult;

    }


    @Value("${file.path}")
    private String filePath;

    //上传图片
    @RequestMapping("/coverImageUpload")
    @ResponseBody
    public Object coverImageUpload(MultipartFile pic){
        String upload = UploadUtil.upload(pic, filePath);
        return upload;
    }




    //上传图片
    @RequestMapping("/contentImage")
    @ResponseBody
    public String uploadUEImage(MultipartFile upfile,HttpServletRequest request) throws Exception{
        UMEditorUploader up = new UMEditorUploader(request);
        String[] fileType = {".gif" , ".png" , ".jpg" , ".jpeg" , ".bmp"};
        up.setAllowFiles(fileType);
        up.setMaxSize(10000); //单位KB
        up.upload(upfile, filePath);

        String callback = request.getParameter("callback");
        String result = "{\"name\":\""+ up.getFileName() +"\", \"originalName\": \""+ up.getOriginalName() +"\", \"size\": "+ up.getSize()
                +", \"state\": \""+ up.getState() +"\", \"type\": \""+ up.getType() +"\", \"url\": \""+ up.getUrl() +"\"}";
        result = result.replaceAll( "\\\\", "\\\\" );
        if(callback == null ){
            return result ;
        }else{
            return "<script>"+ callback +"(" + result + ")</script>";
        }
    }


}
