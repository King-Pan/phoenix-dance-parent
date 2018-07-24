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
            dictCode: $("#search_code").val(),
            dictValue: $("#search_value").val(),
            type: $("#search_type").val()
        }
    };
    Menu.table.refresh(param);
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
    $("#type").select2({
        height: 34,
        width: '100%'
    });

    $("#search_type").select2({
        height: 34,
        width: '100%'
    });
}


function add(flag, permissionId) {
    var row;
    if (flag) {
        if (permissionId) {
            row = $('#dataDictTable').bootstrapTable('getRowByUniqueId', permissionId);
        } else {
            var dataList = $('#dataDictTable').bootstrapTreeTable('getSelections');
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
            success: function (data) {
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
    var selected = $('#dataDictTable').bootstrapTreeTable('getSelections');
    if (selected.length === 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}

var ztree;

var setting = {
    view: {
        showIcon: true,//是否显示节点的图标
        showLine: true,//显示节点之间的连线。
        expandSpeed: "slow",//节点展开、折叠时的动画速度
        selectedMulti: false//不允许同时选中多个节点。
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            name: "dictValue",
            url: "nourl"
        }
    }
};

$(function () {
    var table = new TreeTable(Menu.id, $basicPathVal + "dataDicts/", Menu.initColumn());
    table.setExpandColumn(2);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentId");
    table.setExpandAll(true);
    table.init();
    Menu.table = table;
    initStatus();
});

var Menu = {
    id: "dataDictTable",
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    var columns = [
        {field: 'selectItem', checkbox: true},
        {title: 'ID', field: 'id', visible: false, align: 'center', valign: 'middle', width: '60px'},
        {title: '字典编码', field: 'dictCode', align: 'center', valign: 'middle', sortable: true, width: '120px'},
        {title: '字典值',field: 'dictValue',align: 'center',valign: 'middle',sortable: true,width: '100px'},
        {title: '类型',field: 'type',align: 'center',valign: 'middle',sortable: true,width: '60px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '60px'}
        ];
    return columns;
};