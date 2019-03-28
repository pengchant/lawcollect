package com.justcs.view;

import java.io.Serializable;

/**
 * 工作法律的筛选实体
 */
public class WorkedLawView_S implements Serializable {

    private String lawname;

    private String starttime;

    private String endtime;

    public WorkedLawView_S(String lawname, String starttime, String endtime) {
        this.lawname = lawname;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public String getLawname() {
        return lawname;
    }

    public void setLawname(String lawname) {
        this.lawname = lawname;
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
}
