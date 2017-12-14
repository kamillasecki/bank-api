var url_string = window.location.href;
var url = new URL(url_string);
var token = url.searchParams.get("session");
var id = url.searchParams.get("id");

$(document).ready(function () {
    
    load();
    
    $("#btn_send").click(function () {
        var accName = $("#input_accName").val();
        console.log("Account name: " + accName);
        var data = '{"name":"' + accName + '"}';
        console.log("Data: " + data);
        if (accName === "") {
            console.log("nop!");
        } else {
            $.ajax({
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                    'Authorization': token
                },
                url: "/api/users/user/" + id + "/account/new",
                type: "POST",
                data: data,
                dataType: "json",
                success: function (result) {
                    load();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    console.log("ERROR: " + JSON.stringify(jqXHR));
                }
            });
        }
    });
});


function logout() {
    console.log("Sending Seesion : " + token +  "to be closed");
    $.ajax({
        headers: {
            'Authorization': token
        },
        url: "/api/users/user/" + id + "/logout",
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

function load() {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token
        },
        url: "/api/users/user/" + id,
        type: "GET",
        dataType: "json",
        success: function (result) {
            console.log("Received user details:" + JSON.stringify(result));
            $("#login_button").replaceWith('<a href="#" onClick="logout()" class="btn btn-primary navbar-btn navbar-right"><strong>Log out ' + result.login + '</strong></a>');
            $("#user_name").replaceWith(result.name);
            $("#user_email").empty();
            $("#user_email").append(result.email);
            $("#transactions_link").prop('href', '/transactions.html?session=' + result.token + '&id=' + result.id);
            $("#home_link").prop('href', '/home.html?session=' + result.token + '&id=' + result.id);
            $("#transfers_link").prop('href', '/transfers.html?session=' + result.token + '&id=' + result.id);
            $("#tbody1").empty();
            for (var i = 0; i < result.account.length; i++) {
                $("#tbody1").append('<tr><td>' + result.account[i].name +
                        '</td><td>' + result.account[i].accNumber +
                        '</td><td>' + result.account[i].sortCode +
                        '</td><td>€ ' + result.account[i].balance +
                        '</td></tr>');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR: " + JSON.stringify(jqXHR));
            window.location = "/login.html";
        }
    });
}
