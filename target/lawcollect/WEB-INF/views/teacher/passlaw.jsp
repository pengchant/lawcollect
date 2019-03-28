<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="../common/func_common/func_header_top.jsp" %>
<%-- 引入数据表格资源 --%>
<link rel="stylesheet" href="../../../ZUI/lib/datagrid/zui.datagrid.min.css">
<title>审核法律条文</title>
<%@include file="../common/func_common/func_header_title.jsp" %>
<div class="panel panel-primary">
    <div class="panel-heading" id="headingOne">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordionPanels" href="#collapseOne">
                待审核的信息安全法律/标准
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

                    </div>
                </div>

                <div class="row" style="margin-top:20px;">
                    <div class="col-md-1">
                        <label for="status">选择状态</label>
                    </div>
                    <div class="col-md-3">
                        <select name="status" id="status" class="form-control">
                            <option value="2">待审核</option>
                            <option value="3">审核通过</option>
                            <option value="-1">审核不通过</option>
                        </select>
                    </div>
                    <div class="col-md-1">
                        <label for="msearch">关键字</label>
                    </div>
                    <div class="col-md-3">
                        <input id="msearch" type="search" class="form-control"
                               placeholder="请输入待搜索的法律名称"/>
                    </div>
                    <div class="col-md-4">
                        <button class="btn btn-warning pull-right" id="search_result" type="button"><i
                                class="icon icon-search"></i>搜索
                        </button>
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
<script src="../../../ZUI/lib/datagrid/zui.datagrid.js"></script>
<link rel="stylesheet" href="../../../ZUI/lib/datetimepicker/datetimepicker.min.css"/>
<script src="../../../ZUI/lib/datetimepicker/datetimepicker.min.js" type="text/javascript"></script>
<script>

    /**
     * 查看法律详情
     * @param id
     */
    function startvali(id) {
        window.location.href = "/law/lawDetail/" + id;
    }

    function seeDetail(id) {
        window.location.href = "/law/lawDetail/" + id + "?optype=1";
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

        var datagridflag = true;
        $("#myDataGrid").datagrid({
            dataSource: {
                cols: [
                    {
                        name: 'lawname',
                        label: '法律名称',
                        className: 'text-center',
                        sort: true,
                        width: 0.1
                    },
                    {
                        name: 'version',
                        label: '版本',
                        className: 'text-center',
                        sort: true,
                        width: 0.05
                    },
                    {
                        name: 'entername',
                        label: "录入人",
                        className: 'text-center',
                        sort: true,
                        width: 0.05
                    },
                    {
                        name: "entergrade",
                        label: '得分',
                        html: true,
                        width: 0.05,
                        className: 'text-center',
                        valueOperator: {
                            getter: function (dataValue, cell, dataGrid) {
                                var grade = cell.config.data.entergrade;
                                if (grade == undefined) {
                                    return "<span class='text-danger'>暂无</span>";
                                }
                                return "<span class='text-danger'>" + grade + "</span>"
                            }
                        }
                    },
                    {
                        name: 'checkername',
                        label: "校验人",
                        width: 0.05,
                        className: 'text-center',
                        sort: true,
                    },
                    {
                        name: "checkergrade",
                        label: '得分',
                        html: true,
                        width: 0.05,
                        className: 'text-center',
                        valueOperator: {
                            getter: function (dataValue, cell, dataGrid) {
                                var grade = cell.config.data.checkergrade;
                                if (grade == undefined) {
                                    return "<span class='text-danger'>暂无</span>";
                                }
                                return "<span class='text-danger'>" + grade + "</span>"

                            }
                        }
                    },
                    {
                        name: "lawstatus",
                        label: '法律状态',
                        width: 0.05,
                        className: 'text-center',
                    },
                    {
                        label: '操作',
                        html: true,
                        sort: false,
                        className: 'text-center',
                        // 值转换器仅仅影响当前列
                        valueOperator: {
                            getter: function (dataValue, cell, dataGrid) {

                                var status = $("#status").val();
                                var id = cell.config.data.lawid;
                                if (status == "2") {
                                    return "<button class='btn btn-danger btn-sm' onclick='startvali(" + id + ")'><i class='icon icon-edit'></i>审核</button>";
                                } else {
                                    return "<button class='btn btn-success btn-sm' onclick='seeDetail("
                                        + id + ")'><i class='icon icon-search'></i>查看</button>";
                                }

                            }
                        }
                    }
                ],
                remote: function (params, datagrid) {
                    return {
                        // 远程地址
                        url: '/law/valiLawsEntrys',
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
            checkByClickRow: false, // 是否通过点击行选择
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
            showRowIndex: true, // 是否显示表格的编号
            onRender: function () { // 渲染事件
                if (datagridflag == false) {
                    // 清空表格
                    $(".datagrid-row-cell").remove();
                }
            }
        });


        // 模糊查询
        $("#search_result").click(function () {
            // 获取数据表格实例
            var myDataGrid = $('#myDataGrid').data('zui.datagrid');

            var search = $("#msearch").val();
            var starttime = $("#starttime").val();
            var endtime = $("#endtime").val();
            var lawstatus = $("#status").val();
            var searchObj = {};
            searchObj.starttime = starttime;
            searchObj.endtime = endtime;
            searchObj.lawname = search;
            searchObj.status = lawstatus;
            searchObj.timestamp = (new Date()).getTime();
            myDataGrid.search(JSON.stringify(searchObj));
        });
    });
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>