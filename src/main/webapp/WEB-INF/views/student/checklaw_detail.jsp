<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="../common/func_common/func_header_top.jsp" %>
<link rel="stylesheet" href="../../../editable-select/jquery.editable-select.min.css"/>
<link rel="stylesheet" href="../../../bootstrapvalidator/css/bootstrapValidator.min.css"/>
<title>待校验法律条文详情</title>
<%@include file="../common/func_common/func_header_title.jsp" %>
<div class="panel panel-primary" id="add_laws_div">
    <div class="panel-heading" id="headingTwos">
        <h4 class="panel-title">
            <a href="#collapseTwo" data-toggle="collapse" data-parent="#accordionPanels">待校验的信息安全标准条文</a>
        </h4>
    </div>
    <div id="collapseTwo" class="panel-collapse collapse in">
        <div class="panel-body">
            <form class="form-horizontal" id="valilawfm" method="post" action="/law/validatedLaw">
                <%-- 隐藏域 --%>
                <%-- 法律编号 --%>
                <input type="hidden" id="lawid" name="lawid" value="${lawsadded.lawid}"/>
                <%-- 法律正文内容编号 --%>
                <input type="hidden" id="lawcontentid" name="lawcontentid" value="${lawsadded.lawcontentid}"/>
                <%-- 录入者编号 --%>
                <input type="hidden" id="enterid" name="enterid" value="${lawsadded.enterid}"/>
                <%-- 录入的时间 --%>
                <input type="hidden" id="entertime" name="entertime" value="${lawsadded.entertime}"/>
                <%-- 校验的编号 --%>
                <input type="hidden" id="checkid" name="checkid" value="${lawsadded.checkid}"/>
                <%-- 法律的状态 --%>
                <input type="hidden" id="checkid" name="lawstatus" value="${lawsadded.lawstatus}"/>

                <div class="form-group">
                    <div class="col-sm-offset-1 col-sm-10 text-center">
                        <button type="button" class="btn btn-danger btn-lg btntempaddlaw"><i
                                class="icon icon-save"></i>&nbsp;确认校验
                        </button>&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-success btn-lg btnbackaddlist"><i
                                class="icon icon-list"></i>&nbsp;返回列表
                        </button>&nbsp;&nbsp;&nbsp;&nbsp;
                    </div>
                </div>

                <div class="form-group">
                    <label for="lawname" class="col-sm-2">标准/法律条文名称</label>
                    <div class="col-md-9 col-sm-10">
                        <input type="text" class="form-control" id="lawname"
                               value="${lawsadded.lawname}"
                               name="lawname" placeholder="输入标准/法律条文名称">
                    </div>

                </div>
                <div class="form-group">
                    <label for="version" class="col-sm-2">版本</label>
                    <div class="col-md-9 col-sm-10">
                        <input type="text" class="form-control"
                               value="${lawsadded.version}"
                               id="version" name="version" placeholder="输入版本">
                    </div>
                </div>
                <div class="form-group">
                    <label for="firstreleasedep" class="col-sm-2">第一发布部门</label>
                    <div class="col-sm-3">
                        <select class="form-control meditselect" id="firstreleasedep" name="firstreleasedep">
                            <%-- 动态加载部门 --%>
                            <c:forEach varStatus="status" items="${releasedeps}" var="dep" step="1">
                                <c:choose>
                                    <c:when test="${lawsadded.firstreleasedep==dep.id}">
                                        <option selected="true" value="${dep.depname}">${dep.depname}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${dep.depname}">${dep.depname}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                    <label for="copubdep" class="col-sm-2">共同发布部门</label>
                    <div class="col-sm-3">
                        <select class="form-control meditselect" id="copubdep" name="copubdep">
                            <%-- 动态加载部门 --%>
                            <c:forEach varStatus="status" items="${releasedeps}" var="dep" step="1">
                                <c:choose>
                                    <c:when test="${lawsadded.copubdep==dep.id}">
                                        <option selected="true" value="${dep.depname}">${dep.depname}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${dep.depname}">${dep.depname}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2"></div>
                    <div class="col-sm-10">
                        <span class="text-primary" style="text-decoration: underline;"><em>提示：上述第一部门和共同发布部门，您可以选择下拉框的内容，如果没有相关部门，您可以直接手动录入!</em></span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="keyterms" class="col-sm-2">重点条款</label>
                    <div class="col-md-9 col-sm-10">
                        <textarea class="form-control" id="keyterms"
                                  name="keyterms" rows="6"
                                  placeholder="输入重点条款,例如:
                                  1...
                                  2...
                                  3...">${lawsadded.keyterms}</textarea>
                    </div>
                </div>

                <div class="form-group">
                    <label for="lawattributes" class="col-sm-2">属性</label>
                    <div class="col-md-9 col-sm-10">
                        <c:set value="${ fn:split(lawsadded.lawattributes, ',') }" var="names"/>
                        <select data-placeholder="选择法律属性"
                                id="lawattributes"
                                name="lawattributes"
                                class="chosen-select form-control" tabindex="2" multiple="">

                            <%-- 遍历列表数据 --%>
                            <c:forEach var="item" items="${lawattribute}">
                                <optgroup label="${item.currentName}">
                                    <c:forEach var="element" items="${item.children}">
                                        <%-- 查看是否被选中 --%>
                                        <c:set var="flag" value="${false}"></c:set>
                                        <c:forEach var="selected" items="${names}">
                                            <c:if test="${selected==element.currentName}">
                                                <c:set var="flag" value="${true}"></c:set>
                                            </c:if>
                                        </c:forEach>

                                        <%-- 设置标记 --%>
                                        <c:choose>
                                            <c:when test="${flag == true}">
                                                <option value="${element.currentName}"
                                                        selected="true">${element.currentName}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option value="${element.currentName}">${element.currentName}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </optgroup>
                            </c:forEach>
                        </select>
                        <%--<textarea class="form-control" id="lawattributes" name="lawattributes" rows="6"--%>
                                  <%--placeholder="输入属性,例如:--%>
                                  <%--1...--%>
                                  <%--2...--%>
                                  <%--3...">${lawsadded.lawattributes}</textarea>--%>
                    </div>
                </div>

                <div class="form-group">
                    <label for="upperregulations" class="col-sm-2">上位法规</label>
                    <div class="col-md-9 col-sm-10">
                        <textarea class="form-control" id="upperregulations" name="upperregulations" rows="6"
                                  placeholder="输入上位法规,例如:
                                  1...
                                  2...
                                  3...">${lawsadded.upperregulations}</textarea>
                    </div>
                </div>

                <div class="form-group">
                    <label for="maincontent" class="col-sm-2">正文</label>
                    <div class="col-md-9 col-sm-10">
                        <textarea id="maincontent" name="maincontent" class="form-control" rows="15"
                                  placeholder="输入法律正文,例如:
                                  第一条...
                                  第二条...">${lawsadded.maincontent}</textarea>
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-sm-offset-1 col-sm-10 text-center">
                        <button type="button" class="btn btn-danger btn-lg btntempaddlaw"><i
                                class="icon icon-save"></i>&nbsp;确认校验
                        </button>&nbsp;&nbsp;&nbsp;&nbsp;
                        <button type="button" class="btn btn-success btn-lg btnbackaddlist"><i
                                class="icon icon-list"></i>&nbsp;返回列表
                        </button>&nbsp;&nbsp;&nbsp;&nbsp;
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>

