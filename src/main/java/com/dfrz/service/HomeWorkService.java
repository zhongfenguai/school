package com.dfrz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dfrz.bean.Homework;
import com.dfrz.bean.HomeworkAttach;
import com.dfrz.bean.HomeworkSubmit;

import java.util.List;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/14 17:06
 * 描述:
 */


public interface HomeWorkService {
    //根据ID查询作业
    public Homework getHomeWorkById(Integer id);

    //添加作业
    public int addHomeWork(Homework homework);

    //根据学生ID查询作业列表
    public IPage<Homework> getHomeWorkByStudentId(Page p,Integer studentid);

    //根据TeacherID查询布置作业列表
    public IPage<Homework> getTeacherHomeWorksByTeacherId(Integer teacherid, Page p);

    //通过作业ID查询 提交的学生
    public List<HomeworkSubmit> getHomeWorkSubmitsByHwid(Integer hwid);

    //学生提交作业
    public void submitHomeWork(HomeworkSubmit homeWorkSubmit);

    //检查作业
    public void checkHwSubmit(HomeworkSubmit hws);

    //添加附件
    public void addHomeWorkAttach(HomeworkAttach homeworkAttach);

    //查询是否提交
    public Integer querysubmit(Integer hwid,Integer studentid);



}
