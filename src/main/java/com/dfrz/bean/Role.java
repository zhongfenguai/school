package com.dfrz.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName(value = "role")
public class Role {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(value = "rolename")
    private String rolename;
    @TableField(value = "rolekey")
    private String rolekey;
    @TableField(exist = false)
    private List<Permission> permissions;

    public Role() {
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", rolename='" + rolename + '\'' +
                ", rolekey='" + rolekey + '\'' +
                ", permissions=" + permissions +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRolekey() {
        return rolekey;
    }

    public void setRolekey(String rolekey) {
        this.rolekey = rolekey;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public Role(Integer id, String rolename, String rolekey, List<Permission> permissions) {
        this.id = id;
        this.rolename = rolename;
        this.rolekey = rolekey;
        this.permissions = permissions;
    }
}