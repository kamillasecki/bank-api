var url_string = window.location.href;
var url = new URL(url_string);
var token = url.searchParams.get("session");
var id = url.searchParams.get("id");

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
        url: "/api/users/user/" + id,
        type: "GET",
        dataType: "json",
        success: function (result) {
            console.log("Received user details:" + JSON.stringify(result));
            $("#login_button").replaceWith('<a onClick="logout()" class="btn btn-primary navbar-btn navbar-right"><strong>Log out ' + result.login + '</strong></a>');
            $("#user_name").replaceWith(result.name);
            $("#user_email").empty();
            $("#user_email").append(result.email);
            $("#home_link").prop('href', '/home.html?session=' + result.token + '&id=' + result.id);
            $("#transactions_link").prop('href', '/transactions.html?session=' + result.token + '&id=' + result.id);
            $("#transfers_link").prop('href', '/transfers.html?session=' + result.token + '&id=' + result.id);
            $("#tbody1").empty();
            for (var i = 0; i < result.account.length; i++) {
                $("#tbody1").append('<tr><td>' + result.account[i].name + ' (' + result.account[i].accNumber + ')' +
                        '</td><td>€ ' + result.account[i].balance +
                        '</td><td>' +
                        '<div class="input-group col-md-6">' +
                        '<span class="input-group-addon">€</span>' +
                        '<input class="form-control currency" id="add_money_' + result.account[i].accNumber + '">' +
                        '<span class="input-group-btn">' +
                        '<button class="btn btn-secondary" onClick="addMoney(' + result.account[i].accNumber + ')" type="button">Add</button>' +
                        '</span>' +
                        '</div>' +
                        '</td><td><button class="btn btn-default submit-button" onClick="sendMoney(' + result.account[i].accNumber + ')" type="button">Transfer</button></td></tr>');
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log("ERROR: " + JSON.stringify(jqXHR));
            window.location = "/login.html";
        }
    });
}

function addMoney(acc) {

    var inputId = "#add_money_" + acc;
    var amount = $(inputId).val();
    console.log("Amount: " + amount);
    var data = '{"amount":"' + amount + '"}';
    console.log("Data: " + data);
    if (amount === "") {
        console.log("nop!");
    } else {
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': token
            },
            url: "/api/users/user/" + id + "/account/" + acc + "/addMoney",
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
}

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