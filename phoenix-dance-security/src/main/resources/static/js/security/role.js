var overAllIds = new Set();                // 全局保存选中行的对象
function examine(type, datas) {            // 操作类型，选中的行
    if (type.indexOf('uncheck') == -1) {
        $.each(datas, function (i, v) {        // 如果是选中则添加选中行的 id
            overAllIds.add(v.userId);
        });
    } else {
        $.each(datas, function (i, v) {
            overAllIds.delete(v.userId);     // 删除取消选中行的 id
        });
    }
}

$('#roleTable').on('uncheck.bs.table check.bs.table check-all.bs.table uncheck-all.bs.table', function (e, rows) {
    var datas = $.isArray(rows) ? rows : [rows];        // 点击时获取选中的行或取消选中的行
    examine(e.type, datas);                              // 保存到全局 Array() 里
});

function initTable() {
    $('#roleTable').bootstrapTable({
        method: 'get',                      //请求方式（*）
        url: '/roles/',
        toolbar: '#toolbar',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 10,                     //每页的记录行数（*）
        pageList: [10, 20, 25, 50, 100],        //可供选择的每页的行数（*）,如果总记录数不满足50就不会显示50条
        queryParams: function queryParams(params) {
            var param = {
                rows: params.limit,
                page: params.offset / params.limit,
                roleCode: $("#search_roleCode").val(),
                roleName: $("#search_roleName").val(),
                status: $("#search_status").val()
            };
            return param;
        },			//传递参数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: true,                  //是否显示所有的列（选择显示的列）
        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        //clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "roleId",                     //每一行的唯一标识，一般为主键列
        idField: "roleId",
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showFullscreen: true,
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表
        onLoadSuccess: function () {
        },
        columns: [{
            align: 'center',
            checkbox: true,                          // 显示复选框
            formatter: function (i, row) {            // 每次加载 checkbox 时判断当前 row 的 id 是否已经存在全局 Set() 里
                if ($.inArray(row.userId, Array.from(overAllIds)) != -1) {    // 因为 Set是集合,需要先转换成数组
                    return {
                        checked: true               // 存在则选中
                    }
                }
            }
        }, {
            field: 'roleId',
            title: '角色ID',
            visible: false,
            align: 'center',
            valign: 'center'
        }, {
            field: 'roleCode',
            title: '角色编码',
            align: 'center',
            valign: 'center'
        }, {
            field: 'roleName',
            title: '角色名称',
            align: 'center',
            valign: 'center'
        }, {
            field: 'remark',
            title: '备注',
            align: 'center',
            valign: 'center'
        }, {
            field: 'status',
            title: '状态',
            align: 'center',
            valign: 'center',
            formatter: statusFormat
        }, {
            field: 'createTime',
            title: '创建时间',
            align: 'center',
            valign: 'center',
            formatter: formatterUtils.getFullTime
        }, {
            field: 'updateTime',
            title: '更新时间',
            align: 'center',
            valign: 'center',
            formatter: formatterUtils.getFullTime
        }, {
            field: 'ID',
            title: '操作',
            width: 120,
            align: 'center',
            formatter: actionFormatter
        }]
    });
}

$(function () {
    initTable();
    $("#search_status").select2();
});

function getDatas() {
    var datas = $('#roleTable').bootstrapTable('getSelections');
    return datas;
}


function batchDel() {
    var datas = getDatas();
    if (datas.length == 0) {
        layer.alert('请选择至少一条数据', {icon: 6});
        return;
    }
    var roleIds = new Array();
    datas.forEach(function (item) {
        roleIds.push(item.roleId);
    });
    if (datas) {
        layer.confirm('是否确定要删除该数据？', {
            btn: ['是', '否'] //按钮
        }, function () {
            $.ajax({
                url: $basicPathVal + "role/",
                type: "post",
                dataType: "json",
                data: {
                    _method: 'DELETE',
                    roleIds: roleIds
                },
                success: function (data) {
                    layer.alert(data.msg, {icon: 6});
                    if (data.status === 200) {
                        overAllIds = new Set();
                        $("#roleTable").bootstrapTable('refresh');
                    }
                },
                error: function () {
                    layer.alert('批量删除数据失败', {icon: 6});
                }
            })
        }, function () {

        });
    }
}

function actionFormatter(value, row, index) {
    var id = row.roleId;
    var result = "";
    result += "<a href='javascript:;' class='btn btn-sm btn-info size-S' onclick=\"openAddOrModifyDialog(true," + id + ")\" title='修改'><span class='glyphicon glyphicon-edit'></span></a>";
    result += "<a href='javascript:;' class='btn btn-sm btn-info size-S' onclick=\"delInfo('" + id + "')\" title='删除'><span class='glyphicon glyphicon-trash'></span></a>";
    result += "<a href='javascript:;' class='btn btn-sm btn-info size-S' onclick=\"giveSecurity('" + id + "')\" title='授权'><span class='glyphicon glyphicon-lock'></span></span></a>";
    return result;
}



