(function() {
    $(document).ready(function() {
        var url_string = window.location.href;
        var url = new URL(url_string);
        var token = url.searchParams.get("session");
        var id = url.searchParams.get("id");
        
        load()
        $("#btn_send").click(function(){
            var accName = $("#input_accName").val();
            console.log("Account name: " + accName );
            var data = '{"name":"'+accName+'"}';
            console.log("Data: " + data );
            if(accName === ""){
               console.log("nop!");
            } else {
                $.ajax({
                    headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization' : token
                },
                url: "/api/users/user/" + id + "/account/new",
                type : "POST",
                data: data,
                dataType: "json",
                success: function (result) {
                    load();
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("ERROR: " + JSON.stringify(jqXHR));
                }
                });
            }
        });

        function load(){
            $.ajax({    
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization' : token
                },
                url: "/api/users/user/" + id,
                type: "GET",
                dataType: "json",
                success: function (result) {
                    console.log("Received user details:" + JSON.stringify(result) );
                    $("#login_button").replaceWith('<a href="logout.html" class="btn btn-primary navbar-btn navbar-right"><strong>Log out ' + result.login + '</strong></a>');
                    $("#user_name").replaceWith(result.name);
                    $("#user_email").append(result.email);
                    $("#accounts_link").prop('href', '/accouonts.html?session=' + result.token + '&id=' + result.id);

                    for(var i=0; i< result.account.length; i++){
                        $("#tbody1").append('<tr><td>'+result.account[i].name+
                            '</td><td>'+result.account[i].accNumber+
                            '</td><td>'+result.account[i].sortCode+
                            '</td><td>€ '+result.account[i].balance+
                            '</td></tr>');   
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("ERROR: " + JSON.stringify(jqXHR));
                }
            });
        }
    });
}());
