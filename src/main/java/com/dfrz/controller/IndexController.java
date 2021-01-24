package com.dfrz.controller;

import com.dfrz.bean.User;
import com.dfrz.service.CourseService;
import com.dfrz.service.IUserService;
import com.dfrz.utils.Result;
import com.dfrz.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);
    @Autowired
    CourseService courseService;

    @RequestMapping("/toIndex")
    public ModelAndView toindex() {
        logger.info("toindex-----");
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }

    @RequestMapping("/toWelecome")
    public ModelAndView toWelecome() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("welecome");
        return mv;
    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpSession session, Model model) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @RequestMapping("/course_static")
    public Result courseStudentStatics() {

        List<String> courses = courseService.getCoursesName();
        List<Integer> datas = courseService.getCourseStudentCount();
        Map<String, Object> maps = new HashMap<>();
        maps.put("courses", courses);
        maps.put("datas", datas);

        Result result = ResultUtils.success(maps);
        return result;
    }
}




