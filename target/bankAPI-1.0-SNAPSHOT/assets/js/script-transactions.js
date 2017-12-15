var url_string = window.location.href;
var url = new URL(url_string);
var token = url.searchParams.get("session");
var id = url.searchParams.get("id");
var globalRes;

$(document).ready(function () {
    load();
});

function load() {
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
            globalRes = result;
            console.log("Received user details:");
            console.log(result);
            $("#login_button").replaceWith('<a onClick="logout()" class="btn btn-primary navbar-btn navbar-right"><strong>Log out ' + result.login + '</strong></a>');
            $("#frame").empty();
            $("#frame").append('<br><h2>Hello, ' + result.name + '!</h2><h4>' + result.email + '</h4>');
            $("#home_link").prop('href', '/home.html?session=' + result.token + '&id=' + result.id);
            $("#transactions_link").prop('href', '/transactions.html?session=' + result.token + '&id=' + result.id);
            $("#transfers_link").prop('href', '/transfers.html?session=' + result.token + '&id=' + result.id);
            $("#tbody1").empty();
            var listElement;
            var tabElement;

            for (var i = 0; i < result.account.length; i++) {

                listElement = '<li >'+'<a  href="#'+i+'" data-toggle="tab">'+result.account[i].name+' ['+result.account[i].accNumber+']'+'</a></li>';
                
                
                $('#accountTabs').append(listElement);
                
                tabElement = '<div class="tab-pane active" id="'+i+'">'+'</div>';
                
                $('#tabContent').append(tabElement);
                
                
                var set = '<tr>';
                for (var g = 0; g < result.account[i].transactions.length; g++){
                    console.log('running g'+g);
                    var date = result.account[i].transactions[g].date;
                    var formatDate = date.substring(8,10) + "/" + date.substring(5,7) + "/" + date.substring(0,4)
                    
                     set += '<td>'+formatDate+'</td>'+
                             '<td>'+result.account[i].transactions[g].type+'</td>'+
                             '<td>€ '+result.account[i].transactions[g].amount+'</td>'+
                             '<td>€ '+result.account[i].transactions[g].postBallance+'</td>'+
                             '<td><button class="btn-xs btn-default submit-button" onClick="getDetails(' + i + ','+g+')" type="button">Details</button>';
                     set += '</tr>';
                }
                 
                $('#'+i).append(`<div class="table-responsive table-hover">
                                                <table class="table table-striped">
                                                    <thead>
                                                        <tr>
                                                            <th>Date</th>
                                                            <th>Type</th>
                                                            <th>Ammount</th>
                                                            <th>Balance</th>
                                                            <th></th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>`+ set +`</tbody></table></div>`);

                }

            $('a[href="' + '#1' + '"]').trigger('click');
            $('a[href="' + '#0' + '"]').trigger('click');
        },
        error: function (jqXHR) {
            console.log("ERROR: " + JSON.stringify(jqXHR));
            window.location = "/login.html";
        }
    });
}

function getDetails(acct,trxid){
    
    
    //whats the point to get this from server if we already have it?
    var tmp = globalRes.account[acct].transactions[trxid];
    console.log(tmp);
    $("#account_t").html(tmp.accountNumber);
    $("#amount_t").html(tmp.amount);
    $("#date_t").html(tmp.date);
    $("#description_t").html(tmp.description);
    $("#postBallance_t").html(tmp.postBallance);
    $("#type_t").html(tmp.type);
    
    
    $("#transaction_details_modal").modal('show');
    
}


function logout() {
    console.log("Sending Seesion : " + token + "to be closed");
    $.ajax({
        headers: {
            'Authorization': token
        },
        url: "/api/users/" + id + "/logout",
        type: "GET",
        dataType: "text",
        success: function () {
            window.location = "/login.html";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR: " + JSON.stringify(jqXHR));
        }
    });
}

