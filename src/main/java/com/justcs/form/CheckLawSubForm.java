package com.justcs.form;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class CheckLawSubForm implements Serializable {

    @NotBlank(message = "法律编号不能为空")
    private String lawid;

    @NotBlank(message = "输入账号不能为空")
    private String enterid;

    @NotBlank(message = "校验编号不能为空")
    private String checkerid;

    @NotBlank(message = "录入人的得分不能为空")
    private String enterscore;

    @NotBlank(message = "校验人的得分不能为空")
    private String checkerscore;

    public String getEnterscore() {
        return enterscore;
    }

    public void setEnterscore(String enterscore) {
        this.enterscore = enterscore;
    }

    public String getCheckerscore() {
        return checkerscore;
    }

    public void setCheckerscore(String checkerscore) {
        this.checkerscore = checkerscore;
    }

    public String getLawid() {
        return lawid;
    }

    public void setLawid(String lawid) {
        this.lawid = lawid;
    }

    public String getEnterid() {
        return enterid;
    }

    public void setEnterid(String enterid) {
        this.enterid = enterid;
    }

    public String getCheckerid() {
        return checkerid;
    }

    public void setCheckerid(String checkerid) {
        this.checkerid = checkerid;
    }
}
