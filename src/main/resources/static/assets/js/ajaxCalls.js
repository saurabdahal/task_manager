function sendRequestToController(url,data,formObj){
    $.ajax({
        type: 'POST',
        url: url,
        contentType: false,
        processData: false,
        data: data,
        success: function (response) {
            if(formObj !== null) formObj.trigger("reset");
        },
        error: function (all) {
        }
    })
}

function sendRequestToControllerAndRedirect(url,data,formObj,redirectURL){
    $.ajax({
        type: 'POST',
        url: url,
        contentType: "application/json",
        data: data,
        success: function (response) {
            window.location.replace(redirectURL);
        },
        error: function (all) {
            $.notify(all.responseText, "error");
        }
    })
}
