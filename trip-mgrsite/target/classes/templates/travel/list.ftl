<html lang="en">
<head>
    <title>游记管理</title>
    <#include "../common/header.ftl">
    <script type="text/javascript">
        $(function () {

            //查看文章按钮
            $(".lookBtn").click(function () {
                var id = $(this).data("id");
                //发送ajax请求查询游记内容
                $.get('/travel/getContentById',{id:id},function (data) {
                    $(".modal-body").html(data.content);
                })

                $("#contentModal").modal('show');
            })



            //查询
            $(".clickBtn").click(function () {
                var parentId = $(this).data("parentid");
                $("#currentPage").val(1);
                $("#searchForm").submit();
            })


            //发布： 改状态
            $(".changeStateBtn").click(function () {
                var state = $(this).data("state");
                var id = $(this).data("id");

                $.get("/travel/changeState", {state:state, id:id}, function (data) {
                    if(data.success){
                        window.location.reload();
                    }else{
                        $.messager.alert("温馨提示", data.msg);
                    }
                })

            })
        })
    </script>
</head>
<body>
<!--左侧菜单回显变量设置-->
<#assign currentMenu="travel">

<div class="container-fluid " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-2">
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-10">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">游记管理</h1>
                </div>
            </div>
            <#setting number_format="#">
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/travel/list" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <input type="hidden" name="parentId" id="parentId" value="${qo.parentId!}">
                <div class="form-group">
                    <label for="keyword">关键字:</label>
                    <input type="text" class="form-control" id="keyword" name="keyword" value="${(qo.keyword)!''}" placeholder="请输入名称">
                </div>
                <div class="form-group">
                    <label for="dept">状态:</label>
                    <select class="form-control" id="ishot" name="state">
                        <option value="-1">全部</option>
                        <option value="1">待发布</option>
                        <option value="2">发布</option>
                        <option value="3">拒绝</option>
                    </select>
                    <script>
                        $("#state").val(${qo.state!});
                    </script>
                </div>

            </form>

            <table class="table table-striped table-hover" >
                <thead>
                    <tr>
                        <th>编号</th>
                        <th>标题</th>
                        <th>封面</th>
                        <th>地点</th>
                        <th>作者</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
               <#list pageInfo.list as entity>
                   <tr>
                       <td>${entity_index+1}</td>
                       <td>${entity.title!}</td>
                       <td><img src="${entity.coverUrl!}" width="100px"> </td>
                       <td>${entity.dest.name!}</td>
                       <td>${(entity.author.nickname)!}</td>
                       <td>${(entity.stateDisplay)!}</td>
                       <td>

                           <#if  entity.state == 2>
                            <a class="btn btn-danger btn-xs changeStateBtn" href="javascript:;" data-state='1' data-id="${entity.id}">
                                <span class="glyphicon glyphicon-minus-sign"></span> 下架
                            </a>

                           <#else>
                            <a class="btn btn-default btn-xs changeStateBtn" href="JavaScript:;" data-state="2" data-id="${entity.id}">
                                <span class="glyphicon glyphicon-tag"></span> 发布
                            </a>
                           </#if>


                           <a class="btn btn-info btn-xs inputBtn  lookBtn" href="#" data-id="${entity.id}">
                               <span class="glyphicon glyphicon-edit"></span> 查看
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





    <div class="modal fade" id="contentModal" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">文章内容</h4>
                </div>
                <div class="modal-body">
                    网络问题，请联系你当前移动网络的运营商
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</body>
</html>