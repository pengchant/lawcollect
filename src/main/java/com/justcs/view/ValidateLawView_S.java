package com.justcs.view;

import java.io.Serializable;

public class ValidateLawView_S implements Serializable {

    private String lawname;

    public ValidateLawView_S(String lawname) {
        this.lawname = lawname;
    }

    public String getLawname() {
        return lawname;
    }

    public void setLawname(String lawname) {
        this.lawname = lawname;
    }
}
