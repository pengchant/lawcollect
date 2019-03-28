package com.justcs.view;

import java.io.Serializable;

/**
 * 待校验的视图
 */
public class ValidateLawView implements Serializable {

    private String lawid;

    private String lawname;

    private String version;

    private String firstreldepid;

    private String firstreldep;

    private String copubdepid;

    private String copubdep;

    private String enterid;

    private String entername;

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

    public String getFirstreldep() {
        return firstreldep;
    }

    public void setFirstreldep(String firstreldep) {
        this.firstreldep = firstreldep;
    }

    public String getCopubdepid() {
        return copubdepid;
    }

    public void setCopubdepid(String copubdepid) {
        this.copubdepid = copubdepid;
    }

    public String getCopubdep() {
        return copubdep;
    }

    public void setCopubdep(String copubdep) {
        this.copubdep = copubdep;
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

    public String getLawstatus() {
        return lawstatus;
    }

    public void setLawstatus(String lawstatus) {
        this.lawstatus = lawstatus;
    }
}
