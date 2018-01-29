$(function(){
    $("div[role='formdiv']").load("/crawl/step/step1");
    setupStepStyle();
});

/*设定样式*/
function setupStepStyle(){
    var stepNum = $("li[isok='1']").length ;
    $("li[isok] > i").removeClass("active");
    $(".hr").css("border-top-color","#F8F8F8");
    for(var i = 1 ; i <= stepNum ; i++){
        $("li[role='step"+ i +"'] > i").addClass("active");
        $("hr[role='step"+ i +"']").css("border-top-color","#3FB75F");
    }
    if(stepNum == 1){
        $("button[role='prev']").hide();
        $("button[role='save']").html("保存当前页");
        $("button[role='save']").show();
    }else if(stepNum == 4){
        $("button[role='next']").hide();
        $("button[role='save']").html("开始爬取");
        $("button[role='save']").show();
    }else{
        $("button[role='prev']").show();
        $("button[role='next']").show();
        $("button[role='save']").hide();
    }

    var planid = $("#planid").val();
    if(!planid ){
        $("button[role='next']").attr("disabled","disabled");
    }else{
        $("button[role='next']").removeAttr("disabled");
    }
}


function alterDiaglog(content) {
    $("div.modal-body").html(content);
    $("div[role='dialog']").modal('show');
}

function save(){
    var stepNum = $("li[isok='1']").length ;
    if(stepNum == 1){
        $.ajax({
            url:'/crawl/plan/savePlan',
            async:false,
            data:$("form[role='step1']").serialize(),
            type:"POST",
            success:function(data){
                if (data.isSaved == "ok") {
                    $("button[role='next']").removeAttr("disabled");
                    $("input[role='planid']").val(data.planid);
                    $("#planid").val( data.planid );
                    $.scojs_message('保存成功!', $.scojs_message.TYPE_OK);
                } else {
                    $.scojs_message('保存失败,请稍候重试或联系管理员!', $.scojs_message.TYPE_ERROR);
                }
            }
        });
    }else if( stepNum == 4){
        if($("#isfinished").val() == "true" ){
            $.get('/crawl/crawl/startCrawl',{ planid: $("#planid").val( ) });
            $("button[role='save']").attr("disabled","disabled");
            $("button[role='prev']").attr("disabled","disabled");
            $.scojs_message('已经开始爬取,爬取结束后,将会短信通知您,并将爬取结果发送至您邮箱,您也可自行前往个人主页下载.!', $.scojs_message.TYPE_OK);
            $("#planid").val( "" );
            $("#isfinished").val( "false" );
        }else{
            $("button[role='save']").attr("disabled","disabled");
            $.scojs_message('已经有任务正在进行,请等当前任务结束之后再进行爬取!', $.scojs_message.TYPE_ERROR);
        }
    }
}

function gotonext() {
    var stepNum = $("li[isok='1']").length + 1 ;
    $("li[role='step"+ stepNum +"']").attr("isok","1") ;
    /*1:改变样式,2:跳转页面*/
    setupStepStyle();
    $("div[role='formdiv']").load("/crawl/step/step"+stepNum);
}

function gotoprev() {
    var stepNum = $("li[isok='1']").length  ;
    $("li[role='step"+ stepNum +"']").attr("isok","0") ;
    stepNum -= 1;
    /*1:改变样式,2:跳转页面*/
    setupStepStyle();
    $("div[role='formdiv']").load("/crawl/step/step"+stepNum);
}