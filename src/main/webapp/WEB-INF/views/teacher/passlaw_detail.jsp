<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../common/func_common/func_header_top.jsp" %>
<title>审核法律条文详情</title>
<style>
    .law_content {
        width: 100%;
        height: 300px;
        overflow-y: scroll;
        border: solid 1px #ebebeb;
    }

    .law_item {
        width: 100%;
        height: 80px;
        overflow-y: scroll;
        border: dotted 1px #ebebeb;
    }

    .law_list .row {
        margin-top: 10px;
        font-size: 15px;
    }

    .btn:focus,
    .btn:active:focus,
    .btn.active:focus,
    .btn.focus,
    .btn:active.focus,
    .btn.active.focus {
        outline: none;
        border-color: transparent;
        box-shadow: none;
    }

    .plusbtn, .minusplus {
        width: 35px;
        height: 35px;
        -webkit-border-radius: 50%;
        -moz-border-radius: 50%;
        border-radius: 50%;
        padding-left: 10px;

    }
</style>
<%@include file="../common/func_common/func_header_title.jsp" %>

<%-- 判断是否已经通过验证 --%>
<c:if test="${obj.securitylaw.lawstatus=='2'}">
    <div class="panel ">
        <div class="panel-heading">
            法律审核操作区域
        </div>
        <div class="panel-body">
                <%-- 审核法律操作区域 --%>
            <form class="form-horizontal" id="fmchecklaw">

                    <%-- 法律的编号 --%>
                <input type="hidden" value="${obj.securitylaw.id}" name="lawid" id="lawid"/>
                    <%-- 录入人员编号 --%>
                <input type="hidden" value="${obj.securitylaw.enterid}" name="enterid" id="enterid"/>
                    <%-- 校验人员编号 --%>
                <input type="hidden" value="${obj.securitylaw.checkerid}" name="checkerid" id="checkerid"/>


                <div class="form-group">
                    <label for="enterscore" class="col-sm-2">录入人得分</label>
                    <div class="col-md-4 col-sm-5">
                        <input type="text" class="form-control"
                               id="enterscore" name="enterscore" placeholder="录入人得分" value="80"/>
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-circle plusbtn"><i class="icon icon-plus"></i></button>&nbsp;
                        <button type="button" class="btn btn-circle minusplus"><i class="icon icon-minus"></i></button>
                    </div>

                </div>
                <div class="form-group">
                    <label for="checkerscore" class="col-sm-2">校验人得分</label>
                    <div class="col-md-4 col-sm-5">
                        <input type="text" class="form-control"
                               id="checkerscore" name="checkerscore" placeholder="校验人得分" value="80"/>
                    </div>
                    <div class="col-md-2">
                        <button type="button" class="btn btn-circle plusbtn"><i class="icon icon-plus"></i></button>&nbsp;
                        <button type="button" class="btn btn-circle minusplus"><i class="icon icon-minus"></i></button>
                    </div>

                </div>

                <div class="form-group">
                    <div class="col-sm-offset-1 col-sm-10">
                        <button type="button" id="btnpass" class="btn btn-success"><i
                                class="icon icon-check"></i>&nbsp;通过审核
                        </button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" id="btnunpass" class="btn btn-danger"><i
                                class="icon icon-remove"></i>&nbsp;不通过审核
                        </button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-warning backlist"><i
                                class="icon icon-hand-left"></i>&nbsp;返回列表
                        </button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <%--<button type="button" class="btn btn-primary"><i--%>
                            <%--class="icon icon-download"></i>&nbsp;导出pdf文档--%>
                            <%--</button>--%>
                    </div>
                </div>
            </form>
        </div>
    </div>
</c:if>
<c:if test="${obj.securitylaw.lawstatus!='2'}">
    <button type="button" class="btn btn-warning backlist"><i
            class="icon icon-hand-left"></i>&nbsp;返回列表
    </button>
</c:if>

