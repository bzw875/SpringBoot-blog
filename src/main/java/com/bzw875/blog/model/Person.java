package com.bzw875.blog.model;


/**
 * Created by Administrator on 2017/9/6.
 */
public class Person {
    private String name;
    private Integer age;
    public Person()  {
        super();
    }
    public Person(String name,Integer gae) {
        super();
        this.name=name;
        this.age=gae;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }
    public Integer getAge() {
        return age;
    }

    public Integer setAge(Integer age) {
        return age;
    }
}
