package com.framework.ioc.bean;

import java.util.List;

import lombok.Data;
import lombok.ToString;

/**
 * @author Zhengxin
 */
@Data
@ToString
public class BeanDefinition {

    private String name;

    private String className;

    private String interfaceName;

    private List<ConstructorArg> constructorArgs;

    private List<PropertyArg> propertyArgs;

}
