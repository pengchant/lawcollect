package com.justcs.entity;

import java.io.Serializable;
import java.util.Date;

public class Lawentitytemp implements Serializable {
    private Integer id;

    private Integer lawid;

    private Integer number;

    private Integer accountid;

    private Date inputtime;

    private String lawcontent;

    public Lawentitytemp(Integer id, Integer lawid, Integer number, Integer accountid, Date inputtime, String lawcontent) {
        this.id = id;
        this.lawid = lawid;
        this.number = number;
        this.accountid = accountid;
        this.inputtime = inputtime;
        this.lawcontent = lawcontent;
    }

    public Lawentitytemp() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLawid() {
        return lawid;
    }

    public void setLawid(Integer lawid) {
        this.lawid = lawid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public Date getInputtime() {
        return inputtime;
    }

    public void setInputtime(Date inputtime) {
        this.inputtime = inputtime;
    }

    public String getLawcontent() {
        return lawcontent;
    }

    public void setLawcontent(String lawcontent) {
        this.lawcontent = lawcontent == null ? null : lawcontent.trim();
    }
}