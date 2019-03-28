<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="base" value="${pageContext.request.contextPath}"/>
<!-- jQuery -->
<script src="${base}/jquery-1.11.1/jquery.min.js" type="text/javascript"></script>
<!-- ZUI -->
<script src="${base}/ZUI/js/zui.min.js" type="text/javascript"></script>
<script type="text/javascript">
    // 序列化对象
    $.fn.serializeObject = function () {
        var o = {};
        // 将表单转为数组[{},{}...]
        var a = this.serializeArray();
        $.each(a, function () {
            // 如果对象中已有这个对象
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else { // 如果o中没有这个对象
                o[this.name] = this.value || '';
            }
        });
        return o;
    };

    // 执行ajax的post请求
    window.dopost = function (url, jsondata) {
        return new Promise(function (resolve, reject) {
            $.ajax({
                url: url,
                contentType: "application/json;charset=utf-8",
                dataType: "json",
                type: "post",
                data: JSON.stringify(jsondata),
                success: function (result) {
                    resolve(result);
                },
                error: function (result) {
                    console.log("->dopost异常...")
                }
            });
        });
    }
</script>