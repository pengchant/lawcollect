package com.justcs.form;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class ModifyUserPassForm implements Serializable {

    @NotBlank(message = "旧密码不能为空")
    private String oldpass;

    @NotBlank(message = "新密码不能为空")
    private String newPass;

    @NotBlank(message = "确认密码不能为空")
    private String surenewpass;

    public String getOldpass() {
        return oldpass;
    }

    public void setOldpass(String oldpass) {
        this.oldpass = oldpass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getSurenewpass() {
        return surenewpass;
    }

    public void setSurenewpass(String surenewpass) {
        this.surenewpass = surenewpass;
    }
}
