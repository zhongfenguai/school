package com.dfrz.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

@TableName("homework")
public class Homework {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String details;

    private Integer courseid;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Shanghai")
    private Date createtime;

    private Integer sectionid;

    private Date expirytime;

    private Integer teacherid;

    //所属课程
    @TableField(exist = false)
    private Course course;

    //是否提交
    @TableField(exist = false)
    private Integer issubmit;
    //是否批阅
    @TableField(exist = false)
    private Integer iscorrect;

    public Integer getIssubmit() {
        return issubmit;
    }

    public void setIssubmit(Integer issubmit) {
        this.issubmit = issubmit;
    }

    public Integer getIscorrect() {
        return iscorrect;
    }

    public void setIscorrect(Integer iscorrect) {
        this.iscorrect = iscorrect;
    }

    //提交数量
    @TableField(exist = false)
    private Integer submitcount;

    public Integer getSubmitcount() {
        return submitcount;
    }

    public void setSubmitcount(Integer submitcount) {
        this.submitcount = submitcount;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getAttaches() {
        return attaches;
    }

    public void setAttaches(String attaches) {
        this.attaches = attaches;
    }

    public List<HomeworkAttach> getHomeworkAttaches() {
        return homeworkAttaches;
    }

    public void setHomeworkAttaches(List<HomeworkAttach> homeworkAttaches) {
        this.homeworkAttaches = homeworkAttaches;
    }

    @TableField(exist = false)
    private String attaches;

    //对应多个作业附件
    @TableField(exist = false)
    private List<HomeworkAttach> homeworkAttaches;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    public Integer getCourseid() {
        return courseid;
    }

    public void setCourseid(Integer courseid) {
        this.courseid = courseid;
    }

    public Integer getSectionid() {
        return sectionid;
    }

    public void setSectionid(Integer sectionid) {
        this.sectionid = sectionid;
    }

    public Date getExpirytime() {
        return expirytime;
    }

    public void setExpirytime(Date expirytime) {
        this.expirytime = expirytime;
    }

    public Integer getTeacherid() {
        return teacherid;
    }

    public void setTeacherid(Integer teacherid) {
        this.teacherid = teacherid;
    }
}