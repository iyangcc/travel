$.get("simple_nav.html", function (data) {
    $("#header").html(data);
});

function exitUser(){
    $.cookie("username",null,{ expires: -1,path:"/"});
    $.cookie("password",null,{ expires: -1,path:"/" });
    location.href = "user/exit";
}

function userCenter(id){
    location.href = "http://localhost/travel/user_center.html?uid="+id;
}