<%@include file="../common/func_common/func_footer_foot.jsp" %>
<link rel="stylesheet" href="../../../ZUI/lib/chosen/chosen.min.css"/>
<script src="../../../ZUI/lib/chosen/chosen.min.js" type="text/javascript"></script>
<script type="text/javascript" src="../../../editable-select/jquery.editable-select.min.js"></script>
<script type="text/javascript" src="../../../bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript">
    $(function () {

        $('select.chosen-select').chosen({
            no_results_text: '没有找到',    // 当检索时没有找到匹配项时显示的提示文本
            disable_search_threshold: 10, // 10 个以下的选择项则不显示检索框
            search_contains: true         // 从任意位置开始检索
        });

        $("#lawname").focus();

        // 添加法律条文（需要验证字段）
        $("button.btntempaddlaw").click(function () {
            var bootstrapValidator = $("#valilawfm").data('bootstrapValidator');
            bootstrapValidator.validate();
            if (bootstrapValidator.isValid()) {
                swal({
                    title: "",
                    text: "校验后的内容将无法修改，系统将提交给管理员审核打分！",
                    icon: "warning",
                    buttons: {
                        cancel: "取消",
                        sure: "确定",
                    }
                }).then(function (value) {
                    if (value) {
                        // 下面发起录入请求
                        dopost("/law/validatedLaw",
                            $("#valilawfm").serializeObject()).then(function (v) {
                            if (v.status == 200) {
                                window.location.href = "/law/stucheck";
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

        // 返回列表
        $("button.btnbackaddlist").click(function () {
            window.location.href = "/law/stucheck";
        });

        $("#firstreleasedep").editableSelect({
            effects: 'slide',
            //可选参数default、fade
            filter: false,
            duration: 200,
        });

        $("#copubdep").editableSelect({
            effects: 'slide',
            //可选参数default、fade
            filter: false,
            duration: 200,
        });

        /**
         * 设置选中
         * @param selector
         * @param value
         */
        function setSelectedOption(selector, value) {
            if (value.length != 0) {
                $(selector + " > option[value='" + value + "']").attr("selected", true);
            }
        }

        // bootstrapvalidator验证表单
        $("#valilawfm").bootstrapValidator({
            fields: {
                "lawname": {
                    validators: {
                        notEmpty: {
                            message: '标准/法律条文名称不能为空'
                        },
                        stringLength: {
                            max: 200,
                            message: '标准/法律条文名称过长'
                        }
                    }
                },
                "version": {
                    validators: {
                        notEmpty: {
                            message: '版本不能为空'
                        },
                        stringLength: {
                            max: 150,
                            message: '版本过长'
                        }
                    }
                },
                "firstreleasedep": {
                    validators: {
                        notEmpty: {
                            message: '第一发布部门不能为空'
                        }
                    }
                },
                "copubdep": {
                    validators: {
                        notEmpty: {
                            message: '共同发布部门不能为空'
                        }
                    }
                },
                "keyterms": {
                    validators: {
                        notEmpty: {
                            message: '重点条款不能为空'
                        }
                    }
                },
                "lawattributes": {
                    validators: {
                        notEmpty: {
                            message: '属性不能为空'
                        }
                    }
                },
                "upperregulations": {
                    validators: {
                        notEmpty: {
                            message: "上位法规不能为空"
                        }
                    }
                },
                "maincontent": {
                    validators: {
                        notEmpty: {
                            message: "正文不能为空"
                        }
                    }
                }
            }
        });

    })
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>