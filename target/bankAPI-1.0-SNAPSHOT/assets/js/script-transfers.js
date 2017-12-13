(function() {
    $(document).ready(function() {
        var url_string = window.location.href;
        var url = new URL(url_string);
        var token = url.searchParams.get("session");
        var id = url.searchParams.get("id");
        
        load()
        
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
                    $("#transactions_link").prop('href', '/transactions.html?session=' + result.token + '&id=' + result.id);
                    $("#transfers_link").prop('href', '/transfers.html?session=' + result.token + '&id=' + result.id);

                    for(var i=0; i< result.account.length; i++){
                        $("#tbody1").append('<tr><td>'+result.account[i].name+' (' + result.account[i].accNumber + ')' +
                            '</td><td>€ '+result.account[i].balance+
                            '</td><td>'+
                                '<div class="input-group col-md-6">'+
                                    '<span class="input-group-addon">€</span>'+
                                    '<input class="form-control currency" id="add_money_'+result.account[i].accNumber+'">'+
                                    '<span class="input-group-btn">'+
                                        '<button class="btn btn-secondary" onClick="addMoney('+ result.account[i].accNumber + ')" type="button">Add</button>'+
                                    '</span>'+
                                '</div>'+
                            '</td><td><button class="btn btn-default submit-button" onClick="sendMoney('+ result.account[i].accNumber + ')" type="button">Transfer</button></td></tr>');   
                    }
                },
                error: function(jqXHR, textStatus, errorThrown) {
                    console.log("ERROR: " + JSON.stringify(jqXHR));
                }
            });
        }
    });
}());
