package edu.heuet.android.logindemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/files")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UploadController extends BaseController{

    @RequestMapping(value = "/upload", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public String upload(@RequestParam(name = "name")String name,
                       @RequestParam(name = "photo") String photo) throws IOException {
        // 将photo中的图片字符串解压出来成图片
        // 也就是二进制
        byte[] bs = new BASE64Decoder().decodeBuffer(photo);
        // 确保本地路径存在
        File savePath = new File("F:\\resources\\upload");
        if (!savePath.exists()){
            savePath.mkdir();
        }
        FileOutputStream fos = new FileOutputStream( savePath+ "\\" + name);
        fos.write(bs);
        fos.flush();
        fos.close();
        return "上传成功";
    }
}
