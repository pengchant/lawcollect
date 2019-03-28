<%--
左侧的导航栏
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<nav class="menu" data-ride="menu" style="width: 200px">
    <ul id="treeMenu" class="tree tree-menu" data-ride="tree">

        <c:if test="${sessionScope.current_user.type == '0'}">
            <%-- 学生端 --%>
            <li><a href="/law/stuadd"><i class="icon icon-edit"></i>录入信息安全标准与条文</a></li>
            <li><a href="/law/stucheck"><i class="icon icon-check-board"></i>校验信息安全标准与条文</a></li>
            <li><a href="/usr/personalwork"><i class="icon icon-thumbs-o-up"></i>查看个人工作量</a></li>
            <li><a href="/usr/persoalinfo"><i class="icon icon-sliders"></i>个人信息维护</a></li>
        </c:if>

        <c:if test="${sessionScope.current_user.type == '1'}">
            <%-- 管理员端 --%>
            <li><a href="/usr/checkstu"><i class="icon icon-paint-brush"></i>审核注册用户</a></li>
            <li><a href="/law/passlaw"><i class="icon icon-cube"></i>审核信息安全标准与条文</a></li>
            <li><a href="/law/searchlaw"><i class="icon icon-search"></i>法律条文查询</a></li>
        </c:if>

        <%-- 修改登录密码（公共） --%>
        <li><a href="/usr/modipwd"><i class="icon icon-lock"></i>修改登录密码</a></li>

    </ul>
</nav>
