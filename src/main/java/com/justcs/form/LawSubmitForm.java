package com.justcs.form;

import java.io.Serializable;

/**
 * 法律提交表单
 */
public class LawSubmitForm implements Serializable {

    /**
     * 法律名称
     */
    private String lawname;

    /**
     * 版本
     */
    private String version;

    /**
     * 第一发布部门
     */
    private String firstreleasedep;

    /**
     * 共同发布部门
     */
    private String copubdep;

    /**
     * 重点条款
     */
    private String keyterms;

    /**
     * 属性
     */
    private String lawattributes;

    /**
     * 上位法规
     */
    private String upperregulations;

    /**
     * 正文
     */
    private String maincontent;



    /**
     * 法律编号
     */
    private int lawid;

    /**
     * 法律内容编号
     */
    private int lawcontentid;

    /**
     * 录入者编号
     */
    private String enterid;

    /**
     * 录入时间
     */
    private String entertime;

    /**
     * 校验的编号
     */
    private String checkid;

    /**
     * 法律的状态
     */
    private String lawstatus;



    public String getEnterid() {
        return enterid;
    }

    public void setEnterid(String enterid) {
        this.enterid = enterid;
    }

    public String getCheckid() {
        return checkid;
    }

    public void setCheckid(String checkid) {
        this.checkid = checkid;
    }

    public String getLawstatus() {
        return lawstatus;
    }

    public void setLawstatus(String lawstatus) {
        this.lawstatus = lawstatus;
    }

    public String getEntertime() {
        return entertime;
    }

    public void setEntertime(String entertime) {
        this.entertime = entertime;
    }

    public int getLawid() {
        return lawid;
    }

    public void setLawid(int lawid) {
        this.lawid = lawid;
    }

    public int getLawcontentid() {
        return lawcontentid;
    }

    public void setLawcontentid(int lawcontentid) {
        this.lawcontentid = lawcontentid;
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

    public String getFirstreleasedep() {
        return firstreleasedep;
    }

    public void setFirstreleasedep(String firstreleasedep) {
        this.firstreleasedep = firstreleasedep;
    }

    public String getCopubdep() {
        return copubdep;
    }

    public void setCopubdep(String copubdep) {
        this.copubdep = copubdep;
    }

    public String getKeyterms() {
        return keyterms;
    }

    public void setKeyterms(String keyterms) {
        this.keyterms = keyterms;
    }

    public String getLawattributes() {
        return lawattributes;
    }

    public void setLawattributes(String lawattributes) {
        this.lawattributes = lawattributes;
    }

    public String getUpperregulations() {
        return upperregulations;
    }

    public void setUpperregulations(String upperregulations) {
        this.upperregulations = upperregulations;
    }

    public String getMaincontent() {
        return maincontent;
    }

    public void setMaincontent(String maincontent) {
        this.maincontent = maincontent;
    }
}
