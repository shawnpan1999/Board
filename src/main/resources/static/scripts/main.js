$(function () {
    $(".btn-addmsg").click(function () {
        var msg;
        msg = $("#inputMsg").val();
        alert("id = " + userId + "| text = " + msg);
        $.ajax({
            url: '/board/addmsg/',
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
            alert('出现错误，请重试');
        });
    })
});