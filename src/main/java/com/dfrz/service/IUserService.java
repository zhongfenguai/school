package com.dfrz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dfrz.bean.User;

import java.util.List;

public interface IUserService {
    public boolean login(User user);

    public List<User> getUsers();

    public void save(User user);

    public User getUserById(Integer id);

    public IPage<User> getUsersByPage(Page page);

    public User getUserRolePermissionByUname(String uname);

    public User getUserByUname(String uname);

    public User findById(Integer id);

    public User queryAll(String uname,String upass);


}
