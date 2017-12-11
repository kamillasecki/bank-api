(function() {
    $(document).ready(function() {
    var url_string = window.location.href;
    var url = new URL(url_string);
    var token = url.searchParams.get("session");
    var id = url.searchParams.get("id");
  
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
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("ERROR: " + JSON.stringify(jqXHR));
                    }
                });
            
    });
}());
