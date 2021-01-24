package com.dfrz.bean;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.ArrayList;
import java.util.List;

@TableName(value = "permission")
public class Permission {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "name")
    private String title;
    //父节点ID
    @TableField(value = "pid")
    private Integer pid;
    @TableField(value = "permissionkey")
    private String permissionkey;
    //是否被选中
    @TableField(exist = false)
    boolean checked = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Permission> getChildren() {
        return children;
    }

    public void setChildren(List<Permission> children) {
        this.children = children;
    }

    //子节点
    @TableField(exist = false)
    List<Permission> children = new ArrayList<>();

    /**
     * 递归添加节点
     *
     * @param node
     */
    public void add(Permission node) {
        if ("0".equals(node.pid)) {
            this.children.add(node);
        } else if (node.pid.equals(this.id)) {
            this.children.add(node);
        } else {
            for (Permission tmp_node : children) {
                tmp_node.add(node);
            }
        }
    }

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
        this.title = title;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getPermissionkey() {
        return permissionkey;
    }

    public void setPermissionkey(String permissionkey) {
        this.permissionkey = permissionkey;
    }

    public Permission() {
    }

    public Permission(Integer id, String title, Integer pid, String permissionkey) {
        this.id = id;
        this.title = title;
        this.pid = pid;
        this.permissionkey = permissionkey;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", pid=" + pid +
                ", permissionkey='" + permissionkey + '\'' +
                '}';
    }

    public static void main(String[] args) {
        //根节点
        Permission root = new Permission(0, "顶层", 0, "top");
        Permission node = null;
        node = new Permission(1, "用户管理", 0, "user");
        root.add(node);
        node = new Permission(2, "课程管理", 0, "course");
        root.add(node);
        node = new Permission(3, "角色管理", 0, "role");
        root.add(node);
        //转json
        String jsonString = JSONObject.toJSONString(root);
        System.out.println(jsonString);
    }
}
