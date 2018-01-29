var TableSeed = function () {
    var oTableSeed = new Object();
    //初始化Table
    oTableSeed.Init = function () {
        $('#tb_seed').bootstrapTable({
            url: '/crawl/seed/getSeedPage',         //请求后台的URL（*）
            queryParams:oTableSeed.queryParams,
            method: 'get',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: [5,10, 25, 50, 100],        //可供选择的每页的行数（*）
            contentType: "application/x-www-form-urlencoded",
            height:420,
            singleSelect:true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "seedid",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            idField:'seedid',
            columns: [
                { field: 'seedid',  title: 'ID', visible: false   },
                { field: 'planid',  title: 'planid',  visible: false  },
                { field: 'seedpath', title: '爬取起始地址' ,halign:'center', width:'35%' },
                { field: 'seedpathdesc', title: '爬取起始地址描述' ,halign:'center', width:'40%' },
                { field: 'createtime', title: '创建时间',halign:'center', align:'center', width:'10%' },
                { field: 'operate', title: '操作', formatter: operateFormatter4seed , halign:'center',align:'center' , width:'15%'}
            ]
        });

    };
    oTableSeed.queryParams = function(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit : params.limit
            ,offset : params.offset
            ,planid:  $("#planid").val()
        };
        return temp;
    };
    return oTableSeed;
};


function operateFormatter4seed(value, row, index) {//赋予的参数
    return [
        '<a class="operatebtn" href="javascript:void(0);" seedid="'+ row.seedid +'"  onclick="updateSeed(this)" ><i class="fa fa-pencil-square" aria-hidden="true"></i></a>',
        '<a class="operatebtn" href="javascript:void(0);" seedid="'+ row.seedid +'"  onclick="deleteSeed(this)" ><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
    ].join('');
}

function updateSeed(obj) {
    seedid = $(obj).attr("seedid") ;
    $("a[role='addseed']").click();
}

function deleteSeed(obj){
    var id = $(obj).attr("seedid") ;
    $.ajax({
        url:'/crawl/seed/deleteSeed?seedid='+id,
        async:false,
        success:function(data){
            if(data.isDelete = 'ok'){
                $.scojs_message('删除爬取起始地址成功!', $.scojs_message.TYPE_OK);
            }else{
                $.scojs_message('删除爬取起始地址失败!', $.scojs_message.TYPE_ERROR);
            }
        }
    });
    seedid = "";
    $("#tb_seed").bootstrapTable('refresh');
}