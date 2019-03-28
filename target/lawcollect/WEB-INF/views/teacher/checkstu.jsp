<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="../common/func_common/func_header_top.jsp" %>
<%-- 引入数据表格资源 --%>
<link rel="stylesheet" href="../../../ZUI/lib/datagrid/zui.datagrid.min.css">
<title>审核学生信息</title>
<%@include file="../common/func_common/func_header_title.jsp" %>
<div class="panel panel-primary">
    <div class="panel-heading" id="headingOne">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordionPanels" href="#collapseOne">
                待审核的学生信息
            </a>
        </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
        <div class="panel-body">

            <%-- 功能面板区域 --%>
            <div class="container">

                <div class="row " style="margin-top:20px;">

                    <div class="col-md-1">
                        <label for="starttime">开始日期</label>
                    </div>
                    <div class="col-md-3">

                        <div class="input-group date form-date">
                            <input type="text" id="starttime" readonly="readonly" class="form-control"
                                   placeholder="选择一个开始日期">
                            <span class="input-group-addon"><span class="icon-remove"></span></span>
                            <span class="input-group-addon"><span class="icon-th"></span></span>
                        </div>
                    </div>

                    <div class="col-md-1">
                        <label for="endtime">结束日期</label>
                    </div>
                    <div class="col-md-3">
                        <div class="input-group date form-date">
                            <input type="text" id="endtime" readonly="readonly" class="form-control"
                                   placeholder="选择一个结束日期">
                            <span class="input-group-addon"><span class="icon-remove"></span></span>
                            <span class="input-group-addon"><span class="icon-th"></span></span>
                        </div>
                    </div>

                </div>

                <div class="row" style="margin-top:20px;margin-bottom:20px;">

                    <div class="col-md-1">
                        <label for="endtime">输入姓名</label>
                    </div>
                    <div class="col-md-3">
                        <input type="text" id="searchstuname" class="form-control" placeholder="输入姓名"/>
                    </div>


                    <div class="col-md-1">
                        <label for="stustatus">账户状态</label>
                    </div>
                    <div class="col-md-3">
                        <select class="form-control" style="width:200px;" id="stustatus">
                            <option value="0">待审核</option>
                            <option value="1">审核通过</option>
                            <option value="2">审核不通过</option>
                        </select>
                    </div>

                </div>

                <div class="row">
                    <div class="col-md-5">
                        <button typeof="button" id="btnbatchpass" class="btn btn-success "
                                style="margin-right:10px;"><i class="icon icon-ok"></i>批量通过
                        </button>
                        <button typeof="button" id="btnbatchunpass" class="btn btn-danger"
                                style="margin-right:10px;"><i class="icon icon-remove"></i>批量不通过
                        </button>

                        <button typeof="button" id="btnbatchdelete" class="btn btn-info"
                                style="margin-right:10px; display: none;"><i class="icon icon-trash"></i>批量删除
                        </button>
                    </div>
                    <div class="col-md-7">

                        <button typeof="button" id="btnsearch" class="btn btn-warning pull-right"
                                style="margin-right:10px;"><i class="icon icon-search"></i>按条件查询
                        </button>
                    </div>
                </div>

            </div>

            <%-- 待完成的信息安全标准法律条文 --%>
            <div id="myDataGrid" class="datagrid datagrid-striped" style="margin-top:40px;">
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
     * 通过审核
     * @param id
     */
    function oppass(id, name) {
        swal({
            text: "你确定通过" + name + "审核吗?",
            buttons: {
                cancel: "取消",
                sure: "确定"
            },
            icon: "info",
        }).then(function (value) {
            switch (value) {
                case "sure":
                    // 执行通过逻辑
                    dopost("/usr/passstu/" + id).then(function (v) {
                        if (v.status == 200) {
                            swal("", "操作成功", "success", {
                                timer: 1200,
                                button: false
                            })
                            // 重新加载数据
                            $("#btnsearch").click();
                        }
                    });
                    break;
                default:
                    break;
            }
        })
    }

    /**
     * 不通过审核
     */
    function opunpass(id, name) {
        swal({
            text: "你确定不通过" + name + "的审核吗?",
            buttons: {
                cancel: "取消",
                sure: "确定"
            },
            icon: "warning",
        }).then(function (value) {
            switch (value) {
                case "sure":
                    // 执行通过逻辑
                    dopost("/usr/unpassstu/" + id).then(function (v) {
                        console.log(v);
                        if (v.status == 200) {
                            swal("", "操作成功", "success", {
                                timer: 1200,
                                button: false
                            })
                            // 重新加载数据
                            $("#btnsearch").click();
                        }
                    });
                    break;
                default:
                    break;
            }
        })
    }

    /**
     * 删除记录
     * @param id
     */
    function opdelete(id, name) {
        swal({
            text: "你确定删除" + name + "的注册信息吗?",
            buttons: {
                cancel: "取消",
                sure: "确定"
            },
            icon: "warning",
        }).then(function (value) {
            switch (value) {
                case "sure":
                    // 执行通过逻辑
                    dopost("/usr/delstu/" + id).then(function (v) {
                        console.log(v);
                        if (v.status == 200) {
                            swal("", "操作成功", "success", {
                                timer: 1200,
                                button: false
                            })
                            // 重新加载数据
                            $("#btnsearch").click();
                        }
                    });
                    break;
                default:
                    break;
            }
        })
    }

    $(function () {

        var now = new Date();
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


        $("#stustatus").on("change", function () {
            var checkval = $(this).val();
            switch (checkval) {
                case "0": // 待审核
                    $("#btnbatchpass").show();
                    $("#btnbatchunpass").show();
                    $("#btnbatchdelete").hide();
                    break;
                case "1": // 已经审核
                    $("#btnbatchpass").hide();
                    $("#btnbatchunpass").show();
                    $("#btnbatchdelete").hide();
                    break;
                case "2": // 未曾通过审核
                    $("#btnbatchpass").show();
                    $("#btnbatchunpass").hide();
                    $("#btnbatchdelete").show();
                    break;
            }
        });

        // 为了修复zui 数据表格的一个小bug
        var datagridflag = true;
        $("#myDataGrid").datagrid({
            dataSource: {
                cols: [
                    {
                        name: 'accountid',
                        label: '编号',
                        width: 0.08
                    },
                    {
                        name: 'account',
                        label: '账号',
                        width: 0.1,
                    },
                    {
                        name: 'sts',
                        label: '账户状态',
                        width: 0.1,
                        html: true,
                        valueOperator: {
                            getter: function (dataValue, cell, dataGrid) {
                                var id = cell.config.data.sts;
                                if (id == "0") {
                                    return "<span class='text-warning'><i class='icon icon-edit'></i>待审核</span>";
                                } else if (id == "1") {
                                    return "<span class='text-success'><i class='icon icon-check'></i>已审核</span>";
                                } else if (id == "2") {
                                    return "<span class='text-danger'><i class='icon icon-undo'></i>未通过</span>";
                                }
                            }
                        }
                    },
                    {
                        name: 'registtime',
                        label: '注册时间',
                        valueType: 'date',
                        sort: true,
                        width: 0.1
                    },
                    {
                        name: 'stuname',
                        label: '姓名',
                        width: 0.1
                    },
                    {
                        name: 'classno',
                        label: '班级',
                        width: 0.1
                    },
                    {
                        name: 'tips',
                        label: '备注',
                        width: 0.1
                    },
                    {
                        label: '操作',
                        html: true,
                        sort: false,
                        width: 0.2,
                        className: 'text-center',
                        // 值转换器仅仅影响当前列
                        valueOperator: {
                            getter: function (dataValue, cell, dataGrid) {
                                var data = cell.config.data;
                                if (data.sts == "0") {
                                    return "<button  class='btn btn-success btn-sm op_btnpass' onclick='oppass("
                                        + data.accountid + ", \"" + data.stuname + "\")'><i class='icon icon-ok'></i>通过</button>"
                                        + "&nbsp;&nbsp;<button  class='btn btn-danger btn-sm op_btnunpass' onclick='opunpass("
                                        + data.accountid + ", \"" + data.stuname + "\")'><i class='icon icon-remove'></i>不通过</button>";
                                } else if (data.sts == "1") {
                                    return "<button  class='btn btn-danger btn-sm op_btnunpass' onclick='opunpass("
                                        + data.accountid + ", \"" + data.stuname + "\")'><i class='icon icon-remove'></i>不通过</button>";
                                } else if (data.sts == "2") {
                                    return "<button class='btn btn-success btn-sm op_btnpass' onclick='oppass("
                                        + data.accountid + ",\"" + data.stuname + "\")'><i class='icon icon-ok'></i>通过</button>"
                                        + "&nbsp;&nbsp;<button  class='btn btn-info btn-sm op_btndelete' onclick='opdelete("
                                        + data.accountid + ",\"" + data.stuname + "\")'><i class='icon icon-trash'></i>删除</button>";
                                }
                            }
                        }
                    }
                ],
                remote: function (params) {
                    // 在这里
                    return {
                        // 远程地址
                        url: '/usr/getRegStus',
                        // 请求类型
                        type: 'POST',
                        // 数据类型
                        dataType: 'json',
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
            checkByClickRow: false, // 禁止点击cell就选中行
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
            valueOperator: { // 自定义值类型转化器
                // date 值转换器会影响所以 valueType 为 `date` 的列
                date: {
                    getter: function (dataValue, cell, dataGrid) {
                        var date = new Date(dataValue);
                        var datestr = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
                        return datestr;
                    }
                }
            },
            showRowIndex: true, // 是否显示行号
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
        });


        // 模糊查询
        $("#btnsearch").on("click", function () {
            // 搜索的条件值
            var searchstu = {
                starttime: "",
                endtime: "",
                status: "",
                stuname: "",
                timestamp: (new Date()).getTime(), // 时间戳
            };
            // 封装查询条件
            var starttime = $("#starttime").val();
            var endtime = $("#endtime").val();
            var status = $("#stustatus").val();
            var stuname = $("#searchstuname").val();
            searchstu.starttime = starttime;
            searchstu.endtime = endtime;
            searchstu.status = status;
            searchstu.stuname = stuname;
            // 转化为json字符串
            var search_json = JSON.stringify(searchstu);
            // 模糊查询
            myDataGrid.search(search_json);
        });


        // 批量通过
        $("#btnbatchpass").click(function () {
            var selectedItems = myDataGrid.getCheckItems();
            var dataArray = new Array();
            for (var i = 0; i < selectedItems.length && selectedItems[i] != null; i++) {
                dataArray.push(selectedItems[i].accountid);
            }
            if (dataArray.length > 0) {
                swal({
                    text: "你确定批量通过" + dataArray.length + "位同学的注册信息吗?",
                    buttons: {
                        cancel: "取消",
                        sure: "确定"
                    },
                    icon: "warning",
                }).then(function (value) {
                    switch (value) {
                        case "sure":
                            dopost("/usr/batchpass", dataArray).then(function (v) {
                                console.log(v);
                                if (v.status == 200) {
                                    swal("", "操作成功", "success", {
                                        timer: 1200,
                                        button: false
                                    })
                                    // 重新加载数据
                                    $("#btnsearch").click();
                                }
                            });
                            break;
                        default:
                            break;
                    }
                })
            } else {
                swal("", "请先选择记录！", "error", {
                    timer: 1200,
                    button: false
                })
            }
        });

        // 批量不通过
        $("#btnbatchunpass").click(function () {
            var selectedItems = myDataGrid.getCheckItems();
            var dataArray = new Array();
            for (var i = 0; i < selectedItems.length && selectedItems[i] != null; i++) {
                dataArray.push(selectedItems[i].accountid);
            }
            if (dataArray.length > 0) {
                swal({
                    text: "你确定批量不通过" + selectedItems.length + "位同学的注册信息吗?",
                    buttons: {
                        cancel: "取消",
                        sure: "确定"
                    },
                    icon: "warning",
                }).then(function (value) {
                    switch (value) {
                        case "sure":
                            dopost("/usr/batchunpass", dataArray).then(function (v) {
                                console.log(v);
                                if (v.status == 200) {
                                    swal("", "操作成功", "success", {
                                        timer: 1200,
                                        button: false
                                    })
                                    // 重新加载数据
                                    $("#btnsearch").click();
                                }
                            });
                            break;
                        default:
                            break;
                    }
                })
            } else {
                swal("", "请先选择记录！", "error", {
                    timer: 1200,
                    button: false
                })
            }
        });

        // 批量删除
        $("#btnbatchdelete").click(function () {
            var selectedItems = myDataGrid.getCheckItems();
            var dataArray = new Array();
            for (var i = 0; i < selectedItems.length && selectedItems[i] != null; i++) {
                dataArray.push(selectedItems[i].accountid);
            }
            if (dataArray.length > 0) {
                swal({
                    text: "你确定批量删除" + dataArray.length + "位同学的注册信息吗?",
                    buttons: {
                        cancel: "取消",
                        sure: "确定"
                    },
                    icon: "warning",
                }).then(function (value) {
                    switch (value) {
                        case "sure":
                            dopost("/usr/batchremovestu", dataArray).then(function (v) {
                                console.log(v);
                                if (v.status == 200) {
                                    swal("", "操作成功", "success", {
                                        timer: 1200,
                                        button: false
                                    })
                                    // 重新加载数据
                                    $("#btnsearch").click();
                                }
                            });
                            break;
                        default:
                            break;
                    }
                })
            } else {
                swal("", "请先选择记录！", "error", {
                    timer: 1200,
                    button: false
                })
            }
        });

    });
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>