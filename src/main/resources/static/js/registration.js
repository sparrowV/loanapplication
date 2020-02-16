
document.getElementById("signIn").addEventListener('click',register);

function register() {
    var userName = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    $.ajax({
        url: '/registration',
        data: {"userName":userName,"password":password},
        type:'POST',
        success: function(response){
            if(response.statusCode !==0){
                alert(response.description);
            }else{
                window.location.href ="/login.html";
            }
        }
    });
}