var TablePlan = function () {
    var oTablePlan = new Object();
    //初始化Table
    oTablePlan.Init = function () {
        $('#t_plan').bootstrapTable({
            url: '/crawl/plan/getPlanPage',         //请求后台的URL（*）
            queryParams:oTablePlan.queryParams,
            method: 'get',                      //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5,10, 25, 50, 100],        //可供选择的每页的行数（*）
            contentType: "application/x-www-form-urlencoded",
            height:425,
            singleSelect:true,
            showColumns: false,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            clickToSelect: true,                //是否启用点击选中行
            uniqueId: "planid",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            idField:'planid',
            columns: [
                { field: 'planid',  title: 'planid',  visible: false  }
                ,{ field: 'planname', title: '方案名称' ,halign:'center', width:'20%' }
                ,{ field: 'plandesc', title: '方案描述' ,halign:'center', width:'45%' }
                ,{ field: 'isresumable', title: '是否断点爬取' ,halign:'center',align:'center', width:'10%' }
                ,{ field: 'deptnumber', title: '爬取深度' ,halign:'center',align:'center',  width:'10%' }
                ,{ field: 'createtime', title: '创建时间',halign:'center', align:'center', width:'15%' }
            ]
        });
    };
    oTablePlan.queryParams = function(params) {
        var temp = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            limit : params.limit
            ,offset : params.offset
            ,planid: $("#planid").val()
        };
        return temp;
    };
    return oTablePlan;
};
