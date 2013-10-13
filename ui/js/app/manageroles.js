﻿/*************************************************************************
		File name: manageroles.js
		Author: Alan
		Created date: 09/30/2013
		Purpose: client functionality associated with manageroles page
**************************************************************************/

/************************************************************
		Function name: onready
		Author: Alan
		Created date: 09/30/2013
		Purpose: ready function invoked when page is rendered
**************************************************************/

$(document).ready(function () {
    
    var projects = ["Select","Employee Portal", "HR Portal", "SAP Integration"];
    var roles = ["Select", "Stake Holder", "Business Analyst", "Project Leader", "Developer", "QA Tester", "Release Manager"];
    var users = ["Alan", "Christian", "Manav", "Rachit", "Tandy", "Yuvraj" ];
    var roleMapData = [
        { "Project": "Employee Portal", "User": "Christian", "Role": "Project Leader" },
        { "Project": "Employee Portal", "User": "Manav", "Role": "Developer" },
         { "Project": "Employee Portal", "User": "Tandy", "Role": "QA Tester" },
        { "Project": "Employee Portal", "User": "Yuvraj", "Role": "Release Manager" },

        { "Project": "HR Portal", "User": "Manav", "Role": "Project Leader" },
        { "Project": "HR Portal", "User": "Rachit", "Role": "Developer" },
         { "Project": "HR Portal", "User": "Yuvraj", "Role": "QA Tester" },
        { "Project": "HR Portal", "User": "Tandy", "Role": "Release Manager" },

        { "Project": "SAP Integration", "User": "Tandy", "Role": "Stake Holder" },
        { "Project": "SAP Integration", "User": "Manav", "Role": "Business Analyst" },
         { "Project": "SAP Integration", "User": "Alan", "Role": "Project Leader" },
        { "Project": "SAP Integration", "User": "Yuvraj", "Role": "Developer" },
        { "Project": "SAP Integration", "User": "Rachit", "Role": "QA Tester" },
    ];
    $("#projectddl").kendoComboBox({
        dataSource: projects,
        optionLabel: "select project"
    });
    $("#rolesddl").kendoComboBox({
        dataSource: roles,
        optionLabel: "select role"
    });
    $("#usersac").kendoAutoComplete({
        dataSource: users,
        filter: "startswith",
        placeholder: "select user...",
        separator: ", "
    });

    $("#rolesGrid").kendoGrid({
        dataSource: {
            data: roleMapData,
            pageSize: 15
        },
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

    $("filterBtn").click(function (e) {
        var grid = $("#rolesGrid").data("kendoGrid").dataSource.filter(
            [{ field: "Project", operator: "eq", value: $("projectddl").val() },
            { field: "User", operator: "eq", value: $("usersac").val() },
            { field: "Role", operator: "eq", value: $("rolesddl").val() }]
         );
        grid.refresh();
    });
    $("clearBtn").click(function (e) {
        var grid = $("#rolesGrid").data("kendoGrid").dataSource.filter(
            []
         );
        grid.refresh();
    });
});
