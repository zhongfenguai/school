package com.dfrz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dfrz.bean.Permission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionMapper extends BaseMapper<Permission> {
//    int deleteByPrimaryKey(Integer id);
//
//    int insert(Permission record);
//
//    int insertSelective(Permission record);
//
//    Permission selectByPrimaryKey(Integer id);
//
//    int updateByPrimaryKeySelective(Permission record);
//
//    int updateByPrimaryKey(Permission record);


    public List<Permission> getPermissionByRoleId(@Param("roleid") Integer roleid);

    public Integer getRolePermissionCount(@Param("roleid") Integer roleid, @Param("permissionid") Integer permissionid);
}