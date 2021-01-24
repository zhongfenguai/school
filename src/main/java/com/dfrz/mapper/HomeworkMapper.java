package com.dfrz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dfrz.bean.Homework;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkMapper extends BaseMapper<Homework> {

    public IPage<Homework> getHomeWorkBtStudentId(Page page, @Param("studentid") Integer studentid);

    public Integer querySubmit(@Param("hwid") Integer hwid, @Param("studentid") Integer studentid);

}