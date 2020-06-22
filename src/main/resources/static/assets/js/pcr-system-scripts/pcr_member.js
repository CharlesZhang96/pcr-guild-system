function searchMemberList() {
    let queryCondition = document.getElementById('member-list-condition').value;

    let data = {
        'gameId': queryCondition,
        'username': queryCondition,
        'phone': queryCondition,
        'qq': queryCondition,
        'weChat': queryCondition
    };

    jQuery.ajax({
        url: '/member/search',
        type: 'POST',
        global: false,
        async: false,
        contentType: 'application/json; charset=UTF-8',
        dataType: 'json',
        data: JSON.stringify(data),
        success: function (data) {
            let resultList = data.data;

            let memberListTbody = jQuery('#member-list tbody');
            memberListTbody.html('');

            for (let i = 0; i < resultList.length; i++) {
                let item = '' +
                    '<tr>' +
                    '   <td>' + resultList[i].gameId + '</td>' +
                    '   <td class="table-td-link-normal" onmouseover="linkMouseOver(this)" onmouseout="linkMouseOut(this)">' +
                    '       <a onclick="getMemberInfo(\'' + resultList[i].memberId + '\')">' + resultList[i].username + '</a>' +
                    '   </td>' +
                    '   <td>' + resultList[i].level + '</td>' +
                    '   <td>' + resultList[i].phone + ' / ' + resultList[i].qq + ' / ' + resultList[i].weChat + '</td>' +
                    '</tr>';

                memberListTbody.append(item);
            }
        }
    });
}

function getMemberInfo(memberId) {
    jQuery.ajax({
        url: '/member/' + memberId,
        type: 'GET',
        global: false,
        async: false,
        contentType: 'application/json; charset=UTF-8',
        dataType: 'json',
        data: '',
        success: function (data) {
            if (data.code === 20000 && data.data != null) {
                let member = data.data;
                let formMember = jQuery('#form-member-list-modify input');

                formMember[0].value = member.memberId;
                formMember[1].value = member.gameId;
                formMember[2].value = member.username;
                formMember[3].value = member.level;
                formMember[4].value = member.phone;
                formMember[5].value = member.qq;
                formMember[6].value = member.weChat;

                jQuery('#modal-member-modify').modal('show');
                jQuery(".modal-backdrop.fade").show();
            } else {
                alert('读取成员信息失败！')
            }
        }
    });
}

function submitFormMemberAdd() {
    let formMember = jQuery('#form-member-list-add input');

    let member = {
        'gameId': formMember[0].value,
        'username': formMember[1].value,
        'level': formMember[2].value,
        'phone': formMember[3].value,
        'qq': formMember[4].value,
        'weChat': formMember[5].value,
    };

    if (memberValidation(member)) {
        jQuery.ajax({
            url: '/member/',
            type: 'POST',
            global: false,
            async: false,
            contentType: 'application/json; charset=UTF-8',
            dataType: 'json',
            data: JSON.stringify(member),
            success: function () {
                if (data.code === 20000) {
                    alert('添加成功！');
                } else {
                    alert('添加失败！' + data.message);
                }

                jQuery('#modal-member-add').modal('hide');
                jQuery(".modal-backdrop.fade").hide();

                goSubPage('/member/member-list');
            }
        });
    }
}

function submitFormMemberModify() {
    let formMember = jQuery('#form-member-list-modify input');

    let member = {
        'memberId': formMember[0].value,
        'gameId': formMember[1].value,
        'username': formMember[2].value,
        'level': formMember[3].value,
        'phone': formMember[4].value,
        'qq': formMember[5].value,
        'weChat': formMember[6].value
    };

    if (memberValidation(member)) {
        jQuery.ajax({
            url: '/member/',
            type: 'PUT',
            global: false,
            async: false,
            contentType: 'application/json; charset=UTF-8',
            dataType: 'json',
            data: JSON.stringify(member),
            success: function (data) {
                if (data.code === 20000) {
                    alert('修改成功！');
                } else {
                    alert('修改失败！' + data.message);
                }

                jQuery('#modal-member-modify').modal('hide');
                jQuery(".modal-backdrop.fade").hide();

                goSubPage('/member/member-list');
            }
        });
    }
}

function deleteMemberConfirm() {
    let formMember = jQuery('#form-member-list-modify input');

    jQuery('#member-delete-username')[0].innerText = formMember[2].value;
    jQuery('#member-delete-btn-confirm')[0].setAttribute('onclick', 'deleteMember("' + formMember[0].value + '")');

    jQuery('#modal-member-delete-confirm').modal('show');
    jQuery(".modal-backdrop.fade").show();
}

function deleteMember(memberId) {
    jQuery.ajax({
        url: '/member/' + memberId,
        type: 'DELETE',
        global: false,
        async: false,
        contentType: 'application/json; charset=UTF-8',
        dataType: 'json',
        data: '',
        success: function (data) {
            if (data.code === 20000) {
                alert('删除成功！');
            } else {
                alert('删除失败！');
            }
        }
    });

    jQuery('#modal-member-delete-confirm').modal('hide');
    jQuery('#modal-member-modify').modal('hide');
    jQuery(".modal-backdrop.fade").hide();
    goSubPage('/member/member-list');
}

function enableMemberModify() {
    let modalModify = jQuery('#modal-member-modify');
    modalModify.find('#member-modify-btn-modify').attr('hidden', true);
    modalModify.find('#member-modify-btn-delete').attr('hidden', true);
    modalModify.find('#member-modify-btn-submit').attr('hidden', false);

    jQuery('#form-member-list-modify input').attr("readonly", false);
}

function disableMemberModify() {
    let modalModify = jQuery('#modal-member-modify');
    modalModify.find('#member-modify-btn-modify').attr('hidden', false);
    modalModify.find('#member-modify-btn-delete').attr('hidden', false);
    modalModify.find('#member-modify-btn-submit').attr('hidden', true);

    jQuery('#form-member-list-modify input').attr("readonly", true);
}

function memberValidation(member) {
    if (!member.gameId) {
        alert("玩家ID为空");
        return false;
    } else if (typeof member.gameId === 'number' && !isNaN(member.gameId)) {
        alert("请输入正确的玩家ID");
        return false;
    }

    if (!member.username) {
        alert("玩家名为空");
        return false;
    }

    if (!member.level) {
        alert("玩家等级为空");
        return false;
    } else if (typeof member.level === 'number' && !isNaN(member.level)) {
        alert("请输入正确的玩家等级");
        return false;
    }

    if (!member.phone && !member.qq && !member.weChat) {
        alert("请填写任意一种联系方式");
        return false;
    } else if (member.phone && typeof member.phone === 'number' && !isNaN(member.phone)) {
        alert("请填写正确的手机号");
        return false;
    } else if (member.qq && typeof member.qq === 'number' && !isNaN(member.qq)) {
        alert("请填写正确的QQ号");
        return false;
    }

    return true;
}
