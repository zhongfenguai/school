package com.dfrz.service;

import com.dfrz.bean.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/13 15:07
 * 描述:
 */

public interface CourseService {
    public List<String> getCoursesName();

    public List<Integer> getCourseStudentCount();

    public List<Course> getCoursesByTeacherId(Integer teacherid);
}
