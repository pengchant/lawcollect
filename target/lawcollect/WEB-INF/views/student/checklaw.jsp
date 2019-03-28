<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="../common/func_common/func_header_top.jsp" %>
<%-- 引入数据表格资源 --%>
<link rel="stylesheet" href="../../../ZUI/lib/datagrid/zui.datagrid.min.css">
<title>校验法律条文</title>
<%@include file="../common/func_common/func_header_title.jsp" %>
<div class="panel panel-primary">
    <div class="panel-heading" id="headingOne">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordionPanels" href="#collapseOne">
                待校验的信息安全标准条文
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

    /**
     * 开始验证
     * @param id
     */
    function startValidate(id, lawname) {
        swal({
            text: "你确定开始校验" + lawname + "吗?",
            buttons: {
                cancel: "取消",
                sure: "确定"
            },
            icon: "warning",
        }).then(function (value) {
            switch (value) {
                case "sure":
                    dopost("/law/startValidate/" + id).then(function (v) {
                        if (v.status == 200) {
                            // 进行页面跳转
                            continValidate(id);
                        } else {
                            swal("", v.msg, "error", {
                                button: false,
                                timer: 1500
                            })
                        }
                    });
                    // 重新加载
                    $("#search_result").click();
                    break;
                default:
                    break;
            }
        })
    }

    /**
     * 继续验证
     *
     * @param id
     * @param name
     */
    function continValidate(id) {
        window.location.href = "/law/stusurecheck/" + id;
    }

    $(function () {

        // 为了修复zui 数据表格的一个小bug
        var datagridflag = true;
        $("#myDataGrid").datagrid({
            dataSource: {
                cols: [
                    {
                        name: 'lawid',
                        label: '编号',
                        className: 'text-center',
                        sort: true,
                        width: 0.06
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
                        width: 0.07
                    },
                    {
                        name: 'firstreldep',
                        label: "第一发布部门",
                        className: 'text-center',
                        sort: true,
                    },
                    {
                        name: 'copubdep',
                        label: '共同发布部门',
                        className: 'text-center',
                        sort: true,
                    },
                    {
                        name: 'entername',
                        label: '录入人',
                        className: 'text-center',
                        sort: true,
                    },
                    {
                        name: 'entertime',
                        label: '录入时间',
                        className: 'text-center',
                        sort: true,
                        valueType: 'date'
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
                                var name = cell.config.data.lawname;
                                var status = cell.config.data.lawstatus;
                                if (status == "1") {
                                    return "<button class='btn btn-warning btn-sm' onclick='startValidate("
                                        + id + ",\"" + name + "\")'><i class='icon icon-search'></i>校验</button>";
                                } else if (status == "99") {
                                    return "<button class='btn btn-success btn-sm' onclick='continValidate("
                                        + id + ")'><i class='icon icon-signin'></i>继续</button>";
                                }
                            }
                        }
                    }
                ],
                remote: function (params) {
                    return {
                        // 远程地址
                        url: '/law/valiLaw',
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
                        return curdate.getFullYear()
                            + "-" + (curdate.getMonth() + 1)
                            + "-" + curdate.getDate();
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

        // 模糊查询
        $("#search_result").click(function () {
            var search = $("#msearch").val();
            var searchObj = {};
            searchObj.lawname = search;
            searchObj.timestamp = (new Date()).getTime();
            myDataGrid.search(JSON.stringify(searchObj));
        });
        $("#addlaw").click(function () {
            window.location.href = "/law/stusurecheck";
        });

    });
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>