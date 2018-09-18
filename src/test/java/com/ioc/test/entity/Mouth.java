package com.ioc.test.entity;

public class Mouth {

    String name;

    public Mouth(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void speak() {

        System.out.println(name + ": hello world!");

    }

}
