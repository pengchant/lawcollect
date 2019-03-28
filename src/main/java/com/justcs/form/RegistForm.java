package com.justcs.form;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 注册表单
 */
public class RegistForm implements Serializable {

    @NotEmpty(message = "学号不能为空")
    @Length(min = 1, max = 20, message = "学号最多20位")
    private String stuid;

    @NotEmpty(message = "学生的姓名不能为空")
    @Length(min = 1, max = 40, message = "姓名过长不能超过40个字")
    private String stuname;

    @NotEmpty(message = "班级不能为空")
    @Length(min = 1, max = 50, message = "班级过长不能超过50个字")
    private String classno;

    @NotEmpty(message = "密码不能为空")
    private String stupwd;

    @NotEmpty(message = "确认密码不能为空")
    private String surepwd;

    @Length(max = 100, message = "备注长度不能超过100个字")
    private String tips;

    @NotEmpty(message = "验证码不能为空")
    private String valicode;

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getClassno() {
        return classno;
    }

    public void setClassno(String classno) {
        this.classno = classno;
    }

    public String getStupwd() {
        return stupwd;
    }

    public void setStupwd(String stupwd) {
        this.stupwd = stupwd;
    }

    public String getSurepwd() {
        return surepwd;
    }

    public void setSurepwd(String surepwd) {
        this.surepwd = surepwd;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getValicode() {
        return valicode;
    }

    public void setValicode(String valicode) {
        this.valicode = valicode;
    }
}
