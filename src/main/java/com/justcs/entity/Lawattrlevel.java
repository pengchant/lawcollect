package com.justcs.entity;

public class Lawattrlevel {
    private Integer id;

    private String levelName;

    private Integer level;

    private Integer parentNode;

    public Lawattrlevel(Integer id, String levelName, Integer level, Integer parentNode) {
        this.id = id;
        this.levelName = levelName;
        this.level = level;
        this.parentNode = parentNode;
    }

    public Lawattrlevel() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName == null ? null : levelName.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getParentNode() {
        return parentNode;
    }

    public void setParentNode(Integer parentNode) {
        this.parentNode = parentNode;
    }
}