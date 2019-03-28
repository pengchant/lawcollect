<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="../common/func_common/func_header_top.jsp" %>
<%-- 引入数据表格资源 --%>
<link rel="stylesheet" href="../../../ZUI/lib/datagrid/zui.datagrid.min.css">
<title>法律条文检索</title>
<%@include file="../common/func_common/func_header_title.jsp" %>
<div class="panel panel-primary">
    <div class="panel-heading" id="headingOne">
        <h4 class="panel-title">
            <a data-toggle="collapse" data-parent="#accordionPanels" href="#collapseOne">
                法律条文检索
            </a>
        </h4>
    </div>
    <div id="collapseOne" class="panel-collapse collapse in">
        <div class="panel-body">

            <%-- 功能面板区域 --%>
            <div class="container">
                <div class="row">
                    <div class="col-md-1">
                        <label for="lawattribute">法律属性</label>
                    </div>
                    <div class="col-md-3">
                        <input id="lawattribute" type="search" class="form-control"
                               placeholder="请输入待搜索法律属性"/>
                    </div>
                    <div class="col-md-1">
                        <label for="inputer">录入人</label>
                    </div>
                    <div class="col-md-3">
                        <input id="inputer" type="search" class="form-control"
                               placeholder="请输入录入人"/>
                    </div>

                    <div class="col-md-1">
                        <label for="checker">校验人</label>
                    </div>
                    <div class="col-md-3">
                        <input id="checker" type="search" class="form-control"
                               placeholder="请输入校验人"/>
                    </div>

                </div>

                <div class="row" style="margin-top:20px;">

                    <div class="col-md-1">
                        <label for="msearch">关键字</label>
                    </div>
                    <div class="col-md-3">
                        <input id="msearch" type="search" class="form-control"
                               placeholder="请输入待搜索的法律名称"/>
                    </div>
                    <div class="col-md-4"></div>
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
<script src="../../../ZUI/lib/datagrid/zui.datagrid.min.js"></script>
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
                remote: function (params) {
                    return {
                        // 远程地址
                        url: '/law/searchlawentry',
                        // 请求类型
                        type: 'POST',
                        // 数据类型
                        dataType: 'json'
                    }
                },
                cache: false, // 禁止缓存
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

        $('#myDataGrid').on('onLoad', function (event, result) {
            console.log(result);
            if (result === false) {
                console.log('数据加载失败。');
                // myDataGrid.setDataSource({
                //     "result": "error",
                //     "data": [],
                //     "message": "暂无数据",
                //     "pager": {
                //         "page": 1,           // 当前数据对应的页码
                //         "recTotal": 0,    // 总的数据数目
                //         "recPerPage": 10,    // 每页数据数目
                //     }
                // });
            }
        });

        $('#myDataGrid').on('onRender', function(event) {
            // 表格已重新渲染
            console.log("-render");

        });

        // 模糊查询
        $("#search_result").click(function () {
            var lawattribute = $("#lawattribute").val();
            var inputer = $("#inputer").val();
            var checker = $("#checker").val();
            var msearch = $("#msearch").val();
            var searchObj = {};
            searchObj.attribute = lawattribute;
            searchObj.inputer = inputer;
            searchObj.checker = checker;
            searchObj.lawname = msearch;
            searchObj.timestamp = (new Date()).getTime();
            myDataGrid.search(JSON.stringify(searchObj));
        });
    });
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>