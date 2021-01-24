package com.dfrz.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;


@TableName(value = "user")
public class User {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String uname;

    private String upass;

    private Date createtime;

    private Integer isenabled;

    private Integer roleid;

    private String headpic;

    private String email;

    private String remark;
    //不是数据库字段
    @TableField(exist = false)
    private Role urole;

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getIsenabled() {
        return isenabled;
    }

    public void setIsenabled(Integer isenabled) {
        this.isenabled = isenabled;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Role getUrole() {
        return urole;
    }

    public void setUrole(Role urole) {
        this.urole = urole;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uname='" + uname + '\'' +
                ", upass='" + upass + '\'' +
                ", roleid=" + roleid +
                ", headpic='" + headpic + '\'' +
                ", remark='" + remark + '\'' +
                ", urole=" + urole +
                '}';
    }

    public User(Integer id, String uname, String upass, Integer roleid, String headpic, String remark, Role urole) {
        this.id = id;
        this.uname = uname;
        this.upass = upass;
        this.roleid = roleid;
        this.headpic = headpic;
        this.remark = remark;
        this.urole = urole;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


}