var selectRoleTable;
var noSelectRoleTable;
var USER_ID;
$(function () {
    selectRoleTable = new SelectRoleTable();
    noSelectRoleTable = new NoSelectRoleTable();
});
function modifyRole(userId) {
    USER_ID = userId;
    var modifyRoleStr = '<div class="row">' +
        '<div class="col-md-5">' +
        '<div class="panel panel-info" style="width: 335px;height: 500px;margin:10px ">' +
        '<div class="panel-heading">未选角色</div>' +
        '<div class="panel-body">' +
        '<div class="panel " style="margin-bottom:10px; border: solid 1px #eee;">\n' +
        '                <div class="panel-heading"\n' +
        '                     style="font-weight: bold; background-color: #f5fafe; border-bottom: solid 1px #eee;">\n' +
        '                    查询条件\n' +
        '                    <span style="float:right;">\n' +
        '                        <a class="qry-btn" style="text-decoration: none;" onclick="noSelectRoleTable.queryRole()">\n' +
        '                            <span class="glyphicon glyphicon-search"></span>查询\n' +
        '                        </a>\n' +
        '                    </span>\n' +
        '                </div>\n' +
        '                <form id="roleSearch" class="form-horizontal" style="width: 100%">\n' +
        '                    <div class="form-group" style="margin:12px 0 0 0;">\n' +
        '                        <div class="row">\n' +
        '                            <div class="col-sm-10" style="margin-left:20px;margin-bottom: 10px">\n' +
        '                                <input type="text" class="form-control" id="search_name" name="search_name" placeholder="请输入角色编码或角色名称">\n' +
        '                            </div>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </form>\n' +
        '            </div>'+
        '<table id="roleTable"></table></div></div>' +
        '</div>' +
        '<div class="col-md-2" style="margin: 0 auto;">' +
        '<div class="row" style="height: 180px;"></div>'+
        '<div class="row" style="height: 160px;">' +
        '<button class="btn btn-success" style="margin: 30px 30px; width: 80px;" onclick="selectRoleTable.addSelectRole();">&gt;&gt;</button><br/>'+
        '<button class="btn btn-info" style="margin: 30px 30px; width: 80px;" onclick="selectRoleTable.rmSelectRole();">&lt;&lt;</button><br/></div>'+
        '<div class="row" style="height: 80px;"></div>'+
        '</div>' +
        '<div class="col-md-5">' +
        '<div class="panel panel-success" style="width: 335px;height: 500px;margin:10px ">' +
        '<div class="panel-heading">已选角色</div>' +
        '<div class="panel-body">' +
        '<table id="selectRoleTable"></table>' +
        '</div>' +
        '</div>' +
        '</div>';

    layer.open({
        type: 1,
        skin: 'layui-layer-rim', //加上边框
        area: ['1000px', '680px'], //宽高
        content: modifyRoleStr,
        title: '编辑角色',
        closeBtn: 0, //不显示关闭按钮
        btn: ['取消', '关闭'],
        btn1: function (index) {
            layer.close(index);
        },
        btn2: function (index) {
            layer.close(index);
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
    selectRoleTable.table = new Object();
    selectRoleTable.addSelectRole = function () {
        var dataList = noSelectRoleTable.table.bootstrapTable('getSelections');
        if(dataList.length === 0){
            layer.alert('请选择需要添加的角色', {icon: 6});
            return;
        }else{
            var roleIds = new Array();
            dataList.forEach(function (item) {
                roleIds.push(item.roleId);
            });
            $.ajax({
                url: $basicPathVal + "user/role/" + USER_ID,
                type: 'post',
                dataType: 'json',
                data: {roleIds: roleIds},
                success: function (data) {
                    layer.alert(data.msg, {icon: 6});
                    if(data.status === 200){
                        $("#selectRoleTable").bootstrapTable('refresh');
                        $("#roleTable").bootstrapTable('refresh');
                    }
                }
            });
        }
    };
    selectRoleTable.rmSelectRole = function () {
        var dataList = selectRoleTable.table.bootstrapTable('getSelections');
        if(dataList.length === 0){
            layer.alert('请选择需要删除的角色', {icon: 6});
            return;
        }else{
            var roleIds = new Array();
            dataList.forEach(function (item) {
                roleIds.push(item.roleId);
            });
            $.ajax({
                url: $basicPathVal + "user/role/" + USER_ID,
                type: 'post',
                dataType: 'json',
                data: {roleIds: roleIds,_method: 'DELETE'},
                success: function (data) {
                    layer.alert(data.msg, {icon: 6});
                    if(data.status === 200){
                        $("#selectRoleTable").bootstrapTable('refresh');
                        $("#roleTable").bootstrapTable('refresh');
                    }
                }
            });
        }
    };
    selectRoleTable.init = function (userId) {
        this.table = $("#selectRoleTable").bootstrapTable({
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
            height: 350,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
    noSelectRoleTable.queryRole = function () {
        $("#roleTable").bootstrapTable('refresh');
    };
    noSelectRoleTable.queryParams = function (params) {
        var param = {
            rows: params.limit,
            page: params.offset / params.limit,
            name: $("#search_name").val()
        };
        return param;
    };
    noSelectRoleTable.table = new Object();
    noSelectRoleTable.init = function (userId) {
        this.table = $("#roleTable").bootstrapTable({
            method: 'get',                      //请求方式（*）
            url: '/role/' + userId,
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
            pageSize: 10,                     //每页的记录行数（*）
            pageList: [10, 20, 25, 50, 100],        //可供选择的每页的行数（*）,如果总记录数不满足50就不会显示50条
            queryParams: noSelectRoleTable.queryParams,			//传递参数（*）
            search: false,                      //是否显示表格搜索
            minimumCountColumns: 2,             //最少允许的列数
            height: 340,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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