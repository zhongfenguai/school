package com.dfrz.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 作者：chenbingfeng
 * 日期: 2021/1/24 14:18
 * 描述:
 */
@SpringBootTest

public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
   public void test() {
        userMapper.selectList(null).forEach(System.out::print);
    }

}