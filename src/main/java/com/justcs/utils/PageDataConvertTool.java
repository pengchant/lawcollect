package com.justcs.utils;

import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 将pageInfo->jsonresult
 */
public class PageDataConvertTool {

    /**
     * 分装返回的结果工具(pageInfo->jsonresult)
     *
     * @param data
     * @param result
     * @param message
     * @return
     */
    public static JsonResult convert(PageInfo data, String result, String message) {
        JsonResult mresult = new JsonResult();
        mresult.setResult(result); // 设置result
        mresult.setMessage(message); // 设置提示消息
        try {
            if (data != null) {
                // 设置data
                mresult.setData((List<Object>) data.getList()); // 设置数据
                // pager
                ZUIPager pager = new ZUIPager();
                pager.setPage(data.getPageNum());// 当前页号
                pager.setRecTotal(data.getTotal()); // 总的记录数
                pager.setRecPerPage(data.getPageSize()); // 每页大小
                mresult.setPager(pager);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mresult;
    }
}
