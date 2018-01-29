/* 验证用户名*/
function verifyUsername() {
    var username = $("input[name='username']").val();
    if(!username) {
        $("span[role='username']").hide();
        $("div[role='username']").show();
        $("div[role='username']").attr("isnull","1");
    } else {
        $("div[role='username']").hide();
        $("div[role='username']").attr("isnull","2");
        $("span[role='username']").show();
    }
}

/* 验证邮箱*/
function verifyEmail() {
    var email = $("input[name='email']").val();
    if(!email) {
        $("span[role='email']").hide();
        $("div[role='email']").html("注意:邮箱不能为空!");
        $("div[role='email']").attr("isnull","1");
        $("div[role='email']").show();
    } else {
        if(!(/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/.test(email))) {
            $("span[role='email']").hide();
            $("div[role='email']").html("注意:邮箱格式不正确!");
            $("div[role='email']").attr("isnull","1");
            $("div[role='email']").show();
        } else {
            var result =  verifyAccountIsExist(email);
            if(result == "ok"){
                $("div[role='email']").hide();
                $("div[role='email']").attr("isnull","2");
                $("span[role='email']").show();
            }else{
                $("span[role='email']").hide();
                $("div[role='email']").html("注意:邮箱已经被注册!");
                $("div[role='email']").attr("isnull","1");
                $("div[role='email']").show();
            }
        }
    }
}

/* 验证手机号码*/
function verifyPhone() {
    var phone = $("input[name='phone']").val();
    if(!phone) {
        $("span[role='phone']").hide();
        $("div[role='phone']").html("注意:手机号不能为空!");
        $("div[role='phone']").attr("isnull","1");
        $("div[role='phone']").show();
        $("span.verifycode > button").attr("disabled","disabled");
    } else {
        if(!(/^1[0-9]\d{9}$/.test(phone))) {
            $("span[role='phone']").hide();
            $("div[role='phone']").html("注意:手机号格式不正确!");
            $("div[role='phone']").attr("isnull","1");
            $("div[role='phone']").show();
            $("span.verifycode > button").attr("disabled","disabled");
        } else {
            var result = verifyAccountIsExist(phone);
            if(result == "ok"){
                $("div[role='phone']").hide();
                $("div[role='phone']").attr("isnull","2");
                $("span[role='phone']").show();
                $("span.verifycode > button").removeAttr("disabled");
            }else{
                $("span[role='phone']").hide();
                $("div[role='phone']").html("注意:手机号已经被注册!");
                $("div[role='phone']").attr("isnull","1");
                $("span.verifycode > button").attr("disabled","disabled");
                $("div[role='phone']").show();
            }
        }
    }
}

/* 验证邀请人手机号码*/
function verifyInviterPhone() {
    var inviterphone = $("input[name='inviterphone']").val();
    if(!inviterphone) {
        $("span[role='inviterphone']").hide();
        $("div[role='inviterphone']").html("邀请人手机号不能为空!");
        $("div[role='inviterphone']").attr("isnull","1");
        $("div[role='inviterphone']").show();
    } else {
        if(!(/^1[0-9]\d{9}$/.test(inviterphone))) {
            $("span[role='inviterphone']").hide();
            $("div[role='inviterphone']").html("邀请人手机号格式不正确!");
            $("div[role='inviterphone']").attr("isnull","1");
            $("div[role='inviterphone']").show();
        } else {
            var result = verifyAccountIsExist(inviterphone);
            if(result == "ok"){
                $("span[role='inviterphone']").hide();
                $("div[role='inviterphone']").html("邀请人手机号不存在!");
                $("div[role='inviterphone']").attr("isnull","1");
                $("div[role='inviterphone']").show();
            }else{
                $("div[role='inviterphone']").hide();
                $("div[role='inviterphone']").attr("isnull","2");
                $("span[role='inviterphone']").show();

            }
        }
    }
}

/*验证验证码*/
function verifyCode() {
    var code = $("input[name='code']").val();
    if(!code) {
        $("span[role='code']").hide();
        $("div[role='code']").show();
        $("div[role='code']").attr("isnull","1");
    } else {
        $("div[role='code']").hide();
        $("div[role='code']").attr("isnull","2");
        $("span[role='code']").show();
    }
}

