<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="../common/func_common/func_header_top.jsp" %>
<%-- 引入数据表格资源 --%>
<link rel="stylesheet" href="../../../ZUI/lib/datagrid/zui.datagrid.min.css">
<title>添加法律条文</title>
<%@include file="../common/func_common/func_header_title.jsp" %>
<div class="panel panel-primary">
    <div class="panel-heading" id="headingOne">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordionPanels" href="#collapseOne">
                正在录入的信息安全标准条文
            </a>
        </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
        <div class="panel-body">

            <%-- 功能面板区域 --%>
            <div class="container">
                <div class="row">
                    <div class="col-md-5">
                        <div class="input-group">
                            <div class="input-control search-box  has-icon-left has-icon-right search-example"
                                 id="searchbox">
                                <input id="msearch" type="search" class="form-control search-input"
                                       placeholder="搜索">
                                <label for="msearch" class="input-control-icon-left search-icon"><i
                                        class="icon icon-search"></i></label>
                            </div>
                            <span class="input-group-btn">
                                <button class="btn btn-primary" id="search_result" type="button">搜索</button>
                            </span>
                        </div>
                    </div>
                    <div class="col-md-7">
                        <button class="btn btn-warning pull-right" id="getselected"><i class="icon icon-trash"></i>删除选中
                        </button>
                        <button class="btn btn-success pull-right" id="addlaw" style="margin-right:10px;"><i
                                class="icon icon-plus"></i>新增法律/标准
                        </button>
                    </div>
                </div>
            </div>

            <%-- 待完成的信息安全标准法律条文 --%>
            <div id="myDataGrid" class="datagrid datagrid-striped" style="margin-top:20px;">
                <div class="datagrid-container table-responsive"></div>
                <div class="pager"></div>
            </div>


        </div>
    </div>
</div>


<%@include file="../common/func_common/func_footer_foot.jsp" %>
<script src="../../../ZUI/lib/datagrid/zui.datagrid.min.js"></script>
<script>

    // 删除法律条文
    function delLaw(id, lawname) {
        swal({
            title: "你确定要删除" + lawname + "？",
            text: "删除后该法律的内容将会消失，并不能够找回！",
            icon: "warning",
            buttons: {
                cancel: "取消",
                sure: "确定",
            }
        }).then(function (value) {
            if (value) {
                // 下面发起录入请求
                dopost("/law/deleteLaw?lawid=" + id, null).then(function (v) {
                    if (v.status == 200) {
                        swal("", "删除成功!", "success", {
                            button: false,
                            timer: 1000
                        })
                        $("#search_result").click();
                    } else {
                        swal("", v.msg, "error", {
                            button: false,
                            timer: 1000
                        })
                    }
                })
            } else {
            }
        });
    }

    // 查看法律条文
    function watchLaw(id) {
        window.location.href = "/law/stuaddetail?lawid=" + id;
    }

    $(function () {

        var datagridflag = true;
        $("#myDataGrid").datagrid({
            dataSource: {
                cols: [
                    {
                        name: 'lawid',
                        label: '编号',
                        sort: true,
                        width: 0.05
                    },
                    {
                        name: 'lawname',
                        label: '法律名称',
                        sort: true,
                    },
                    {
                        name: 'version',
                        label: '版本',
                        sort: true,
                        width: 0.08
                    },
                    {
                        name: 'firstdepname',
                        label: "第一发布部门",
                        sort: true,
                    },
                    {
                        name: 'copdepname',
                        label: '共同发布部门',
                        sort: true,
                    },
                    {
                        name: 'entertime',
                        label: "最后修改时间",
                        valueType: 'date',
                        width: 0.07
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
                                var lawname = cell.config.data.lawname;
                                return "<button class='btn btn-danger btn-sm' onclick='delLaw(" + id + ", \"" + lawname + "\")'><i class='icon icon-trash'></i>删除</button>&nbsp;&nbsp;" +
                                    "<button class='btn btn-success btn-sm' onclick='watchLaw(" + id + ")'><i class='icon icon-search'></i>查看</button>";
                            }
                        }
                    }
                ],
                remote: function (params) {
                    return {
                        // 远程地址
                        url: '/law/stuinputing',
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
            checkable: true, // 可选择
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
            valueOperator: {
                // date 值转换器会影响所以 valueType 为 `date` 的列
                date: {
                    getter: function (dataValue, cell, dataGrid) {
                        var curdate = new Date(dataValue);
                        return curdate.getFullYear() + "-" + (curdate.getMonth() + 1) + "-" + curdate.getDate()
                    }
                }
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
        // 获取选中
        $("#getselected").click(function () {
            // 获取当前已选中的行数据项
            var selectedItems = myDataGrid.getCheckItems();
            var dataArray = new Array();
            for (var i = 0; i < selectedItems.length && selectedItems[i] != null; i++) {
                dataArray.push(selectedItems[i].lawid);
            }
            if (dataArray.length > 0) {
                swal({
                    title: "你确定要删除" + dataArray.length + "个记录？",
                    text: "删除后该法律的内容将会消失，并不能够找回！",
                    icon: "warning",
                    buttons: {
                        cancel: "取消",
                        sure: "确定",
                    }
                }).then(function (value) {
                    if (value) {
                        // 下面发起录入请求
                        dopost("/law/batchdeleteLaw", dataArray).then(function (v) {
                            if (v.status == 200) {
                                swal("", "删除成功!", "success", {
                                    button: false,
                                    timer: 1000
                                })
                                $("#search_result").click();
                            } else {
                                swal("", v.msg, "error", {
                                    button: false,
                                    timer: 1000
                                })
                            }
                        })
                    } else {
                    }
                });
            } else {
                swal("", "请先选择记录!", "error", {
                    button: false,
                    timer: 1000
                })
            }
        });
        // 模糊查询
        $("#search_result").click(function () {
            var search = $("#msearch").val();
            var searchObj = {};
            searchObj.lawname = search;
            searchObj.timestamp = (new Date()).getTime();
            myDataGrid.search(JSON.stringify(searchObj));
        });
        $("#addlaw").click(function () {
            window.location.href = "/law/stuaddetail";
        });

    });
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>