var SERVER_URL = "http://localhost:8000/api";

function clearCustomerCreateForms() {
    $("#create-form").find( "input[name='cname']" ).val("");
    $("#get-form").find( "input[name='cid']" ).val("");
    $("#activate-form").find( "input[name='cid']" ).val("");
    $("#deactivate-form").find( "input[name='cid']" ).val("");
    $("#delete-form").find( "input[name='cid']" ).val("");

    $(".create-message-success").hide();
    $(".create-message-failure").hide();
    $(".get-message-success").hide();
    $(".get-message-failure").hide();
    $(".activate-message-success").hide();
    $(".activate-message-failure").hide();
    $(".deactivate-message-success").hide();
    $(".deactivate-message-failure").hide();
    $(".delete-message-success").hide();
    $(".delete-message-failure").hide();
}


$(document).ready(function() {

    clearCustomerCreateForms();

    $("#create-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            cname = $form.find( "input[name='cname']" ).val();

        // Compose the data in the format that the API is expecting
        var data = { name: cname };

        // Send the data using post
        $.ajax({
            url: SERVER_URL + '/customer/create',
            type: 'POST',
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            timeout: 1000,
            success: function(result){
                if(result.id) {
                    $('.create-message-failure').hide()
                    $('#create-details-body').empty()
                    $('.create-message-success').show()
                    $('#create-details-body').append(
                        '<tr><td>' + result.id + '</td>' +
                        '<td>' + result.name + '</td>' +
                        '<td>' + result.active + '</td></tr>'
                    );
                } else {
                    $('.create-message-success').hide()
                    $('.create-message-failure').show()
                    $('.create-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                if(xhr.status==404) {
                    $('.create-message-success').hide()
                    $('.create-message-failure').show()
                    $('.create-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }else {
                    $('.create-message-success').hide()
                    $('.create-message-failure').show()
                    $('.create-message-failure').empty()
                        .append("<p class='bg-danger text-center'>The service may be down!</p>");                    
                }
            }
        });

    });


    $("#get-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            cid = $form.find( "input[name='cid']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/customer/get/'+cid,
            async: false,
            success: function(result){
                if(result.id) {
                    $('.get-message-failure').hide()
                    $('#get-details-body').empty()
                    $('.get-message-success').show()
                    $('#get-details-body').append(
                        '<tr><td>' + result.id + '</td>' +
                        '<td>' + result.name + '</td>' +
                        '<td>' + result.active + '</td></tr>'
                    );
                } else {
                    $('.get-message-success').hide()
                    $('.get-message-failure').show()
                    $('.get-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Ooops. Something went wrong!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                if(xhr.status==404) {
                    $('.get-message-success').hide()
                    $('.get-message-failure').show()
                    $('.get-message-failure').empty()
                        .append("<p class='bg-danger text-center'>This customer does not exist!</p>");
                }
            }
        });

    });


    $("#activate-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            cid = $form.find( "input[name='cid']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/customer/activate/'+cid,
            async: false,
            success: function(result){
                if(result.response) {
                    $('.activate-message-failure').hide()
                    $('.activate-message-success').empty()
                    $('.activate-message-success').show()
                    $('.activate-message-success').append(
                        "<p class='bg-success text-center'>Customer Activated!</p>"
                    );
                } else {
                    $('.activate-message-success').hide()
                    $('.activate-message-failure').show()
                    $('.activate-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Customer Activation Failed!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                if(xhr.status==404) {
                    $('.activate-message-success').hide()
                    $('.activate-message-failure').show()
                    $('.activate-message-failure').empty()
                        .append("<p class='bg-danger text-center'>This customer does not exist!</p>");
                }
            }
        });

    });


    $("#deactivate-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            cid = $form.find( "input[name='cid']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/customer/deactivate/'+cid,
            async: false,
            success: function(result){
                if(result.response) {
                    $('.deactivate-message-failure').hide()
                    $('.deactivate-message-success').empty()
                    $('.deactivate-message-success').show()
                    $('.deactivate-message-success').append(
                        "<p class='bg-success text-center'>Customer Deactivated!</p>"
                    );
                } else {
                    $('.deactivate-message-success').hide()
                    $('.deactivate-message-failure').show()
                    $('.deactivate-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Customer Deactivation Failed!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                if(xhr.status==404) {
                    $('.deactivate-message-success').hide()
                    $('.deactivate-message-failure').show()
                    $('.deactivate-message-failure').empty()
                        .append("<p class='bg-danger text-center'>This customer does not exist!</p>");
                }
            }
        });

    });


    $("#delete-form").submit(function( event ) {

        // Don't submit the form normally
        event.preventDefault();

        // Get some values from elements on the page
        var $form = $( this ),
            cid = $form.find( "input[name='cid']" ).val();

        // Send the data
        $.ajax({
            url: SERVER_URL + '/customer/delete/'+cid,
            async: false,
            success: function(result){
                if(result.response) {
                    $('.delete-message-failure').hide()
                    $('.delete-message-success').empty()
                    $('.delete-message-success').show()
                    $('.delete-message-success').append(
                        "<p class='bg-success text-center'>Customer Deleted!</p>"
                    );
                } else {
                    $('.delete-message-success').hide()
                    $('.delete-message-failure').show()
                    $('.delete-message-failure').empty()
                        .append("<p class='bg-danger text-center'>Customer Deletion Failed!</p>");
                }
            },
            error: function(xhr, ajaxOptions, thrownError){
                if(xhr.status==404) {
                    $('.delete-message-success').hide()
                    $('.delete-message-failure').show()
                    $('.delete-message-failure').empty()
                        .append("<p class='bg-danger text-center'>This customer does not exist!</p>");
                }
            }
        });

    });

});
