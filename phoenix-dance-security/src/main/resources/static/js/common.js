var formatterUtils = {};
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