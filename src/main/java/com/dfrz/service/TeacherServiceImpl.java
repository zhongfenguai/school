package com.dfrz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dfrz.bean.Teacher;
import com.dfrz.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/14 18:31
 * 描述:
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public Teacher getTeacherByUserId(Integer userid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", userid);
        Teacher teacher = teacherMapper.selectOne(queryWrapper);
        return teacher;
    }
}
