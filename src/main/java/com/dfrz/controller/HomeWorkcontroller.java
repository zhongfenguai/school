package com.dfrz.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dfrz.bean.*;
import com.dfrz.mapper.CourseMapper;
import com.dfrz.mapper.HomeworkMapper;
import com.dfrz.mapper.TeacherMapper;
import com.dfrz.service.*;
import com.dfrz.utils.Result;
import com.dfrz.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者：chenbingfeng
 * 日期: 2021/1/14 17:01
 * 描述:
 */
@RestController
@RequestMapping("homework")
public class HomeWorkcontroller {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    HomeWorkService homeWorkService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    CourseService courseService;
    @Autowired
    StudentSerivce studentSerivce;
    @Autowired
    HwSubmitService hwSubmitService;
    @Autowired
    HomeworkMapper homeworkMapper;


    @RequestMapping("/toTeacherHomeworkList")
    public ModelAndView teacher_homework_list() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("teacher_homework_list");
        return modelAndView;
    }

    @RequestMapping("/toStudentHomeworkList")
    public ModelAndView toStudentHomeworkList() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("student_homework_list");
        return modelAndView;
    }

    @RequestMapping("/toCheckHomework")
    public ModelAndView toCheckHomework(Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        List<HomeworkSubmit> homeworkSubmits = hwSubmitService.gethomeworksubmitsbyhwid(id);
        modelAndView.setViewName("homework_check");
        modelAndView.addObject("submits", homeworkSubmits);
        return modelAndView;
    }

    //老师课程作业列表
    @RequestMapping("/teacher_homework_list")
    public Result teacherHomeworkList(Integer page, Integer limit) {
        Page page1 = new Page();
        page1.setSize(limit);
        page1.setCurrent(page);
        //获取当前登陆用户的信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Teacher teacher = teacherService.getTeacherByUserId(user.getId());
        IPage iPage = homeWorkService.getTeacherHomeWorksByTeacherId(teacher.getId(), page1);
        Result result = ResultUtils.success(iPage.getRecords());
        result.setCode(0);
        result.setCount((int) iPage.getTotal());
        return result;

    }

    //学生作业列表
    @RequestMapping("/student_homework_list")
    public Result student_homework_list(Integer page, Integer limit) {
        Page page1 = new Page();
        page1.setSize(limit);
        page1.setCurrent(page);
        //获取当前登陆用户的信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Student student = studentSerivce.getStudentByUserId(user.getId());
        IPage iPage = homeWorkService.getHomeWorkByStudentId(page1, student.getId());
        List<Homework> homeworkList = iPage.getRecords();
        for (Homework homework : homeworkList) {
            Integer i = homeworkMapper.querySubmit(homework.getId(), student.getId());
            if (i > 0) {
                homework.setIssubmit(1);
            } else {
                homework.setIssubmit(0);
            }
        }
        Result result = ResultUtils.success(homeworkList);
        result.setCode(0);
        result.setCount((int) iPage.getTotal());
        return result;

    }

    //学生提交作业附件
    @RequestMapping("/uploadHwSubmitImage")
    public Result uploadHwSubmitImage(MultipartFile file) {
        logger.info("excute uploadHwSubmitImage");
        Result result = ResultUtils.success();
        try {
            //上传服务器上的路径
            String path = ResourceUtils.getURL("classpath:").getPath();
            logger.info("path" + path);
            File projectpath = new File(path);
            File upload = new File(projectpath.getAbsolutePath(), "static/upload/homework/submit/images");
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

    //老师添加作业附件
    @RequestMapping("/uploadImage")
    public Result uploadImage(MultipartFile file) {
        logger.info("excute uploadImage");
        Result result = ResultUtils.success();
        try {
            //上传服务器上的路径
            String path = ResourceUtils.getURL("classpath:").getPath();
            logger.info("path" + path);
            File projectpath = new File(path);
            File upload = new File(projectpath.getAbsolutePath(), "static/upload/homework/images");
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

    //老师添加作业附件doc
    @RequestMapping("/uploadDoc")
    public Result uploadDoc(MultipartFile file) {
        logger.info("excute uploadDoc");
        Result result = ResultUtils.success();
        try {
            //上传服务器上的路径
            String path = ResourceUtils.getURL("classpath:").getPath();
            logger.info("path" + path);
            File projectpath = new File(path);
            File upload = new File(projectpath.getAbsolutePath(), "static/upload/homework/doc");
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

    //学生提交作业doc 附件
    @RequestMapping("/uploadHwSubmitDoc")
    public Result uploadHwSubmitDoc(MultipartFile file) {
        logger.info("excute uploadHwSubmitDoc");
        Result result = ResultUtils.success();
        try {
            //上传服务器上的路径
            String path = ResourceUtils.getURL("classpath:").getPath();
            logger.info("path" + path);
            File projectpath = new File(path);
            File upload = new File(projectpath.getAbsolutePath(), "static/upload/homework/submit/doc");
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


    @RequestMapping("/toTeacherHomeworkAdd")
    public ModelAndView toTeacherHomeworkAdd() {
        ModelAndView modelAndView = new ModelAndView();
        //获取当前登陆用户的信息
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Teacher teacher = teacherService.getTeacherByUserId(user.getId());
        //获取课程列表
        List<Course> courses = courseService.getCoursesByTeacherId(teacher.getId());
        modelAndView.setViewName("teacher_homework_add");
        modelAndView.addObject("courses", courses);
        return modelAndView;
    }

    @RequestMapping("/toStudentHomeworkSubmit")
    public ModelAndView toStudentHomeworkSubmit(Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        Homework homework = homeWorkService.getHomeWorkById(id);

        modelAndView.setViewName("student_homework_submit");
        modelAndView.addObject("homework", homework);
        return modelAndView;
    }

    @RequestMapping("/addHomeWork")
    public Result addHomeWork(String jsonstr) {
        logger.info(jsonstr);
        System.out.println(jsonstr);
        //解析json
        JSONObject jsonObject = JSON.parseObject(jsonstr);
        JSONObject jsonhomework = jsonObject.getJSONObject("dataField");
        String title = jsonhomework.getString("title");
        String details = jsonhomework.getString("details");
        Integer courseid = jsonhomework.getInteger("courseid");
        Homework homework1 = new Homework();
        homework1.setTitle(title);
        homework1.setDetails(details);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        homework1.setCreatetime(timestamp);
        homework1.setCourseid(courseid);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Teacher teacher = teacherService.getTeacherByUserId(user.getId());
        homework1.setTeacherid(teacher.getId());
        homeWorkService.addHomeWork(homework1);
        System.out.println("主键" + homework1.getId());
        //解析附件
        JSONArray hwres = jsonObject.getJSONArray("hwres");
        for (int i = 0; i < hwres.size(); i++) {
            String src = hwres.getJSONObject(i).getString("src");
            Integer type = hwres.getJSONObject(i).getInteger("type");
            HomeworkAttach homeworkAttach = new HomeworkAttach();
            //加入对应课程的id
            homeworkAttach.setHwid(homework1.getId());
            homeworkAttach.setType(type);
            homeworkAttach.setUrl(src);
            homeWorkService.addHomeWorkAttach(homeworkAttach);
        }
        Result result = ResultUtils.success(1);
        result.setCode(0);

        return result;
    }


    @RequestMapping("/submitHomeWork")
    public Result submitHomeWork(String jsonstr) {
        logger.info(jsonstr);
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Student student = studentSerivce.getStudentByUserId(user.getId());
        System.out.println(jsonstr);
        //解析json
        JSONObject jsonObject = JSON.parseObject(jsonstr);
        JSONObject jsonhomeworkSubmit = jsonObject.getJSONObject("dataField");
        Integer hwid = jsonhomeworkSubmit.getInteger("hwid");
        HomeworkSubmit homeworkSubmit = new HomeworkSubmit();
        homeworkSubmit.setHwid(hwid);
        homeworkSubmit.setStudentid(student.getId());
        hwSubmitService.addHomeWorkSubmit(homeworkSubmit);
        //解析附件
        JSONArray hwsubmit_res = jsonObject.getJSONArray("hwre_submit_res");
        for (int i = 0; i < hwsubmit_res.size(); i++) {
            String src = hwsubmit_res.getJSONObject(i).getString("src");
            Integer type = hwsubmit_res.getJSONObject(i).getInteger("type");
            HomeworkSubmitAttach homeworkAttach = new HomeworkSubmitAttach();
            //加入对应课程的id
            homeworkAttach.setHwsubid(homeworkSubmit.getId());
            homeworkAttach.setType(type);
            homeworkAttach.setUrl(src);
            hwSubmitService.addHomeWorkSubmitAttach(homeworkAttach);
        }
        Result result = ResultUtils.success(1);
        result.setCode(0);

        return result;
    }

    @RequestMapping("/getHomeworksubmit")
    public Result getHomeworksubmit(Integer hwid) {
        List<HomeworkSubmit> homeworkSubmits = homeWorkService.getHomeWorkSubmitsByHwid(hwid);
        Result result = ResultUtils.success(homeworkSubmits);
        result.setCode(0);
        return result;

    }

    @RequestMapping("/homework_Check")
    public Result homework_Check(HomeworkSubmit homeworkSubmit) {
        int res = hwSubmitService.homeworkCheck(homeworkSubmit);
        Result result1 = ResultUtils.success(res);
        return result1;


    }


}
