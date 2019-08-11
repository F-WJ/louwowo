<html lang="en">
<head>
    <title>攻略编辑</title>
<#include "../common/header.ftl"/>
    <link rel="stylesheet" type="text/css" href="/js/plugins/bootstrap-select/bootstrap-select.css"/>
    <script src="/js/plugins/bootstrap-select/bootstrap-select.js"></script>

    <link type="text/css" rel="stylesheet" href="/js/plugins/uploadifive/uploadifive.css" />
    <script type="text/javascript" src="/js/plugins/uploadifive/jquery.uploadifive.min.js"></script>

    <link type="text/css" rel="stylesheet" href="/js/plugins/bootstrap-tagsinput/bootstrap-tagsinput.css" />
    <script type="text/javascript" src="/js/plugins/bootstrap-tagsinput/bootstrap-tagsinput.js"></script>
    <script src="/js/ckeditor/ckeditor.js"></script>
    <script>

        //表单提交验证
        $(function () {


            //富文本框图片配置
            var ck = CKEDITOR.replace( 'content',{
                filebrowserBrowseUrl: '/图片服务器，假装这里有',
                filebrowserUploadUrl: '/uploadImg_ck'
            });

            //图片上传
            $('.imgBtn').uploadifive({
                'auto' : true,
                'uploadScript' : '/uploadImg',
                buttonClass:"btn-link",
                'fileObjName' : 'pic',
                'buttonText' : '浏览...',
                'fileType' : 'image',
                'multi' : false,
                'fileSizeLimit'   : 5242880,
                'removeCompleted' : true, //取消上传完成提示
                'uploadLimit' : 1,
                //'queueSizeLimit'  : 10,
                'overrideEvents': ['onDialogClose', 'onError'],    //onDialogClose 取消自带的错误提示
                'onUploadComplete' : function(file, data) {
                    $("#imgUrl").attr("src" , "/" + data);
                    $("#coverUrl").val("/" + data);

                },
                onFallback : function() {
                    $.messager.alert("温馨提示","该浏览器无法使用!");
                }
            });

            $("#strategy").change(function () {
                var strategyId = $(this).val();

                if(strategyId == -1){
                    $('#catalog').html('<option value="-1">--请选择--</option>');
                    return;
                }

                $.get("/strategyDetail/getCatalogByStrategyId", {strategyId:strategyId}, function (data) {

                    var html = ''

                    $.each(data, function (index, item) {
                        html += '<option value='+item.id+'>' + item.name +'</option>'
                    })


                    $('#catalog').html(html);

                })
            })
            /*
            //追加标签
            $("#tagsinputval").tagsinput('add','aaa,bbb,ccc,ddd');

            //移除标签
            $("#tagsinputval").tagsinput('remove','aaa');

            //移除所有标签
            $("#tagsinputval").tagsinput('removeAll');

            //刷新标签
            $("#tagsinputval").tagsinput('refresh');

            */


            //保存
            $("#btn_submit").click(function () {

                //异步提交时， 富文本框可能出问题
                $("#content").val(ck.getData())

                $("#editForm").ajaxSubmit(function (data) {
                    console.log(data);
                    if(data.success){
                        window.location.href = "/strategyDetail/list";
                    }else{
                        $.messager.alert("温馨提示", data.msg);
                    }
                })
            })


            //回显
            $.get("/strategyDetail/getCatalogByStrategyId", {strategyId:$("#strategy").val()}, function (data) {

                var html = ''

                $.each(data, function (index, item) {
                    html += '<option value='+item.id+'>' + item.name +'</option>'
                })
                $('#catalog').html(html);
                $("#catalog").val(${(strategyDetail.catalog.id)!});



            })
        })


    </script>
</head>
<body>
<!--设置菜单回显-->
<#assign currentMenu = 'strategyDetail'>
<div class="container-fluid " style="margin-top: 20px">
<#include "../common/top.ftl"/>
    <div class="row">
        <div class="col-sm-2">
        <#include "../common/menu.ftl"/>
        </div>
        <div class="col-sm-10">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">攻略编辑</h1>
                </div>
            </div>
            <div class="row col-sm-10">
                <form class="form-horizontal" action="/strategyDetail/saveOrUpdate" method="post" id="editForm">
                    <input type="hidden" value="${(strategyDetail.id)!}" name="id">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">标题：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="title" name="title" value="${(strategyDetail.title)!}" placeholder="请输入攻略标题">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">副标题：</label>
                        <div class="col-sm-8">
                            <input type="text" class="form-control" id="subTitle" name="subTitle"  value="${(strategyDetail.subTitle)!}" placeholder="请输入攻略副标题">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">封面：</label>
                        <div class="col-sm-8">
                            <input type="hidden"  class="form-control" id="coverUrl"  name="coverUrl" value="${(strategyDetail.coverUrl)!}" >
                            <img src="${(strategyDetail.coverUrl)!'/images/default.jpg'}" width="100px" id="imgUrl">
                            <button type="button" class="imgBtn">浏览</button>
                        </div>

                    </div>

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">所属攻略：</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="strategy" name="strategy.id">
                                <option value="-1">--请选择--</option>
                                <#list strategies as s>
                                    <option value="${s.id}">${s.title}</option>
                                </#list>
                            </select>
                        </div>

                        <script>
                            $("#strategy").val(${(strategyDetail.strategy.id)!});
                        </script>
                    </div>

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">分类：</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="catalog" name="catalog.id">
                                <option value="-1">--请选择--</option>

                            </select>
                        </div>
                        <script>
                            $("#catalog").val(${(strategyDetail.catalog.id)!});
                        </script>
                    </div>


                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">主题：</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="theme" name="theme.id">
                                <option value="-1">--请选择--</option>
                                <#list themes as t>
                                    <option value="${t.id}">${t.name}</option>
                                </#list>
                            </select>

                            <script>
                                $("#theme").val(${(strategyDetail.theme.id)!});
                            </script>
                        </div>
                    </div>



                    <div class="form-group">
                        <label for="dept" class="col-sm-2 control-label">状态：</label>
                        <div class="col-sm-8">
                            <select class="form-control" id="state" name="state">
                                <option value="0">正常</option>
                                <option value="1">发布</option>
                            </select>
                        </div>

                        <script>
                            $("#state").val(${(strategyDetail.state)!});
                        </script>
                    </div>

                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">标签：</label>
                        <div class="col-sm-8">
                            <input name="tags" id="tagsinputval" class="form-control tagsinput" value="${tags!}"
                                   data-role="tagsinput"
                                   placeholder="输完后按回车"
                            />
                        </div>
                    </div>
                    <div class="form-group">
                        <textarea id="content" name="strategyContent.content" class="ckeditor">${(strategyDetail.strategyContent.content)!}</textarea>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-8">
                            <button id="btn_submit" class="btn btn-primary" type="button">保存</button>
                            <button type="reset" class="btn btn-danger">重置</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>