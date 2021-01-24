package com.dfrz.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dfrz.bean.Permission;
import com.dfrz.bean.Role;
import com.dfrz.bean.User;
import com.dfrz.mapper.PermissionMapper;
import com.dfrz.mapper.RoleMapper;
import com.dfrz.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public boolean login(User user) {
        //条件构造器
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        Map<String, String> parm = new HashMap<>();
        parm.put("uname", user.getUname());
        parm.put("upass", user.getUpass());
        queryWrapper.allEq(parm);
        List<User> list = userMapper.selectList(queryWrapper);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<User> getUsers() {
        return userMapper.selectList(null);
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public IPage<User> getUsersByPage(Page page) {
//         userMapper.selectPage(page,null);
        return userMapper.selectPage(page, null);
    }

    @Override
    public User getUserRolePermissionByUname(String uname) {
        //1.根据用户名找用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uname", uname);
        User user = userMapper.selectOne(queryWrapper);
        //2.找角色
        Role role = roleMapper.selectById(user.getRoleid());
        user.setUrole(role);
        //3.找权限
        List<Permission> permissions = permissionMapper.getPermissionByRoleId(role.getId());
        role.setPermissions(permissions);
        return user;
    }

    @Override
    public User getUserByUname(String uname) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uname", uname);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User findById(Integer id) {
        return userMapper.selectById(id);

    }

    @Override
    public User queryAll(String uname, String upass) {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("uname",uname);
        queryWrapper.eq("upass",upass);
        User user= userMapper.selectOne(queryWrapper);
        return user;
    }


}
