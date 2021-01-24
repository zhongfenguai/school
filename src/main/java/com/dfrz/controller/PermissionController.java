package com.dfrz.controller;

import com.dfrz.service.PermissionService;
import com.dfrz.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.jnlp.PersistenceService;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/12 16:15
 * 描述:
 */

@RestController
@RequestMapping("/permission")
public class PermissionController {
    private static Logger logger = LoggerFactory.getLogger(PermissionController.class);
    @Autowired
    PermissionService permissionService;

    @RequestMapping("/tree")
    public ModelAndView toTree() {
        logger.info("excute toTree Method");
        ModelAndView modelAndVieme = new ModelAndView();
        modelAndVieme.setViewName("tree01");
        return modelAndVieme;
    }

    //异步加载树形节点
    @RequestMapping("/ajaxTree")
    public String ajaxTree(@RequestParam("roleid") Integer roleid) {
        String json = permissionService.jsonTree(roleid);
        System.out.println(json);
        return json;

    }
}
