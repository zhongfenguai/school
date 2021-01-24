package com.dfrz.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dfrz.bean.Student;
import com.dfrz.bean.User;
import com.dfrz.mapper.StudentMapper;
import com.dfrz.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/17 14:31
 * 描述:
 */
@Service
public class StudentServiceImpl implements StudentSerivce {
    private static Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public Student getStudentByUserId(Integer userid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("userid", userid);
        Student student = studentMapper.selectOne(queryWrapper);
        return student;
    }

    //批量导入
    @Override
    public int insertStudentBatch(List<Student> list) {
        int result = 1;
        try {
            for (Student s :
                    list) {
                //先添加到用户表
                User user=new User();
                user.setUname(s.getEmail());
                user.setUpass("123");
                user.setEmail(s.getEmail());
                user.setRoleid(2);
                user.setIsenabled(1);
                user.setHeadpic("1606733176595q.JPG");
                user.setCreatetime(new Date());
                userMapper.insert(user);
                //再添加到学生表,并获取学生id
                s.setUserid(user.getId());
                studentMapper.insert(s);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            result = 0;
        }
        return result;
    }


}
