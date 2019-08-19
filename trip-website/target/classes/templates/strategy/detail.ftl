<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link href="/styles/base.css" rel="stylesheet" type="text/css">
    <link href="/styles/strategyDetail.css" rel="stylesheet" type="text/css">

    <link rel="stylesheet" href="/js/plugins/zebrazooltips/public/css/zebra_tooltips.css" type="text/css">
    <link rel="stylesheet" href="/js/plugins/sns_share/css/style.css" type="text/css">



    <script type="text/javascript" src="/js/jquery/jquery.js"></script>

    <#--    分享-->
    <script type="text/javascript" src="/js/plugins/sns_share/js/xuanfeng_sns_share.js"></script>

    <script type="text/javascript" src="/js/plugins/jquery-form/jquery.form.js"></script>
    <link href="/js/plugins/jqPaginator/jqPagination.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/plugins/jqPaginator/jq-paginator.min.js"></script>
    <script type="text/javascript" src="/js/system/strategyDetail.js"></script>
    <script type="text/javascript" src="/js/system/common.js"></script>

    <script type="text/javascript" src="/js/plugins/zebrazooltips/public/javascript/zebra_tooltips.js"></script>


    <!-- 引入依赖 -->
<#--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/social-share.js/1.0.16/css/share.min.css">-->
<#--    <script src="https://cdnjs.cloudflare.com/ajax/libs/social-share.js/1.0.16/js/social-share.min.js"></script>-->






    <script>

        // 分享
        $(function(){
            var shareTitle = $(".post_title h1").text()+"ONEDULL";
            var sinaTitle = '分享文章 「' + shareTitle + '」 （分享自FWJ）',
                renrenTitle = '分享文章 「' + shareTitle + '」（分享自FWJ)）',
                tqqTitle = '分享文章 「' + shareTitle + '」（分享自FWJ）',
                tqzoneTitle = '分享文章 「' + shareTitle + '」-ONEDULL（分享自FWJ）';
            var picShare = encodeURIComponent($(".post_title").data("thumb"));

            $('body').xuanfengSnsShare({
                tsina:{
                    url : encodeURIComponent(window.location.href),
                    title: sinaTitle,
                    pic: picShare
                },
                renren:{
                    url : encodeURIComponent(window.location.href),
                    title: renrenTitle,
                    pic: picShare
                },
                tqq:{
                    url : encodeURIComponent(window.location.href),
                    title: tqqTitle,
                    pic: picShare
                },
                tqzone:{
                    url : encodeURIComponent(window.location.href),
                    title: tqzoneTitle,
                    pic: picShare
                }
            });

            // 微信分享
            $(".share_weixin").click(function(){
                $("#ewmimg").remove();
                var thisURL = window.location.href,
                    strwrite = "<img id='ewmimg' class='ewmimg' src='https://chart.googleapis.com/chart?cht=qr&chs=150x150&choe=UTF-8&chld=L|4&chl=" + thisURL + "' width='85' height='85' alt='轩枫阁文章 二维码分享' />";
                $("#ewm").prepend(strwrite);
                $("#wemcn").show();
            });
            $("#ewmkg").click(function(){
                $("#wemcn").hide();
            });

        });








       $(function () {
           //评论
           $("#searchForm").ajaxForm(function (data) {
               $("#strategyComment").html(data);
           });
           $("#searchForm").submit();




           //没有登录弹出显示信息
           $(document).ready(function() {
               new $.Zebra_Tooltips($('.zebra_tips1'));
           });

           //发表评论
           $("#commentBtn").click(function () {

               //判断是否已经登录
               var user = $(this).data("user");
               console.log(user);
               if(user == "") {
                   window.location.href = "/login.html";
               }else {

                   var content = $("#content").val();
                   if (!content) {
                       popup("评论内容必填");
                       return;
                   }
                   $("#content").val('');
                   var detailId = $(this).data("detailid");
                   var detailTitle = $(this).data("title");

                   $.post("/strategy/commentAdd", {
                       detailId: detailId,
                       content: content,
                       detailTitle: detailTitle
                   }, function (data) {
                       if (data.success) {
                           //修改评论数
                           $(".replay_num").html(data.data);

                           $("#currentPage").val(1);
                           $("#searchForm").submit();  //重新插一次
                       }
                   });
               }
           })

           //顶：点赞
           $("._j_support_btn").click(function () {

               var sid = $(this).data("sid");
               $.get("/strategy/strategyThumbup", {sid:sid}, function (data) {
                   if(data.success){
                       $(".support_num").html(data.data.thumbsupnum);
                       popup("顶成功啦"); //

                   }else{
                       if(data.code == 102){
                           popup(data.msg);
                       }else{
                           popup("今天你已经定过了"); //
                       }

                   }
               });
           })
           
           //评论
           $("._j_goto_comment").click(function () {
               $("#content").focus();
           })

           //收藏
           $(".btn-collect").click(function () {
               var sid = $(this).data("sid");
               $.get("/strategy/favor", {sid:sid}, function (data) {
                   console.log(data);

                   if(data.success){
                       $(".favorite_num").html(data.data.favornum);
                       $(".collect_icon").addClass("on-i02")

                       popup("收藏成功"); //
                   }else{
                       if(data.code == 102){
                           $(".collect_icon").removeClass("on-i02")
                           popup(data.msg);
                       }else{
                           $(".collect_icon").removeClass("on-i02")
                           $(".favorite_num").html(data.data.favornum);
                           popup("已取消收藏"); //
                       }
                   }
               });
           })



       })


       // 分享













       //分享
       $(document).ready(function(){
           $(".btn-share").mousedown(function(){
               $(".pop").slideToggle();
           });
       });

       $(function () {
           console.log(window.location.href);
       })

    </script>
