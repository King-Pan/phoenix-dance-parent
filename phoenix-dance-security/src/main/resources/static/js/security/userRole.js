var selectRoleTable;
var noSelectRoleTable;
$(function () {
    selectRoleTable = new SelectRoleTable();
    noSelectRoleTable = new NoSelectRoleTable();
});
function modifyRole(userId) {
    var modifyRoleStr = '<div class="row">' +
        '<div class="col-md-5">' +
        '<div class="panel panel-success" style="width: 330px;height: 300px;margin:10px ">' +
        '<div class="panel-heading">已选角色</div>' +
        '<div class="panel-body">' +
        '<table id="selectRoleTable"></table>' +
        '</div>' +
        '</div>' +
        '</div>' +
        '<div class="col-md-2" style="margin: 0 auto;">' +
        '<div class="row" style="height: 80px;"></div>'+
        '<div class="row" style="height: 160px;">' +
        '<button class="btn btn-success" style="margin: 10px 25px; width: 100px;" onclick="addRole();">&gt;</button><br/>' +
        '<button class="btn btn-success" style="margin: 10px 25px; width: 100px;" onclick="addRole();">&gt;&gt;</button><br/>'+
        '<button class="btn btn-info" style="margin: 10px 25px; width: 100px;" onclick="addRole();">&lt;</button><br/>'+
        '<button class="btn btn-info" style="margin: 10px 25px; width: 100px;" onclick="addRole();">&lt;&lt;</button><br/></div>'+
        '<div class="row" style="height: 80px;"></div>'+
        '</div>' +
        '<div class="col-md-5">' +
        '<div class="panel panel-info" style="width: 330px;height: 300px;margin:10px ">' +
        '<div class="panel-heading">未选角色</div>' +
        '<div class="panel-body">' +
        '<table id="roleTable"></table>' +
        '</div></div></div></div>';

    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['900px', '420px'], //宽高
        content: modifyRoleStr,
        title: '编辑角色',
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
    selectRoleTable.init(userId);
    noSelectRoleTable.init(userId);
}
var SelectRoleTable = function () {
    var selectRoleTable = new Object();
    selectRoleTable.refresh = function (userId) {
        $("#selectRoleTable").bootstrapTable('refresh',{url:'/user/' + userId});
    };
    selectRoleTable.init = function (userId) {
        $("#selectRoleTable").bootstrapTable({
            method: 'get',                      //请求方式（*）
            url: '/user/' + userId,
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 10,                     //每页的记录行数（*）
            pageList: [10, 20, 25, 50, 100],        //可供选择的每页的行数（*）,如果总记录数不满足50就不会显示50条
            queryParams: {},			//传递参数（*）
            search: false,                      //是否显示表格搜索
            minimumCountColumns: 2,             //最少允许的列数
            height: 220,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "roleId",                     //每一行的唯一标识，一般为主键列
            idField: "roleId",
            formatLoadingMessage: function(){
                return "数据加载中，请稍候......";
            },
            onLoadError: function(status){
                layer.alert("查询数据失败，错误码：" + status, {icon: 5, title:'提示'});
            },
            onLoadSuccess: function(data){
                if(data.flag == '-1'){
                    layer.alert("查询数据失败，错误信息：<br>" + data.msg, {icon: 5, title:'提示'});
                    return;
                }
            },
            columns: [{
                align: 'center',
                checkbox: true                          // 显示复选框
            }, {
                field: 'roleId',
                title: '角色ID',
                visible: false,
                align: 'center',
                valign: 'center'
            }, {
                field: 'roleCode',
                title: '用户编码',
                align: 'center',
                valign: 'center'
            }, {
                field: 'roleName',
                title: '角色名称',
                align: 'center',
                valign: 'center'
            }]
        });
    };

    return selectRoleTable;
};

var noSelectRoleTable;

var NoSelectRoleTable = function () {

    var noSelectRoleTable = new Object();
    noSelectRoleTable.init = function (userId) {
        $("#roleTable").bootstrapTable({
            method: 'get',                      //请求方式（*）
            url: '/role/' + userId,
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            //pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 10,                     //每页的记录行数（*）
            pageList: [10, 20, 25, 50, 100],        //可供选择的每页的行数（*）,如果总记录数不满足50就不会显示50条
            queryParams: {},			//传递参数（*）
            search: false,                      //是否显示表格搜索
            minimumCountColumns: 2,             //最少允许的列数
            height: 220,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "roleId",                     //每一行的唯一标识，一般为主键列
            idField: "roleId",
            formatLoadingMessage: function(){
                return "数据加载中，请稍候......";
            },
            onLoadError: function(status){
                layer.alert("查询数据失败，错误码：" + status, {icon: 5, title:'提示'});
            },
            onLoadSuccess: function(data){
                if(data.flag == '-1'){
                    layer.alert("查询数据失败，错误信息：<br>" + data.msg, {icon: 5, title:'提示'});
                    return;
                }
            },
            columns: [{
                align: 'center',
                checkbox: true                          // 显示复选框
            }, {
                field: 'roleId',
                title: '角色ID',
                visible: false,
                align: 'center',
                valign: 'center'
            }, {
                field: 'roleCode',
                title: '用户编码',
                align: 'center',
                valign: 'center'
            }, {
                field: 'roleName',
                title: '角色名称',
                align: 'center',
                valign: 'center'
            }]
        });
    };
    noSelectRoleTable.refresh = function (userId) {
        $("#selectRoleTable").bootstrapTable('refresh',{url:'/role/' + userId});
    };
    return noSelectRoleTable;
};