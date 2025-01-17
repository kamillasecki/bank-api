(function() {
    $(document).ready(function() {
        $("#btn_send").click(function(){
            var login = $('#input_username').val();
            var password = $('#input_password').val();
            
            if(login === "" || password === ""){
               console.log("nop!");
            } else {
               var user = new Object();
                user.login = login;
                user.password = password;
               
                console.log("sending : " + JSON.stringify(user));

                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    url: "/api/users/login",
                    type: "POST",
                    data: JSON.stringify(user),
                    dataType: "json",
                    success: function (result) {
                        console.log("Received: " + JSON.stringify(result));
                        window.location = "/home.html?session=" + result.token + "&id=" + result.id;
                    },
                    error: function(jqXHR) {
                        var error = '<div class="alert alert-danger fade in">' +
                            '<strong>Error!</strong> ' + JSON.parse(jqXHR.responseText).text + '</div>';
                        $("#alert").append(error);
                    }
                });  
            }
        });
    });
}());
