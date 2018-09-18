package com.framework.ioc.utils;

/**
 * @program: demo
 * @description: 类加载工具类
 * @author: quintin
 * @create: 2018-09-18 18:37
 **/
public class ClassUtils {

    public static ClassLoader getDefaultClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static Class loadClass(String className){
        try {
            return getDefaultClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
