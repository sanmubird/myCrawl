function login() {
    var okCount = $("div[isnull = '2']").length;
    if (okCount == 2) {
        $.ajax({
            url: '/user/login',
            data: $('#userLoginForm').serialize(),
            type: "get",
            async: false ,
            success: function (data) {
                if (data.result == "ok") {
                    window.location.href = "../index/index";
                } else {
                    alterDiaglog("登陆失败!");
                    $("div[role='password']").html("注意:密码不正确!");
                    $("div[role='password']").attr("isnull", "1");
                    $("div[role='password']").show();
                    $("span[role='password']").hide();
                }
            }
        });
    }
}


/*验证账号*/
function verifyAccount() {
    var account = $("input[name='account']").val();
    if (!account) {
        $("div[role='account']").html("手机号或邮箱不能为空!");
        $("div[role='account']").attr("isnull", "1");
        $("div[role='account']").show();
        $("span[role='account']").hide();
    } else {
        if (!(/^1[0-9]\d{9}$/.test(account))) { //不是手机号
            if (!(/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/.test(account))) { //也不是邮箱地址
                $("span[role='account']").hide();
                $("div[role='account']").html("手机号或邮箱格式不正确!");
                $("div[role='account']").attr("isnull", "1");
                $("div[role='account']").show();
            } else {
                verifyIsExsit(account);
            }
        } else {
            verifyIsExsit(account);
        }
    }
}

/*验证是否存在*/
function verifyIsExsit(account) {
    $.ajax({
        url: "/user/verifyAccount", type: "get", async: false, data: {account: account}, success: function (data) {
            if (data.result == "ok") {
                $("span[role='account']").hide();
                $("div[role='account']").html("手机号或邮箱格式不存在!");
                $("div[role='account']").attr("isnull", "1");
                $("div[role='account']").show();
            } else {
                $("div[role='account']").hide();
                $("div[role='account']").attr("isnull", "2");
                $("span[role='account']").show();
            }
        }
    });
}


/*验证密码*/
function verifyPassword() {
    var password = $("input[name='password']").val();
    if (!password) {
        $("div[role='password']").html("注意:密码不能为空!");
        $("div[role='password']").attr("isnull", "1");
        $("div[role='password']").show();
        $("span[role='password']").hide();
    } else {
        $("div[role='password']").hide();
        $("div[role='password']").attr("isnull", "2");
        $("span[role='password']").show();
    }
}

function alterDiaglog(content) {
    $("div.modal-body").html(content);
    $("div[role='dialog']").modal('show');
}