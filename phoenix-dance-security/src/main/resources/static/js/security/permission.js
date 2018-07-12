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
        {title: '资源ID', field: 'permissionId', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '资源名称', field: 'permissionName', align: 'center', valign: 'middle', sortable: true, width: '120px'},
        {title: '上级资源', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '100px',formatter:function (item, index) {
                if(!item.parentName){
                    return "-";
                }
                return item.parentName;
            }},
        {title: '图标', field: 'icon', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
                return item.icon == null ? '' : '<span style="line-height: 19px;margin-left:22px;"><i class="'+item.icon+' fa-lg"></i></span>';
            }},
        {title: '类型', field: 'permissionType', align: 'center', valign: 'middle', sortable: true, width: '80px', formatter: function(item, index){
                if(item.permissionType === "0"){
                    return '<span class="label label-primary" style="line-height: 19px;margin-left:22px;">目录</span>';
                }
                if(item.permissionType === "1"){
                    return '<span class="label label-success" style="line-height: 19px;margin-left:22px;">菜单</span>';
                }
                if(item.permissionType === "2"){
                    return '<span class="label label-warning" style="line-height: 19px;margin-left:22px;">按钮</span>';
                }
            }},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '60px'},
        {title: '状态', field: 'status', align: 'center', valign: 'middle', sortable: true, width: '80px',formatter: function (item,index) {
                var status;
                var value = item.status;
                if(value){
                    if(value==0){
                        status = '<span class="label label-warning" style="line-height: 19px;margin-left:22px;">禁用</span>';
                    }else if(value==1){
                        status = '<span class="label label-success" style="line-height: 19px;margin-left:22px;">启用</span>';
                    }else if(value==2){
                        status = '<span class="label label-danger" style="line-height: 19px;margin-left:22px;">删除</span>';
                    }else{
                        status ='<span class="label label-info" style="line-height: 19px;margin-left:22px;">未知的状态</span>';
                    }
                }
                return status;
            }},
        {title: '菜单URL', field: 'url', align: 'center', valign: 'middle', sortable: true, width: '160px',formatter:function (item,index) {
                if(!item.url){
                    return "-";
                }
                return item.url;
            }},
        {title: '授权标识', field: 'expression', align: 'center', valign: 'middle', width: '80px', sortable: true,formatter:function (item,index) {
                if(!item.expression){
                    return "-";
                }
                return item.expression;
            }}];
    return columns;
};

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

var vm = new Vue({
    // 选项
    el:'#permissionVm',
    data:{
        showList: true,
        title: null,
        menu:{
            parentName:null,
            parentId:1,
            resourceType:1,
            orderNum:0
        },
        bootstrapTable: null
    },
    methods:{
        getMenu: function(menuId){
            //加载菜单树
            $.get($basicPathVal+"resource/select", function(r){
                ztree = $.fn.zTree.init($("#menuTree"), setting, r.data);
                var node = ztree.getNodeByParam("resourceId", vm.menu.parentId);
                if(node){
                    ztree.selectNode(node);
                    vm.menu.parentName = node.resourceName;
                }
            })
        },
        add: function(){
            vm.title = "新增";
            vm.menu = {parentName:null,parentId:1,resourceType:1,resourceOrder:0};
            vm.getMenu();
            $("#resourceModalLabel").html("新增资源");
            $("#addResourceForm")[0].reset();
            $("#resourceId").val('');
            $("#hiddenMethod").empty();
            $("#addResourceDialog").modal("show");
            vm.initStatus();
        },
        initStatus:function () {
            $("#m_status").select2({
                height:34,
                width:'100%'
            });
        },
        update: function () {
            var resourceId = getResourceId();
            if(resourceId == null){
                return ;
            }

            $.get($basicPathVal+"resource/"+resourceId, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.menu = r.data;
                vm.getMenu();
                $("#resourceModalLabel").html("修改资源");
                $("#addResourceForm")[0].reset();
                $("#resourceId").val('');
                $("#m_status").val(vm.menu.status).trigger("change");

                $("#hiddenMethod").empty();
                $("#addResourceDialog").modal("show");
                vm.initStatus();
            });
        },
        del:function () {
            var resourceId = getResourceId();
            if(resourceId == null){
                return ;
            }
            Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
                if (!e) {
                    return;
                }
                $.ajax({
                    url: $basicPathVal+ '/resource/' + resourceId,
                    type: 'delete',
                    dataType: 'json',
                    contentType: "application/json;charset=UTF-8",
                    success: function (data) {
                        if (data.status === 0) {
                            //成功后的处理
                            toastr.success(data.msg);
                            vm.refresh();
                        } else {
                            //失败后的处理
                            toastr.warning(data.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate:function () {
            if(vm.validator()){
                return ;
            }
            var url;
            var method = 'post';
            if(vm.menu.resourceId == null){
                url = $basicPathVal +"resource";
            }else{
                url = $basicPathVal +"resource/"+ vm.menu.resourceId;
                vm.menu._method = "put";
                method = 'put';
            }
            var resource = {
                resourceId: vm.menu.resourceId||'',
                resourceType: vm.menu.resourceType||'',
                resourceName: vm.menu.resourceName||'',
                url: vm.menu.url||'',
                icon: vm.menu.icon||'',
                expression: vm.menu.expression||'',
                parentName: vm.menu.parentName||'',
                parentId: vm.menu.parentId||'',
                resourceOrder: vm.menu.resourceOrder||'',
                status: vm.menu.status||''
            };
            $.ajax({
                url:  url,
                contentType: "application/json",
                type: method,
                data: JSON.stringify(resource),
                success: function(data){
                    if(data.status === 0){
                        toastr.success(data.msg);
                        vm.refresh();
                        $("#addResourceDialog").modal("hide");
                    }else{
                        toastr.warning(data.msg);
                    }
                }
            });
        },
        refresh:function () {
            var param = {
                query:{
                    resourceName: this.$refs.resourceName.value,
                    status: this.$refs.status.value
                }
            };
            Menu.table.refresh(param);
        },
        resourceTree: function(){
            layer.open({
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
                    vm.menu.parentId = node[0].resourceId;
                    vm.menu.parentName = node[0].resourceName;

                    layer.close(index);
                }
            });
        },
        validator:function () {
            if(isBlank(vm.menu.resourceName)){
                alert("资源名称不能为空");
                return true;
            }

            //菜单
            if(vm.menu.resourceType === 1 && isBlank(vm.menu.url)){
                alert("菜单URL不能为空");
                return true;
            }
        }
    }
});

function getResourceId () {
    var selected = $('#resourceTable').bootstrapTreeTable('getSelections');
    if (selected.length === 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}

$(function () {
    var table = new TreeTable(Menu.id,  $basicPathVal + "permissions/", Menu.initColumn());
    table.setExpandColumn(2);
    table.setIdField("permissionId");
    table.setCodeField("permissionId");
    table.setParentCodeField("parentId");
    table.setExpandAll(true);
    //table.method = 'post';
    table.init();
    Menu.table = table;
    $("#status").select2({
        width:172,
        height:34
    });
    vm.initStatus();
});