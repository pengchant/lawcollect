package com.justcs.form;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * 登录表单
 */
public class LoginForm implements Serializable {

    @NotEmpty(message = "用户账号不能为空")
    private String userid;

    @NotEmpty(message = "用户密码不能为空")
    private String userkl;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserkl() {
        return userkl;
    }

    public void setUserkl(String userkl) {
        this.userkl = userkl;
    }
}
