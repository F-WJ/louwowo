$(function() {
    $('.mfw-header').on('mouseenter', function() {
        $(this).removeClass('header-place-default');
    }).on('mouseleave', function() {
        $(this).addClass('header-place-default');
    });

    $('.search-input').bind('input propertychange', function() {
        var val = $(this).val();
        // debugger;
        if (val) {
            $('.search-suggest-panel').show();
        } else {
            $('.search-suggest-panel').hide();
        }
    });
    $('.search-suggest-panel').on('mouseenter', function() {

    }).on('mouseleave', function() {
        var self = this;
        setTimeout(function() {
            $(self).hide();
        }, 1500);
    });



    //获取国内数据
    $.get("/destination/getHotDestByRegionId", {regionId:-1}, function (data) {
        $("#hotContent").append(data);
    })


    $('.row-hot .r-navbar a').on('hover', function() {
        var rid = $(this).data("rid");
        if(!rid){
            return;
        }
        $('.row-hot .r-navbar a').removeClass('on');
        $(this).addClass('on');

        $('.row-hot .hot-list').addClass('hide');

        //请求页面
        if($(".hot-list-"+rid).size() != 0){
            $(".hot-list-"+rid).removeClass('hide');
            return;
        }
        $.get("/destination/getHotDestByRegionId", {regionId:rid}, function (data) {
            if($(".hot-list-"+rid).size() == 0){
                $("#hotContent").append(data);
            }
            $(".hot-list-"+rid).removeClass('hide');
        })

    });

    $('.pagelet-block .r-navbar a').on('hover', function() {
        var idx = $(this).index();
        idx = idx == 0 ? 0 : idx / 2;
        $('.pagelet-block .r-navbar a').removeClass('on');
        $(this).addClass('on');
        $('.pagelet-block .tiles').addClass('hide');
        $('.pagelet-block .tiles').eq(idx).removeClass('hide');
    });

    $('.pagelet-block1 .r-navbar a').on('hover', function() {
        var idx = $(this).index();
        idx = idx == 0 ? 0 : idx / 2;
        $('.pagelet-block1 .r-navbar a').removeClass('on');
        $(this).addClass('on');
        $('.pagelet-block1 .tiles').addClass('hide');
        $('.pagelet-block1 .tiles').eq(idx).removeClass('hide');
    });
    $('.suggest-list li').click(function() {
        window.location.href = "./index.ftl"
    })
});