package cn.edu.heuet.demo.litepal.model;

import org.litepal.crud.LitePalSupport;

import java.sql.Date;

public class Student extends LitePalSupport {
    private long id;
    private String studentNo;
    private String name;
    private Date createTime;
    private Date updateTime;

    public Student() {
    }

    public Student(String studentNo, String name) {
        this.studentNo = studentNo;
        this.name = name;
    }

    public Student(long id, String studentNo, String name) {
        this.id = id;
        this.studentNo = studentNo;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
