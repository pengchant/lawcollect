package com.justcs.view;

import com.justcs.entity.Bodyofentry;
import com.justcs.entity.Lawentitytemp;
import com.justcs.entity.SecuritylawWithBLOBs;

import java.io.Serializable;
import java.util.List;

/**
 * 已经录入的法律条目验证视图
 */
public class CheckLawEntryView implements Serializable {

    /**
     * 法律正文
     */
    private SecuritylawWithBLOBs securitylaw;

    /**
     * 法律正文暂存表
     */
    private Lawentitytemp lawentitytemp;

    /**
     * 校验通过后法律正文表
     */
    private List<Bodyofentry> bodyofentryList;

    /**
     * 录入人
     */
    private String entername;

    /**
     * 校验人
     */
    private String checkername;

    /**
     * 第一发布部门
     */
    private String firstdep;


    /**
     * 共同发布部门
     */
    private String codep;

    // 展示类型
    private String optype;

    public String getOptype() {
        return optype;
    }

    public void setOptype(String optype) {
        this.optype = optype;
    }

    public SecuritylawWithBLOBs getSecuritylaw() {
        return securitylaw;
    }

    public void setSecuritylaw(SecuritylawWithBLOBs securitylaw) {
        this.securitylaw = securitylaw;
    }

    public Lawentitytemp getLawentitytemp() {
        return lawentitytemp;
    }

    public void setLawentitytemp(Lawentitytemp lawentitytemp) {
        this.lawentitytemp = lawentitytemp;
    }

    public List<Bodyofentry> getBodyofentryList() {
        return bodyofentryList;
    }

    public void setBodyofentryList(List<Bodyofentry> bodyofentryList) {
        this.bodyofentryList = bodyofentryList;
    }

    public String getEntername() {
        return entername;
    }

    public void setEntername(String entername) {
        this.entername = entername;
    }

    public String getCheckername() {
        return checkername;
    }

    public void setCheckername(String checkername) {
        this.checkername = checkername;
    }

    public String getFirstdep() {
        return firstdep;
    }

    public void setFirstdep(String firstdep) {
        this.firstdep = firstdep;
    }

    public String getCodep() {
        return codep;
    }

    public void setCodep(String codep) {
        this.codep = codep;
    }


}
