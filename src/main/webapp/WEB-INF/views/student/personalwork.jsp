<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="../common/func_common/func_header_top.jsp" %>
<%-- 引入数据表格资源 --%>
<link rel="stylesheet" href="../../../ZUI/lib/datagrid/zui.datagrid.min.css">
<title>个人工作量</title>
<%@include file="../common/func_common/func_header_title.jsp" %>
<div class="panel panel-primary">
    <div class="panel-heading" id="headingOne">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordionPanels" href="#collapseOne">
                个人完成情况
            </a>
        </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
        <div class="panel-body">

            <%-- 功能面板区域 --%>
            <div class="container">
                <div class="row">
                    <div class="col-md-1">
                        <label for="starttime">开始时间</label>
                    </div>
                    <div class="col-md-3">
                        <%-- 开始时间 --%>
                        <div class="input-group date form-date">
                            <input type="text" id="starttime" readonly="readonly" class="form-control"
                                   placeholder="选择一个开始日期">
                            <span class="input-group-addon"><span class="icon-remove"></span></span>
                            <span class="input-group-addon"><span class="icon-th"></span></span>
                        </div>
                    </div>
                    <div class="col-md-1">
                        <label for="endtime">结束时间</label>
                    </div>
                    <div class="col-md-3">
                        <%-- 结束时间 --%>
                        <div class="input-group date form-date">
                            <input type="text" id="endtime" readonly="readonly" class="form-control"
                                   placeholder="选择一个结束日期">
                            <span class="input-group-addon"><span class="icon-remove"></span></span>
                            <span class="input-group-addon"><span class="icon-th"></span></span>
                        </div>
                    </div>

                    <div class="col-md-4">
                        <div class="input-group">
                            <div class="input-control search-box  has-icon-left has-icon-right search-example"
                                 id="searchbox">
                                <input id="msearch" type="search" class="form-control search-input"
                                       placeholder="请输入待搜索的法律名称">
                                <label for="msearch" class="input-control-icon-left search-icon"><i
                                        class="icon icon-search"></i></label>
                            </div>
                            <span class="input-group-btn">
                                <button class="btn btn-primary" id="search_result" type="button">搜索</button>
                            </span>
                        </div>
                    </div>
                </div>
            </div>

            <%-- 待完成的信息安全标准法律条文 --%>
            <div id="myDataGrid" class="datagrid datagrid-striped" style="margin-top:30px;">
                <div class="datagrid-container table-responsive"></div>
                <div class="pager"></div>
            </div>


        </div>
    </div>
</div>


<%@include file="../common/func_common/func_footer_foot.jsp" %>
<script src="../../../ZUI/lib/datagrid/zui.datagrid.min.js"></script>
<link rel="stylesheet" href="../../../ZUI/lib/datetimepicker/datetimepicker.min.css"/>
<script src="../../../ZUI/lib/datetimepicker/datetimepicker.min.js" type="text/javascript"></script>
<script>

    /**
     * 查看法律条文详情
     * @param id
     */
    function startSee(id) {
        window.location.href = "/law/seeworkdetail/" + id;
    }

    $(function () {

        // 仅选择日期
        $(".form-date").datetimepicker(
            {
                language: "zh-CN",
                weekStart: 1,
                todayBtn: 1,
                autoclose: 1,
                todayHighlight: 1,
                startView: 2,
                minView: 2,
                forceParse: 0,
                format: "yyyy-mm-dd"
            });

        // 为了修复zui 数据表格的一个小bug
        var datagridflag = true;
        $("#myDataGrid").datagrid({
            dataSource: {
                cols: [
                    {
                        name: 'workid',
                        label: '编号',
                        className: 'text-center',
                        sort: true,
                        width: 0.05
                    },
                    {
                        name: 'lawname',
                        label: '法律名称',
                        className: 'text-center',
                        sort: true,
                    },
                    {
                        name: 'version',
                        label: '版本',
                        className: 'text-center',
                        sort: true,
                        width: 0.05
                    },
                    {
                        name: 'optime',
                        label: "操作时间",
                        className: 'text-center',
                        sort: true,
                    },
                    {
                        name: 'optype',
                        label: '操作类型',
                        className: 'text-center',
                        sort: true,
                        width: 0.06,
                    },
                    {
                        name: "grade",
                        label: '得分',
                        className: 'text-center',
                        width: 0.06,
                    },
                    {
                        name: "gradetime",
                        label: '打分时间',
                        className: 'text-center',
                    },
                    {
                        name: "status",
                        label: '法律状态',
                        className: 'text-center',
                        width: 0.06,
                    },
                    {
                        label: '操作',
                        html: true,
                        sort: false,
                        className: 'text-center',
                        // 值转换器仅仅影响当前列
                        valueOperator: {
                            getter: function (dataValue, cell, dataGrid) {
                                var id = cell.config.data.lawid;
                                return "<button class='btn btn-warning btn-sm' onclick='startSee("
                                    + id + ")'><i class='icon icon-search'></i>查看详情</button>";

                            }
                        }
                    }
                ],
                remote: function (params) {
                    return {
                        // 远程地址
                        url: '/usr/workedLaw',
                        // 请求类型
                        type: 'POST',
                        // 数据类型
                        dataType: 'json'
                    }
                },
                cache: false, // 禁用缓存
                remoteConverter: function (responseData, textStatus, jqXHR, datagrid) {
                    datagridflag = true;
                    if (responseData.result != "success") {
                        datagridflag = false;
                    }
                    return responseData;
                }
            },
            sortable: true, // 可排序
            checkable: false, // 可选择
            checkByClickRow: false,
            states: {
                pager: { // 分页
                    page: 1,
                    recPerPage: 10
                },
                fixedLeftUntil: 0,    // 固定左侧第一列
                fixedRightFrom: 12,   // 从第12列开始固定到右侧
                fixedTopUntil: 0,     // 固定顶部第一行（标题行）
                fixedBottomFrom: 100, // 从第100行（在此例中是最后一行）开始固定到底部
            },
            showRowIndex: true,
            onRender: function () { // 渲染事件
                if (datagridflag == false) {
                    // 清空表格
                    $(".datagrid-row-cell").remove();
                }
            }
        });

        // 获取数据表格实例
        var myDataGrid = $('#myDataGrid').data('zui.datagrid');

        // 模糊查询
        $("#search_result").click(function () {
            var search = $("#msearch").val();
            var starttime = $("#starttime").val();
            var endtime = $("#endtime").val();
            var searchObj = {};
            searchObj.starttime = starttime;
            searchObj.endtime = endtime;
            searchObj.lawname = search;
            searchObj.timestamp = (new Date()).getTime();
            myDataGrid.search(JSON.stringify(searchObj));
        });

    });
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>