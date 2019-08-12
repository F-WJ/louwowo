<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link href="/styles/base.css" rel="stylesheet" type="text/css">
    <link href="/styles/travelnotes.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/system/travelnotes.js"></script>
    <link href="/js/plugins/jqPaginator/jqPagination.css" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="/js/plugins/zebrazooltips/public/css/zebra_tooltips.css" type="text/css">
    <script type="text/javascript" src="/js/plugins/jqPaginator/jq-paginator.min.js"></script>

    <script type="text/javascript" src="/js/plugins/zebrazooltips/public/javascript/zebra_tooltips.js"></script>


</head>

<script>
    $(function () {

        //编写游记检测是否登录
        $(".input-login").click(function () {
            //获取当前用户
            var user = $(this).data("user");
            console.log(user);
            if(user != ""){
                window.location.href = "/travel/input";
            }else {
                new $.Zebra_Tooltips($('.zebra_tips1'),{
                    'background_color': 'rgba(83,149,196,0.13)',
                    'color':            '#ffac52',
                    'position':     'right',
                });
            }



        })

        //没有登录弹出显示信息
        $(document).ready(function() {
            new $.Zebra_Tooltips($('.zebra_tips1'));
        });


        //游记分页
        $(".com-opt").change(function () {
            $("#currentPage").val(1);
            $("#searchForm").submit();
        })
        $(".orderBy").click(function () {
            $(".orderBy").closest("div").removeClass("on");
            $(this).addClass("on");
            $("#currentPage").val(1);
            $("#orderType").val($(this).data("sort"))
            $("#searchForm").submit();
        })






    })
</script>


