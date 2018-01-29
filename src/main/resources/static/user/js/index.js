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

$(function () {
    var userinfo = getUserinfo();
    if(userinfo.isnull == "false"){
        $("div[role='unlogin']").hide();
        $("#userLogo").html(userinfo.username);
        $("div[role='login']").show();
    }else{
        $("div[role='unlogin']").show();
        $("div[role='login']").hide();
    }
});