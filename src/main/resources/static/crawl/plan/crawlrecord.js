var TableRecord = function () {
    var oTableRecord = new Object();
    //初始化Table
    oTableRecord.Init = function () {
        $('#table_record').bootstrapTable({
            url: '/crawl/record/getRecordPage',         //请求后台的URL（*）
            queryParams:oTableRecord.queryParams,
            method: 'get',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5,10, 25, 50, 100],        //可供选择的每页的行数（*）
            contentType: "application/x-www-form-urlencoded",
            height:650,
            singleSelect:true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "recordid",               //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            idField:'recordid',
            responseHandler:function(res){
                var rows = res.rows ;
                for(var i in rows ){
                    if(!rows[i].isfinished ){
                        $("#isfinished").val( "false" );
                        return res ;
                    }
                }
                $("#isfinished").val( "true" );
                return res ;
            },
            columns: [
                { field: 'recordid',  title: 'recordid',  visible: false  }
                ,{ field: 'planid',  title: 'planid',  visible: false }
                ,{ field: 'planname', title: '方案名称' , halign:'center', width:'50%' ,formatter: plannameFormatter }
                ,{ field: 'createtime', title: '创建时间', halign:'center', align:'center', width:'20%' }
                ,{ field: 'isfinished', title: '是否完成', halign:'center', align:'center', width:'10%' }
                ,{ field: 'operate', title: '操作', formatter: operateFormatter4record , halign:'center',align:'center' , width:'20%'}
            ]
        });
    };
    oTableRecord.queryParams = function(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit : params.limit
            ,offset : params.offset
        };
        return temp;
    };
    return oTableRecord;
};


function plannameFormatter(value, row, index){
    return  '<a class="linkbtn"  title="点击方案名称,可查询方案详情!" href="javascript:void(0);" isfinished="'+ row.isfinished +'"  planid="'+ row.planid +'" onclick="openplan(this)"  >'+ value +'</a>';
}

function operateFormatter4record(value, row, index) {//赋予的参数
    if(row.isfinished){
        return  '<a class="operatebtn" title="下载" href="/crawl/record/downloadResultById?recordid=' +  row.recordid  + '"  ><i class="fa fa-download" aria-hidden="true"></i></a>';
    }else{
        return '<a class="operatebtn" title="爬取任务正在进行中..."  href="javascript:void(0);"  ><i class="fa fa-spinner" aria-hidden="true"></i></i></a>' ;
    }
}


function openplan(obj){
    $("#planid").val( $(obj).attr("planid") );
    $("a[role='addcrawltask']").click();
}