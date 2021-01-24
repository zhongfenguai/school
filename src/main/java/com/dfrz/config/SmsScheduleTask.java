package com.dfrz.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/20 14:39
 * 描述:
 */
@Configuration
//开始定时任务
@EnableScheduling
public class SmsScheduleTask {

    //添加一个定时任务 每隔五秒钟执行
    @Scheduled(cron = "0/5 * * * * *")
    public void configTask() {
//        System.out.println("执行任务发送短信：" + LocalDateTime.now());
    }
}