<div class="panel " style="margin-top:20px;">
    <div class="panel-heading">
        法律详情
    </div>
    <div class="panel-body">
        <div class="container law_list">
            <div class="row">
                <div class="col-md-2">
                    <label>法律名称:</label>
                </div>
                <div class="col-md-4">${obj.securitylaw.lawname}</div>
                <div class="col-md-2">
                    <label>版本:</label>
                </div>
                <div class="col-md-4">${obj.securitylaw.version}</div>
            </div>

            <div class="row">
                <div class="col-md-2">
                    <label>第一发布部门:</label>
                </div>
                <div class="col-md-4">${obj.firstdep}</div>
                <div class="col-md-2">
                    <label>共同发布部门:</label>
                </div>
                <div class="col-md-4">${obj.codep}</div>
            </div>

            <div class="row">
                <div class="col-md-2">
                    <label>重点条款:</label>
                </div>
                <div class="col-md-9">
                    <div class="law_item">
                        ${obj.securitylaw.keyterms}
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-2">
                    <label>法律属性:</label>
                </div>
                <div class="col-md-9">
                    <div class="law_item">
                        ${obj.securitylaw.lawattributes}
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-md-2">
                    <label>上位法规:</label>
                </div>
                <div class="col-md-9">
                    <div class="law_item">
                        ${obj.securitylaw.upperregulations}
                    </div>
                </div>
            </div>

            <div class="row" style="margin-top:20px;">
                <div class="col-md-6">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-3">
                                <label>录入人：</label>
                            </div>
                            <div class="col-md-6">
                                ${obj.entername}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <label>录入时间：</label>
                            </div>
                            <div class="col-md-6">
                                <fmt:formatDate value="${obj.securitylaw.entertime}"
                                                pattern="yyyy年MM月dd日 HH:mm:ss"></fmt:formatDate>
                            </div>
                        </div>
                        <div class="row">
                            <div class="law_content text-primary">
                                ${obj.lawentitytemp.lawcontent}
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6" style="border-left: solid 1px rgba(157,236,253,0.23);">
                    <div class="container">
                        <div class="row">
                            <div class="col-md-3">
                                <label>校验人:</label>
                            </div>
                            <div class="col-md-6">
                                ${obj.checkername}
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">
                                <label>校验时间:</label>
                            </div>
                            <div class="col-md-6">
                                <fmt:formatDate value="${obj.securitylaw.checkedtime}"
                                                pattern="yyyy年MM月dd日 HH:mm:ss"></fmt:formatDate>
                            </div>
                        </div>
                        <div class="row">
                            <div class="law_content text-primary">
                                <c:forEach items="${obj.bodyofentryList}" var="item">
                                    ${item.lawcontent}<br><br>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<%@include file="../common/func_common/func_footer_foot.jsp" %>
<script>
    $(function () {
        $(".plusbtn").click(function () {
            var $input = $(this).parents("div.form-group").find("input");
            var v = $input.val();
            if (v < 100) {
                $input.val(++v);
            }
        });
        $(".minusplus").click(function () {
            var $input = $(this).parents("div.form-group").find("input");
            var v = $input.val();
            if (v > 0) {
                $input.val(--v);
            }
        });

        $(".backlist").click(function () {
            window.location.href = "/law/passlaw";
        });

        $("#btnpass").click(function () {
            swal({
                title: "你确定要通过审核吗？",
                text: "系统将会收录该条法律条文并且无法撤销！",
                icon: "warning",
                buttons: {
                    cancel: "取消",
                    sure: "确定",
                }
            }).then(function (value) {
                if (value) {
                    // 下面发起录入请求
                    dopost("/law/checkLaw/1", $("#fmchecklaw").serializeObject()).then(function (v) {
                        if (v.status == 200) {
                            swal("", "审核成功!", "success", {
                                button: false,
                                timer: 1200
                            });
                            setTimeout(function () {
                                window.location.href = "/law/passlaw";
                            }, 1300);
                        } else {
                            swal("", v.msg, "error", {
                                button: false,
                                timer: 1200
                            })
                        }
                    })
                } else {
                }
            });
        });
        $("#btnunpass").click(function () {
            swal({
                title: "你确定不通过通过审核吗？",
                text: "系统将会屏蔽该条法律条文并且无法撤销！",
                icon: "warning",
                buttons: {
                    cancel: "取消",
                    sure: "确定",
                }
            }).then(function (value) {
                if (value) {
                    // 下面发起录入请求
                    dopost("/law/checkLaw/0", $("#fmchecklaw").serializeObject()).then(function (v) {
                        if (v.status == 200) {
                            swal("", "操作成功!", "success", {
                                button: false,
                                timer: 1200
                            })
                            setTimeout(function () {
                                window.location.href = "/law/passlaw";
                            }, 1300);
                        } else {
                            swal("", v.msg, "error", {
                                button: false,
                                timer: 1200
                            })
                        }
                    })
                } else {
                }
            });
        });

    })
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>
