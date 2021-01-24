package com.dfrz.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dfrz.bean.User;
import com.dfrz.service.IUserService;
import com.dfrz.utils.Result;
import com.dfrz.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    IUserService userService;

    @RequestMapping("/toLogin")
    public ModelAndView toLogin() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("login");
        return mv;
    }


    @RequestMapping("/login")
    public Map<String, String> login(User user) {
        logger.info("execute login method. Login uname=" + user.getUname() + ",upass=" + user.getUpass());
//        boolean flag = userService.login(user);
//        logger.info("login flag=" + flag);
        Map<String, String> map = new HashMap<>();
//        map.put("flag", flag + "");
//        return map;
        //shiro完成
        //1.获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUname(), user.getUpass());
        //3.执行shiro的Login方法， 会自动调用realm中的认证方法doGetAuthenticationInfo
        try {
            subject.login(token);
            map.put("flag", "1");
            Session session = SecurityUtils.getSubject().getSession();
            User user1= userService.queryAll(user.getUname(),user.getUpass());
            session.setAttribute("user", user1);
            System.out.println(session.getAttribute("user"));
        } catch (UnknownAccountException e) {
            logger.info("没有此账号");
            map.put("flag", "-1");
        } catch (IncorrectCredentialsException e) {
            logger.info("密码不正确");
            map.put("flag", "-2");
        } catch (AuthenticationException e) {
            logger.info("认证失败");
            map.put("flag", "-3");
        }
        return map;
    }

    @RequestMapping("/toList")
    public ModelAndView toList() {
        logger.info("toList-----");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user_list");
        return mv;
    }

    @RequestMapping("/listAjax")
    public Result getList(Integer page, Integer limit) {
        Page page1 = new Page();
        page1.setSize(limit);
        page1.setCurrent(page);
        IPage iPage = userService.getUsersByPage(page1);
        Result result = ResultUtils.success(iPage.getRecords());
        result.setCode(0);
        result.setCount((int) iPage.getTotal());
        return result;

    }

    @RequestMapping("/addAjax")
    public Result addUser(User user) {
        userService.save(user);
        Result result = ResultUtils.success();
        result.setCode(0);
        return result;
    }

    @RequestMapping("/toAdd")
    @RequiresPermissions(value = "course")
    public ModelAndView toAddUser() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("user_add");
        return mv;
    }

    @RequestMapping("/toDetails")
    public ModelAndView toDetails(Integer id) {
        ModelAndView mv = new ModelAndView();
        User user = userService.getUserById(id);
        mv.setViewName("user_details");
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping("/uploadHeadPic")
    public Result uploadHeadPic(MultipartFile file) {
        logger.info("excute uploadHeadPic");
        Result result = ResultUtils.success();
        try {
            //上传服务器上的路径
            String path = ResourceUtils.getURL("classpath:").getPath();
            logger.info("path" + path);
            File projectpath = new File(path);
            File upload = new File(projectpath.getAbsolutePath(), "static/upload/user/images");
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
        result.setCode(0);
        result.setData(map);
        return result;
    }

    @RequestMapping("/uploadRemark")
    public Result uploadRemark(MultipartFile file) {
        logger.info("excute uploadRemark");
        Result result = ResultUtils.success();
        try {
            //上传服务器上的路径
            String path = ResourceUtils.getURL("classpath:").getPath();
            logger.info("path" + path);
            File projectpath = new File(path);
            File upload = new File(projectpath.getAbsolutePath(), "static/upload/user/remark/images");
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
        map.put("src", "http://localhost:8080/upload/user/remark/images/" + file.getOriginalFilename());
        map.put("title", file.getOriginalFilename());
        result.setCode(0);
        result.setData(map);
        return result;
    }

}
