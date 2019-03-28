package com.justcs.view;

import java.io.Serializable;

public class LawValidatingView_S implements Serializable {

    private String starttime;

    private String endtime;

    private String lawname;

    private String status;

    public LawValidatingView_S(String starttime, String endtime, String lawname, String status) {
        this.starttime = starttime;
        this.endtime = endtime;
        this.lawname = lawname;
        this.status = status;
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

    public String getLawname() {
        return lawname;
    }

    public void setLawname(String lawname) {
        this.lawname = lawname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
