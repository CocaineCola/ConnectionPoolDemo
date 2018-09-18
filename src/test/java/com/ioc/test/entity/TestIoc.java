package com.ioc.test.entity;

import com.framework.ioc.core.JsonApplicationContext;

/**
 * @program: demo
 * @description: 测试类
 * @author: quintin
 * @create: 2018-09-18 18:56
 **/
public class TestIoc {
    public static void main(String[] args) throws Exception {
        JsonApplicationContext context = new JsonApplicationContext("application.json");
        context.init();

        Robot robot = (Robot) context.getBean("robot");
        robot.show();
    }
}
