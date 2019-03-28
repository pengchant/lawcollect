<%--
    网页的头部导航
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .m_banner {
        height: 80px;
        background: #3280fc;
        font-size: 17px;
        color: white;
    }

    .logo {
        line-height: 80px;
        font-size: 19px;
        margin-left:40px;
    }

    .navfunc {
        line-height: 90px;
        font-size: 15px;
        margin-right:40px;
    }
    
    .m_a, .m_a:hover {
        text-decoration: none;
        color: #fff;
    }


</style>
<!-- banner -->
<nav class="m_banner">
    <div class="container-fluid">
        <div class="logo pull-left">
            <i class="icon icon-group"></i>信息安全标准与法律收录系统
        </div>
        <div class="pull-right navfunc">
            欢迎<span>${sessionScope.current_user.account}</span>,
            <a href="javascript:void(0);" id="alogout" class="m_a"><i class="icon icon-off"></i>退出登录</a>
        </div>
    </div>
</nav>