<body>


    <#assign currentNav="travel">
    <#include "../common/navbar.ftl">
    <div class="wrapper" style="padding-top: 10px">
        <div class="p-content">
            <div class="m-post">
                <form action="/travel" method="post" id="searchForm">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <input type="hidden" name="orderType" id="orderType" value="${qo.orderType!1}">
                    <div class="post-tab clearfix">
                        <div class="tab-item on"><a href="javascript:;" class="orderBy" data-sort="1" style="margin-right: 20px;">最新游记</a></div>
                        <div class="tab-item "><a href="javascript:;"  class="orderBy" data-sort="2" >最热游记</a></div>
                    </div>
                <div class="navbar-sub clearfix">
                    出发时间：
                    <select class="com-opt" name="travelTimeType" id="travelTimeType">
                        <option value="-1">不限</option>
                        <option value="1">1-2月</option>
                        <option value="2">3-4月</option>
                        <option value="3">5-6月</option>
                        <option value="4">7-8月</option>
                        <option value="5">9-10月</option>
                        <option value="6">11-12月</option>
                    </select>

                    人均花费：
                    <select  class="com-opt" name="perExpendType" id="perExpendType">
                        <option value="-1">不限</option>
                        <option value="1">1-999</option>
                        <option value="2">1K-6K</option>
                        <option value="3">6K-20K</option>
                        <option value="4">20K以上</option>
                    </select>

                    出行天数：
                    <select  class="com-opt" name="dayType" id="dayType">
                        <option value="-1">不限</option>
                        <option value="1">3天以下</option>
                        <option value="2">4-7天</option>
                        <option value="3">8-14天</option>
                        <option value="4">15天以上</option>
                    </select>

                        <#if userInfo?? >
                            <div class="tn-write"><a class="btn-add input-login" target="_blank" data-user = ${(userInfo)!}><i></i>写游记</a></div>
                        <#else>


                            <div class="tn-write">
                                <a class="btn-add input-login zebra_tips1" title="亲，请登录账户，或者点击写游记按钮进入登录页面" target="_blank" href="/login.html" data-user = ${(userInfo)!}><i></i>写游记
                                </a>
                            </div>
                        </#if>




                    <script>
                        $("#travelTimeType").val(${qo.travelTimeType!});
                        $("#dayType").val(${qo.dayType!});
                        $("#perExpendType").val(${qo.perExpendType!});

                        if(${qo.orderType!} != -1){
                            $(".orderBy").closest("div").removeClass("on");
                            $(".orderBy[data-sort=${qo.orderType!}]").closest("div").addClass("on");
                        }

                    </script>
                </div>
                </form>
                <div class="_notelist">
                    <div class="post-list">
                        <ul>
                            <#list pageInfo.list as t>
                            <li class="post-item clearfix">
                                <div class="post-cover">
                                    <a href="/travel/detail?id=${t.id!}" target="_blank">
                                        <img class="lazy" width="215" height="135" alt="${t.title!}" src="${t.coverUrl!}" style="display: inline;"></a>

                                </div>
                                <div class="post-ding"><span id="topvote11710590">${t.thumbsupnum!}</span><a class="btn-ding" href="javascript:;" data-japp="articleding" data-iid="11710590" data-vote="2067">顶</a></div>
                                <h2 class="post-title yahei hasxjicon">

                                    <a href="/travel/detail?id=${t.id!}" class="title-link" target="_blank">·${t.title!}</a>
                                </h2>
                                <div class="post-author">
                                    <span class="author">
                                        <a href="javascript:;" target="_blank" rel="nofollow"><img class="lazy"
                                                src="${(t.author.headImgUrl)!'/images/default.jpg'}"
                                                style="display: inline;" width="15px"></a>
                                        作者：<a href="javascript:;" target="_blank" rel="nofollow">${(t.author.nickname)!}</a>
                                    </span>
                                </div>
                                <div class="post-content">
                                    ${t.summary!}
                                </div>
                                <span class="status"><i class="icon_view"></i>${t.viewnum!}<i
                                        class="icon_comment"></i>${t.replynum!}</span>
                            </li>
                            </#list>
                        </ul>
                    </div>
                    <#include "../common/page.ftl">
                </div>
            </div>
        </div>
        <div class="p-aside">
            <div class="p-top1 clearfix">
                <div class="hd">
                    <h2><i></i>旅游攻略</h2>
                </div>
                <ol>
                    <li class="top3">
                        <em class="rank-num">1</em>
                        <strong>
                            <a href="javascript:;" target="_blank">北海道</a>
                        </strong>
                        <a href="javascript:;" target="_blank">在皑皑白雪中泡温泉</a>
                    </li>
                    <li class="top3">
                        <em class="rank-num">2</em>
                        <strong>
                            <a href="javascript:;" target="_blank">北海道</a>
                        </strong>
                        <a href="javascript:;" target="_blank">在皑皑白雪中泡温泉</a>
                    </li>
                    <li class="top3">
                        <em class="rank-num">3</em>
                        <strong>
                            <a href="javascript:;" target="_blank">北海道</a>
                        </strong>
                        <a href="javascript:;" target="_blank">在皑皑白雪中泡温泉</a>
                    </li>
                    <li>
                        <em class="rank-num">4</em>
                        <strong>
                            <a href="javascript:;" target="_blank">北海道</a>
                        </strong>
                        <a href="javascript:;" target="_blank">在皑皑白雪中泡温泉</a>
                    </li>
                    <li>
                        <em class="rank-num">5</em>
                        <strong>
                            <a href="javascript:;" target="_blank">北海道</a>
                        </strong>
                        <a href="javascript:;" target="_blank">在皑皑白雪中泡温泉</a>
                    </li>
                </ol>
            </div>
            <div class="m-box m-comm">
                <div class="hd">
                    <h2><i></i>最新游记动态</h2>
                </div>
                <div class="bd">
                    <ul id="_j_new_action_list" style="height: auto; overflow:hidden">

                       <#-- <#list tcs as tc>
                            <li data-action_id="19886923">
                                <a href="javascript:;" target="_blank" rel="nofollow">
                                    <img src="${tc.headUrl!}"  rel="nofollow">${tc.username!}
                                </a>刚刚点评了
                                <a href="javascript:;" target="_blank" title="新加坡植物园(Singapore Botanic Gardens)">

                                    <#if tc.travelTitle?length <5 >
                                        ${tc.travelTitle}
                                    <#else>
                                        ${tc.travelTitle?substring(0, 5)}...
                                    </#if>

                                </a>
                                <p>
                                    <#if tc.content?length <10 >
                                        ${tc.content}
                                    <#else>
                                        ${tc.content?substring(0, 10)}...
                                    </#if>
                                </p>
                            </li>
                        </#list>-->
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <#include "../common/footer.ftl"/>
</body>

</html>