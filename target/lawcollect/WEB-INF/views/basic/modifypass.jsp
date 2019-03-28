<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="../common/func_common/func_header_top.jsp" %>
<%-- 引入数据表格资源 --%>
<link rel="stylesheet" href="../../../ZUI/lib/datagrid/zui.datagrid.min.css"/>
<link rel="stylesheet" href="../../../bootstrapvalidator/css/bootstrapValidator.min.css"/>
<title>修改密码</title>
<%@include file="../common/func_common/func_header_title.jsp" %>
<div class="panel panel-primary">
    <div class="panel-heading" id="headingOne">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordionPanels" href="#collapseOne">
                修改密码
            </a>
        </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
        <div class="panel-body">

            <%-- 功能面板区域 --%>
            <div class="container">
                <form class="form-horizontal" id="fmpwd">
                    <div class="form-group">
                        <label for="oldpass" class="col-sm-2">原密码</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="password" class="form-control"
                                   id="oldpass" name="oldpass" placeholder="请输入原来的密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="newPass" class="col-sm-2">新密码</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="password" class="form-control"
                                   id="newPass" name="newPass" placeholder="请输入新密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="surenewpass" class="col-sm-2">确认密码</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="password" class="form-control"
                                   id="surenewpass" name="surenewpass" placeholder="请再次输入新密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="button" id="btnmodifypwd" class="btn btn-warning"><i
                                    class="icon icon-lock"></i>修改密码
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


<%@include file="../common/func_common/func_footer_foot.jsp" %>
<script src="../../../ZUI/lib/datagrid/zui.datagrid.min.js"></script>
<script src="../../static/js/md5.js" type="text/javascript"></script>
<script type="text/javascript" src="../../../bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script>
    $(function () {

        $("#oldpass").focus();

        // bootstrapvalidator验证表单
        $("#fmpwd").bootstrapValidator({
            fields: {
                "oldpass": {
                    validators: {
                        notEmpty: {
                            message: '旧密码不能为空'
                        }
                    }
                },
                "newPass": {
                    validators: {
                        notEmpty: {
                            message: '新密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 20,
                            message: '密码至少6位,最多20位'
                        }
                    }
                },
                "surenewpass": {
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        identical: {
                            field: 'newPass',
                            message: '密码和确认密码不一致'
                        },
                        stringLength: {
                            min: 6,
                            max: 20,
                            message: '密码至少6位,最多20位'
                        }
                    }
                }
            }
        });

        // 提交修改表单
        $("#btnmodifypwd").click(function () {
            var bootstrapValidator = $("#fmpwd").data('bootstrapValidator');
            bootstrapValidator.validate();
            if (bootstrapValidator.isValid()) {
                swal({
                    text: "你确定要修改吗？",
                    icon: "warning",
                    buttons: {
                        cancel: "取消",
                        sure: "确定",
                    }
                }).then(function (value) {
                    if (value) {
                        // 下面发起录入请求
                        var reqObj = $("#fmpwd").serializeObject();
                        reqObj["oldpass"] = hex_md5(reqObj["oldpass"]);
                        reqObj["newPass"] = hex_md5(reqObj["newPass"]);
                        reqObj["surenewpass"] = hex_md5(reqObj["surenewpass"]);
                        dopost("/usr/modiStuPwd",
                            reqObj
                        ).then(function (v) {
                            if (v.status == 200) {
                                swal("", "修改用户信息成功!", "success", {
                                    button: false,
                                    timer: 1500
                                });
                                setTimeout(function () {
                                    window.location.href = "/usr/logout";
                                },1500);
                            } else {
                                swal("", v.msg, "error", {
                                    button: false,
                                    timer: 1500
                                })
                            }
                        })
                    } else {
                    }
                });
            }
        });

    });
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>