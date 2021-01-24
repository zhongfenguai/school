package com.dfrz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dfrz.bean.Course;
import com.dfrz.bean.Homework;
import com.dfrz.bean.HomeworkAttach;
import com.dfrz.bean.HomeworkSubmit;
import com.dfrz.controller.UserController;
import com.dfrz.mapper.CourseMapper;
import com.dfrz.mapper.HomeworkAttachMapper;
import com.dfrz.mapper.HomeworkMapper;
import com.dfrz.mapper.HomeworkSubmitMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/14 17:13
 * 描述:
 */
@Service
public class HomeWorkServiceImpl implements HomeWorkService {
    private static Logger logger = LoggerFactory.getLogger(HomeWorkServiceImpl.class);

    @Autowired
    HomeworkMapper homeworkMapper;
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    HomeworkSubmitMapper homeworkSubmitMapper;
    @Autowired
    HomeworkAttachMapper homeworkAttachMapper;


    @Override
    public Homework getHomeWorkById(Integer id) {
        Homework homework = homeworkMapper.selectById(id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hwid", id);
        //把附件也查出来
        List<HomeworkAttach> homeworkAttachList = homeworkAttachMapper.selectList(queryWrapper);

        homework.setHomeworkAttaches(homeworkAttachList);
        return homework;
    }

    @Override
    public int addHomeWork(Homework homework) {
        return homeworkMapper.insert(homework);
    }

    @Override
    public IPage<Homework> getHomeWorkByStudentId(Page p, Integer studentid) {
        IPage<Homework> homeworkList = homeworkMapper.getHomeWorkBtStudentId(p, studentid);


        return homeworkList;
    }

    @Override
    public IPage<Homework> getTeacherHomeWorksByTeacherId(Integer teacherid, Page p) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("teacherid", teacherid);
        IPage<Homework> homeworkIPage = homeworkMapper.selectPage(p, queryWrapper);

        List<Homework> list = homeworkIPage.getRecords();
        for (Homework homework : list) {
            //查询课程
            Course course = courseMapper.selectById(homework.getCourseid());
            homework.setCourse(course);
            //已提交数
            QueryWrapper queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("hwid", homework.getId());
            Integer count = homeworkSubmitMapper.selectCount(queryWrapper1);
            homework.setSubmitcount(count);
        }
        return homeworkIPage;
    }

    @Override
    public List<HomeworkSubmit> getHomeWorkSubmitsByHwid(Integer hwid) {
        return null;
    }

    @Override
    public void submitHomeWork(HomeworkSubmit homeWorkSubmit) {

    }

    @Override
    public void checkHwSubmit(HomeworkSubmit hws) {

    }

    @Override
    public void addHomeWorkAttach(HomeworkAttach homeworkAttach) {
        homeworkAttachMapper.insert(homeworkAttach);

    }

    @Override
    public Integer querysubmit(Integer hwid, Integer studentid) {
        return homeworkMapper.querySubmit(hwid, studentid);
    }

}
