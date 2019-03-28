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

                    <div class="col-md-1">
                        <label for="msearch">关键字</label>
                    </div>
                    <div class="col-md-3">
                        <input id="msearch" type="search" class="form-control"
                               placeholder="请输入待搜索的法律名称"/>
                    </div>

                </div>

                <div class="row" style="margin-top:20px;">
                    <div class="col-md-1">
                        <label for="lawattribute">法律属性</label>
                    </div>
                    <div class="col-md-7">
                        <%--<input id="lawattribute" type="search" class="form-control"--%>
                        <%--placeholder="请输入待搜索法律属性"/>--%>
                        <select data-placeholder="选择法律属性"
                                id="lawattribute"
                                name="lawattribute"
                                class="chosen-select form-control" multiple="">
                            <%-- 遍历列表数据 --%>
                            <c:forEach var="item" items="${lawattribute}">
                                <optgroup label="${item.currentName}">
                                    <c:forEach var="element" items="${item.children}">
                                        <option value="${element.currentName}">${element.currentName}</option>
                                    </c:forEach>
                                </optgroup>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="col-md-4 text-center">
                        <button class="btn btn-warning" id="search_result" type="button"><i
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
<link rel="stylesheet" href="../../../ZUI/lib/chosen/chosen.min.css"/>
<script src="../../../ZUI/lib/chosen/chosen.min.js" type="text/javascript"></script>
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

        $('select.chosen-select').chosen({
            no_results_text: '没有找到',    // 当检索时没有找到匹配项时显示的提示文本
            disable_search_threshold: 10, // 10 个以下的选择项则不显示检索框
            search_contains: true         // 从任意位置开始检索
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
                        width: 0.2
                    },
                    {
                        name: 'version',
                        label: '版本',
                        className: 'text-center',
                        sort: true,
                        width: 0.08
                    },
                    {
                        name: 'lawattributes',
                        label: '法律属性',
                        className: 'text-center',
                        sort: true,
                        width: 0.3
                    },
                    {
                        name: 'inputer',
                        label: "录入人",
                        className: 'text-center',
                        sort: true,
                        width: 0.08
                    },
                    {
                        name: 'checker',
                        label: "校验人",
                        width: 0.08,
                        className: 'text-center',
                        sort: true,
                    },
                    {
                        name: 'reviewtime',
                        label: "审核时间",
                        width: 0.15,
                        className: 'text-center',
                        sort: true,
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
            var lawattribute = $("#lawattribute").val();
            var inputer = $("#inputer").val();
            var checker = $("#checker").val();
            var msearch = $("#msearch").val();
            var searchObj = {};
            searchObj.attribute = lawattribute // 属性数组
            searchObj.inputer = inputer;
            searchObj.checker = checker;
            searchObj.lawname = msearch;
            searchObj.timestamp = (new Date()).getTime();
            myDataGrid.search(JSON.stringify(searchObj));
        });
    });
</script>
<%@include file="../common/func_common/func_footer_bottom.jsp" %>