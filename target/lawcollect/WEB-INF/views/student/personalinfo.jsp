<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="../common/func_common/func_header_top.jsp" %>
<%-- 引入数据表格资源 --%>
<link rel="stylesheet" href="../../../ZUI/lib/datagrid/zui.datagrid.min.css">
<link rel="stylesheet" href="../../../bootstrapvalidator/css/bootstrapValidator.min.css"/>
<title>个人信息</title>
<%@include file="../common/func_common/func_header_title.jsp" %>
<div class="panel panel-primary">
    <div class="panel-heading" id="headingOne">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordionPanels" href="#collapseOne">
                个人信息维护
            </a>
        </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
        <div class="panel-body">

            <%-- 功能面板区域 --%>
            <div class="container">

                <form class="form-horizontal form" id="usrform">
                    <%-- 隐藏域 --%>
                    <input type="hidden" name="accountid" value="${curuser.accountid}"/>
                    <input type="hidden" name="stuid" value="${curuser.stuid}"/>

                    <div class="form-group">
                        <label for="stunum" class="col-sm-2">学号</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="text" class="form-control" disabled id="stunum" name="stunum"
                                   value="${curuser.stunum}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="stuname" class="col-sm-2">姓名</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="text" value="${curuser.stuname}" class="form-control"
                                   id="stuname" name="stuname"
                                   placeholder="请输入姓名">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="classno" class="col-sm-2">班级</label>
                        <div class="col-md-6 col-sm-10">
                            <input type="text" class="form-control"
                                   id="classno" name="classno"
                                   value="${curuser.classno}"
                                   placeholder="请输入班级"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="tips" class="col-sm-2">备注</label>
                        <div class="col-md-6 col-sm-10">
                            <textarea name="tips" class="form-control" id="tips" cols="60"
                                      rows="4">${curuser.tips}</textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10 ">
                            <button id="btnsuremodi" type="button" class="btn btn-success"><i
                                    class="icon icon-edit"></i>修改
                            </button>&nbsp;&nbsp;&nbsp;
                        </div>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>


<%@include file="../common/func_common/func_footer_foot.jsp" %>
<script src="../../../ZUI/lib/datagrid/zui.datagrid.min.js"></script>
<script type="text/javascript" src="../../../bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script>
    $(function () {
        // bootstrapvalidator验证表单
        $("#usrform").bootstrapValidator({
            fields: {
                "stuname": {
                    validators: {
                        notEmpty: {
                            message: '学生的姓名不能为空'
                        }
                    }
                },
                "classno": {
                    validators: {
                        notEmpty: {
                            message: '班级不能为空'
                        }
                    }
                }
            }
        });

        $("#btnsuremodi").click(function () {
            var bootstrapValidator = $("#usrform").data('bootstrapValidator');
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
                        dopost("/usr/modifyPersonal",
                            $("#usrform").serializeObject()).then(function (v) {
                            if (v.status == 200) {
                                swal("", "修改用户信息成功!", "success", {
                                    button: false,
                                    timer: 1500
                                })
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