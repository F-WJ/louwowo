<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link href="/styles/base.css" rel="stylesheet" type="text/css">
    <link href="/styles/travelnotedetail.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery-form/jquery.form.js"></script>
    <script type="text/javascript" src="/js/system/travelnotedetail.js"></script>
    <script type="text/javascript" src="/js/system/emoji.js"></script>

    <style>
        .vc_articleT  img{
            width: 100%;
        }
    </style>

    <script>

        function emoji(str) {
            //匹配中文
            var reg = /\([\u4e00-\u9fa5A-Za-z]*\)/g;
            var matchArr = str.match(reg);
            if(!matchArr){
                return str;
            }
            for(var i = 0; i < matchArr.length; i++){
                str = str.replace(matchArr[i], '<img src="'+EMOJI_MAP[matchArr[i]]+'"style="width: width:28px;"/>')
            }
            return str;
        }


        $(function () {
            var index = 0;
            //回复
            $("#_j_reply_list").on("click", ".replyBtn",function () {
                var touser = $(this).data("touser");
                var toid = $(this).data("toid");
                $("#commentContent").focus();
                $("#commentContent").attr("placeholder","回复：" + touser );

                $("#commentType").val(1);
                $("#refCommentId").val(toid);
            })

            //发表回复
            $(".commentBtn").click(function () {
                if(!$("#commentContent").val()){
                    alert("评论不能为空");
                    return;
                }
                $("#commentForm").ajaxSubmit(function (data) {
                    $("#commentContent").val("");
                    $("#commentContent").attr("placeholder","");
                    $("#_j_reply_list").append(data);
                })
            })

            //替换表情
            var ps = $("._j_reply_content");
            console.log(ps);
            for(var i = 0;i < ps.length; i++){
               $(ps[i]).html( emoji($(ps[i]).html() + ""));
            }
        })



    </script>
</head>

<body>
    <#assign currentNav="travel">
    <#include "../common/navbar.ftl">
    <div class="main">
        <div class="set_index" id="_j_cover_box" style="height: 449.667px;">
            <<div class="set_bg _j_load_cover" style="z-index: 1; background-image: url(&quot;${detail.coverUrl!};); opacity: 1;">
                <img src="${detail.coverUrl!}" style="display: none">
            </div>
            <div class="_j_titlebg">
                <div class="title_bg"></div>
                <div class="view_info" data-length="8">
                    <div class="vi_con">
                        <h1 data-length="8" class="headtext lh80" style="white-space: nowrap; word-wrap: normal;">
                        ${detail.title!}</h1>
                    </div>
                </div>
            </div>
        </div>
        <div class="view_title clearfix">
            <div id="pagelet-block-a674ace86856fc38da868e9d1ed7b49c" class="pagelet-block">
                <div class="vt_center">
                    <div class="ding _j_ding_father">
                        <a role="button" data-japp="articleding" rel="nofollow" data-iid="12655354" data-vote="7" class="up_act " title="顶">顶</a>
                        <div class="num _j_up_num topvote12655354">${detail.thumbsupnum!}</div>
                    </div>
                    <div class="person" data-cs-t="ginfo_person_operate">
                        <a href="javascript:;" target="_blank" class="per_pic"><img width="120" height="120" src="${(detail.author.headImgUrl)!'/images/default.jpg'}"></a>
                        <strong><a href="javascript:;" target="_blank" class="per_name">${(detail.author.username)!}
                            (${(detail.dest.name)!}) </a></strong>
                        <a href="javascript:;" target="_blank" class="per_grade" title="LV.9">LV.9</a>
                        <a href="javascript:void(0);" class="per_attention" data-japp="following" data-uid="67648461" data-follow_class="hide">
                            <img src="http://images.mafengwo.net/images/home/tweet/btn_sfollow.gif" width="38" height="13" border="0" title="关注TA">
                        </a>
                        <div class="vc_time">
                            <span class="time">${detail.createTime?string("yyyy-MM-dd HH:mm:ss")}</span>
                            <span><i class="ico_view"></i>${detail.viewnum!}/${detail.starnum!}</span>
                        </div>

                    </div>

                    <div class="bar_share _j_share_father _j_top_share_group">
                        <div class="bs_collect ">
                            <a href="javascript:void(0);" rel="nofollow" title="收藏" class="bs_btn _j_do_fav" data-ctime="2019-05-07 21:16:29"><i></i><span>${detail.starnum!}</span><strong>收藏</strong></a>
                        </div>
                        <div class="bs_share">
                            <a href="javascript:void(0);" rel="nofollow" title="分享" class="bs_btn"><i></i><span>${detail.sharenum!}</span><strong>分享</strong></a>
                            <div class="bs_pop clearfix" style="display: none;">
                                <a title="分享到新浪微博" rel="nofollow" role="button" class="sina"></a>
                                <a title="分享到QQ空间" rel="nofollow" role="button" class="zone"></a>
                                <a title="分享到微信" rel="nofollow" role="button" class="weixin"></a>
                            </div>
                        </div>
                        <div class="bs_collect ">
                            <a href="/travel/input?id=${detail.id!}"  class="bs_btn _j_goto_comment" ><i></i><strong>编辑</strong></a>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div class="view clearfix" style="position: relative;">
            <div class="view_con">
                <div class="travel_directory _j_exscheduleinfo">
                    <div class="tarvel_dir_list clearfix">
                        <ul>
                            <li class="time">出发时间<span>/</span>${detail.travelTime?string("yyyy-MM-dd")}<i></i></li>
                            <li class="day">出行天数<span>/</span>${detail.days!} 天</li>
                            <li class="people">人物<span>/</span>${detail.personDisplay!}</li>
                            <li class="cost">人均费用<span>/</span>${detail.perExpends!}RMB</li>
                        </ul>
                    </div>
                </div>
                <div class="vc_article vc_articleT" >
                    ${(detail.travelContent.content)!}
                </div>
