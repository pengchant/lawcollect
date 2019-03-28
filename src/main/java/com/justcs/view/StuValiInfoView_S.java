package com.justcs.view;

import java.io.Serializable;

/**
 * 条件查询实体
 */
public class StuValiInfoView_S implements Serializable {

    private String starttime;

    private String endtime;

    private String status;

    private String stuname;

    public StuValiInfoView_S() {
    }

    public StuValiInfoView_S(String starttime, String endtime, String status, String stuname) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.status = status;
        this.stuname = stuname;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }
}
