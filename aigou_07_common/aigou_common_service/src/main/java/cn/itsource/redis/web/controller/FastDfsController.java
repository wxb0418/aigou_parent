package cn.itsource.redis.web.controller;

import cn.itsource.redis.utils.FastDfsApiOpr;
import cn.itsource.utils.AjaxResult;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/common")
public class FastDfsController {

    /**
     * 上传文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public AjaxResult upload(@RequestBody MultipartFile file){
        //拿到文件的原始名称
        String originalFilename = file.getOriginalFilename();
        //根据原始名称拿到文件的后缀
        String extName = FilenameUtils.getExtension(originalFilename);
        try {
            //上传文件:文件的名称 和 文件的后缀名
            String filePath = FastDfsApiOpr.upload(file.getBytes(), extName);
            return AjaxResult.me().setMsg("文件上传成功").setObject(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMsg("文件上传失败");
        }
    }



    //文件的下载

    //文件的删除
}