<#--                <div>-->
<#--                    <div class="mfw-cmt-wrap" id="_j_reply_list">-->
<#--                        <!--评论&ndash;&gt;-->
<#--                        <#list list as c>-->
<#--                            <div class="mfw-cmt _j_reply_item" >-->
<#--                                <div class="mcmt-info">-->
<#--                                    <div class="mcmt-photo">-->
<#--                                        <a href="javascript:;" target="_blank">-->
<#--                                            <img src="${c.headUrl!}"-->
<#--                                                 width="48" height="48" alt="${c.username!}">-->
<#--                                        </a>-->
<#--                                    </div>-->
<#--                                    <div class="mcmt-user">-->
<#--                                        <a href="javascript:;" target="_blank" class="name">${c.username!}(${c.city!})</a>-->
<#--                                        <a href="javascript:;" class="level">LV.${c.level!}</a>-->
<#--                                        <a href="javascript:;" class="identity identity-guide" target="_blank"></a>-->
<#--                                        <a href="javascript:void(0);" class="per_attention" data-japp="following"-->
<#--                                           data-uid="76382990" data-follow_class="hide">-->
<#--                                            <img src="http://images.mafengwo.net/images/home/tweet/btn_sfollow.gif"-->
<#--                                                 width="38" height="13" border="0" title="关注TA">-->
<#--                                        </a>-->
<#--                                    </div>-->
<#--                                    <div class="mcmt-other">-->
<#--                                        <span class="floor">${c_index +1}F</span>-->
<#--                                    </div>-->
<#--                                </div>-->
<#--                                <div class="mcmt-con-wrap clearfix">-->
<#--                                    <div class="mcmt-con">-->

