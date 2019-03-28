<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../public/header_top.jsp" %>
<link rel="stylesheet" href="../../bootstrapvalidator/css/bootstrapValidator.min.css"/>
<title>超管管理</title>
<style>
    body {
        background: #1D212A;
    }

    .regist_link {
        text-decoration: underline;
        cursor: pointer;
    }
</style>
<%@ include file="../public/header_title.jsp" %>
<div class="container-fluid" style="margin-top: 60px;">
    <div class="row text-center">
        <h1 style="color: white;font-size:29px;line-height:86px;">系统维护人员登录</h1>
    </div>

    <div class="row text-center">
        <div style="width:400px;margin:0 auto;">
            <div class="panel">
                <div class="panel-heading text-left">
                    <div class="text-gray" style="font-weight: 600;line-height:40px;font-size:20px;">登入</div>
                </div>
                <div class="panel-body">
                    <form method="post" id="usrlogin" class="form" action="/test/surlogin">
                        <div class="form-group">
                            <div class="input-control has-icon-left has-success">
                                <input id="userid" name="userid" type="text" class="form-control"
                                       placeholder="用户名">
                                <label for="userid" class="input-control-icon-left"><i
                                        class="icon icon-user "></i></label>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="input-control has-icon-left has-success">
                                <input id="userkl" name="userkl" type="password" class="form-control"
                                       placeholder="密码">
                                <label for="userkl" class="input-control-icon-left"><i
                                        class="icon icon-key"></i></label>
                            </div>
                        </div>

                        <div style="margin-top:10px;">
                            <button type="submit" id="btnlogin" class="btn btn-success btn-group-lg">
                                <i class="icon icon-circle-arrow-right"></i>&nbsp;登入
                            </button>&nbsp;&nbsp;&nbsp;&nbsp;

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>

<div style="color:#fff;position: fixed;bottom: 10px;text-align: center;width:100%;">
    <div class="container">
        <div class="row text-center">
            <span>苏ICP备17019332号-2</span>&nbsp;
        </div>
    </div>
</div>
<%@ include file="../public/footer_foot.jsp" %>
<script src="../../bootstrapvalidator/js/bootstrapValidator.min.js" type="text/javascript"></script>
<script src="../../static/js/md5.js" type="text/javascript"></script>
<script src="../../static/js/sweetalert.min.js"></script>
<script>
    $(function () {
        $("#userid").focus();

        // 登录
        $("#btnlogin").click(function (e) {
            e.preventDefault();
            $("#userkl").val(hex_md5($("#userkl").val()));
            $("#usrlogin").submit();
        });

    })
</script>
<%@ include file="../public/footer_bottom.jsp" %>