</head>

<body>
<#assign currentNav="strategy">
    <#include "../common/navbar.ftl">
<div class="wrap clearfix">

    <div class="local-con clearfix">
        <div class="sideL">
            <div class="l-topic">
                <h1>${detail.title!}</h1>
                <div class="sub-tit">
                    <i class="i-zan"></i>
                    51人体验过
                    <span class="time" style="margin-left: 20px;"><em>阅读 ${(vo.viewnum)!0}</em></span>
                    <span class="time">旅游攻略<em>${(detail.createTime?string("yyyy-MM-dd"))!}</em></span>
                </div>






                <div id="sns_share" class="cf">
                    <span class="sns_share_to fl">分享到：</span>
                    <a class="share_weixin share_icon fl" href="javascript:;" title="查看本文二维码，分享至微信"><em>二维码</em></a>
                    <a class="share_tsina share_icon fl" href="javascript:;" title="分享到新浪微博"><em>新浪微博</em></a>
                    <a class="share_tqq share_icon fl" href="javascript:;" title="分享到腾讯微博"><em>腾讯微博</em></a>
                    <a class="share_renren share_icon fl" href="javascript:;" title="分享到人人网"><em>人人网</em></a>
                    <a class="share_tqzone share_icon fl" href="javascript:;" title="分享到QQ空间"><em>QQ空间</em></a>

                    <div class="wemcn" id="wemcn">
                        <div id="ewm" class="ewmDiv clearfix">
                            <div class="rwmtext">
                                <p>扫一扫，用手机观看！</p>
                                <p>用微信扫描还可以</p>
                                <p>分享至好友和朋友圈</p>
                            </div>
                        </div>
                        <a class="share_icon" href="javascript:void(0)" id="ewmkg"></a>
                        <i class="ewmsj share_icon"></i>
                    </div>
                </div>


                <div class="user_list">
                    <div class="clearfix">
                        <div class="author">
                            <a href="javascript:;" target="_blank">
                                <img src="https://p3-q.mafengwo.net/s13/M00/AB/00/wKgEaVy2nheAN9y5AAorszCM1vQ56.jpeg?imageMogr2%2Fthumbnail%2F%21120x120r%2Fgravity%2FCenter%2Fcrop%2F%21120x120%2Fquality%2F90" alt="" width="90" height="90">
                            </a>
                        </div>
                        <div class="info">
                            <div class="in-t">
                                <a href="javascript:;" target="_blank">
                                    <span class="name">${(detail.author.username)!}</span>
                                </a>
                                <span class="more">6篇游记 429个粉丝</span>
                            </div>
                            <p>
                                人有欢乐，也有苦衷。所谓的完美，其实只是来源于我们的心灵。人生不能重新来过，每个人也不可能重复站在同一个路口。但请不要害怕选择，因为选择没有绝对的好与坏，每种选择都会为你带来一种不一样的感受和别样的精彩。
                            </p>
                        </div>


                        <div class="contact user-home">
                            <a href="javascript:;" target="_blank">
                                <i></i>
                                <p>TA的窝</p>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="l-topic">
                <div class="_j_content">
                ${(detail.strategyContent.content)!}
                </div>
            </div>
            <div class="copyRight m_t_20">
                <p style="text-align: left;">本文著作权归 骡窝窝 所有，任何形式转载请联系作者。©2019 骡窝窝自由行
                    <a class="r-report" style="display:inline;float: right;color: #999;">举报</a>
                </p>
            </div>
            <div class="l-comment">
                <div class="clearfix com-form">
                    <div class="img"><img
                            src=" http://n1-q.mafengwo.net/s12/M00/35/98/wKgED1uqIreAU9QZAAAXHQMBZ74008.png?imageMogr2%2Fthumbnail%2F%2148x48r%2Fgravity%2FCenter%2Fcrop%2F%2148x48%2Fquality%2F100 ">
                    </div>
                    <div class="fm-tare user-log">
                        <textarea class="_j_comment_content" id="content"></textarea>

                        <#if userInfo??>
                        <button type="button" class="_j_save_comment" id="commentBtn" data-detailid="${detail.id!}"
                           data-title="${detail.title}"
                        >评论</button>
                            <#else>
                                <button type="button" class="_j_save_comment zebra_tips1" title="亲，请登录账户，可以点击一下评论按钮进入登录页面" id="commentBtn" data-detailid="${detail.id!}"
                                        data-title="${detail.title}"  data-user = "${userInfo!}"
                                >评论</button>
                        </#if>

                    </div>
                </div>
                <!--评论-->
                <form id = "searchForm" action="/strategy/comment" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <input type="hidden" name="detailId"  value="${detail.id!}">
                    <div class="com-box " id="strategyComment">

                    </div>
                </form>
            </div>
        </div>
        <div class="sideR">
            <div class="side_inner _j_sticky_block">
                <div class="_j_other_column">
                    <div class="bar-sar clearfix">
                        <a href="javascript:;" class="_j_goto_comment" title="评论"><i class="i01"></i><em class="replay_num">${(vo.replynum)!0}</em></a>
                        <div class="bs_collect">
                            <a href="javascript:void(0);" title="收藏" class="bs_btn btn-collect" data-sid="${detail.id!}"><i
                                    class="collect_icon i02 ${(isFavor?string('on-i02',''))!}" data-uid="53383161"></i>
                                <em class="favorite_num ">${(vo.favornum)!0}</em>
                            </a>
                        </div>
                        <div class="bs_share">
                            <a href="javascript:;" title="分享" class="btn-share bs_btn"><i
                                    class="i03"></i><em>${(vo.sharenum)!0}</em></a>
                        </div>

                        <a href="javascript:;" class="_j_support_btn" title="顶" data-sid="${detail.id!}"><i class="i05 "></i><em
                                class="support_num">${(vo.thumbsupnum)!0}</em></a>
                    </div>

                </div>
                <!-- <p class="title" id="_j_catalogtitle">攻略目录</p> -->
            </div>



            <div class="side-sales">



            </div>



            <div class="side-sales">
                <h3>本周热卖</h3>
                <ul>
                    <li>
                        <a href="javascript:;" target="_blank">
                                <span class="image"><img
                                        src="/wKgBEFs6E4yAPz00AAhnvJUJ1j8238.gif"></span>
                            <div class="title"
                                 title="广州长隆野生动物世界门票    随买随用 ／广州长隆旅游度假区／一票通玩 ／ 快速出票快捷入园 ／ 含空中览车及小火车/亲子乐园/赠送电子导览／自然零距离／广州长隆野生动物园">
                                广州长隆野生动物世界门票 随买随用 ／广州...</div>
                            <span class="price">¥260</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;" target="_blank">
                                <span class="image"><img
                                        src="/wKgED1wweM2AVCMFAAzr37WPWDI967.gif"></span>
                            <div class="title"
                                 title="广州长隆欢乐世界门票   当天可买／广州长隆旅游度假区／快速出票快捷入园／收藏店铺送卷送攻略／广东番禺汉溪长隆/长隆成人票／情侣票／家庭票／双人票／儿童票">
                                广州长隆欢乐世界门票 当天可买／广州长隆旅...</div>
                            <span class="price">¥187</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;" target="_blank">
                                <span class="image"><img
                                        src="/wKgED1wdwVmAVaZUADon6OL7_xw084.gif"></span>
                            <div class="title" title="当天可订/广州长隆野生动物世界门票/长隆野生动物园/广州长隆旅游度假区/含缆车小火车/南北门均可取票（提前1天规则退）">
                                当天可订/广州长隆野生动物世界门票/长隆野生...</div>
                            <span class="price">¥256</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;" target="_blank">
                                <span class="image"><img
                                        src="/wKgBEFrEdj-Ac-nXAAOvgGsSLJI85.jpeg"></span>
                            <div class="title" title="广州长隆水上乐园门票 一票通玩（电子票／当地必玩／免预约／超级大喇叭/热浪谷/意想不到的水上乐园）">广州长隆水上乐园门票
                                一票通玩（电子票／当地...</div>
                            <span class="price">¥122</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:;" target="_blank">
                                <span class="image"><img
                                        src="/wKgED1wk2LeAC2NJAAJQtab6Yqw67.jpeg"></span>
                            <div class="title" title="寻味广州1日游（6人小团·探黄埔军校陈家祠·电车看广州塔+沙面·西关美食秘籍·本地人带玩）">
                                寻味广州1日游（6人小团·探黄埔军校陈家祠·...</div>
                            <span class="price">¥288</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<#include "../common/footer.ftl">
</body>

</html>