package com.dfrz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dfrz.bean.HomeworkSubmit;
import com.dfrz.bean.HomeworkSubmitAttach;
import com.dfrz.bean.Student;
import com.dfrz.mapper.HomeworkSubmitAttachMapper;
import com.dfrz.mapper.HomeworkSubmitMapper;
import com.dfrz.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/17 18:22
 * 描述:
 */
@Service
public class HwSubmitServiceImpl implements HwSubmitService {
    @Autowired
    HomeworkSubmitMapper homeworkSubmitMapper;
    @Autowired
    HomeworkSubmitAttachMapper homeworkSubmitAttachMapper;
    @Autowired
    StudentMapper studentMapper;


    @Override
    public Integer addHomeWorkSubmit(HomeworkSubmit homeworkSubmit) {
        return homeworkSubmitMapper.insert(homeworkSubmit);
    }

    @Override
    public int addHomeWorkSubmitAttach(HomeworkSubmitAttach homeworkSubmitAttach) {
        return homeworkSubmitAttachMapper.insert(homeworkSubmitAttach);
    }

    @Override
    public List<HomeworkSubmit> gethomeworksubmitsbyhwid(Integer hwid) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("hwid", hwid);
        List<HomeworkSubmit> list = homeworkSubmitMapper.selectList(queryWrapper);
        //提交的学生
        for (HomeworkSubmit h : list
                ) {
            Student student = studentMapper.selectById(h.getStudentid());
            h.setSubmitman(student);
            //提交附件
            QueryWrapper queryWrapper1 = new QueryWrapper();
            queryWrapper1.eq("hwsubid", h.getId());
            List<HomeworkSubmitAttach> homeworkSubmitAttaches = homeworkSubmitAttachMapper.selectList(queryWrapper1);
            h.setAttaches(homeworkSubmitAttaches);
        }
        return list;
    }

    @Override
    public int homeworkCheck(HomeworkSubmit newhomeworksubmit) {

        //在修改前先查询
        HomeworkSubmit oldhomeworksubmit = homeworkSubmitMapper.selectById(newhomeworksubmit.getId());
        oldhomeworksubmit.setMark(newhomeworksubmit.getMark());
        oldhomeworksubmit.setRemark(newhomeworksubmit.getRemark());
        oldhomeworksubmit.setIscorrect(1);
        homeworkSubmitMapper.updateById(oldhomeworksubmit);
        return homeworkSubmitMapper.updateById(oldhomeworksubmit);
    }
}
