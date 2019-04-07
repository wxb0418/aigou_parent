package cn.itsource.client.web.controller;

import cn.itsource.client.utils.FastDfsApiOpr;
import cn.itsource.utils.AjaxResult;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/common")
public class FastDfsController {

    /**
     * 上传文件
     * @param file
     * @return
     */
    @RequestMapping(value = "/fastdfds",method = RequestMethod.POST)
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

    /**
     * 下载文件
     * @param filePathName
     * @param response
     * @return
     */
    @RequestMapping(value = "/fastdfds",method = RequestMethod.GET)
    public AjaxResult download(@RequestParam String filePathName, HttpServletResponse response){
        String substring = filePathName.substring(1);
        //System.out.println(substring);
        String groupName = substring.substring(0,substring.indexOf("/"));
        //System.out.println(groupName);
        String fileName = substring.substring(substring.indexOf("/")+1);

        //String groupName = null;
        //String fileName = null;
        //下载文件
        byte[] download = FastDfsApiOpr.download(groupName, fileName);

        //将文件从输入复制到输出
        InputStream input = null;
        OutputStream ouput = null;
        try {
            input = new ByteArrayInputStream(download);
            ouput = response.getOutputStream();
            IOUtils.copy(input,ouput);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ouput != null){
                try {
                    ouput.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;

    }
    /*
    测试文件路径截取
     */
   /* public static void main(String[] args) {
        String filePathName = "/group1/M00/00/01/wKgCa1ynRpiAB04PAADlnQkBBPg241.jpg";
        String substring = filePathName.substring(1);
        System.out.println(substring);
        String substring1 = substring.substring(0,substring.indexOf("/"));
        System.out.println(substring1);

        String substring2 = substring.substring(substring.indexOf("/")+1);
        System.out.println(substring2);
    }*/

    /**
     * 删除文件
     * @param filePathName
     * @param response
     * @return
     */
    @RequestMapping(value = "/fastdfds",method = RequestMethod.DELETE)
    public AjaxResult delete(@RequestParam String filePathName, HttpServletResponse response){
        String substring = filePathName.substring(1);
        //System.out.println(substring);
        String groupName = substring.substring(0,substring.indexOf("/"));
        //System.out.println(groupName);
        String fileName = substring.substring(substring.indexOf("/")+1);
        int deleteResult = FastDfsApiOpr.delete(groupName, fileName);
        if(deleteResult == 0){
            return AjaxResult.me().setMsg("文件删除成功").setObject(deleteResult);
        }else {
            return AjaxResult.me().setSuccess(false).setMsg("文件删除失败");

        }
    }
}
