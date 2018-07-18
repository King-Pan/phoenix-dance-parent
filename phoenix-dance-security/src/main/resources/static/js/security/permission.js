function validator() {
    $('#infoForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            permissionName: {
                validators: {
                    notEmpty: {
                        message: '资源名称不能为空'
                    }
                }
            },
            parentName: {
                validators: {
                    notEmpty: {
                        message: '上级资源不能为空'
                    }
                }
            },
            orderNum: {
                validators: {
                    notEmpty: {
                        message: '排序号不能为空'
                    }
                }
            }
        }
    });
}

function refresh() {
    var param = {
        query: {
            permissionName: $("#search_permissionName").val(),
            status: $("#search_status").val()
        }
    };
    Menu.table.refresh(param);
}

function getMenu() {
    //加载菜单树
    $.get($basicPathVal + "permission/select", function (r) {
        ztree = $.fn.zTree.init($("#menuTree"), setting, r.data);
        var node = ztree.getNodeByParam("permissionId", '1');
        if (node) {
            ztree.selectNode(node);
           //permission.parentName = node.permissionName;
        }
    })
}

function del() {
    var permissionId = getResourceId();
    if (permissionId == null) {
        return;
    }

    layer.confirm('是否确定要删除该数据？', {
        btn: ['是', '否'] //按钮
    }, function () {
        $.ajax({
            url: $basicPathVal + "permission/" + permissionId,
            type: "post",
            dataType: "json",
            data: {
                _method: 'DELETE'
            },
            success: function (data) {
                layer.alert(data.msg, {icon: 6});
                if (data.status === 200) {
                    refresh();
                }
            },
            error: function () {
                layer.alert('批量删除数据失败', {icon: 6});
            }
        })
    }, function () {

    });
}

function initStatus() {
    $("#status").select2({
        height: 34,
        width: '100%'
    });

    $("#search_status").select2({
        height: 34,
        width: '100%'
    });
}


function add(flag, permissionId) {
    var row;
    if (flag) {
        if (permissionId) {
            row = $('#permissionTable').bootstrapTable('getRowByUniqueId', permissionId);
        } else {
            var dataList = $('#permissionTable').bootstrapTreeTable('getSelections');
            if (dataList.length !== 1) {
                layer.alert('请选择一条数据进行修改', {icon: 6});
                return;
            }
            row = dataList[0];
        }
    }
    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['650px', '520px'], //宽高
        content: jQuery("#permissionForm"),
        btn: ['取消', '确定'],
        btn1: function (index) {
            layer.close(index);
        },
        btn2: function () {
            $("#infoForm").bootstrapValidator('validate');//提交验证
            if ($("#infoForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                $("#parentName").removeAttr("disabled");
                var postData = $("#infoForm").serializeJson();//表单序列化
                $("#parentName").attr("disabled", "disabled");
                if (postData.permissionId) {
                    console.log(postData);
                    console.log(postData.permissionId);
                    postData._method = 'PUT';
                }
                $.ajax({
                    url: $basicPathVal + "permission/",
                    type: 'post',
                    dataType: 'json',
                    data: postData,
                    success: function (data) {
                        layer.alert(data.msg, {icon: 6});
                        if (data.status === 200) {
                            refresh();
                            return true;
                        } else {
                            return false;
                        }
                    }
                })
            } else {
                layer.alert('格式不正确，请按照正确的格式填写', {icon: 6});
                return false;//禁止关闭窗口
            }
        }
    });
    $("#status").select2();
    if (flag) {
        $.ajax({
            type: 'get',
            url: $basicPathVal + "permission/" + row.id,
            dataType: 'json',
            success:function (data) {
                console.log(data);
                if (data.status === 200) {
                    //row = data.data;
                    $('#infoForm').setForm(data.data);
                } else {
                    layer.alert(data.msg, {icon: 6});
                }
            }
        });
        console.log(row);
        $('#infoForm').setForm(row);
    } else {
        //$("#roleCode").removeAttr("disabled");
    }
    getMenu();
    validator();
}

