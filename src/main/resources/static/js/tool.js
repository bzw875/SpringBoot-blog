
function formatDate(t,str){
    if (typeof t === 'number') {
        t = new Date(t);
    }
    var obj = {
        yyyy:t.getFullYear(),
        yy:(""+ t.getFullYear()).slice(-2),
        M:t.getMonth()+1,
        MM:("0"+ (t.getMonth()+1)).slice(-2),
        d:t.getDate(),
        dd:("0" + t.getDate()).slice(-2),
        H:t.getHours(),
        HH:("0" + t.getHours()).slice(-2),
        h:t.getHours() % 12,
        hh:("0"+t.getHours() % 12).slice(-2),
        m:t.getMinutes(),
        mm:("0" + t.getMinutes()).slice(-2),
        s:t.getSeconds(),
        ss:("0" + t.getSeconds()).slice(-2),
        w:['日', '一', '二', '三', '四', '五', '六'][t.getDay()]
    };
    return str.replace(/([a-z]+)/ig,function($1){return obj[$1]});
}

function calcRecentlyDateStr(dateIn){
    var oneDay = 86400000;
    var now = new Date();

    var ms1 = now.getTime();
    var ms2 = dateIn.getTime();
    ms1 -= ms1 % oneDay;
    ms2 -= ms2 % oneDay;
    // 今天显示 09:22
    // 昨天天显示 昨天 09:22
    // 7天内显示 星期三 09:22
    // 今年显示 2月23日 09:22
    // 否则 2019年 2月23日 09:22

    if (ms1 - ms2 < oneDay) {
        return formatDate(dateIn, '今天 hh:mm');
    }
    if (ms1 - ms2 < oneDay * 2) {
        return formatDate(dateIn, '昨天 hh:mm');
    }
    if (ms1 - ms2 < oneDay * 7) {
        return formatDate(dateIn, '星期w hh:mm');
    }
    if (now.getFullYear() === dateIn.getFullYear()) {
        return formatDate(dateIn, 'MM:dd hh:mm');
    }
    return formatDate(dateIn, 'yyyy-MM-dd hh:mm');
}
//fegewgwgewe
