package com.dfrz.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("homework_submit_attach")
public class HomeworkSubmitAttach {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer hwsubid;

    private String url;

    private Integer type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHwsubid() {
        return hwsubid;
    }

    public void setHwsubid(Integer hwsubid) {
        this.hwsubid = hwsubid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}