<#--                                    <#if c.type == 1>-->
<#--                                        <div class="mcmt-quote">-->
<#--                                            <p>引用 ${(c.refComment.username)!} 发表于 ${(c.refComment.createTime?string('yyyy-MM-dd HH:ss'))!} 的回复：</p>-->
<#--                                            <p class="_j_reply_content">${(c.refComment.content)!}</p>-->
<#--                                        </div>-->
<#--                                        <div class="mcmt-word">-->
<#--                                            <p class="_j_reply_content" >回复${(c.refComment.username)!}：${(c.content)!}</p>-->
<#--                                        </div>-->
<#--                                    <#else>-->
<#--                                        <div class="mcmt-quote">-->
<#--                                        </div>-->
<#--                                        <div class="mcmt-word">-->
<#--                                            <p class="_j_reply_content">${(c.content)!}</p>-->
<#--                                        </div>-->
<#--                                    </#if>-->
<#--                                    </div>-->
<#--                                    <div class="mcmt-tag">-->
<#--                                    </div>-->
<#--                                </div>-->
<#--                                <div class="mcmt-bot">-->
<#--                                    <div class="time">${(c.createTime?string('yyyy-MM-dd HH:ss'))!}</div>-->
<#--                                    <div class="option">-->
<#--                                        <a role="button" class="reply-report">举报</a>-->
<#--                                        <a role="button" class="_j_reply replyBtn" data-touser="${c.username!}" data-toid="${c.id!}">回复</a>-->
<#--                                    </div>-->
<#--                                </div>-->
<#--                            </div>-->
<#--                        </#list>-->
<#--                    </div>-->
<#--                    <!--发表回复&ndash;&gt;-->
<#--                    <div class="mcmt-reply-wrap _j_replywrap ">-->
<#--                        <div class="mcmt-tab">-->
<#--                            <ul class="_j_replytab">-->
<#--                                <li class="_j_publish_tab on" data-mode="article">回复游记<i></i></li>-->
<#--                            </ul>-->
<#--                        </div>-->
<#--                        <div class="mcmt-tab-con">-->
<#--                            <div class="photo-con">-->
<#--                                <a href="/u/53383161.html" target="_blank" title="蚂蜂测试窝用户"><img-->
<#--                                        src="http://n1-q.mafengwo.net/s12/M00/35/98/wKgED1uqIreAU9QZAAAXHQMBZ74008.png?imageMogr2%2Fthumbnail%2F%2148x48r%2Fgravity%2FCenter%2Fcrop%2F%2148x48%2Fquality%2F90"-->
<#--                                        alt="骡窝窝测试用户"></a>-->
<#--                            </div>-->
<#--                            <div class="reply-con clearfix _j_article_mode _j_editor" id="_j_editor"-->
<#--                                style="display: block;">-->
<#--                                <dl>-->
<#--                                    <dt>-->
<#--                                        <!-- 各种选项&ndash;&gt;-->
<#--                                        <div class="reply-choice">-->
<#--                                            <a role="button" class="expression" id="_j_replyfacetrigger"-->
<#--                                                title="选择表情"></a>-->
<#--                                            <div class="clear"></div>-->
<#--                                             <!--表情&ndash;&gt;-->
<#--                                            <#include "../common/emotion.ftl">-->
<#--                                        </div>-->
<#--                                    </dt>-->
<#--                                    <dd>-->
<#--                                        <div class="reply-text">-->

<#--                                            <form action="/travel/commentAdd" method="post" id="commentForm">-->
<#--                                                <input type="hidden" name="travelId" value="${detail.id!}">-->
<#--                                                <input type="hidden" name="travelTitle" value="${detail.title!}">-->
<#--                                                <input type="hidden" name="type" value="0" id="commentType">-->
<#--                                                <input type="hidden" name="refComment.id" id="refCommentId">-->
<#--                                                <input type="hidden" name="floor" value="${(list?size)!0}">-->

<#--                                                <textarea class="_j_replyarea" name="content" cols="30" rows="10" id="commentContent"-->
<#--                                                    placeholder=""></textarea>-->

