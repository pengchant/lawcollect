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
    }

    .law_list .row {
        margin-top: 10px;
        font-size: 15px;
    }
</style>
<%@include file="../common/func_common/func_header_title.jsp" %>

<button type="button" id="backbuttton" class="btn btn-warning backlist"><i
        class="icon icon-hand-left"></i>&nbsp;返回列表
</button>

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
                                <c:choose>
                                    <c:when test="${obj.securitylaw.checkedtime!=null}">
                                        <fmt:formatDate value="${obj.securitylaw.checkedtime}"
                                                        pattern="yyyy年MM月dd日 HH:mm:ss"></fmt:formatDate>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-danger">暂无时间</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <div class="row">
                            <div class="law_content text-primary">
                                <c:choose>
                                    <c:when test="${obj.bodyofentryList!=null&&obj.bodyofentryList.size()>0}">
                                        <c:forEach items="${obj.bodyofentryList}" var="item">
                                            ${item.lawcontent}<br><br>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="text-danger">暂未校验</span>
                                    </c:otherwise>
                                </c:choose>
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
        $("#backbuttton").click(function () {
            window.location.href = "/usr/personalwork";
        });
    })
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>
