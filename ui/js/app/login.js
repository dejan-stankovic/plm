/*************************************************************************
		File name: login.js
		Author: Rachit
		Created date: 09/30/2013
		Purpose: client functionality associated with login page
**************************************************************************/

/************************************************************
		Function name: onready
		Author: Rachit
		Created date: 09/30/2013
		Purpose: ready function invoked when page is rendered
**************************************************************/
$(document).ready(function () {
    var window = $("#splashPopup");
    if (!window.data("kendoWindow")) {
        window.kendoWindow({
            width: "600px",
            modal: true,
            title: "Project Life Cycle Management 1.0",
            actions: [
                "Pin",
                "Minimize",
                "Maximize",
                "Close"
            ]
        });
    }
    window.data("kendoWindow").center().open();
    setTimeout(function () { window.data("kendoWindow").close(); document.getElementById("body").style.display = ""; }, 3000);
});


