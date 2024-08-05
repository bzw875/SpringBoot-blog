document.addEventListener('DOMContentLoaded', function() {
    var spans = document.querySelectorAll('.calc-recently-date');
    [].forEach.call(spans, function(ele){
        var text = ele.textContent.replace(' ', 'T');
        var d = new Date(text);
        ele.textContent = calcRecentlyDateStr(d);
    });
    highlightCode();
})

function highlightCode () {
    document.querySelectorAll('.post-content code').forEach((ele,i) => addAttr(ele))
}

function addAttr(ele) {
	const text = ele.textContent;
	const langReg = /\s*\/\/\s?(\w+)\s+/;
	const matchData = text.match(langReg);
	const removeCommText = text.replace(langReg, '');
	if (matchData && matchData[1]) {
		const lang = matchData[1].toLowerCase();
		const parentEle = ele.parentElement;
		parentEle.innerHTML =  Prism.highlight(removeCommText, Prism.languages.javascript, 'javascript');
	}
}

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
// <input type="hidden" id="pageNum" value="0">
//     <input type="hidden" id="pageSize" value="10">
//     <input type="hidden" id="pageCount" value="3">
//     <input type="hidden" id="total" value="30">

function indexInitNav () {
    var pageNum = parseInt(document.querySelector('#pageNum').value);
    var pageSize = parseInt(document.querySelector('#pageSize').value);
    var pageCount = parseInt(document.querySelector('#pageCount').value);

    var nav = document.querySelector('#pageNav');
    if (isNaN(pageNum) || pageCount<=1) {
        nav.style.display = 'none';
        return;
    }

    var showAll = pageCount < 15;

    var str = '';
    if (!showAll && pageNum > 0) {
        str += '<li><a href="?pageNum=' + (pageNum - 1) + '&pageSize=' + pageSize + '">上一页</a></li>';
    }
    if (pageNum > 5) {
        str += '<li><a href="?pageNum=0&pageSize=' + pageSize + '">1..</a></li>';
    }
    if (showAll) {
        var start = 0;
        var end = pageCount;
    } else {
        var start = Math.max(0, pageNum - 5);
        var end = Math.min(pageNum + 5, pageCount - 1);
    }
    for (var i = start; i < end; i++) {
        var className = i === pageNum ? 'selected' : '';
        str += '<li class="'+className+'"><a href="?pageNum='+i+'&pageSize=' + pageSize+'">'+(i+1)+'</a></li>';
    }
    if (!showAll) {
        str += '<li class="' + (pageNum + 1 === pageCount ? 'selected' : '') + '"><a title="最后一页" href="?pageNum=' + (pageCount - 1) + '&pageSize=' + pageSize + '">...' + pageCount + '</a></li>';
    }
    if (!showAll && pageCount > (pageNum+1)){
        str += '<li><a href="?pageNum='+(pageNum+1)+'&pageSize=' + pageSize+'">下一页</a></li>';
    }
    nav.innerHTML = str;
}