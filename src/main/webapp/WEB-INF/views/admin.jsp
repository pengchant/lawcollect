<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>后台管理</title>
    <script src="../../static/js/md5.js" type="text/javascript"></script>
</head>
<body>
<input type="button" value="退出登录" onclick="window.location.href='/test/logout'" style="float:right;"/>
<form action="/test/addadmin" method="post" id="addadminfm">
    账户: <input type="text" name="acc" id="acc"/>
    <br>
    密码: <input type="password" name="kl" id="kl"/>
    <br>
    <br>
    <input id="btnsub" type="submit" value="添加管理员">
</form>
<br>

<script type="text/javascript">
    window.onload = function (ev) {
        var btn = document.getElementById("btnsub");
        var adminfm = document.getElementById("addadminfm");
        var inputkl = document.getElementById("kl");
        var inputacc = document.getElementById("acc");
        btn.onclick = function (e) {
            e.preventDefault();
            if (inputkl.value == "" || inputacc == "") {
                alert("请输入账号密码！");
                return;
            }
            inputkl.value = hex_md5(inputkl.value);
            adminfm.submit();
        }
    }
</script>
<hr>
<table border="2" style="margin:0 auto;">
    <thead>
    <tr>
        <th>编号</th>
        <th>账户编号</th>
        <th>账户</th>
        <th>类别</th>
        <th>密码</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${objlist}" var="obj" varStatus="status">
        <tr>
            <td>${status.index+1}</td>
            <td>${obj.id}</td>
            <td>${obj.account}</td>
            <td>${obj.type}</td>
            <td>${obj.pwd}</td>
            <td>${obj.sts}</td>
            <td>
                <button type="button"
                        onclick="window.location.href='/test/deladmin?accid=${obj.id}'">删除
                </button>
                <c:if test="${obj.sts=='1'}">
                    <button type="button"
                            onclick="window.location.href='/test/unpass?accid=${obj.id}'">禁用
                    </button>
                </c:if>
                <c:if test="${obj.sts=='0'}">
                    <button type="button"
                            onclick="window.location.href='/test/pass?accid=${obj.id}'">启用
                    </button>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
