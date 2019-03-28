<%--
    页脚
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
</div>
</div>
<%@include file="../../../public/footer_foot.jsp" %>
<script src="../../static/js/sweetalert.min.js"></script>
<script>
    $(function () {
        $("#alogout").on("click", function () {
            swal({
                text: "你确定要退出登录吗？",
                icon: "warning",
                buttons: {
                    cancel: "取消",
                    sure: "确定",
                }
            }).then(function (value) {
                if (value) {
                    // 确定退出登录
                    window.location.href="/usr/logout";
                }
            });
        });
    });
</script>