function delInfo(roleId) {
    layer.confirm('是否确定要删除该数据？', {
        btn: ['是', '否'] //按钮
    }, function () {
        $.ajax({
            url: $basicPathVal + "role/" + roleId,
            type: "post",
            dataType: "json",
            data: {
                _method: 'DELETE'
            },
            success: function (data) {
                layer.alert(data.msg, {icon: 6});
                if (data.status === 200) {
                    overAllIds = new Set();
                    $("#roleTable").bootstrapTable('refresh');
                }
            },
            error: function () {
                layer.alert('删除数据失败', {icon: 6});
            }
        })
    }, function () {

    });
}

function openAddOrModifyDialog(flag, roleId) {
    var row;
    if (flag) {
        if (roleId) {
            row = $('#roleTable').bootstrapTable('getRowByUniqueId', roleId);
        } else {
            var dataList = $('#roleTable').bootstrapTable('getSelections');
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
        area: ['450px', '330px'], //宽高
        content: $("#roleForm"),
        btn: ['取消', '确定'],
        btn1: function (index) {
            layer.close(index);
        },
        btn2: function () {
            $("#infoForm").bootstrapValidator('validate');//提交验证
            if ($("#infoForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                $("#roleCode").removeAttr("disabled");
                var postData = $("#infoForm").serializeJson();//表单序列化
                $("#roleCode").attr("disabled", "disabled");
                if (postData.roleId) {
                    console.log(postData);
                    console.log(postData.roleId);
                    postData._method = 'PUT';
                }
                $.ajax({
                    url: $basicPathVal + "role/",
                    type: 'post',
                    dataType: 'json',
                    data: postData,
                    success: function (data) {
                        layer.alert(data.msg, {icon: 6});
                        if (data.status === 200) {
                            $("#roleTable").bootstrapTable('refresh');
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
    if (flag) {
        $('#infoForm').setForm(row);
        //$("#status").val(row.status).trigger("change");
    } else {
        $("#roleCode").removeAttr("disabled");
    }
    $("#status").select2();
    validatorUser();
}

function validatorUser() {
    $('#infoForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            userName: {
                message: 'The username is not valid',
                validators: {
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    stringLength: {//检测长度
                        min: 6,
                        max: 30,
                        message: '长度必须在6-30之间'
                    },
                    regexp: {//正则验证
                        regexp: /^[a-zA-Z0-9]+$/,
                        message: '所输入的字符不符要求【必须是字母和数字】'
                    },
                }
            },
            email: {
                validators: {
                    notEmpty: {
                        message: '邮件不能为空'
                    },
                    emailAddress: {
                        message: '请输入正确的邮件地址如：123@qq.com'
                    }
                }
            },
            nickName: {
                validators: {
                    notEmpty: {
                        message: '昵称不能为空'
                    },
                    stringLength: {//检测长度
                        min: 2,
                        max: 10,
                        message: '长度必须在2-10之间'
                    },
                    regexp: {//正则验证
                        regexp: /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/,
                        message: '所输入的字符不符要求【必须是字母、数字和中文字符】'
                    }


                }
            },
            phoneNum: {
                validators: {
                    notEmpty: {
                        message: '手机号码不能为空'
                    },
                    stringLength: {
                        min: 11,
                        max: 11,
                        message: '请输入11位手机号码'
                    },
                    regexp: {
                        regexp: /^0{0,1}(13[0-9]|15[7-9]|153|156|18[7-9])[0-9]{8}$/,
                        message: '请输入正确的手机号码'
                    }
                }
            }
        }
    });
}

function doQuery() {
    $("#roleTable").bootstrapTable('refresh');
}



function giveSecurity(roleId) {
    getMenu();
    resourceTree(roleId);
    console.log(roleId);

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


function resourceTree(roleId) {
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
            var nodes= ztree.getCheckedNodes();
            console.log(nodes);
            var permissionIds = [];
            nodes.forEach(function (item) {
                permissionIds.push(item.permissionId);
            });
            $.ajax({
                type: 'post',
                url: $basicPathVal + "role/"+roleId,
                data: {permissionIds: permissionIds},
                success: function (data) {
                    layer.alert(data.msg, {icon: 6});
                    if (data.status === 200) {
                        layer.close(index);
                    }
                }

            });
        }
    });
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
            url: "nourl",
            check: {
                chkboxType: { "Y" : "ps", "N" : "ps" }
            }
        }
    },
    check: {
        chkboxType: { "Y" : "ps", "N" : "ps" },
        chkStyle: "checkbox",
        enable: true
    },
    usericon:{
        // 折叠icon
        OPEN: "open fa ftopen fa-folder-open", // 打开文件图标
        CLOSE: "close fa ftclose fa-folder",   // 折叠文件图标
        OPENDK:'open icon-folder ace-icon tree-minus',  // 打开状态图标
        CLOSEZD:'close icon-folder ace-icon tree-plus', // 折叠状态图标
        DOCU: "docu",
        // 子节点图标
        CHILDRENNODE: "fa-book",
        // 复选框图标
        UNFACHECK: "fa fa-check",
        FACHECKED: "fa fa-times",
        // 编辑图标
        EDIT: "fa-pencil-square-o",
        // 移除图标
        REMOVE: "fa-eraser",
        // 增加节点图标
        ADDNODE: "fa-plus-square",
        DOCU: "docu"
    }

};


