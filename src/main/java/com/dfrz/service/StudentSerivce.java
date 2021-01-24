package com.dfrz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dfrz.bean.Student;

import java.util.List;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/17 14:31
 * 描述:
 */

public interface StudentSerivce {
    public Student getStudentByUserId(Integer userid);

    //批量导入
    public int insertStudentBatch(List<Student> list);


}
