package com.justcs.entity;

import java.io.Serializable;
import java.util.Date;

public class Securitylaw implements Serializable {
    private Integer id;

    private String lawname;

    private String version;

    private Integer firstreldepid;

    private Integer copubdepid;

    private Integer enterid;

    private Date entertime;

    private Integer checkerid;

    private Date checkedtime;

    private Short lawstatus;

    private Integer reviewerid;

    private Date reviewtime;

    public Securitylaw(Integer id, String lawname, String version, Integer firstreldepid, Integer copubdepid, Integer enterid, Date entertime, Integer checkerid, Date checkedtime, Short lawstatus, Integer reviewerid, Date reviewtime) {
        this.id = id;
        this.lawname = lawname;
        this.version = version;
        this.firstreldepid = firstreldepid;
        this.copubdepid = copubdepid;
        this.enterid = enterid;
        this.entertime = entertime;
        this.checkerid = checkerid;
        this.checkedtime = checkedtime;
        this.lawstatus = lawstatus;
        this.reviewerid = reviewerid;
        this.reviewtime = reviewtime;
    }

    public Securitylaw() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLawname() {
        return lawname;
    }

    public void setLawname(String lawname) {
        this.lawname = lawname == null ? null : lawname.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Integer getFirstreldepid() {
        return firstreldepid;
    }

    public void setFirstreldepid(Integer firstreldepid) {
        this.firstreldepid = firstreldepid;
    }

    public Integer getCopubdepid() {
        return copubdepid;
    }

    public void setCopubdepid(Integer copubdepid) {
        this.copubdepid = copubdepid;
    }

    public Integer getEnterid() {
        return enterid;
    }

    public void setEnterid(Integer enterid) {
        this.enterid = enterid;
    }

    public Date getEntertime() {
        return entertime;
    }

    public void setEntertime(Date entertime) {
        this.entertime = entertime;
    }

    public Integer getCheckerid() {
        return checkerid;
    }

    public void setCheckerid(Integer checkerid) {
        this.checkerid = checkerid;
    }

    public Date getCheckedtime() {
        return checkedtime;
    }

    public void setCheckedtime(Date checkedtime) {
        this.checkedtime = checkedtime;
    }

    public Short getLawstatus() {
        return lawstatus;
    }

    public void setLawstatus(Short lawstatus) {
        this.lawstatus = lawstatus;
    }

    public Integer getReviewerid() {
        return reviewerid;
    }

    public void setReviewerid(Integer reviewerid) {
        this.reviewerid = reviewerid;
    }

    public Date getReviewtime() {
        return reviewtime;
    }

    public void setReviewtime(Date reviewtime) {
        this.reviewtime = reviewtime;
    }
}