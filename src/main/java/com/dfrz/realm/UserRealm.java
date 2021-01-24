package com.dfrz.realm;


import com.dfrz.bean.Permission;
import com.dfrz.bean.User;
import com.dfrz.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    IUserService userService;

    /*登陆之后-授权*/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取Subject
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //根据uname从数据库查询角色和权限
        User userRolePermission = userService.getUserRolePermissionByUname(user.getUname());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        List<String> roles = new ArrayList<>();
        roles.add(userRolePermission.getUrole().getRolekey());
        //Shiro框架中添加角色
        info.addRoles(roles);

        List<String> permissions = new ArrayList<>();
        for (Permission permission : userRolePermission.getUrole().getPermissions()
                ) {
            permissions.add(permission.getPermissionkey());
        }
        //Shiro框架中添加权限
        info.addStringPermissions(permissions);
        return info;
    }

    /*登陆-认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //根据用户名查询用户
        User user = userService.getUserByUname(token.getUsername());
        if (user == null) {
            System.out.println("用户不存在");
            throw new UnknownAccountException();
        } else {
            user = userService.getUserById(user.getId());
            if (user == null) {
                //认证失败
                throw new AuthenticationException();
            }
        }
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUname());
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getUpass(),
                credentialsSalt, getName());
        return simpleAuthenticationInfo;
    }
}
