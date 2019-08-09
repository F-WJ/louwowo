<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <link href="/styles/base.css" rel="stylesheet" type="text/css">
    <link href="/styles/search.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/system/common.js"></script>

    <script>
        $(function () {
            $("#_j_search_button").click(function () {

                $("#searchType").val($(".nav-list a[class='on _j_search_link']").data("index"));
                $("#kwSearchForm").submit();
            })

            $("._j_search_link").click(function () {
                $("#searchType").val($(this).data("index"));
                $("#currentPage").val(1);
                $("#kwSearchForm").submit();
            })
        })
    </script>
</head>

<body>
    <div class="s-head">
        <div class="wid clearfix">
            <div class="s-head-logo">
                <a href="javascript:;"></a>
            </div>
            <div class="searchbar">
                <div class="search-wrapper">
                    <form action="/q" method="get" id="kwSearchForm">
                        <input type="hidden" name="type" value="" id="searchType">
                    <div class="search-input"><input name="keyword" type="text" id="_j_search_input" value="${qo.keyword!}"
                            placeholder="搜目的地/攻略/游记/用户" autocomplete="off" ></div>
                    </form>
                </div>
                <div class="search-button"><a role="button" href="javascript:" id="_j_search_button"><i
                            class="icon-search"></i></a></div>
            </div>
            <div id="pagelet-block-6d34f3fc7bb6504c1b634be7d8ff2a67" class="pagelet-block"
                data-api="apps:user:pagelet:pageViewHeadInfo" data-params="{&quot;type&quot;:1}" data-async="1"
                data-controller="/js/pageletcommon/pageHeadUserInfoWWWNormal">
                <div class="login-info">
                    <div class="head-user" id="_j_head_user">
                        <a class="drop-trigger" href="javascript:;" title="骡窝窝用户的窝" rel="nofollow">
                            <div class="user-image"><img
                                    src="http://b2-q.mafengwo.net/s12/M00/35/B7/wKgED1uqIs-AMYTwAAAX-VIKIo0071.png?imageMogr2%2Fthumbnail%2F%2132x32r%2Fgravity%2FCenter%2Fcrop%2F%2132x32%2Fquality%2F90"
                                    height="32" width="32" alt="骡窝窝用户的窝"></div>
                            <i class="icon-caret-down"></i>
                        </a>
                    </div>
                    <div class="head-msg" id="_j_head_msg">
                        <a class="drop-trigger" href="javascript:" rel="nofollow">
                            <i class="icon-msg"></i>
                            消息
                            <i class="icon-caret-down"></i>
                            <span class="head-msg-new hide" style="display: none;"></span>
                        </a>
                    </div>
                    <div class="head-daka ">
                        <a class="btn head-btn-daka" href="javascript:" rel="nofollow" id="head-btn-daka" title="打卡"
                            data-japp="daka">打卡</a>
                        <!-- <a class="btn-active head-btn-daka" href="javascript:" rel="nofollow" id="head-btn-daka-active" title="打卡推荐" data-japp="daka">打卡推荐</a> -->
                        <a class="btn-active head-btn-daka" href="javascript:" rel="nofollow" id="head-btn-daka-active"
                            title="已打卡" data-japp="daka">已打卡</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="mfw-search-main" id="_j_mfw_search_main">

        <div class="s-nav">
            <div class="wid">
                <div class="nav-list clearfix">
                    <a href="javascript:;" class="on _j_search_link" data-index="-1">全部</a>
                    <a href="javascript:;" class=" _j_search_link" data-index="0">目的地</a>
                    <a href="javascript:;" class=" _j_search_link" data-index="1">攻略</a>
                    <a href="javascript:;" class=" _j_search_link" data-index="2">游记</a>
                    <a href="javascript:;" class=" _j_search_link" data-index="3">找人</a>
                </div>
            </div>
        </div>
        <div class="wid clearfix">
            <div class="ser-nums">
                <div class="sr-nums">
                    <p class="ser-result-primary">
                        以下是为您找到的“${qo.keyword!}”相关结果${(data.total)!0}条
                    </p>
                </div>
            </div>
            <div class="flt1 ser-lt" id="_j_search_result_left">



                <#if data.dests?size &gt; 0>
                    <div class="_j_search_section">
                        <div class="att-list">
                            <ul>
                                <#list data.dests as dest>
                                    <li>
                                        <div class="clearfix">
                                            <div class="flt1"><a href="javascript:;" target="_blank" class="_j_search_link"><img
                                                    src="${(dest.coverUrl)!'/wKgB4lM7jsqATNaRAATlZu6KzSU34.jpeg'}"
                                                    style="width:150px;height:90px;"></a></div>
                                            <div class="ct-text ">
                                                <h3>
                                                    <a href="javascript:;" target="_blank"
                                                       class="_j_search_link">${(dest.name)!}</a>
                                                </h3>
                                                <p class="seg-desc">${(dest.info)!}</p>

                                            </div>
                                        </div>
                                    </li>
                                </#list>
                            </ul>
                        </div>
                    </div>
                    <div class="heg30"></div>
                </#if>




                <#if data.strategys?size &gt; 0 >
                <div class="_j_search_section">
                    <div class="att-list">
                        <div class="clearfix ser-title">
                            <h2>
                                <a href="javascript:;" target="_blank" class="_j_search_link" data-index="1">攻略</a>
                            </h2>
                            <a href="javascript:;" target="_blank" class="_j_search_link" data-index="1">查看更多攻略
                                &gt;&gt;</a>
                        </div>
                        <ul>
                            <li>
                                <div class="clearfix">

                                    <#list data.strategys as s>
                                    <div class="flt1"><a href="javascript:;" target="_blank" class="_j_search_link"><img
                                                src="${s.coverUrl!}"
                                                style="width:150px;height:90px;"></a></div>
                                    <div class="ct-text ">
                                        <h3>
                                            <a href="javascript:;" target="_blank"
                                                class="_j_search_link">${s.title!}</a>
                                        </h3>
                                        <p class="seg-desc">${s.summary!}</p>
                                        <ul class="seg-info-list clearfix">
                                            <li>
                                                <a href="javascript:;" target="_blank" class="_j_search_link">${s.provinceName!'${s.countryName!}'}-${s.destName!}</a>
                                            </li>
                                            <li>
                                                浏览(${s.viewNum!0})
                                            </li>
                                            <li>
                                                评论(${s.replyNum!0})
                                            </li>
                                            <li>
                                                ${(s.createTime?string('yyyy年MM月dd日'))!}
                                            </li>
                                        </ul>
                                    </div>
                                    </#list>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="heg30"></div>
                </#if>

                <#if data.travels?size &gt; 0 >
                <div class="travel-notes _j_search_section" data-category="info">
                    <div class="clearfix ser-title">
                        <h2>
                            <a href="javascript:;" target="_blank" class="_j_search_link" data-index="2">游记</a>
                        </h2>
                        <a href="javascript:;" target="_blank" class="_j_search_link" data-index="2">查看更多游记
                            &gt;&gt;</a>
                    </div>
                    <ul>
                        <#list data.travels as t>
                        <li>
                            <p class="clearfix">
                                <a href="javascript:;" target="_blank"
                                    class="_j_search_link">${t.title!}</a>
                                <span class="seg-info">${(t.createTime?string('yyyy年MM月dd日'))!}</span>
                                <span class="seg-info">${t.authorName!}</span>
                                <span class="seg-info">浏览(${t.viewNum!0})</span>
                                <span class="seg-info">回复(${t.replyNum!0})</span>
                            </p>
                        </li>
                        </#list>

                    </ul>
                </div>
                <div class="heg30"></div>
                </#if>

                <#if data.users?size &gt; 0 >
                <div class="_j_search_section" data-category="user">
                    <div class="clearfix ser-title">
                        <h2>
                            <a href="javascript:;" class="_j_search_link" data-index="3">用户</a>
                        </h2>
                        <a href="javascript:;" class="_j_search_link" data-index="3">更多用户 &gt;&gt;</a>
                    </div>
                    <ul class="user-list-row">
                        <#list data.users as u>
                        <li>
                            <div class="btns">
                                <a class="btn-follow _j_user_follow" href="javascript:;" data-touid="75081249"
                                    data-has-followed="0"><i></i>关注</a>
                                <a class="btn-msg _j_user_letter" href="javascript:;" data-touid="75081249">私信</a>
                            </div>
                            <span class="avatar"><a href="javascript:;" target="_blank" class="_j_search_link"><img
                                        src="${u.headUrl!}"
                                        title="gzsclf" style="width:45px;height:45px;"></a></span>
                            <div class="base">
                                <span class="name"><a href="javascript:;" target="_blank"
                                        class="_j_search_link">${u.nickname!}-${u.destName!}</a></span>
                                <a class="grade" href="javascript:;">LV.${u.level!1}</a>
                            </div>
                            <div class="nums">
                                <a href="javascript:;" target="_blank" class="_j_search_link">游记：${u.travelNum!0}</a>
                                <a href="javascript:;" target="_blank" class="_j_search_link">粉丝：${u.fansNum!0}</a>
                                <a href="javascript:;" target="_blank" class="_j_search_link">回复：${u.replyNum!0}</a>
                            </div>
                        </li>
                        </#list>

                    </ul>
                </div>
                </#if>
            </div>
                <div class="flt1 ser-rt">
                    <div class="_j_search_section" data-category="poi">
                        <div class="clearfix ser-title">
                            <h2>
                                <a href="javascript:;" target="_blank" class="_j_search_link">${qo.keyword!}相关景点</a>
                            </h2>
                        </div>
                        <div class="hot-att">
                            <ul class="clearfix">
                                <li>
                                    <a href="javascript:;" target="_blank" class="_j_search_link"><img
                                            src="http://n1-q.mafengwo.net/s9/M00/ED/76/wKgBs1gl2CWAbUytABAWJaKdmGA57.jpeg?imageMogr2%2Fthumbnail%2F%21180x180r%2Fgravity%2FCenter%2Fcrop%2F%21180x180%2Fquality%2F90"
                                            style="width:90px;height:90px;"></a>
                                    <p><a href="javascript:;" target="_blank" class="_j_search_link">xxxx旅游度假区</a></p>
                                </li>
                                <li>
                                    <a href="javascript:;" target="_blank" class="_j_search_link"><img
                                            src="http://p1-q.mafengwo.net/s11/M00/CE/1D/wKgBEFtr-IaAS0GtAFG11wwxAa051.jpeg?imageMogr2%2Fthumbnail%2F%21180x180r%2Fgravity%2FCenter%2Fcrop%2F%21180x180%2Fquality%2F90"
                                            style="width:90px;height:90px;"></a>
                                    <p><a href="javascript:;" target="_blank" class="_j_search_link">xxxx塔</a></p>
                                </li>
                                <li>
                                    <a href="javascript:;" target="_blank" class="_j_search_link"><img
                                            src="http://p2-q.mafengwo.net/s10/M00/A0/51/wKgBZ1m1GOaARk_EADxTppCJcxM97.jpeg?imageMogr2%2Fthumbnail%2F%21180x180r%2Fgravity%2FCenter%2Fcrop%2F%21180x180%2Fquality%2F90"
                                            style="width:90px;height:90px;"></a>
                                    <p><a href="javascript:;" target="_blank" class="_j_search_link">沙面岛</a></p>
                                </li>
                                <li>
                                    <a href="javascript:;" target="_blank" class="_j_search_link"><img
                                            src="http://b3-q.mafengwo.net/s7/M00/C7/AD/wKgB6lR5opGAGEHlAD25BlYH-ko88.jpeg?imageMogr2%2Fthumbnail%2F%21180x180r%2Fgravity%2FCenter%2Fcrop%2F%21180x180%2Fquality%2F90"
                                            style="width:90px;height:90px;"></a>
                                    <p><a href="javascript:;" target="_blank" class="_j_search_link">石室圣心大教堂</a></p>
                                </li>
                                <li>
                                    <a href="javascript:;" target="_blank" class="_j_search_link"><img
                                            src="http://b2-q.mafengwo.net/s6/M00/C3/9D/wKgB4lNfwpSAKyXyAEkj1i6lSVY39.jpeg?imageMogr2%2Fthumbnail%2F%21180x180r%2Fgravity%2FCenter%2Fcrop%2F%21180x180%2Fquality%2F90"
                                            style="width:90px;height:90px;"></a>
                                    <p><a href="javascript:;" target="_blank" class="_j_search_link">xxx野生动物世界</a></p>
                                </li>
                                <li>
                                    <a href="javascript:;" target="_blank" class="_j_search_link"><img
                                            src="http://p3-q.mafengwo.net/s7/M00/C8/5C/wKgB6lR5o4KAc7l-AEk0iSpwfGg16.jpeg?imageMogr2%2Fthumbnail%2F%21180x180r%2Fgravity%2FCenter%2Fcrop%2F%21180x180%2Fquality%2F90"
                                            style="width:90px;height:90px;"></a>
                                    <p><a href="javascript:;" target="_blank" class="_j_search_link">xxx公园</a></p>
                                </li>
                                <li>
                                    <a href="javascript:;" target="_blank" class="_j_search_link"><img
                                            src="http://b4-q.mafengwo.net/s11/M00/A7/76/wKgBEFrbyiKAKSn0AAaqOqFhpEo01.jpeg?imageMogr2%2Fthumbnail%2F%21180x180r%2Fgravity%2FCenter%2Fcrop%2F%21180x180%2Fquality%2F90"
                                            style="width:90px;height:90px;"></a>
                                    <p><a href="javascript:;" target="_blank" class="_j_search_link">xxx步行街</a></p>
                                </li>
                                <li>
                                    <a href="javascript:;" target="_blank" class="_j_search_link"><img
                                            src="http://b2-q.mafengwo.net/s11/M00/4A/AD/wKgBEFs-PPmAIUaUAA1AsJQA6qs30.jpeg?imageMogr2%2Fthumbnail%2F%21180x180r%2Fgravity%2FCenter%2Fcrop%2F%21180x180%2Fquality%2F90"
                                            style="width:90px;height:90px;"></a>
                                    <p><a href="javascript:;" target="_blank" class="_j_search_link">xx欢乐世界</a></p>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="s-hr"></div>

                </div>
        </div>
    </div>
    <#include "../common/footer.ftl">
</body>

</html>