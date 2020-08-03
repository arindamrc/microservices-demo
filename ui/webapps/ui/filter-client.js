var SERVER_URL = "http://localhost:8000/api";

function clearRequestFilterForms() {
    $("[id$='-form']").find( "input[type=text]" ).val("");
    
    $('[class$="-success"]').hide();
    $('[class$="-failure"]').hide();
}


$(document).ready(function() {

    clearRequestFilterForms();

    $("#make-request-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            cid = $form.find( "input[name='cid']" ).val(),
            ip = $form.find( "input[name='ip']" ).val(),
            ua = $form.find( "input[name='ua']" ).val(),
            tag = $form.find( "input[name='tag']" ).val(),
            ts = $form.find( "input[name='ts']" ).val();

        if (!ts) {
            ts = Math.round(new Date() / 1000);
        }

        // Compose the data in the format that the API is expecting
        var data = { cid: cid, tid: tag, uid: ua, ip: ip, timestamp: ts };

        // Send the data using post
        $.ajax({
            url: SERVER_URL + '/stats/request',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            success: function(result){
                if(result.id) {
                    $('.make-request-message-failure').hide()
                    $('#make-request-body').empty()
                    $('.make-request-message-success').show()
                    $('#make-request-body').append(
                        '<tr><td>' + result.id + '</td>' +
                        '<td>' + result.cid + '</td>' +
                        '<td>' + result.timestamp + '</td>' +
                        '<td>' + result.validCount + '</td>' +
                        '<td>' + result.invalidCount + '</td></tr>'
                    );
                } else {
                    $('.make-request-message-success').hide()
                    $('.make-request-message-failure').show()
                    $('.make-request-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                $('.make-request-message-success').hide()
                $('.make-request-message-failure').show()
                $('.make-request-message-failure').empty()
                    .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
            }
        });

    });


    $("#customer-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            cid = $form.find( "input[name='cid']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/stats/customer/'+cid,
            async: false,
            success: function(result){
                if(result.valid || result.invalid) {
                    $('.customer-message-failure').hide()
                    $('#customer-body').empty()
                    $('.customer-message-success').show()
                    $('#customer-body').append(
                        '<tr><td>' + result.valid + '</td>' +
                        '<td>' + result.invalid + '</td></tr>'
                    );
                } else {
                    $('.customer-message-success').hide()
                    $('.customer-message-failure').show()
                    $('.customer-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                $('.customer-message-success').hide()
                $('.customer-message-failure').show()
                $('.customer-message-failure').empty()
                    .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
            }
        });

    });


    $("#customer-day-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            cid = $form.find( "input[name='cid']" ).val(),
            date = $form.find( "input[name='date']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/stats/customer-day/'+cid+'/'+date,
            async: false,
            success: function(result){
                if(result.valid || result.invalid) {
                    $('.customer-day-message-failure').hide()
                    $('#customer-day-body').empty()
                    $('.customer-day-message-success').show()
                    $('#customer-day-body').append(
                        '<tr><td>' + result.valid + '</td>' +
                        '<td>' + result.invalid + '</td></tr>'
                    );
                } else {
                    $('.customer-day-message-success').hide()
                    $('.customer-day-message-failure').show()
                    $('.customer-day-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                $('.customer-day-message-success').hide()
                $('.customer-day-message-failure').show()
                $('.customer-day-message-failure').empty()
                    .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
            }
        });

    });


    $("#day-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            date = $form.find( "input[name='date']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/stats/day/'+date,
            async: false,
            success: function(result){
                if(result.valid || result.invalid) {
                    $('.day-message-failure').hide()
                    $('#day-body').empty()
                    $('.day-message-success').show()
                    $('#day-body').append(
                        '<tr><td>' + result.valid + '</td>' +
                        '<td>' + result.invalid + '</td></tr>'
                    );
                } else {
                    $('.day-message-success').hide()
                    $('.day-message-failure').show()
                    $('.day-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                $('.day-message-success').hide()
                $('.day-message-failure').show()
                $('.day-message-failure').empty()
                    .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
            }
        });

    });


    $("#blacklist-ip-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            ip = $form.find( "input[name='ip']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/ip/blacklist/'+ip,
            async: false,
            success: function(result){
                if(result.response) {
                    $('.blacklist-ip-message-failure').hide()
                    $('.blacklist-ip-message-success').empty()
                    $('.blacklist-ip-message-success').show()
                    $('.blacklist-ip-message-success').append(
                        "<p class='bg-success text-center'>IP Blacklisted!</p>"
                    );
                } else {
                    $('.blacklist-ip-message-success').hide()
                    $('.blacklist-ip-message-failure').show()
                    $('.blacklist-ip-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                $('.blacklist-ip-message-success').hide()
                $('.blacklist-ip-message-failure').show()
                $('.blacklist-ip-message-failure').empty()
                    .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
            }
        });

    });



    $("#whitelist-ip-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            ip = $form.find( "input[name='ip']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/ip/whitelist/'+ip,
            async: false,
            success: function(result){
                if(result.response) {
                    $('.whitelist-ip-message-failure').hide()
                    $('.whitelist-ip-message-success').empty()
                    $('.whitelist-ip-message-success').show()
                    $('.whitelist-ip-message-success').append(
                        "<p class='bg-success text-center'>IP Whitelisted!</p>"
                    );
                } else {
                    $('.whitelist-ip-message-success').hide()
                    $('.whitelist-ip-message-failure').show()
                    $('.whitelist-ip-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                $('.whitelist-ip-message-success').hide()
                $('.whitelist-ip-message-failure').show()
                $('.whitelist-ip-message-failure').empty()
                    .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
            }
        });

    });


    $("#blacklist-ua-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            ua = $form.find( "input[name='ua']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/ua/blacklist/'+ua,
            async: false,
            success: function(result){
                if(result.response) {
                    $('.blacklist-ua-message-failure').hide()
                    $('.blacklist-ua-message-success').empty()
                    $('.blacklist-ua-message-success').show()
                    $('.blacklist-ua-message-success').append(
                        "<p class='bg-success text-center'>UA Blacklisted!</p>"
                    );
                } else {
                    $('.blacklist-ua-message-success').hide()
                    $('.blacklist-ua-message-failure').show()
                    $('.blacklist-ua-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                $('.blacklist-ua-message-success').hide()
                $('.blacklist-ua-message-failure').show()
                $('.blacklist-ua-message-failure').empty()
                    .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
            }
        });

    });


    $("#whitelist-ua-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            ua = $form.find( "input[name='ua']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/ua/whitelist/'+ua,
            async: false,
            success: function(result){
                if(result.response) {
                    $('.whitelist-ua-message-failure').hide()
                    $('.whitelist-ua-message-success').empty()
                    $('.whitelist-ua-message-success').show()
                    $('.whitelist-ua-message-success').append(
                        "<p class='bg-success text-center'>UA Whitelisted!</p>"
                    );
                } else {
                    $('.whitelist-ua-message-success').hide()
                    $('.whitelist-ua-message-failure').show()
                    $('.whitelist-ua-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                $('.whitelist-ua-message-success').hide()
                $('.whitelist-ua-message-failure').show()
                $('.whitelist-ua-message-failure').empty()
                    .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
            }
        });

    });

});
