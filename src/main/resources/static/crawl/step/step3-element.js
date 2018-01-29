var TableElement = function () {
    var oTableElement = new Object();
    //初始化Table
    oTableElement.Init = function () {
        $('#tb_element').bootstrapTable({
            url: '/crawl/element/getElementPage',         //请求后台的URL（*）
            queryParams:oTableElement.queryParams,
            method: 'get',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: [5,10, 25, 50, 100],        //可供选择的每页的行数（*）
            contentType: "application/x-www-form-urlencoded",
            height:408,
            singleSelect:true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "elementid",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            idField:'elementid',
            columns: [
                { field: 'elementid',  title: 'ID', visible: false  },
                { field: 'planid',  title: 'planid',  visible: false },
                { field: 'elementname', title: '目标名称' ,halign:'center', width:'15%' },
                { field: 'elementcode', title: '英文代码' ,halign:'center', width:'15%' },
                { field: 'elementdesc', title: '目标描述' ,halign:'center', width:'15%' },
                { field: 'target', title: '抓取规则' ,halign:'center', width:'25%' },
                { field: 'lastestdays', title: '抓取天数',halign:'center', align:'center', width:'10%' },
                { field: 'createtime', title: '创建时间',halign:'center', align:'center', width:'10%' },
                { field: 'testurl', title: '测试url',  visible: false },
                { field: 'operate', title: '操作', formatter: operateFormatter4element , halign:'center',align:'center' , width:'10%'}
            ]
        });
    };
    oTableElement.queryParams = function(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit : params.limit
            ,offset : params.offset
            ,planid: $("#planid").val()
        };
        return temp;
    };
    return oTableElement;
};


function operateFormatter4element(value, row, index) {//赋予的参数
    return [
        '<a class="operatebtn" href="javascript:void(0);" elementid="'+ row.elementid +'"  onclick="updateElement(this)" ><i class="fa fa-pencil-square" aria-hidden="true"></i></a>',
        '<a class="operatebtn" href="javascript:void(0);" elementid="'+ row.elementid +'"  onclick="deleteElement(this)" ><i class="fa fa-trash-o" aria-hidden="true"></i></a>'
    ].join('');
}

function updateElement(obj) {
    elementid = $(obj).attr("elementid") ;
    $("a[role='addelement']").click();
}

function deleteElement(obj){
    var id = $(obj).attr("elementid") ;
    $.ajax({
        url:'/crawl/element/deleteElement?elementid='+id,
        async:false,
        success:function(data){
            if(data.isDelete = 'ok'){
                $.scojs_message('删除具体爬取目标成功!', $.scojs_message.TYPE_OK);
            }else{
                $.scojs_message('删除具体爬取目标失败!', $.scojs_message.TYPE_ERROR);
            }
        }
    });
    elementid = "";
    $("#tb_element").bootstrapTable('refresh');
}