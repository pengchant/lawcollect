package com.justcs.entity;

import java.io.Serializable;

public class Releasedep implements Serializable {
    private Integer id;

    private String depname;

    private String depno;

    public Releasedep(Integer id, String depname, String depno) {
        this.id = id;
        this.depname = depname;
        this.depno = depno;
    }

    public Releasedep() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepname() {
        return depname;
    }

    public void setDepname(String depname) {
        this.depname = depname == null ? null : depname.trim();
    }

    public String getDepno() {
        return depno;
    }

    public void setDepno(String depno) {
        this.depno = depno == null ? null : depno.trim();
    }
}