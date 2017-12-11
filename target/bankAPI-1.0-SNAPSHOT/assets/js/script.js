(function() {
    $(document).ready(function() {

        $('#form').validator().on('submit', function (e) {
            if (e.isDefaultPrevented()) {
                console.log("noooo: ");
            } else {
                var user = new Object();
                var address = new Object();

                address.street = $('#input_address').val();
                address.city = $('#input_city').val();
                address.county = $('#input_county').val();

                user.name = $('#input_name').val();
                user.email = $('#input_email').val();
                user.address = address;
                user.login = $('#input_username').val();
                user.password = $('#input_password').val();


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
                        console.log("ERROR: " + textStatus, errorThrown);
                    }
                });
                console.log("sending : " + user)
            }
        })
    })
}());