function resourceTree() {
    var index = layer.open({
        type: 1,
        offset: '50px',
        skin: 'layui-layer-molv',
        title: "选择菜单",
        area: ['300px', '450px'],
        shade: 0,
        shadeClose: false,
        content: jQuery("#menuLayer"),
        btn: ['确定', '取消'],
        btn1: function (index) {
            var node = ztree.getSelectedNodes();
            //选择上级菜单
            $("#parentName").val(node[0].permissionName);
            $("#parentId").val(node[0].permissionId);
            layer.close(index);
        }
    });
}

function getResourceId() {
    var selected = $('#permissionTable').bootstrapTreeTable('getSelections');
    if (selected.length === 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}

var ztree;

var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "permissionId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            name: "permissionName",
            url: "nourl"
        }
    }
};

$(function () {
    var table = new TreeTable(Menu.id, $basicPathVal + "permissions/", Menu.initColumn());
    table.setExpandColumn(2);
    table.setIdField("permissionId");
    table.setCodeField("permissionId");
    table.setParentCodeField("parentId");
    table.setExpandAll(true);
    //table.method = 'post';
    table.init();
    Menu.table = table;
    $("#status").select2({
        width: 172,
        height: 34
    });
    initStatus();
});

var Menu = {
    id: "permissionTable",
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '资源ID', field: 'permissionId', visible: false, align: 'center', valign: 'middle', width: '60px'},
        {title: '资源名称', field: 'permissionName', align: 'center', valign: 'middle', sortable: true, width: '120px'},
        {
            title: '上级资源',
            field: 'parentName',
            align: 'center',
            valign: 'middle',
            sortable: true,
            width: '100px',
            formatter: function (item, index) {
                if (!item.parentName) {
                    return "-";
                }
                return item.parentName;
            }
        },
        {
            title: '图标',
            field: 'icon',
            align: 'center',
            valign: 'middle',
            sortable: true,
            width: '80px',
            formatter: function (item, index) {
                return item.icon == null ? '' : '<span style="line-height: 19px;margin-left:22px;"><i class="' + item.icon + ' fa-lg"></i></span>';
            }
        },
        {
            title: '类型',
            field: 'permissionType',
            align: 'center',
            valign: 'middle',
            sortable: true,
            width: '60px',
            formatter: function (item, index) {
                if (item.permissionType === "0") {
                    return '<span class="label label-primary" style="line-height: 19px;margin-left:5px;">目录</span>';
                }
                if (item.permissionType === "1") {
                    return '<span class="label label-success" style="line-height: 19px;margin-left:5px;">菜单</span>';
                }
                if (item.permissionType === "2") {
                    return '<span class="label label-warning" style="line-height: 19px;margin-left:5px;">按钮</span>';
                }
            }
        },
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '60px'},
        {
            title: '状态',
            field: 'status',
            align: 'center',
            valign: 'middle',
            sortable: true,
            width: '60px',
            formatter: function (item, index) {
                var status;
                var value = item.status;
                if (value) {
                    if (value == 0) {
                        status = '<span class="label label-warning" style="line-height: 19px;margin-left:5px;">禁用</span>';
                    } else if (value == 1) {
                        status = '<span class="label label-success" style="line-height: 19px;margin-left:5px;">启用</span>';
                    } else if (value == 2) {
                        status = '<span class="label label-danger" style="line-height: 19px;margin-left:5px;">删除</span>';
                    } else {
                        status = '<span class="label label-info" style="line-height: 19px;margin-left:5px;">未知的状态</span>';
                    }
                }
                return status;
            }
        },
        {
            title: '菜单URL',
            field: 'url',
            align: 'center',
            valign: 'middle',
            sortable: true,
            width: '160px',
            formatter: function (item, index) {
                if (!item.url) {
                    return "-";
                }
                return item.url;
            }
        },
        {
            title: '授权标识',
            field: 'expression',
            align: 'center',
            valign: 'middle',
            width: '110px',
            sortable: true,
            formatter: function (item, index) {
                if (!item.expression) {
                    return "-";
                }
                return item.expression;
            }
        }];
    return columns;
};