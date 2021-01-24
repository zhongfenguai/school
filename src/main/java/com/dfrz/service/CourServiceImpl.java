package com.dfrz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dfrz.bean.Course;
import com.dfrz.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/13 15:16
 * 描述:
 */
@Service
public class CourServiceImpl implements CourseService {
    @Autowired
    CourseMapper courseMapper;

    @Override
    public List<String> getCoursesName() {
        //用来存放每个课程的名字
        List<String> CoursesNames = new ArrayList<>();

        List<Course> list = courseMapper.selectList(null);
        for (Course course : list) {

            CoursesNames.add(course.getCname());
        }
        return CoursesNames;
    }

    @Override
    public List<Integer> getCourseStudentCount() {
        //用来计数每个课程的人数
        List<Integer> result = new ArrayList<>();

        //查询所有课程
        List<Course> courses = courseMapper.selectList(null);
        for (Course cours : courses) {

            //对每个课程进行查询 统计这门课程的选择人数
            Integer i = courseMapper.getCourseStudentCountByCid(cours.getId());
            result.add(i);
        }
        return result;
    }

    @Override
    public List<Course> getCoursesByTeacherId(Integer teacherid) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("teacherid",teacherid);
        List<Course> list= courseMapper.selectList(queryWrapper);
        return list;
    }
}
