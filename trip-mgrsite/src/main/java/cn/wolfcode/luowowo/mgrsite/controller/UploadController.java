package cn.wolfcode.luowowo.mgrsite.controller;

import cn.wolfcode.luowowo.mgrsite.util.UploadUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController {
    @Value("${file.path}")
    private String filePath;


    @RequestMapping("/uploadImg")
    @ResponseBody
    public String delete(MultipartFile pic){

        //filePath

        return UploadUtil.upload(pic, filePath);
    }

    @RequestMapping("/uploadImg_ck")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile upload, String module){
        Map<String, Object> map = new HashMap<String, Object>();
        String imagePath= null;
        if(upload != null && upload.getSize() > 0){
            try {
                //图片保存, 返回路径
                imagePath =  UploadUtil.upload(upload,filePath );
                //表示保存成功
                map.put("uploaded", 1);
                map.put("url", "/" + imagePath);

            }catch (Exception e){
                e.printStackTrace();
                map.put("uploaded", 0);
                Map<String, Object> mm = new HashMap<String, Object>();
                mm.put("message",e.getMessage() );
                map.put("error", mm);
            }
        }
        return map;
    }






}
