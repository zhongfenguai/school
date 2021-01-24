package com.dfrz.controller;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class NoPermissionException {
    // 授权失败，就是说没有该权限
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public String handleShiroException(Exception ex) {
        System.out.println("授权失败");
        //return "/error/unAuth";
        return "No Permission";
    }

    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception ex) {
        System.out.println("权限认证失败");
        return "权限认证失败";
    }
}