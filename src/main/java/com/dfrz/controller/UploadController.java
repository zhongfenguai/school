package com.dfrz.controller;

import com.dfrz.utils.Result;
import com.dfrz.utils.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/19 12:14
 * 描述:
 */
@RestController
@RequestMapping("upload")
public class UploadController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/uploadfile")
    public Result uploadfile(MultipartFile file,String destdir) {
        logger.info("excute uploadfile");
        Result result = ResultUtils.success();
        try {
            //上传服务器上的路径
            String path = ResourceUtils.getURL("classpath:").getPath();
            logger.info("path" + path);
            File projectpath = new File(path);
            File upload = new File(projectpath.getAbsolutePath(), "static/"+destdir);
            if (!upload.exists()) {
                upload.mkdirs();
            }
            //构建在服务端上传目录的文件
            File dest = new File(upload.getAbsolutePath() + File.separator + file.getOriginalFilename());
            //上传io读写
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, String> map = new HashMap<>();
        map.put("src", file.getOriginalFilename());
        System.out.println(file.getOriginalFilename());
        result.setCode(0);
        result.setData(map);
        return result;
    }
}
