var url_string = window.location.href;
var url = new URL(url_string);
var token = url.searchParams.get("session");
var id = url.searchParams.get("id");

$(document).ready(function () {
    
    $("#change_user_details").click( ()=> { $("#user_details_modal").modal('show'); })
    
    loadUser();


    $("#save_user_details").click(function () {
            var street = $('#input_address').val();
            var city = $('#input_city').val();
            var county = $('#input_county').val();

            var name = $('#input_name').val();
            var email = $('#input_email').val();
            
            if(street === "" || city === "" || county === "" || name === "" || email === ""){
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
                
                console.log("sending : ");
                console.log(user);

                $.ajax({
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    },
                    url: "/api/users/"+id,
                    type: "POST",
                    data: JSON.stringify(user),
                    dataType: "json",
                    success: function (result) {
                        console.log("successfully sent" + result );
                        location.reload();
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        var error = '<div class="alert alert-danger fade in">' +
                            '<strong>Error!</strong> ' + JSON.parse(jqXHR.responseText).text + '</div>';
                        $("#alert").append(error);
                    }
                });  
            }
        });
    
    function loadUser() {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token
        },
        url: "/api/users/" + id,
        type: "GET",
        dataType: "json",
        success: function (result) {
            console.log(result);
            
            $("#input_name").val(result.name);
            $("#input_email").val(result.email);
            $("#input_address").val(result.address.street);
            $("#input_city").val(result.address.city);
            $("#input_county").val(result.address.county);

        },
        error: function (jqXHR) {
            console.log("ERROR: " + JSON.stringify(jqXHR));
            window.location = "/login.html";
        }
    });
}
    
    
    
});
