package com.justcs.view;

import java.io.Serializable;

public class WorkedLawView implements Serializable {

    private String workid;

    private String lawid;

    private String optime;

    private String optype;

    private String lawname;

    private String version;

    private String grade;

    private String gradetime;

    private String status;

    public String getWorkid() {
        return workid;
    }

    public void setWorkid(String workid) {
        this.workid = workid;
    }

    public String getLawid() {
        return lawid;
    }

    public void setLawid(String lawid) {
        this.lawid = lawid;
    }

    public String getOptime() {
        return optime;
    }

    public void setOptime(String optime) {
        this.optime = optime;
    }

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype;
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

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGradetime() {
        return gradetime;
    }

    public void setGradetime(String gradetime) {
        this.gradetime = gradetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
