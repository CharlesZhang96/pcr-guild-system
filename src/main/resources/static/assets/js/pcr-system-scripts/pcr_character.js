function searchCharacterList() {
    let queryCondition = document.getElementById('character-list-condition').value;

    let data = {
        'charName': queryCondition,
        'nickname': queryCondition
    };

    jQuery.ajax({
        url: '/character/search',
        type: 'POST',
        global: false,
        async: false,
        contentType: 'application/json; charset=UTF-8',
        dataType: 'json',
        data: JSON.stringify(data),
        success: function (data) {
            let resultList = data.data;

            let characterListTbody = jQuery('#character-list tbody');
            characterListTbody.html('');

            for (let i = 0; i < resultList.length; i++) {
                let item = '' +
                    '<tr>' +
                    '   <td>' + resultList[i].avatarUrl + '</td>' +
                    '   <td class="table-td-link-normal" onmouseover="linkMouseOver(this)" onmouseout="linkMouseOut(this)">' +
                    '       <a onclick="getCharacterInfo(\'' + resultList[i].characterId + '\')">' + resultList[i].charName + '</a>' +
                    '   </td>' +
                    '   <td>' + resultList[i].nickname + '</td>' +
                    '</tr>';

                characterListTbody.append(item);
            }
        }
    });
}

function getCharacterInfo(characterId) {
    jQuery.ajax({
        url: '/character/' + characterId,
        type: 'GET',
        global: false,
        async: false,
        contentType: 'application/json; charset=UTF-8',
        dataType: 'json',
        data: '',
        success: function (data) {
            if (data.code === 20000 && data.data != null) {
                let character = data.data;
                let formCharacter = jQuery('#form-character-list-modify input');

                formCharacter[0].value = character.characterId;
                formCharacter[1].value = character.charName;
                formCharacter[2].value = character.nickname;
                formCharacter[3].value = getImageSrcUrl(character.avatarUrl, true);

                updateCharacterModifyAvatarView(formCharacter[3]);

                jQuery('#modal-character-modify').modal('show');
                jQuery('.modal-backdrop.fade').show();
            }
        }
    });
}

function submitFormCharacterAdd() {
    let formCharacter = jQuery('#form-character-list-add input');

    let character = {
        'charName': formCharacter[0].value,
        'nickname': formCharacter[1].value,
        'avatarUrl': '<img src="' + formCharacter[2].value + '" alt="' + formCharacter[0].value + '" height="50" width="50">'
    };

    if (characterValidation(character)) {
        jQuery.ajax({
            url: '/character/',
            type: 'POST',
            global: false,
            async: false,
            contentType: 'application/json; charset=UTF-8',
            dataType: 'json',
            data: JSON.stringify(character),
            success: function (data) {
                if (data.code === 20000) {
                    alert('添加成功！');
                } else {
                    alert('添加失败！' + data.message);
                }

                jQuery('#modal-character-add').modal('hide');
                jQuery('.modal-backdrop.fade').hide();

                goSubPage('/character/character-list');
            }
        });
    }
}

function submitFormCharacterModify() {
    let formCharacter = jQuery('#form-character-list-modify input');

    let character = {
        'characterId': formCharacter[0].value,
        'charName': formCharacter[1].value,
        'nickname': formCharacter[2].value,
        'avatarUrl': '<img src="' + formCharacter[3].value + '" alt="' + formCharacter[1].value + '" height="50" width="50">'
    };

    if (characterValidation(character)) {
        jQuery.ajax({
            url: '/character/',
            type: 'PUT',
            global: false,
            async: false,
            contentType: 'application/json; charset=UTF-8',
            dataType: 'json',
            data: JSON.stringify(character),
            success: function (data) {
                if (data.code === 20000) {
                    alert('修改成功！');
                } else {
                    alert('修改失败！' + data.message);
                }

                jQuery('#modal-character-modify').modal('hide');
                jQuery('.modal-backdrop.fade').hide();

                goSubPage('/character/character-list');
            }
        });
    }
}

function deleteCharacterConfirm() {
    let formCharacter = jQuery('#form-character-list-modify input');

    jQuery('#character-delete-charName')[0].innerText = formCharacter[1].value;
    jQuery('#character-delete-btn-confirm')[0].setAttribute('onclick', 'deleteCharacter("' + formCharacter[0].value + '")');

    jQuery('#modal-character-delete-confirm').modal('show');
    jQuery('.modal-backdrop.fade').show();
}

function deleteCharacter(characterId) {
    jQuery.ajax({
        url: '/character/' + characterId,
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

    jQuery('#modal-character-delete-confirm').modal('hide');
    jQuery('#modal-character-modify').modal('hide');
    jQuery('.modal-backdrop.fade').hide();
    goSubPage('/character/character-list');
}

function enableCharacterModify() {
    let modalModify = jQuery('#modal-character-modify');
    modalModify.find('#character-modify-btn-modify').attr('hidden', true);
    modalModify.find('#character-modify-btn-delete').attr('hidden', true);
    modalModify.find('#character-modify-btn-submit').attr('hidden', false);

    jQuery('#form-character-list-modify input').attr('readonly', false);
}

function disableCharacterModify() {
    let modalModify = jQuery('#modal-character-modify');
    modalModify.find('#character-modify-btn-modify').attr('hidden', false);
    modalModify.find('#character-modify-btn-delete').attr('hidden', false);
    modalModify.find('#character-modify-btn-submit').attr('hidden', true);

    jQuery('#form-character-list-modify input').attr('readonly', true);
}

function characterValidation(character) {
    if (!character.charName) {
        alert('角色名为空');
        return false;
    }

    let regUrl = /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\/])+$/;
    let avatarUrl = getImageSrcUrl(character.avatarUrl, true);
    if (avatarUrl && !regUrl.test(avatarUrl)) {
        alert('请输入正确格式的网址');
        return false;
    }

    return true;
}

function updateCharacterAddAvatarView(element) {
    let regUrl = /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\/])+$/;

    if (!element.value || element.value && !regUrl.test(element.value)) {
        jQuery('#character-add-avatar-view').attr('src', 'https://pic.cangku.one/images/2020/06/23/wEClR.jpg');
    } else {
        jQuery('#character-add-avatar-view').attr('src', element.value);
    }
}

function updateCharacterModifyAvatarView(element) {
    let regUrl = /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\/])+$/;

    if (!element.value || element.value && !regUrl.test(element.value)) {
        jQuery('#character-modify-avatar-view').attr('src', 'https://pic.cangku.one/images/2020/06/23/wEClR.jpg');
        return false;
    } else {
        jQuery('#character-modify-avatar-view').attr('src', element.value);
    }
}