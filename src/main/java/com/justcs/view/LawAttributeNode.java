package com.justcs.view;

import java.util.List;

/**
 * 法律属性节点
 */
public class LawAttributeNode {

    private int id;

    private String currentName;

    private List<LawAttributeNode> children;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurrentName() {
        return currentName;
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

    public List<LawAttributeNode> getChildren() {
        return children;
    }

    public void setChildren(List<LawAttributeNode> children) {
        this.children = children;
    }
}
