<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../public/header_top.jsp" %>
<link rel="stylesheet" href="../../bootstrapvalidator/css/bootstrapValidator.min.css"/>
<title>信息安全标准与法律收录系统</title>
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
        <h1 style="color: white;font-size:29px;line-height:86px;">信息安全标准与法律收录系统</h1>
    </div>

    <div class="row text-center">
        <div style="width:400px;margin:0 auto;">
            <div class="panel">
                <div class="panel-heading text-left">
                    <div class="text-gray" style="font-weight: 600;line-height:40px;font-size:20px;">登入</div>
                </div>
                <div class="panel-body">
                    <form method="post" id="usrlogin" class="form" action="/usr/login">
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

                        <div style="text-align: left;color: red;" class="regist_link">
                            <span onclick="window.location.href='/usr/regist'" class="text-danger">还没有账号?去注册>></span>
                        </div>

                        <div style="margin-top:10px;">
                            <button type="submit" id="btnlogin" class="btn btn-success btn-group-lg">
                                <i class="icon icon-circle-arrow-right"></i>&nbsp;登入
                            </button>&nbsp;&nbsp;&nbsp;&nbsp;
                            <button type="button" id="reset" class="btn btn-danger btn-group-lg">
                                <i class="icon icon-undo"></i>&nbsp;重置
                            </button>
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
        $('#usrlogin').bootstrapValidator({
            message: "请输入有效的字段",
            fields: {
                "userid": {
                    validators: {
                        notEmpty: {
                            message: '学号不能为空'
                        },
                        stringLength: {
                            max: 20,
                            message: '学号最多20位'
                        }
                    }
                },
                "userkl": {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        }
                    }
                }
            }
        });

        // 登录
        $("#btnlogin").click(function (e) {
            e.preventDefault();
            // 验证字段
            var bootstrapValidator = $("#usrlogin").data('bootstrapValidator');
            bootstrapValidator.validate();
            if (bootstrapValidator.isValid()) {
                var formobj = $("#usrlogin").serializeObject();
                formobj["userkl"] = hex_md5(formobj['userkl']);
                dopost("/usr/login", formobj).then(function (value) {
                    if (value.status == 200) {
                        window.location.href=value.data;
                    } else {
                        swal({
                            text: value.msg,
                            icon: "error",
                            button: "确定"
                        });
                        $("#reset").click();
                    }
                });
            }
        });
        // 重置表单
        $("#reset").click(function () {
            $("#userid").focus();
            $('#usrlogin').data('bootstrapValidator').resetForm(true);
        });
    })
</script>
<%@ include file="../public/footer_bottom.jsp" %>

