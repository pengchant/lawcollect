<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../public/header_top.jsp" %>
<link rel="stylesheet" href="../../bootstrapvalidator/css/bootstrapValidator.min.css"/>
<title>信息安全标准与法律收录系统-注册</title>
<style>
    body {
        background: #1D212A;
    }

    .valicodeimg {
        width: 80px;
        height: 30px;
        background-size: 100%;
    }
</style>
<%@ include file="../public/header_title.jsp" %>
<div class="container-fluid" style="margin-top:50px;">
    <div class="row text-center">
        <div style="width:500px;margin:0 auto;">
            <div class="panel">
                <div class="panel-heading text-left">
                    <div class="text-gray" style="font-weight: 600;line-height:40px;font-size:20px;">学生注册</div>
                </div>
                <div class="panel-body">
                    <form method="post" id="registform" class="form-horizontal" action="#">

                        <div class="form-group">
                            <label for="stuid" class="col-sm-3">学号</label>
                            <div class="col-md-7 col-sm-9">
                                <input type="text" class="form-control" name="stuid" id="stuid"
                                       placeholder="请输入您的学号(必填)">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="stuname" class="col-sm-3">姓名</label>
                            <div class="col-md-7 col-sm-9">
                                <input type="text" class="form-control" name="stuname" id="stuname"
                                       placeholder="请输入姓名(必填)">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="classno" class="col-sm-3">班级</label>
                            <div class="col-md-7 col-sm-9">
                                <input type="text" class="form-control" name="classno" id="classno"
                                       placeholder="请输入班级(必填)">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="stupwd" class="col-sm-3">输入密码</label>
                            <div class="col-md-7 col-sm-9">
                                <input type="password" class="form-control" name="stupwd" id="stupwd"
                                       placeholder="请输入账号密码(必填)">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="surepwd" class="col-sm-3">确认密码</label>
                            <div class="col-md-7 col-sm-9">
                                <input type="password" class="form-control" name="surepwd" id="surepwd"
                                       placeholder="请再次输入密码(必填)">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="tips" class="col-sm-3">备注</label>
                            <div class="col-md-7 col-sm-9">
                                <textarea class="form-control" id="tips" name="tips"
                                          placeholder="请输入备注(非必填项)"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="valicode" class="col-sm-3">验证码</label>
                            <div class="col-md-4 col-sm-6">
                                <input type="text" class="form-control" id="valicode" name="valicode"
                                       placeholder="验证码(必填)"/>
                            </div>
                            <div class="col-md-4 col-sm-4">
                                <img src="/usr/checkcode" id="valicodeimg"
                                     onclick="this.src=this.src+'?timestamp='+new Date().getTime()"
                                     class="valicodeimg" alt=""/>
                            </div>
                        </div>

                        <div style="margin-top:20px;margin-bottom:20px;">
                            <button
                                    type="button"
                                    id="subregistbtn"
                                    class="btn btn-success">
                                提交注册
                            </button>

                            &nbsp;&nbsp;&nbsp;&nbsp;

                            <button
                                    type="button"
                                    id="resetregist"
                                    class="btn btn-info">
                                重置表单
                            </button>
                        </div>

                        <br>
                        <span onclick="window.location.href='/'"
                              class="text-danger" style="text-decoration: underline;cursor: pointer;"><<返回登录</span>

                    </form>
                </div>
            </div>

        </div>
    </div>

</div>

<div style="color:#fff;bottom: 10px;text-align: center;width:100%;margin-top:10px;height:50px;line-height:50px;">
    <div class="container">
        <div class="row text-center">
            <span>苏ICP备17019332号-2</span>&nbsp;
        </div>
    </div>
</div>
<%@ include file="../public/footer_foot.jsp" %>
<%-- 引入bootstrap表单验证资源 --%>
<script src="../../bootstrapvalidator/js/bootstrapValidator.min.js" type="text/javascript"></script>
<script src="../../static/js/md5.js" type="text/javascript"></script>
<script src="../../static/js/sweetalert.min.js"></script>
<script type="text/javascript">

    $(function () {
        $("#stuid").focus();
        $('#registform').bootstrapValidator({
            message: "请输入有效的字段",
            fields: {
                "stuid": {
                    message: "学号非法",
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
                "stuname": {
                    validators: {
                        notEmpty: {
                            message: '姓名不能为空'
                        },
                        stringLength: {
                            max: 40,
                            message: '姓名过长不能超过40个字'
                        }
                    }
                },
                "classno": {
                    validators: {
                        notEmpty: {
                            message: '班级不能为空'
                        },
                        stringLength: {
                            max: 50,
                            message: '班级过长不能超过50个字'
                        }
                    }
                },
                "stupwd": {
                    validators: {
                        notEmpty: {
                            message: '密码不能为空'
                        },
                        stringLength: {
                            min: 6,
                            max: 20,
                            message: '密码至少6位,最多20位'
                        },
                        different: {
                            field: 'stuid',
                            message: '密码不能和学号一致'
                        }
                    }
                },
                "surepwd": {
                    validators: {
                        notEmpty: {
                            message: '确认密码不能为空'
                        },
                        identical: {
                            field: 'stupwd',
                            message: '密码和确认密码不一致'
                        }
                    }
                },
                "valicode": {
                    validators: {
                        notEmpty: {
                            message: "验证码不能为空"
                        }
                    }
                }
            }
        });

        // 提交注册功能
        $("#subregistbtn").click(function () {
            var bootstrapValidator = $("#registform").data('bootstrapValidator');
            bootstrapValidator.validate();
            if (bootstrapValidator.isValid()) {
                var obj = $("#registform").serializeObject();
                console.log(obj);
                obj['stupwd'] = hex_md5(obj['stupwd']);
                obj['surepwd'] = hex_md5(obj['surepwd']);
                console.log(obj);
                dopost("/usr/doregist", obj).then(function (value) {
                    if (value.msg == "success") {
                        $("#resetregist").click();
                        $("#valicodeimg").attr("src", "/usr/checkcode?timestamp=" + new Date().getTime());
                        swal({
                            text: "恭喜你注册成功!",
                            icon: "success",
                            button: "确定"
                        });
                    } else {
                        $("#valicodeimg").click();
                        swal({
                            text: value.msg,
                            icon: "error",
                            button: "确定"
                        })
                    }
                });
            }
        });

        // 重置表单
        $("#resetregist").click(function () {
            $("#stuid").focus();
            $('#registform').data('bootstrapValidator').resetForm(true);
        });

    });
</script>
<%@ include file="../public/footer_bottom.jsp" %>

