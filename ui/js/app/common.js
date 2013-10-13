/*************************************************************************
		File name: common.js
		Author: Manav
		Created date: 09/30/2013
		Purpose: common routines
**************************************************************************/

/************************************************************
		Function name: onready
		Author: Manav
		Created date: 09/30/2013
		Purpose: ready function invoked when any page is rendered
**************************************************************/
$(document).ready(function () {
    $("#vertical").kendoSplitter({
        orientation: "vertical",
        panes: [
            { collapsible: false },
            { collapsible: false, size: "100px" },
            { collapsible: false, resizable: false, size: "100px" }
        ]
    });

    $("#horizontal").kendoSplitter({
        panes: [
            { collapsible: true, size: "320px" },
            { collapsible: false },
            { collapsible: true, size: "320px" }
        ]
    });

    $("#panelbar-images").kendoPanelBar({
        select : onitemSelected,
        dataSource: [
            {
                text: "Views", imageUrl: "../css/Images/edit_enable.png",
                items: [
                    { text: "Dashboard", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/star.png" },
                    { text: "Notifications", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
                    { text: "Pipeline", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/video.png" },
                ]
            },
            {
                text: "Search", imageUrl: "../css/Images/addtask_enable.png",
                items: [
                    { text: "Search By Type", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
                ]
            },
            {
                text: "Release Management", imageUrl: "../css/Images/download_enable.png",
                items: [
                    { text: "Manage Release", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/star.png" },
                ]
            },
            {
                text: "Configuration", imageUrl: "../css/Images/gear-icon.png",
                items: [
                    { text: "Manage Projects", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
                    { text: "Manage Users", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/video.png" },
                    { text: "Manage Roles", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
                ]
            },
            {
                text: "Reporting", imageUrl: "../css/Images/inactive_16.png",
                items: [
                    { text: "Resource Allocation", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/star.png" },
                ]
            },
        ]
    });
    function onitemSelected(e) {
        var selectedItem = $(e.item).find("> .k-link").text();
        switch (selectedItem) {
            case ('Dashboard'):
                window.location = "dashboard.html";
                break;
            case ('Notifications'):
                window.location = "notifications.html";
                break;
            case ('Manage Roles'):
                window.location = "manageroles.html";
                break;
        }
    }
});
