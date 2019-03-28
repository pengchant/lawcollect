package com.justcs.view;

/**
 * 检索法律的实体
 */
public class SearchLawView {

    private String id;

    private String lawname;

    private String version;

    private String lawattributes;

    private String inputer;

    private String checker;

    private String reviewtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLawname() {
        return lawname;
    }

    public void setLawname(String lawname) {
        this.lawname = lawname;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLawattributes() {
        return lawattributes;
    }

    public void setLawattributes(String lawattributes) {
        this.lawattributes = lawattributes;
    }

    public String getInputer() {
        return inputer;
    }

    public void setInputer(String inputer) {
        this.inputer = inputer;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public String getReviewtime() {
        return reviewtime;
    }

    public void setReviewtime(String reviewtime) {
        this.reviewtime = reviewtime;
    }
}
