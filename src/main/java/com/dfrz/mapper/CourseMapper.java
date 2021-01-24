package com.dfrz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dfrz.bean.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseMapper extends BaseMapper<Course> {
    public Integer getCourseStudentCountByCid(Integer courseid);

}