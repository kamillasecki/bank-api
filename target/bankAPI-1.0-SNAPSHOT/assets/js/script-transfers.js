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
        url: "/api/users/" + id,
        type: "GET",
        dataType: "json",
        success: function (result) {
            console.log("Received user details:" + JSON.stringify(result));
            $("#login_button").replaceWith('<a onClick="logout()" class="btn btn-primary navbar-btn navbar-right"><strong>Log out ' + result.login + '</strong></a>');
            $("#frame").empty();
            $("#frame").append('<br><h2>Hello, ' + result.name + '!</h2><h4>' + result.email + '</h4>');
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
        error: function (jqXHR) {
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
            url: "/api/users/" + id + "/accounts/" + acc,
            type: "POST",
            data: data,
            dataType: "json",
            success: function () {
                load();
            },
            error: function (jqXHR) {
                console.log("ERROR: " + JSON.stringify(jqXHR));
                var error = '<div class="alert alert-danger fade in">' +
                        '<strong>Error!</strong> ' + JSON.parse(jqXHR.responseText).text + '</div>';
                $("#alert2").append(error);
            }
        });
    }
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

function sendMoney(acc) {
    $("#frame").empty();
    $("#frame").append('<div class="row register-form">' +
            '<div class="col-md-9 col-md-offset-1">' +
            '<form class="form-horizontal custom-form" id="form" data-toggle="validator" role="form">' +
            '<h4>Transfer from account ' + acc + '</h4><hr>' +
            '<div class="form-group">' +
            '<div class="col-sm-4 label-column">' +
            '<label class="control-label">To account no.</label>' +
            '</div>' +
            '<div class="col-sm-6 input-column">' +
            '<input class="form-control" id="input_acc_no" type="text" required data-minlength="8">' +
            '<div class="help-block with-errors"></div>' +
            '</div>' +
            '</div>' +
            '<div class="form-group">' +
            '<div class="col-sm-4 label-column">' +
            '<label class="control-label" for="username-input-field">Amount</label>' +
            '</div>' +
            '<div class="col-sm-6 input-column">' +
            '<div class="input-group">' +
            '<span class="input-group-addon">€</span>' +
            '<input class="form-control currency" id="input_amount" required>' +
            '</div>' +
            '<div class="help-block with-errors"></div>' +
            '</div>' +
            '</div>' +
            '<div class="form-group">' +
            '<div class="col-sm-4 label-column">' +
            '<label class="control-label" for="username-input-field">Description</label>' +
            '</div>' +
            '<div class="col-sm-6 input-column">' +
            '<input class="form-control" id="input_desc" type="text" required>' +
            '<div class="help-block with-errors"></div>' +
            '</div>' +
            '</div>' +
            '<div id="alert"></div>' +
            '<button class="btn btn-default submit-button" onClick="transferMoney(' + acc + ')" type="button">Send</button>' +
            '</form>' +
            '</div>' +
            '</div>');
}

function transferMoney(acc) {
    var amount = $("#input_amount").val();
    var toAcc = $("#input_acc_no").val();
    var descr = $("#input_desc").val();

    var data = {
        "amount": amount,
        "description": descr,
        "accountNumber": toAcc
    }

    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': token
        },
        url: "/api/users/" + id + "/accounts/" + acc,
        type: "PUT",
        data: JSON.stringify(data),
        dataType: "json",
        success: function () {
            load();
        },
        error: function (jqXHR) {
            console.log("ERROR: " + JSON.stringify(jqXHR));
            var error = '<div class="alert alert-danger fade in">' +
                        '<strong>Error!</strong> ' + JSON.parse(jqXHR.responseText).text + '</div>';
                $("#alert").append(error);
        }
    });
}