package com.justcs.entity;

import java.io.Serializable;

public class Student implements Serializable {
    private Integer id;

    private String stuid;

    private String stuname;

    private String classno;

    private String tips;

    private Integer accountid;

    public Student(Integer id, String stuid, String stuname, String classno, String tips, Integer accountid) {
        this.id = id;
        this.stuid = stuid;
        this.stuname = stuname;
        this.classno = classno;
        this.tips = tips;
        this.accountid = accountid;
    }

    public Student() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid == null ? null : stuid.trim();
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname == null ? null : stuname.trim();
    }

    public String getClassno() {
        return classno;
    }

    public void setClassno(String classno) {
        this.classno = classno == null ? null : classno.trim();
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips == null ? null : tips.trim();
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }
}