package com.dfrz.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

@TableName("homework_submit")
public class HomeworkSubmit {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer hwid;

    private Integer studentid;

    private Integer iscorrect;

    private String mark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date createtime;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    private String remark;

    private Integer status;

    private String url;

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public Student getSubmitman() {
        return submitman;
    }

    public void setSubmitman(Student submitman) {
        this.submitman = submitman;
    }

    public List<HomeworkSubmitAttach> getAttaches() {
        return attaches;
    }

    public void setAttaches(List<HomeworkSubmitAttach> attaches) {
        this.attaches = attaches;
    }

    @TableField(exist = false)
    private Homework homework;
    //提交人
    @TableField(exist = false)
    private Student submitman;
    //提交附件
    @TableField(exist = false)
    private List<HomeworkSubmitAttach> attaches;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHwid() {
        return hwid;
    }

    public void setHwid(Integer hwid) {
        this.hwid = hwid;
    }

    public Integer getStudentid() {
        return studentid;
    }

    public void setStudentid(Integer studentid) {
        this.studentid = studentid;
    }

    public Integer getIscorrect() {
        return iscorrect;
    }

    public void setIscorrect(Integer iscorrect) {
        this.iscorrect = iscorrect;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
}