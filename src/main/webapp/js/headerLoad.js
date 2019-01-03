$(function () {
    var cid = getParameter("cid");
    var filed = getParameter("filed");
    var keywords = getParameter("keywords");
    var number = location.href.indexOf("route_list");
    var sub_nav = "";
    cid = !cid?0:cid;
    filed = !filed?"":filed;
    keywords = !keywords?"":window.decodeURIComponent(keywords);
    $("#keywords").val(keywords);
    /**
     * 查询已登录的用户信息
     */
    $.get("user/findOne",{},function (result) {
        if(result.status){
            $("#login_out").hide();
            $("#login").show();
            $("#logged_user").html("欢迎回来，<a href='javascript:userCenter("+result.data.uid+");' id='userCenter'>"+result.data.name+"</a>");
            $("#my_favorite").prop("href","myfavorite.html?uid="+result.data.uid);
        }else{
            if(!autoLogin()){
                $("#login").hide();
                $("#login_out").show();
            }
        }
    });

    function autoLogin(){
        if($.cookie("username")&&$.cookie("password")){
            var params = {};
            params.username = $.cookie("username");
            params.password = $.cookie("password");
            params.remberUser = true;
            console.log(params);
            $.post("user/login",params,function (result) {
                if (result.status) {
                    $("#login_out").hide();
                    $("#login").show();
                    $("#logged_user").html("欢迎回来，<a href='javascript:userCenter("+result.data.uid+");' id='userCenter'>"+result.data.name+"</a>");
                    $("#my_favorite").prop("href","myfavorite.html?uid="+result.data.uid);
                }
            });
            return true;
        }
        return false;
    }

    /**
     * 查询所有分类并显示
     */
    $.get("category/findAll",{},function (result) {
        var data = result.data;
        var categorys = "";
        categorys += "<li class=\"nav-active\"><a href=\"index.html\">首页</a></li>";
        for (var i = 0; i < data.length; i++) {
            categorys += '<li><a href="route_list.html?cid='+data[i].cid+'&filed='+data[i].cname+'">'+data[i].cname+'</a></li>';
            if(i==cid){
                sub_nav = data[i].cname;
            }
        }
        categorys += "<li><a href=\"favoriterank.html\">收藏排行榜</a></li>";
        $("#category").html(categorys);
    });

    if(number>0){
        $("#bread").html(sub_nav);
    }
    /**
     * 搜索按钮绑定事件
     */
    $("#search_button").on("click",function () {
        keywords = $("#keywords").val();
        location.href="http://localhost/travel/route_list.html?cid="+cid+"&filed="+filed+"&keywords="+keywords;
    });
    $("body").on("keydown",function (event) {
        if (event.keyCode == "13") {
            keywords = $("#keywords").val();
            location.href="http://localhost/travel/route_list.html?cid="+cid+"&filed="+filed+"&keywords="+keywords;
        }
    });
});

function exitUser(){
    $.cookie("username",null,{ expires: -1,path:"/"});
    $.cookie("password",null,{ expires: -1,path:"/" });
    location.href = "user/exit";
}

function userCenter(id){
    location.href = "http://localhost/travel/user_center.html?uid="+id;
}