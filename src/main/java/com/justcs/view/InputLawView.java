package com.justcs.view;

import java.io.Serializable;

/**
 * 正在录入的法律条文
 */
public class InputLawView implements Serializable {
    private String lawid;
    private String lawname;
    private String version;
    private String firstreldepid;
    private String firstdepname;
    private String copubdepid;
    private String copdepname;
    private String enterid;
    private String entertime;
    private String lawstatus;

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

    public String getFirstreldepid() {
        return firstreldepid;
    }

    public void setFirstreldepid(String firstreldepid) {
        this.firstreldepid = firstreldepid;
    }

    public String getFirstdepname() {
        return firstdepname;
    }

    public void setFirstdepname(String firstdepname) {
        this.firstdepname = firstdepname;
    }

    public String getCopubdepid() {
        return copubdepid;
    }

    public void setCopubdepid(String copubdepid) {
        this.copubdepid = copubdepid;
    }

    public String getCopdepname() {
        return copdepname;
    }

    public void setCopdepname(String copdepname) {
        this.copdepname = copdepname;
    }

    public String getEnterid() {
        return enterid;
    }

    public void setEnterid(String enterid) {
        this.enterid = enterid;
    }

    public String getEntertime() {
        return entertime;
    }

    public void setEntertime(String entertime) {
        this.entertime = entertime;
    }

    public String getLawstatus() {
        return lawstatus;
    }

    public void setLawstatus(String lawstatus) {
        this.lawstatus = lawstatus;
    }
}