/*验证密码*/
function verifyPassword() {
    var password = $("input[name='password']").val();
    if(!password) {
        $("div[role='password']").html("注意:密码不能为空!");
        $("div[role='password']").attr("isnull","1");
        $("div[role='password']").show();
        $("span[role='password']").hide();
    } else {
        var passwordrepeat = $("input[name='passwordrepeat']").val();
        if(!passwordrepeat) {
            $("div[role='passwordrepeat']").html("注意:确认密码不能为空! ");
            $("div[role='passwordrepeat']").attr("isnull","1");
            $("div[role='passwordrepeat']").show();
            $("span[role='password']").hide();
        }else{
            if(passwordrepeat != password ){
                $("div[role='password']").html("两次输入的密码不一致!");
                $("div[role='password']").attr("isnull","1");
                $("div[role='password']").show();
                $("span[role='password']").hide();
            }else{
                $("div[role='password']").hide();
                $("div[role='password']").attr("isnull","2");
                $("span[role='password']").show();
                $("div[role='passwordrepeat']").hide();
                $("div[role='passwordrepeat']").attr("isnull","2");
                $("span[role='passwordrepeat']").show();
            }
        }
    }
}

/*验证确认密码*/
function verifyPasswordrepeat() {
    var passwordrepeat = $("input[name='passwordrepeat']").val();
    if(!passwordrepeat) {
        $("div[role='passwordrepeat']").html("注意:确认密码不能为空! ");
        $("div[role='passwordrepeat']").attr("isnull","1");
        $("div[role='passwordrepeat']").show();
        $("span[role='passwordrepeat']").hide();
    } else {
        var password = $("input[name='password']").val();
        if(!password) {
            $("div[role='password']").html("注意:密码不能为空!");
            $("div[role='password']").attr("isnull","1");
            $("div[role='password']").show();
            $("span[role='passwordrepeat']").hide();
        } else {
            if(passwordrepeat != password ){
                $("div[role='passwordrepeat']").html("两次输入的密码不一致!");
                $("div[role='passwordrepeat']").attr("isnull","1");
                $("div[role='passwordrepeat']").show();
                $("span[role='passwordrepeat']").hide();
            }else{
                $("div[role='passwordrepeat']").hide();
                $("div[role='passwordrepeat']").attr("isnull","2");
                $("span[role='passwordrepeat']").show();
                $("div[role='password']").hide();
                $("div[role='password']").attr("isnull","2");
                $("span[role='password']").show();
            }
        }
    }
}

/*验证是否存在*/
function verifyAccountIsExist(account) {
    var result = "" ;
    $.ajax({
        url: "/user/verifyAccount", type: "get", async: false, data: {account: account}, success: function (data) {
            result =  data.result;
        }
    });
    return result ;
}

//获取发送短信
function getVerifyCode(){
    var phone = $("input[name='phone']").val();
    $.ajax({
        url: "/user/verifyCode", type: "get", async: false, data: {phone: phone}
    });
    $("span.verifycode > button").attr("disabled","disabled");
}

function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg))
        return unescape(arr[2]);
    else
        return null;
}

function register(){
    var arr =  $("div[isnull]") ;
    for(var i = 0 , l = arr.length ; i < l ; i++ ){
        if(arr[i].attributes.isnull.nodeValue != "2"){  //不等于2 则肯定是等于0的; 那现在就把等于0的给揪出来;
            var role = arr[i].attributes.role.nodeValue ;
            $("div[role='"+ role +"']").html("注意:此处不能为空! ");
            $("div[role='"+ role +"']").attr("isnull","1");
            $("div[role='"+ role +"']").show();
            $("span[role='"+ role +"']").hide();
            return ;
        }
    }
    var code = $("input[name='code']").val();
    var verifycode = getCookie("verifycode");
    if(code == verifycode ){
        $.ajax({
            url:'/user/register',
            data:$('#userRegisterForm').serialize(),
            type:"POST",
            success:function(data){
                if (data.result == "ok") {
                    window.location.href = "sign_in";
                    $.scojs_message('注册成功,请登录!', $.scojs_message.TYPE_OK);
                } else {
                    $.scojs_message('注册失败,请稍后重试,或联系管理员!', $.scojs_message.TYPE_ERROR);
                }
            }
        });
    }else{
        $("span[role='code']").hide();
        $("div[role='code']").html("输入的验证码不正确!");
        $("div[role='code']").show();
        $("div[role='code']").attr("isnull","1");
    }
}
