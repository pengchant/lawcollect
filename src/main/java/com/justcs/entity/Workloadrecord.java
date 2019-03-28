package com.justcs.entity;

import java.io.Serializable;
import java.util.Date;

public class Workloadrecord implements Serializable {
    private Integer id;

    private Short type;

    private Integer accountid;

    private Integer lawid;

    private Float grade;

    private Date optime;

    private Date gradetime;

    public Workloadrecord(Integer id, Short type, Integer accountid, Integer lawid, Float grade, Date optime, Date gradetime) {
        this.id = id;
        this.type = type;
        this.accountid = accountid;
        this.lawid = lawid;
        this.grade = grade;
        this.optime = optime;
        this.gradetime = gradetime;
    }

    public Workloadrecord() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public Integer getLawid() {
        return lawid;
    }

    public void setLawid(Integer lawid) {
        this.lawid = lawid;
    }

    public Float getGrade() {
        return grade;
    }

    public void setGrade(Float grade) {
        this.grade = grade;
    }

    public Date getOptime() {
        return optime;
    }

    public void setOptime(Date optime) {
        this.optime = optime;
    }

    public Date getGradetime() {
        return gradetime;
    }

    public void setGradetime(Date gradetime) {
        this.gradetime = gradetime;
    }
}