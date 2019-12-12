
function userLogout() {
    $.removeCookie("token",{domain:'localhost',path:'/'});
    $.ajax({
        url:"/user/logout",
        success:function () {
            window.location.reload()
        }
    });
}
