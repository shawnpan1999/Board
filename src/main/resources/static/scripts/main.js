$(function () {
    $(".btn-addmsg").click(function () {
        var msg;
        msg = $("#inputMsg").val();
        if (!(userId > 1)) {
            alert('请先登录！');
            return;
        }
        $.ajax({
            url: '/addmsg/',
            type: 'post',
            dataType: 'json',
            data: {
                authorId: userId,
                text: msg,
            }
        }).done(function (oResult) {
            if (oResult.code === 0) {
                window.location.reload();
            } else {
                alert(oResult.msgerr);
            }
        }).fail(function () {
            alert('留言失败，请重试');
        });
    })
});