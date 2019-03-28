package com.justcs.entity;

import java.io.Serializable;
import java.util.Date;

public class SecuritylawWithBLOBs extends Securitylaw implements Serializable {
    private String keyterms;

    private String lawattributes;

    private String upperregulations;

    public SecuritylawWithBLOBs(Integer id, String lawname, String version, Integer firstreldepid, Integer copubdepid, Integer enterid, Date entertime, Integer checkerid, Date checkedtime, Short lawstatus, Integer reviewerid, Date reviewtime, String keyterms, String lawattributes, String upperregulations) {
        super(id, lawname, version, firstreldepid, copubdepid, enterid, entertime, checkerid, checkedtime, lawstatus, reviewerid, reviewtime);
        this.keyterms = keyterms;
        this.lawattributes = lawattributes;
        this.upperregulations = upperregulations;
    }

    public SecuritylawWithBLOBs() {
        super();
    }

    public String getKeyterms() {
        return keyterms;
    }

    public void setKeyterms(String keyterms) {
        this.keyterms = keyterms == null ? null : keyterms.trim();
    }

    public String getLawattributes() {
        return lawattributes;
    }

    public void setLawattributes(String lawattributes) {
        this.lawattributes = lawattributes == null ? null : lawattributes.trim();
    }

    public String getUpperregulations() {
        return upperregulations;
    }

    public void setUpperregulations(String upperregulations) {
        this.upperregulations = upperregulations == null ? null : upperregulations.trim();
    }
}