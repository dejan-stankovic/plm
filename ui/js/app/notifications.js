
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
        dataSource: [
            {
                text: "Views", imageUrl: "http://demos.kendoui.com/content/shared/icons/sports/baseball.png",
                items: [
                    { text: "Dashboard", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/star.png" },
                    { text: "Notifications", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
                    { text: "Pipeline", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/video.png" },
                ]
            },
            {
                text: "Search", imageUrl: "http://demos.kendoui.com/content/shared/icons/sports/swimming.png",
                items: [
                    { text: "User Story", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
                    { text: "Task", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
                    { text: "Bug", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/video.png" },
                ]
            },
            {
                text: "Release Management", imageUrl: "http://demos.kendoui.com/content/shared/icons/sports/swimming.png",
                items: [
                    { text: "Manage Release", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/star.png" },
                ]
            },
            {
                text: "Configuration", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/snowboarding.png",
                items: [
                    { text: "Manage Projects", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
                    { text: "Manage Users", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/video.png" },
                    { text: "Manage Roles", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/photo.png" },
                    { text: "User Profiles", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/star.png" },
                ]
            },
            {
                text: "Reporting", imageUrl: "http://demos.kendoui.com/content/shared/icons/sports/swimming.png",
                items: [
                    { text: "Resource Allocation", imageUrl: "http://demos.kendoui.com/content/shared/icons/16/star.png" },
                ]
            },
        ]
    });
});