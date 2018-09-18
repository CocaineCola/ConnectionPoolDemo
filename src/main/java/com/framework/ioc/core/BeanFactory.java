package com.framework.ioc.core;

public interface BeanFactory {

    Object getBean(String name) throws Exception;

}
