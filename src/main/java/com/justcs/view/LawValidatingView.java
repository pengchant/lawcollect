package com.justcs.view;

import java.io.Serializable;

public class LawValidatingView implements Serializable {

    private String lawid;

    private String lawname;

    private String version;

    private String enterid;

    private String entername;

    private String entertime;

    private String checkerid;

    private String checkername;

    private String checkedtime;

    private String lawstatus;

    private String entergrade;

    private String checkergrade;


    // 法律属性
    private String lawattributes;

    public String getLawattributes() {
        return lawattributes;
    }

    public void setLawattributes(String lawattributes) {
        this.lawattributes = lawattributes;
    }



    public String getLawid() {
        return lawid;
    }

    public void setLawid(String lawid) {
        this.lawid = lawid;
    }

    public String getLawname() {
        return lawname;
    }

    public void setLawname(String lawname) {
        this.lawname = lawname;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEnterid() {
        return enterid;
    }

    public void setEnterid(String enterid) {
        this.enterid = enterid;
    }

    public String getEntername() {
        return entername;
    }

    public void setEntername(String entername) {
        this.entername = entername;
    }

    public String getEntertime() {
        return entertime;
    }

    public void setEntertime(String entertime) {
        this.entertime = entertime;
    }

    public String getCheckerid() {
        return checkerid;
    }

    public void setCheckerid(String checkerid) {
        this.checkerid = checkerid;
    }

    public String getCheckername() {
        return checkername;
    }

    public void setCheckername(String checkername) {
        this.checkername = checkername;
    }

    public String getCheckedtime() {
        return checkedtime;
    }

    public void setCheckedtime(String checkedtime) {
        this.checkedtime = checkedtime;
    }

    public String getLawstatus() {
        return lawstatus;
    }

    public void setLawstatus(String lawstatus) {
        this.lawstatus = lawstatus;
    }

    public String getEntergrade() {
        return entergrade;
    }

    public void setEntergrade(String entergrade) {
        this.entergrade = entergrade;
    }

    public String getCheckergrade() {
        return checkergrade;
    }

    public void setCheckergrade(String checkergrade) {
        this.checkergrade = checkergrade;
    }
}
