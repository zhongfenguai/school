package com.dfrz.test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dfrz.bean.Permission;
import com.dfrz.bean.User;
import com.dfrz.mapper.HomeworkMapper;
import com.dfrz.mapper.PermissionMapper;
import com.dfrz.mapper.UserMapper;
import com.dfrz.service.HomeWorkService;
import com.dfrz.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/8 17:04
 * 描述:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    IUserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    HomeWorkService homeWorkService;
    @Autowired
    HomeworkMapper homeworkMapper;

    @Test
    public void test01() {
        User user = new User();
//        user.setId(14);
        user.setUname("123");
        user.setUpass("2123");
        userService.save(user);
    }

    @Test
    public void test02() {
        userMapper.testselect();
    }

    @Test
    public void test03() {
        User user = userService.getUserRolePermissionByUname("xiaohong");
        System.out.println("===============");
        System.out.println(user);
        System.out.println("========");
        System.out.println(user.getUrole().getRolename());
        List<Permission> permissions = user.getUrole().getPermissions();
        for (Permission permission : permissions) {
            System.out.println(permission.getTitle());
        }
    }

    @Test
    public void test04() {
        Integer i = permissionMapper.getRolePermissionCount(1, 1);
        System.out.println(i);

    }

    @Test
    public void test05() {
        Integer i = permissionMapper.getRolePermissionCount(1, 1);
        System.out.println(i);

    }

    @Test
    public void test06() {
        Page page = new Page();
        page.setSize(1);
        page.setCurrent(1);
        IPage page1 = homeWorkService.getHomeWorkByStudentId(page, 1);
        System.out.println(page1.getSize());

    }

    @Test
    public void test07() {
        Integer i = homeworkMapper.querySubmit(25, 1);
        System.out.println(i);
    }

    @Test
    public void test08(HttpSession session) {
        session.getAttribute("user");
    }

}
