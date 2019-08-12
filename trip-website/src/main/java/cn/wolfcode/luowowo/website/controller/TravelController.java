package cn.wolfcode.luowowo.website.controller;


import cn.wolfcode.luowowo.article.domain.*;
import cn.wolfcode.luowowo.article.query.TravelQuery;
import cn.wolfcode.luowowo.article.service.IDestinationService;
import cn.wolfcode.luowowo.article.service.IStrategyDetailService;
import cn.wolfcode.luowowo.article.service.ITravelService;
import cn.wolfcode.luowowo.common.util.AjaxResult;
import cn.wolfcode.luowowo.member.domain.UserInfo;
import cn.wolfcode.luowowo.website.annotation.UserParam;
import cn.wolfcode.luowowo.website.util.UMEditorUploader;
import cn.wolfcode.luowowo.website.util.UploadUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.json.AbstractJackson2Decoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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


    @RequestMapping("")
    public String list(Model model, @ModelAttribute("qo") TravelQuery qo, @UserParam UserInfo userInfo){
        //展示已发布的游记
        PageInfo<Travel> pageInfo = travelService.selectTravelByState(qo, Travel.STATE_RELEASE);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("userInfo", userInfo);
        ''
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
    public String detail(Model model, Long id){
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
