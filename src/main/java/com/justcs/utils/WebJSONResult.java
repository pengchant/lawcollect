package com.justcs.utils;

import com.justcs.entity.Account;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebJSONResult {

    /**
     * 200：表示成功
     */
    public static final String RESP_SUCCESS = "200";

    /**
     * 500：表示错误，错误信息在msg字段中
     */
    public static final String RESP_ERROR = "500";

    /**
     * 501：bean验证错误，不管多少个错误都以map形式返回
     */
    public static final String RESP_VALIDATEERROR = "501";

    /**
     * 502：拦截器拦截到用户token出错
     */
    public static final String RESP_TOKENERROR = "502";

    /**
     * 555：异常抛出信息
     */
    public static final String RESP_EXCEPERROR = "555";

    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;


    public static WebJSONResult build(Integer status, String msg, Object data) {
        return new WebJSONResult(status, msg, data);
    }

    public static WebJSONResult ok(Object data) {
        return new WebJSONResult(data);
    }

    public static WebJSONResult ok() {
        return new WebJSONResult(null);
    }

    public static WebJSONResult errorMsg(String msg) {
        return new WebJSONResult(500, msg, null);
    }

    public static WebJSONResult errorMsgData(String msg, Object data) {
        return new WebJSONResult(501, msg, data);
    }

    public static WebJSONResult errorTokenMsg(String msg) {
        return new WebJSONResult(502, msg, null);
    }

    public static WebJSONResult errorException(String msg) {
        return new WebJSONResult(555, msg, null);
    }

    public WebJSONResult() {

    }

    public WebJSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public WebJSONResult(Object data) {
        this.status = 200;
        this.msg = "success";
        this.data = data;
    }


    public Boolean isOK() {
        return this.status == 200;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将错误转化为map
     *
     * @param result
     * @return
     */
    public static Map convertErrorToMap(BindingResult result) {
        Map<String, Object> map = null;
        if (result != null) {
            map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }
        return map;
    }


    /**
     * 获取当前账户
     *
     * @return
     */
    public static Account getCurrentAccount(HttpServletRequest request) {
        Account account = null;
        try {
            HttpSession session = request.getSession();
            account = (Account) session.getAttribute(Constrants.CURRENT_USER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

}
