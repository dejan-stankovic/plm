/*define(["jquery", "kendo"], function ($, kendo) {
    $('.carousel').carousel();
    alert('hi');
});*/
$(document).ready(function () {
    //$('.carousel').carousel();

});
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
                text: "Configuration", imageUrl: "http://demos.kendoui.com/content/shared/icons/sports/snowboarding.png",
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
$(document).ready(function () {
    $("#projectddl").kendoComboBox();
    $("#releaseddl").kendoComboBox();
    $("#statusddl").kendoMultiSelect();

    var data1 = [
        { "Type": "User Story", "Title": "Employee login info", "Category": "Functional", "Status": "Initial", "Priority": "Medium", "Risk": "Medium" },
        { "Type": "User Story", "Title": "Capture Employee details", "Category": "Functional", "Status": "InProgress", "Priority": "High", "Risk": "Low" },
        { "Type": "Task", "Title": "Analyze employee login", "Category": "Analysis", "Status": "Initial", "Priority": "Medium", "Risk": "Medium" },
        { "Type": "Task", "Title": "Implement employee login form", "Category": "Development", "Status": "Pending", "Priority": "High", "Risk": "High" },
        { "Type": "Bug", "Title": "Employee detail accepts invalid chars.", "Category": "Bug", "Status": "Complete", "Priority": "High", "Risk": "High" },
        { "Type": "Bug", "Title": "Error in login page", "Category": "Bug", "Status": "Initial", "Priority": "High", "Risk": "High" }
    ];
    $("#grid").kendoGrid({
        dataSource: {
            data: data1,
            group: {
                field: "Type"
            },
            pageSize: 5
        },
        columns: [{
            field: "Type",
            Title: "Type",
            width: "4%",
            template: function (item) {
                if (item.Type == "Bug") {
                    return "<span style='align-content:center' class='icon_24 bug' title='Bug'></span>";
                }
                else if (item.Type == "User Story") {
                    return "<span style='align-content:center' class='icon_24 br' title='User Story'></span>";
                }
                else {
                    return "<span style='align-content:center' class='icon_24 task' title='Task'></span>";
                }
            } 
        }, {
            field: "Title",
            Title: "Title",
            width: "55%",
            template : "<a href='\\#'>${Title}</a>"
        }, {
            field: "Category",
            Title: "Category",
            width: "10%"
        },
        {
            field: "Priority",
            Title: "Priority",
            width: "10%"
        }, {
            field: "Risk",
            Title: "Risk",
            width: "10%"
        },
        {
            field: "Status",
            Title: "Status",
            width: "10%",
            template: function (item) {
                if (item.Status == "Initial") {
                    return "<span style='text-align:center' class='icon_24 initial' title='Initial'></span>";
                }
                else if (item.Status == "Pending") {
                    return "<span style='text-align:center' class='icon_24 warning' title='Pending'></span>";
                }
                else if (item.Status == "InProgress") {
                    return "<span style='text-align:center' class='icon_24 inprogress' title='InProgress'></span>";
                }
                else if (item.Status == "Complete") {
                    return "<span style='text-align:center' class='icon_24 complete' title='Complete'></span>";
                }
                else {
                    return "";
                }
            }
        }
        ],
        sortable: {
            mode: "multiple",
            allowUnsort: true
        },
        reorderable: true,
        resizable: true,
        pageable: true,
        scrollable: false,
        filterable: {
            extra: false,
            operators: {
                string: {
                    startswith: "Starts with",
                    eq: "Is equal to",
                    neq: "Is not equal to"
                }
            }
        }
    });
});

