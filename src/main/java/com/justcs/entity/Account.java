package com.justcs.entity;

import java.io.Serializable;
import java.util.Date;

public class Account implements Serializable {
    private Integer id;

    private String account;

    private String pwd;

    private Short type;

    private Short sts;

    private Date registtime;

    public Account(Integer id, String account, String pwd, Short type, Short sts, Date registtime) {
        this.id = id;
        this.account = account;
        this.pwd = pwd;
        this.type = type;
        this.sts = sts;
        this.registtime = registtime;
    }

    public Account() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Short getSts() {
        return sts;
    }

    public void setSts(Short sts) {
        this.sts = sts;
    }

    public Date getRegisttime() {
        return registtime;
    }

    public void setRegisttime(Date registtime) {
        this.registtime = registtime;
    }
}