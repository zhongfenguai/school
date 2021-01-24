package com.dfrz.service;

import com.alibaba.fastjson.JSONObject;
import com.dfrz.bean.Permission;
import com.dfrz.controller.UserController;
import com.dfrz.mapper.PermissionMapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/12 17:44
 * 描述:
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public String jsonTree(Integer roleid) {
        Subject subject = SecurityUtils.getSubject();
        List<Permission> list = permissionMapper.selectList(null);
        Permission root = new Permission(0, "顶层", 0, "top");
        Permission node = null;
        for (Permission p : list) {
            node = new Permission(p.getId(), p.getTitle(), p.getPid(), p.getPermissionkey());
            //递归加入树形节点中
            //通过shiro进行权限判断
//            if (subject.isPermitted(p.getPermissionkey())
//                    ) {
//                node.setChecked(true);
//            }
            //根据角色找权限 查询 role_permission
            Integer isPermitted = permissionMapper.getRolePermissionCount(roleid, p.getId());
            if (isPermitted > 0) {
                //亮起来
                node.setChecked(true);
            }
            root.add(node);
        }
        String jsonString = JSONObject.toJSONString(root);
        logger.info(jsonString);
        return jsonString;
    }
}
