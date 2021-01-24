package com.dfrz.service;

import com.dfrz.bean.Homework;
import com.dfrz.bean.HomeworkSubmit;
import com.dfrz.bean.HomeworkSubmitAttach;

import java.util.List;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/17 18:20
 * 描述:
 */
public interface HwSubmitService {

    //提交作业
    public Integer addHomeWorkSubmit(HomeworkSubmit homeworkSubmit);

    //提交作业附件
    public int addHomeWorkSubmitAttach(HomeworkSubmitAttach homeworkSubmitAttach);

    //检查作业,获取提交列表
    public List<HomeworkSubmit> gethomeworksubmitsbyhwid(Integer hwid);

    //批改作业
    public int homeworkCheck(HomeworkSubmit homeworkSubmit);

}
