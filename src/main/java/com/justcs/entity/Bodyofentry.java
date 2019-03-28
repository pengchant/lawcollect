package com.justcs.entity;

import java.io.Serializable;

public class Bodyofentry implements Serializable {
    private Integer id;

    private Integer lawid;

    private Integer number;

    private String lawcontent;

    public Bodyofentry(Integer id, Integer lawid, Integer number, String lawcontent) {
        this.id = id;
        this.lawid = lawid;
        this.number = number;
        this.lawcontent = lawcontent;
    }

    public Bodyofentry() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLawid() {
        return lawid;
    }

    public void setLawid(Integer lawid) {
        this.lawid = lawid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getLawcontent() {
        return lawcontent;
    }

    public void setLawcontent(String lawcontent) {
        this.lawcontent = lawcontent == null ? null : lawcontent.trim();
    }
}