function getLongDate() {
    let dateObj = new Date();
    let year = dateObj.getFullYear();
    let month = dateObj.getMonth() + 1;
    let date = dateObj.getDate();
    let hour = dateObj.getHours();
    let minute = dateObj.getMinutes();
    let second = dateObj.getSeconds();

    month < 10 ? month = '0' + month : null;
    date < 10 ? date = '0' + date : null;
    hour < 10 ? hour = '0' + hour : null;
    minute < 10 ? minute = '0' + minute : null;
    second < 10 ? second = '0' + second : null;

    document.getElementById('dateStr').innerHTML = year + '-' + month + '-' + date + ' ' + hour + ':' + minute + ':' + second;
    setTimeout('getLongDate()', 1000);
}

function goHome() {
    location.reload();
}

function goSubPage(url) {
    jQuery.ajax({
        url: url,
        global: false,
        type: 'POST',
        dataType: 'html',
        async: false,
        success: function (page) {
            document.getElementById('blank-panel').innerHTML = page;
        }
    });
}

function clearForm(formId) {
    jQuery('#' + formId + ' input').val('');
}

function linkMouseOver(element) {
    element.className = "table-td-link-click";
}

function linkMouseOut(element) {
    element.className = "table-td-link-normal ";
}

function showModalBackdrop() {
    jQuery(".modal-backdrop.fade").show();
}

function getImageSrcUrl(string, isReturnSingle) {
    let imgReg = /<img.*?(?:>|\/>)/gi; // 匹配图片中的img标签
    let srcReg = /src=[\'\"]?([^\'\"]*)[\'\"]?/i; // 匹配图片中的src
    let arr = string.match(imgReg);  // 筛选出所有的img
    let srcArr = [];
    for (let i = 0; i < arr.length; i++) {
        let src = arr[i].match(srcReg);
        srcArr.push(src[1]); // 获取图片地址
    }

    if (isReturnSingle) {
        return srcArr[0];
    }
}
