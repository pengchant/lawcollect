package com.justcs.utils;

/**
 * zui-datagrid 的分页数据结构
 */
public class ZUIPager {

    // 当前书对应的页码
    private int page;

    // 总的数据数目
    private long recTotal;

    // 每页数据数目
    private long recPerPage;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getRecTotal() {
        return recTotal;
    }

    public void setRecTotal(long recTotal) {
        this.recTotal = recTotal;
    }

    public long getRecPerPage() {
        return recPerPage;
    }

    public void setRecPerPage(long recPerPage) {
        this.recPerPage = recPerPage;
    }
}
