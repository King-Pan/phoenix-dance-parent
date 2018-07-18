var formatterUtils = {};

var $basicPathVal =  $("#basicPath").attr("href");
formatterUtils.getDayTime = function (value,row,index) {
    var time;
    if(value){
        var t = new Date(value);
        var y = t.getFullYear();    // 年
        var m = t.getMonth() + 1;   // 月
        var d = t.getDate();        // 日

        time = [y, m, d].map(formatterUtils.formatNumber).join('-');
    }
    return time;
};
formatterUtils.formatNumber = function(n) {
    n = n.toString();
    return n[1] ? n : '0' + n;
}
formatterUtils.getFullTime = function (value,row,index) {
    var time;
    if(value){
        var t = new Date(value);
        var y = t.getFullYear();    // 年
        var m = t.getMonth() + 1;   // 月
        var d = t.getDate();        // 日

        var h = t.getHours();       // 时
        var i = t.getMinutes();     // 分
        var s = t.getSeconds();     // 秒
        time = [y, m, d].map(formatterUtils.formatNumber).join('-') + ' ' + [h, i, s].map(formatterUtils.formatNumber).join(':');
    }
    return time;
};


var formUtils = {};
formUtils.serializeJson = function (id) {
    // 取得指定FORM里的元素
    var x = $('#'+id).serializeArray();
    var m = [], idata;
    // 按 AJAX数据格式归入数组，方便后面的数据打包
    $.each(x, function(i, field){
        // 由于会出现"双引号字符会导致接下来的数据打包失败，故此对元素内容进行encodeURI编码
        // 后台PHP采用urldecode()函数还原数据
        m.push('"' + field.name + '":"' + encodeURI(field.value) + '"');
    });
    idata ='{' +  m.join(',') + '}';
    // 按字符 idata 转换成 JSON 格式
    idata = eval('(' +idata+ ')');
    return idata;
}

var statusFormat = function (value,row,index) {
    var status;
    if(value){
        if(value==0){
            status = '<span class="label label-warning">禁用</span>';
        }else if(value==1){
            status = '<span class="label label-success">启用</span>';
        }else if(value==2){
            status = '<span class="label label-danger">删除</span>';
        }else{
            status ='<span class="label label-info">未知的状态</span>';
        }
    }
    return status;
};


$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

$.fn.serializeJson=function(){
    var serializeObj={};
    var array=this.serializeArray();
    var str=this.serialize();
    $(array).each(function(){
        if(serializeObj[this.name]){
            if($.isArray(serializeObj[this.name])){
                serializeObj[this.name].push(this.value);
            }else{
                serializeObj[this.name]=[serializeObj[this.name],this.value];
            }
        }else{
            serializeObj[this.name]=this.value;
        }
    });
    return serializeObj;
};

/**
  * 将josn对象赋值给form
  * @param {dom} 指定的选择器
  * @param {obj} 需要给form赋值的json对象
  * @method serializeJson
  * */
$.fn.setForm = function(jsonValue){
    var obj = this;
    $.each(jsonValue,function(name,ival){
        var $oinput = obj.find("input[name="+name+"]");
        if($oinput.attr("type")=="checkbox"){
            if(ival !== null){
                var checkboxObj = $("[name="+name+"]");
                var checkArray = ival.split(";");
                for(var i=0;i<checkboxObj.length;i++){
                    for(var j=0;j<checkArray.length;j++){
                        if(checkboxObj[i].value == checkArray[j]){
                            checkboxObj[i].click();
                        }
                    }
                }
            }
        }
        else if($oinput.attr("type")=="radio"){
            $oinput.each(function(){
                var radioObj = $("[name="+name+"]");
                for(var i=0;i<radioObj.length;i++){
                    if(radioObj[i].value == ival){
                        radioObj[i].click();
                    }
                }
            });
        }
        else if($oinput.attr("type")=="textarea"){
            obj.find("[name="+name+"]").html(ival);
        }
        else{
            obj.find("[name="+name+"]").val(ival);
        }
    })
}

$.fn.modal.Constructor.prototype.enforceFocus = function() {};
