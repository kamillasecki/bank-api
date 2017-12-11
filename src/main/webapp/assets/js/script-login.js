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
                    dataType: "text",
                    success: function (result) {
                        console.log( result );
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        var error = '<div class="alert alert-danger fade in">' +
                            '<strong>Error!</strong> ' + jqXHR.responseText + '</div>';
                        $("#alert").append(error);
                        console.log("ERROR: " + JSON.stringify(jqXHR))
                    }
                });  
            }
        });
    });
}());
