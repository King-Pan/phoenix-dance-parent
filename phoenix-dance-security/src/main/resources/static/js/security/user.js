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

$('#userTable').on('uncheck.bs.table check.bs.table check-all.bs.table uncheck-all.bs.table', function (e, rows) {
    var datas = $.isArray(rows) ? rows : [rows];        // 点击时获取选中的行或取消选中的行
    examine(e.type, datas);                              // 保存到全局 Array() 里
});

function initTable() {
    $('#userTable').bootstrapTable({
        method: 'get',                      //请求方式（*）
        url: '/users/',
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
                userName: $("#search_userName").val(),
                nickName: $("#search_nickName").val(),
                status: $("#search_status").val()
            };
            return param;
        },			//传递参数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: true,                  //是否显示所有的列（选择显示的列）
        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "userId",                     //每一行的唯一标识，一般为主键列
        idField: "userId",
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        showFullscreen: true,
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表
        onLoadSuccess: function () {
            overAllIds = new Set();
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
            field: 'userId',
            title: '用户编码',
            visible: false,
            align: 'center',
            valign: 'center'
        }, {
            field: 'userName',
            title: '用户名称',
            align: 'center',
            valign: 'center'
        }, {
            field: 'nickName',
            title: '昵称',
            align: 'center',
            valign: 'center'
        }, {
            field: 'email',
            title: '邮箱',
            align: 'center',
            valign: 'center'
        }, {
            field: 'phoneNum',
            title: '电话',
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
            field: 'lastLoginTime',
            title: '最后登录时间',
            align: 'center',
            valign: 'center',
            formatter: formatterUtils.getFullTime
        },{
            field:'ID',
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
    var datas = $('#userTable').bootstrapTable('getSelections');
    if(datas.length === 0){
        layer.alert('请选择至少一条数据', {icon: 6});
    }
    return datas;
}


function batchDel(){
    var datas = getDatas();
    var userIds = new Array();
    datas.forEach(function (item) {
        userIds.push(item.userId);
    });
    if(datas){
        layer.confirm('是否确定要删除该数据？', {
            btn: ['是','否'] //按钮
        }, function(){
            $.ajax({
                url: $basicPathVal + "user/",
                type : "post",
                dataType : "json",
                data: {
                    _method: 'DELETE',
                    userIds: userIds
                },
                success : function(data){
                    layer.alert(data.msg, {icon: 6});
                    if(data.status === 200){
                        $("#userTable").bootstrapTable('refresh');
                    }
                },
                error : function(){
                    layer.alert('批量删除数据失败', {icon: 6});
                }
            })
        }, function(){

        });
    }
}

function actionFormatter(value, row, index) {
    var id = row.userId;
    var result = "";
    var reg = new RegExp( '#id#' , "g" )

    result = $("#actionFormatter").html();
    // result += "<a href='javascript:;' shiro:hasPermission='user:add22' class='btn btn-sm btn-info size-S' onclick=\"openAddOrModifyDialog(true)\" title='修改'><span class='glyphicon glyphicon-edit'></span></a>";
    // result += "<a href='javascript:;' class='btn btn-sm btn-info size-S' onclick=\"delInfo('" + id + "')\" title='删除'><span class='glyphicon glyphicon-trash'></span></a>";
    // result += "<a href='javascript:;'  class='btn btn-sm btn-info size-S' onclick=\"modifyRole('" + id + "')\" title='编辑用户角色'><span class='glyphicon glyphicon-user'></span></a>";
    // result = result.replaceAll('${id}}',id);
    result = result.replace( reg , id );
    return result;
}

function delInfo(userId){
    layer.confirm('是否确定要删除该数据？', {
        btn: ['是','否'] //按钮
    }, function(){
        $.ajax({
            url: $basicPathVal + "user/" + userId,
            type : "post",
            dataType : "json",
            data: {
                _method: 'DELETE'
            },
            success : function(data){
                layer.alert(data.msg, {icon: 6});
                if(data.status === 200){
                    $("#userTable").bootstrapTable('refresh');
                }
            },
            error : function(){
                layer.alert('删除数据失败', {icon: 6});
            }
        })
    }, function(){

    });
}

function openAddOrModifyDialog(flag) {


    var divStr = '<div style="overflow: auto;">';
    divStr += '<form class="form-horizontal" id="infoForm" th:action="@{/user/}" method="post">';
    divStr += '<div class="modal-body" style="width: 100%;overflow-y:auto;">';
    divStr += '<input type="hidden" id="userId" name="userId" value="">';
    //用户名
    divStr += '<div class="form-group">';
    divStr += '<span class="col-sm-3 control-label">用户名</span>';
    divStr += '<div class="col-sm-8"><input placeholder="" name="userName" id="userName" value="" class="form-control" type="text"></div>';
    divStr += '</div>';
    //昵称
    divStr += '<div class="form-group">';
    divStr += '<span class="col-sm-3 control-label">昵称</span>';
    divStr += '<div class="col-sm-8"><input placeholder="" name="nickName" id="nickName" value="" class="form-control" type="text"></div>';
    divStr += '</div>';
    //邮箱
    divStr += '<div class="form-group">';
    divStr += '<span class="col-sm-3 control-label">邮箱</span>';
    divStr += '<div class="col-sm-8"><input placeholder="" name="email" id="email" value="" class="form-control" type="text"></div>';
    divStr += '</div>';
    //电话
    divStr += '<div class="form-group">';
    divStr += '<span class="col-sm-3 control-label">电话</span>';
    divStr += '<div class="col-sm-8"><input placeholder="" name="phoneNum" id="phoneNum" value="" class="form-control" type="text"></div>';
    divStr += '</div>';
    divStr += '</div></div>';
    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['450px', '320px'], //宽高
        content: divStr,
        btn: ['取消', '确定'],
        btn1: function (index) {
            layer.close(index);
        },
        btn2: function () {
            $("#infoForm").bootstrapValidator('validate');//提交验证
            if ($("#infoForm").data('bootstrapValidator').isValid()) {//获取验证结果，如果成功，执行下面代码
                $("#roleCode").removeAttr("disabled");
                var postData = $("#infoForm").serializeJson();//表单序列化
                $("#roleCode").attr("disabled","disabled");
                if(postData.userId){
                    console.log(postData);
                    console.log(postData.userId);
                    postData._method = 'PUT';
                }
                $.ajax({
                    url: $basicPathVal + "user/",
                    type: 'post',
                    dataType: 'json',
                    data: postData,
                    success:function (data) {
                        layer.alert(data.msg, {icon: 6});
                        if(data.status === 200){
                            $("#userTable").bootstrapTable('refresh');
                            return true;
                        }else{
                            return false;
                        }
                    }
                })
            }else{
                layer.alert('格式不正确，请按照正确的格式填写', {icon: 6});
                return false;//禁止关闭窗口
            }


        }
    });
    if(flag){
        var datas = $('#userTable').bootstrapTable('getSelections');
        if(datas.length !== 1){
            layer.alert('请选择一条数据进行修改', {icon: 6});
            return;
        }else{
            $('#infoForm').setForm(datas[0]);
        }

    }
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
    $("#userTable").bootstrapTable('refresh');
}


