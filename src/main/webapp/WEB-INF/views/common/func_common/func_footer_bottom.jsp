<%--
  页面底部
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    $(function () {

        // 修改左侧页面导航的样式
        var XP_URL = window.location.href;
        $("#treeMenu li").each(function (i, v) {
            var cur_a = $(v).find("a").attr("href");
            if(XP_URL.lastIndexOf(cur_a)!=-1){
                $(v).addClass("active").siblings().removeClass("active");
                return;
            }
        });

    });
</script>
<%@include file="../../../public/footer_bottom.jsp" %>
