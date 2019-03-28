package com.justcs.view;

import java.io.Serializable;

public class LawValidatingView_SS implements Serializable {

    private String[] attribute;

    private String inputer;

    private String checker;

    private String lawname;

    public LawValidatingView_SS(String[] attribute, String inputer, String checker, String lawname) {
        this.attribute = attribute;
        this.inputer = inputer;
        this.checker = checker;
        this.lawname = lawname;
    }

    public String[] getAttribute() {
        return attribute;
    }

    public void setAttribute(String[] attribute) {
        this.attribute = attribute;
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

    public String getLawname() {
        return lawname;
    }

    public void setLawname(String lawname) {
        this.lawname = lawname;
    }
}
