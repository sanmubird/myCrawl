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
    $("div[role='phone']").hide();
    $("div[role='phone']").attr("isnull","2");
    $("span[role='phone']").show();
}



/*验证旧密码*/
function verifyOldPassword() {
    var oldpassword = $("input[name='oldpassword']").val();
    if(!oldpassword) {
        $("div[role='oldpassword']").html("注意:旧密码不能为空!");
        $("div[role='oldpassword']").attr("isnull","1");
        $("div[role='oldpassword']").show();
        $("span[role='oldpassword']").hide();
    } else {
        var passwordrepeat = $("input[name='passwordrepeat']").val();
        if( passwordrepeat != oldpassword ){
            $("div[role='oldpassword']").html("旧密码不正确!");
            $("div[role='oldpassword']").attr("isnull","1");
            $("div[role='oldpassword']").show();
            $("span[role='oldpassword']").hide();
        }else{
            $("div[role='oldpassword']").hide();
            $("div[role='oldpassword']").attr("isnull","2");
            $("span[role='oldpassword']").show();
        }
    }
}

/*验证新密码是否为空*/
function verifyPassword() {
    var password = $("input[name='password']").val();
    if(!password) {
        $("span[role='password']").hide();
        $("div[role='password']").show();
        $("div[role='password']").attr("isnull","1");
    } else {
        $("div[role='password']").hide();
        $("div[role='password']").attr("isnull","2");
        $("span[role='password']").show();
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


function update(){
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
    $.ajax({
        url:'/user/update',
        data:$('#useUpdateForm').serialize(),
        type:"POST",
        success:function(data){
            if (data.result == "ok") {
                window.location.href = "sign_in";
                $.scojs_message('修改个人资料成功!', $.scojs_message.TYPE_OK);
            } else {
                $.scojs_message('修改个人资料失败!', $.scojs_message.TYPE_ERROR);
            }
        }
    });
}


function getUserinfo() {
    var userinfo = {};
    $.ajax({
        url:'/user/sessionuserinfo',
        async:false,
        success:function(data){
            userinfo = data ;
        }
    });
    return userinfo;
}