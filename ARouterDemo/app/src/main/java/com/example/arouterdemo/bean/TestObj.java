package com.example.arouterdemo.bean;

public class TestObj {
    public String name;
    public int id;

    public TestObj() {
    }

    public TestObj(int id,String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestObj{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}