<#--                                            </form>-->
<#--                                        </div>-->
<#--                                    </dd>-->
<#--                                </dl>-->
<#--                                <div class="reply-submit">-->
<#--                                    <!-- 有loading状态 &ndash;&gt;-->
<#--                                    <a role="button" class="_j_publish_reply commentBtn" title="发表回复">发表回复</a>-->
<#--                                </div>-->
<#--                            </div>-->
<#--                        </div>-->
<#--                    </div>-->
<#--                </div>-->
            </div>
            <div class="view_side">
                <div class="relations _j_stas_content">
                    <div id="pagelet-block-b50249fefe90f4816744a1eed56a7049" class="pagelet-block"
                        data-api=":note:pagelet:rightMddApi" data-params="{&quot;iid&quot;:&quot;12655354&quot;}"
                        data-async="1" data-controller="">
                        <div class="slide-right-container">
                            <div class="relation_mdd">相关目的地： &nbsp;&nbsp;

                                <#list  toasts as t>
                                    <a href="/destination/guide?id=${t.id!}" target="_blank" title="${t.name}" class="_j_mdd_stas">${t.name!}</a>
                                    &nbsp;&nbsp;
                                </#list>
                            </div>
                            <div class="its_others">
                                <div class="mdd_info">
                                    <!--_j_mdd_sta为目的地点击统计，修改注意-->
                                    <a href="/destination/guide?id=${(detail.dest.id)!}" title="${(detail.dest.name)!}" class="_j_mdd_stas" target="_blank">
                                        <img src="${(detail.dest.coverUrl)!}"
                                            width="240px" alt="${(detail.dest.name)!}">
                                        <i></i>
                                        <strong>${(detail.dest.name)!}</strong>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="side_sticky _j_sticky_block">
                    <div id="pagelet-block-cf05fa839072c3781ee6b0ff89ced384" class="pagelet-block">
                        <div class="mainmdd_rel_notes _j_topblock" id="_j_mainmdd_rel_gls">
                            <div class="notes_gonglve" style="margin-bottom: 0">
                                <div class="side_title">相关攻略</div>
                                <div class="gonglve_slide gs1" id="_j_gl_slide_container">
                                    <ul class="gs_content" style="left: 0px;">
                                        <#list sds as s>
                                            <li>
                                                <a href="/strategy/detail?id=${s.id!}" target="_blank" class="_j_mddrel_gl_item"
                                                     title="${s.title!}"><img src="${s.coverUrl!}">
                                                    <div class="bg"></div><span><i></i>${s.viewnum!}</span>
                                                    <h3>${s.title!}</h3>
                                                </a>
                                            </li>
                                        </#list>

                                    </ul>
                                    <ul class="gs_nav gs_nav3">
                                        <li data-id="0" class="gs_nav_item1 _j_gl_item_switch on"></li>
                                        <li data-id="1" class="gs_nav_item2  _j_gl_item_switch"></li>
                                        <li data-id="2" class="gs_nav_item3  _j_gl_item_switch"></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div id="pagelet-block-9ec395fc9c1fc520835dc7de18ceacb1" class="pagelet-block">
                    <div class="mainmdd_rel_notes _j_topblock" id="_j_mainmdd_rec_gls">
                        <div class="notes_gonglve" style="margin-bottom: 0">
                            <div class="side_title">相关游记</div>
                            <div class="gonglve_slide gs1" id="_j_rec_slide_container">
                                <ul class="gs_content" style="left: 0px;">
                                    <#list ts as t>

                                    <li>
                                        <a href="/travel/detail?id=${t.id!}" target="_blank" class="_j_mddrel_gl_item"
                                              title="${t.title!}">
                                            <img src="${t.coverUrl!}">
                                            <div class="bg"></div><span><i></i>${t.viewnum!}</span>
                                            <h3>${t.title!}</h3>
                                        </a>
                                    </li>

                                    </#list>

                                </ul>
                                <ul class="gs_nav gs_nav3">
                                    <li data-id="0" class="gs_nav_item1 _j_gl_item_switch on"></li>
                                    <li data-id="1" class="gs_nav_item2  _j_gl_item_switch"></li>
                                    <li data-id="2" class="gs_nav_item3  _j_gl_item_switch"></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <#include "../common/footer.ftl">
</body>

</html>