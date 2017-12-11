(function() {
    $(document).ready(function() {

        $("#btn_send").click(function(){
            var street = $('#input_address').val();
            var city = $('#input_city').val();
            var county = $('#input_county').val();

            var name = $('#input_name').val();
            var email = $('#input_email').val();
            var login = $('#input_username').val();
            var password = $('#input_password').val();
            var password2 = $('#input_repeate_password').val();
            
            if(street === "" || city === "" || county === "" || name === "" || email === "" || login === "" || password === "" || (password !== password2)){
               console.log("nop!");
            } else {
               var user = new Object();
                var address = new Object();

                address.street = street;
                address.city = city;
                address.county = county;

                user.name = name;
                user.email = email;
                user.address = address;
                user.login = login;
                user.password = password;
                console.log("sending : " + JSON.stringify(user));

                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    url: "/api/users",
                    type: "POST",
                    data: JSON.stringify(user),
                    dataType: "json",
                    success: function (result) {
                        console.log("successfully sent" + result );
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        var error = '<div class="alert alert-danger fade in">' +
                            '<strong>Error!</strong> ' + jqXHR.responseText + '</div>';
                        $("#alert").append(error);
                                            }
                });  
            }
        });
    });
}());
