<html lang="en">
<head>
    <title>攻略管理</title>
    <#include "../common/header.ftl">
    <link rel="stylesheet" type="text/css" href="/js/plugins/bootstrap-select/bootstrap-select.css"/>
    <script src="/js/plugins/bootstrap-select/bootstrap-select.js"></script>

    <link type="text/css" rel="stylesheet" href="/js/plugins/uploadifive/uploadifive.css" />
    <script type="text/javascript" src="/js/plugins/uploadifive/jquery.uploadifive.min.js"></script>


    <script type="text/javascript">
        $(function () {
            var dest;
            $.get("/strategy/getDestByDeep", {deep:3}, function (data) {
                var html = '';
                $.each(data, function (index, item) {
                    html += '<option value="' + item.id + '">'+item.name+'</option>'
                })
                dest = html;
            })


            //图片上传
            $('.imgBtn').uploadifive({
                'auto' : true,    //自动提交
                'uploadScript' : '/uploadImg',  //图片上传过来处理路径(接受)
                buttonClass:"btn-link",  //按钮的样式
                'fileObjName' : 'pic',  // 图片文件上传之后, 在springmvc 映射方法接受的参数名
                'buttonText' : '浏览...',  //按钮上面文字
                'fileType' : 'image',   //指定上传的图片
                'multi' : false,   //是否为多文件上传, 此处设置单文件
                'fileSizeLimit'   : 5242880,  //文件上传大小
                'removeCompleted' : true, //取消上传完成提示
                'uploadLimit' : 1,  //一次允许上传文件个数
                //'queueSizeLimit'  : 10,  //文件上传等待队列大小
                'overrideEvents': ['onDialogClose', 'onError'],    //onDialogClose 取消自带的错误提示
                'onUploadComplete' : function(file, data) {

                    //data 请求方法处理完之后, 传回来的数据: 约定: 图片的路径(url)
                    $("#imgUrl").attr("src" , "/" + data);
                    $("#coverUrl").val("/" + data);

                },
                onFallback : function() {
                    $.messager.alert("温馨提示","该浏览器无法使用!");
                }
            });

            //编辑/添加
            $(".inputBtn").click(function () {
                //数据复原
                $("#editForm").clearForm(true);
                $("#dest").html(dest);
                $('#dest').selectpicker('refresh');

                //攻略回显数据
                var data = $(this).data("json");
                if(data){
                    console.log(data);
                    $("#editForm input[name='id']").val(data.id);
                    $("#editForm input[name='name']").val(data.name);
                    $("#editForm input[name='sequence']").val(data.sequence);
                    $("#editForm input[name='title']").val(data.title);
                    $("#editForm input[name='subTitle']").val(data.subTitle);
                    $("#editForm select[name='state']").val(data.state);


                    $("#imgUrl").attr("src" ,data.coverUrl);
                    $("#coverUrl").val(data.coverUrl);

                    $('#dest').selectpicker('val',data.destId );
                    $('#dest').selectpicker('refresh');


                }
                //弹出模态框
                $("#editModal").modal("show");

            })
            
            $(".submitBtn").click(function () {
                //模态框表单提交
                $("#editForm").ajaxSubmit(function (data) {
                    if(data.success){
                        window.location.reload();
                    }else{
                        $.messager.alert("温馨提示", data.msg)
                    }
                })
            })

            //查询
            $(".clickBtn").click(function () {
                $("#currentPage").val(1);
                $("#searchForm").submit();
            })


        })
    </script>
</head>
<body>
<!--左侧菜单回显变量设置-->
<#assign currentMenu="strategy">

<div class="container-fluid " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-2">
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-10">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">攻略管理</h1>
                </div>
            </div>
            <#setting number_format="#">
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/strategy/list" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <a href="JavaScript:;" class="btn btn-success inputBtn"><span class="glyphicon glyphicon-plus"></span>  添加</a>
            </form>
            <table class="table table-striped table-hover" >
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>封面</th>
                        <th>标题</th>
                        <th>目的地</th>
                        <th>状态</th>
                        <th>排序</th>
                        <th>操作</th>
                    </tr>
                </thead>
               <#list pageInfo.list as entity>
                   <tr>
                       <td>${entity_index+1}</td>
                       <td><img src="${entity.coverUrl!}" width="50px"></td>
                       <td>${entity.title!}</td>
                       <td>${(entity.dest.name)!}</td>
                       <td>${entity.stateDisplay!}</td>
                       <td>${entity.sequence!}</td>
                       <td>

                           <a class="btn btn-info btn-xs inputBtn" href="javascript:;" data-json='${entity.jsonString}'>
                               <span class="glyphicon glyphicon-edit"></span> 编辑
                           </a>
                           <a href="javascript:;" class="btn btn-danger btn-xs deleteBtn"
                              data-url="/strategy/delete?id=${entity.id}">
                               <span class="glyphicon glyphicon-trash"></span> 删除
                           </a>
                       </td>
                   </tr>
               </#list>
            </table>
            <#--分页-->
            <#include "../common/page.ftl"/>
        </div>
    </div>
</div>


<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span> </button>
                <h4 class="modal-title">攻略添加/编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/strategy/saveOrUpdate" method="post" id="editForm">
                    <input type="hidden" value="" name="id">
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">标题：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="title" name="title"  placeholder="请输入攻略标题">
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">副标题：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="subTitle" name="subTitle"  placeholder="请输入攻略副标题">
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">关联目的地：</label>
                        <div class="col-sm-6">
                            <select class="form-control selectpicker "  id="dest" name="dest.id" data-live-search="true" title="请选择关联的目的地">
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">封面：</label>
                        <div class="col-sm-6">
                            <input type="hidden"  class="form-control" id="coverUrl"  name="coverUrl" value="" >
                            <img src="/images/default.jpg" width="100px" id="imgUrl">
                            <button type="button" class="imgBtn">浏览</button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">状态：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="state" name="state">
                                <option value="0">普通</option>
                                <option value="1">推荐</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label  class="col-sm-3 control-label">排序：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sequence" name="sequence" placeholder="请输入序号">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary submitBtn" >保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>