package com.justcs.utils;

import java.util.List;

/**
 * ZUI-放回的datagrid数据对象
 */
public class JsonResult {

    // 数据请求的结果
    private String result;

    // 远程数据内容
    List<Object> data;

    // 用户页面提示消息
    private String message;

    // 远程数据的分页信息对象
    private ZUIPager pager;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ZUIPager getPager() {
        return pager;
    }

    public void setPager(ZUIPager pager) {
        this.pager = pager;
    }
}
