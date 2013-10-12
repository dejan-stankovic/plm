define(["jquery", "kendo"], function ($, kendo) {

    /*  variables */
    var genericList = ["ADG1234", "ADG5678", "ADG6789"];
    var gridIds = new Array();
    var phaseStates = { Initial: "Initial", PendingInitiate: "PendingInitiate", InProgress: "InProgress", Complete: "Complete" };
    var taskTypes = { Template: "Template", User: "User" };
    var genericLinks = new Array();
    var newPhaseId = 105;
    var newTaskId = 20;
    var tapeoutSequenceNumber = 1;
    var EntityStatus = { UnChanged: "UnChanged", Added: "Added", Updated: "Updated", Deleted: "Deleted" };
    var isReset = false;
    var teamMembers;
    var psd0tasks, psd2tasks, psd25tasks, psd3tasks, psd25Phase, taskList, templateList, templatePhaseList;
    var genericMappingList;
    var templates;
    var linksList;
    var currentPhaseLocked = true;
    var currentGeneric;
    var role;
    var selectedItemType = '';
    var selectedPhaseId = 0;
    var selectedTaskId = 0;
    var selectedGridId = '';
    var selectedHeaderRowId = 0;
    var currentPhaseIndex = 0;

    var storedData;
    var storedGenericMapping;
    var phaseList;
    var genericMapping;

    var viewModel;

    /* ready */
    $(function () {
        constructData();
        initialize();
        kendofy();
        render();
        registerEvents();
        alert('hi');
    });

    /* common code */
    function constructData() {
        psd0tasks = [{ SequenceNumber: 1, Id: 1, Title: "Generics/Models for Sign-Off", Description: "Attach a list of all the Generics/Models that are included for sign off in this phase", Template: [], SignOff: [{ Role: "Design Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                { SequenceNumber: 2, Id: 2, Title: "Technical Requirements Document", Description: "This document describes all technical requirements of the product including Design-for-Test features, ESD requirements and Datasheet specs. Please use template in the link below or an approved template from your PTD", Template: [], SignOff: [{ Role: "Applications Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                { SequenceNumber: 3, Id: 3, Title: "Architecture Review", Description: "", Template: ["http://goo.gl/66TH9"], SignOff: [{ Role: "Design Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                { SequenceNumber: 4, Id: 4, Title: "Fuse IP", Description: "Identify centrally controlled fuse IP to be used in the development. Justify any specific IP development for the project", Template: [], SignOff: [{ Role: "Design Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] }
        ];
        psd2tasks = [{ SequenceNumber: 1, Id: 5, Title: "Additional Documents", Description: "Please attach any additional relevant documents here. For example Change Management forms", Template: [], SignOff: [{ Role: "Team/Project Leader", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 2, Id: 6, Title: "Generics/Models for sign off", Description: "Attach a list of all the Generics/Models that are included for sign off in this phase. Highlight any additional Generics introduced since PSD1 approval", Template: [], SignOff: [{ Role: "Team/Project Leader", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 3, Id: 7, Title: "Technical Requirements Document", Description: "Attach updated TRD", Template: [], SignOff: [{ Role: "Team/Project Leader", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 4, Id: 8, Title: "Manufacturing Engagement (Savvion) Launched", Description: "Sign off to confirm Savvionn has been launched. Enter Project ID", Template: [], SignOff: [{ Role: "New Product Coordinator", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 5, Id: 9, Title: "Fuse IP Integration", Description: "Verify that Fuse IP integration has followed the guidelines in ADI spec ADI1194 Justify any deviations from the guidelines", Template: [], SignOff: [{ Role: "Design Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 6, Id: 10, Title: "Design/Esd Reviews", Description: "Post SharePoint link for completed reviews and any actions. Specifically list any unsupported (CAD) structures used in the development (e.g. ESD structures; Bond Pads etc) and Risk Mitigation measures taken for any of the above", Template: [], SignOff: [{ Role: "Design Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 7, Id: 11, Title: "Layout Review", Description: "Post SharePoint link for completed reviews. Please include any waivers on DRC’s. Specifically list any unsupported (CAD) structures used in the development (e.g. ESD structures; Bond Pads etc)", Template: [], SignOff: [{ Role: "Layout Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
        ];
        psd25tasks = [{ SequenceNumber: 1, Id: 5, Title: "Generics/Models for Sign-Off", Description: "Approvals indicate that you have reviewed the above information, and are in agreement to proceed to the next PSD stage", Template: [], SignOff: [{ Role: "Team/Project Leader", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 2, Id: 6, Title: "Design/Layout Review", Description: "Attach a list of all the Generics/Models that are included for sign off in this phase. Highlight any additional Generics introduced since PSD2 approval", Template: [], SignOff: [{ Role: "Design Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 3, Id: 7, Title: "Silicon Evaluation Status", Description: "Attach summary documents or links as appropriate.Reason for Iteration.", Template: [], SignOff: [{ Role: "Team/Project Leader", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 4, Id: 8, Title: "Update Development Resources, Schedule and Cost", Description: "Attach summary documents or links as appropriate.Functionality report.", Template: [], SignOff: [{ Role: "New Product Coordinator", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 5, Id: 9, Title: "Project Review/Delta Report- All Layer Edits", Description: "Attach current versions of the following - Microsoft project plan or similar, please attach in Pdf for all to be able to view.", Template: [], SignOff: [{ Role: "Design Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 6, Id: 10, Title: "Update Manufacturing Cost (FFC)", Description: "Attach current versions of the following: FFC detailed breakdown Ensure BJD is updated to reflect any changes to cost and schedule.", Template: [], SignOff: [{ Role: "Design Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 7, Id: 11, Title: "Update Technical Risk Management Plan", Description: "Attach updated version of Risk Management Plan showing current status of Risk items", Template: [], SignOff: [{ Role: "Layout Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 6, Id: 12, Title: "Attach updated BJD", Description: "Save BJD as Excel or Pdf and attach", Template: [], SignOff: [{ Role: "Design Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 7, Id: 13, Title: "Manager Approval", Description: "Approvals indicate that you have reviewed the above information, and are in agreement to proceed to the next PSD stage", Template: [], SignOff: [{ Role: "Design Engineering Manager", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
        ];
        psd3tasks = [{ SequenceNumber: 1, Id: 12, Title: "Characterization/YA Limits & Final Design Evaluation", Description: "", Template: [], SignOff: [{ Role: "Product/Characterization Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 2, Id: 13, Title: "Market Launch plan-Corporate Release Requirements", Description: "", Template: [], SignOff: [{ Role: "Marketing Engineer", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 3, Id: 14, Title: "Maskworks Registration.", Description: "", Template: [], SignOff: [{ Role: "Team/Project Leader", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] },
                     { SequenceNumber: 4, Id: 15, Title: "PL/Engineering Director Phase Task Approval", Description: "", Template: [], SignOff: [{ Role: "Engineering Manager/Director", User: "", Date: "", Comments: "", IsSignedOff: false, IsNa: false }], IsLocked: true, Status: "UnChanged", Type: "Template", Links: [] }
        ];
        psd25Phase = { SequenceNumber: 2, Id: 16, Title: "PSD 2.5: Approval for Iterations", Description: "Approvals indicate that you have reviewed the above information, and are in agreement to proceed to the next PSD stage", Approver: "", Tasks: psd25tasks, SignOff: [{ Role: "Products and Technology Director", User: "", Date: "", Comments: "", IsSignedOff: false }], IsLocked: false, State: "Initial", SignOffState: "Initial", selectedTemplate: '', IsTapeoutPhase: true, CanApprove: false, IsNa: false };

        taskList = [psd0tasks, psd2tasks, psd3tasks];
        templatePhaseList = {
            Phases: [
                { SequenceNumber: 1, Id: 101, Title: "Generic Start", Description: "", Approver: "", Tasks: [], SignOff: [{ Role: "", User: "", Date: "", Comments: "", IsSignedOff: false }], IsLocked: false, State: "PendingInitiate", SignOffState: "Initial", selectedTemplate: '', IsTapeoutPhase: false, CanApprove: false, IsNa: false },
                { SequenceNumber: 2, Id: 102, Title: "PSD 2: Approval for Tapeout/Manufacturing start", Description: "", Approver: "", Tasks: [], SignOff: [{ Role: "", User: "", Date: "", Comments: "", IsSignedOff: false }], IsLocked: false, State: "Initial", SignOffState: "Initial", selectedTemplate: '', IsTapeoutPhase: false, CanApprove: true, IsNa: false },
                { SequenceNumber: 3, Id: 103, Title: "PSD 3: Approval for Release To Sales", Description: "", Approver: "", Tasks: [], SignOff: [{ Role: "", User: "", Date: "", Comments: "", IsSignedOff: false }], IsLocked: false, State: "Initial", SignOffState: "Initial", selectedTemplate: '', IsTapeoutPhase: false, CanApprove: false, IsNa: false },
            ],
        };
        templateList = [
                                { Name: "DIV[PTG]", Description: "Template for 'PTG' division", IsSelected: false, IsEditable: false },
                                { Name: "BUS[PPT]", Description: "Template for 'PPT' business unit", IsSelected: false, IsEditable: false },
                                { Name: "STG[PCV]", Description: "Template for 'PCV' strategy", IsSelected: false, IsEditable: false },
                                { Name: "SBS[AUT]", Description: "Template for 'AUT' segment", IsSelected: false, IsEditable: false }
        ];
        teamMembers = ["Martin, Brad", "Kemp, Kelly", "Cuozzo, Michael", "Ponnapalli, Vamshi"];
        genericMappingList = [{ Id: 0, Generics: [], Lock: "" }, { Id: 1, Generics: [], Lock: "" }, { Id: 2, Generics: [], Lock: "" }];
        templates = new kendo.data.ObservableObject({
            data: templateList
        });
        linksList = new kendo.data.ObservableArray([]);
        currentPhaseLocked = true;
        currentGeneric = getQueryParamValue("generic");
        role = getQueryParamValue("role");
        if (role == null) { role = "basic"; }
        if (currentGeneric == null) { currentGeneric = "ADG1234"; }

        storedData = localStorage.getItem(currentGeneric);
        storedGenericMapping = localStorage.getItem(getGenericMappingKey());
        phaseList = (storedData == null) ? templatePhaseList : JSON.parse(storedData);
        genericMapping = (storedGenericMapping == null) ? genericMappingList : JSON.parse(storedGenericMapping);

        viewModel = new kendo.data.ObservableObject({
            data: phaseList,
            addTask: function (phaseId, task) {
                $.each(this.data.Phases, function (index, phase) {
                    if (phase.Id == phaseId) {
                        phase.Tasks.push(task);
                    }
                });
            },
            updateTask: function (showFlag) {
                for (j = 0; j < this.data.Phases.length; j++) {
                    for (i = 0; i < this.data.Phases[j].Tasks.length; i++) {
                        this.data.Phases[j].Tasks[i].IsLocked = showFlag;
                    }
                }
            }
        });

    }
    function kendofy() {
        $("#signOffPopupFile").kendoUpload();
        $("#newApproverRoles").kendoDropDownList({});
        $("#taskRole").kendoDropDownList({});
        $("#emailAdditionalInfo").kendoEditor();
    }
    function setLockedField() {
        var key = getGenericMappingKey();
        lsGenericMapping = localStorage.getItem(key);
        if (lsGenericMapping != null) {
            var lsMappings = JSON.parse(lsGenericMapping);
            $.each(lsMappings, function (i, mapItem) {
                if ((mapItem.Lock != "") && (mapItem.Id == key)) {
                    $.each(mapItem.Generics, function (index, generic) {
                        if ((generic == currentGeneric) &&
                            (mapItem.Lock != currentGeneric)) {
                            $("#editCurrentPhaseBtn").attr("disabled", "disabled");
                            $("#resetPhaseBtn").attr("disabled", "disabled");
                            $("#saveTasksBtn").attr("disabled", "disabled");
                        }
                    });
                }
            });
        }
    }
    function showSnapshot() {
        $("#taskSnapshotPopup").data("kendoWindow").center().open();
        return false;
    }
    function selectTemplate(name) {
        $.each(templates.data, function (index, template) {
            template.IsSelected = false;
            if (template.Name == name) {
                var currentPhase = viewModel.data.Phases[currentPhaseIndex];
                currentPhase.SelectedTemplate = template.Name;
                template.IsSelected = true;
            }
        });
        $("#selectTemplateList").data("kendoListView").refresh();
    }
    function saveSignOff(phase) {
        var signoffList;
        var signoffTemplateList = {
            Phases: [
                { SequenceNumber: 1, Id: 101, Title: "Generic Start", Approvers: [], State: "Initial", IsLocked: false },
                { SequenceNumber: 2, Id: 102, Title: "PSD 2: Approval for Tapeout/Manufacturing start", Approvers: [], State: "Initial", IsLocked: false },
                { SequenceNumber: 3, Id: 103, Title: "PSD 3: Approval for Release To Sales", Approvers: [], State: "Initial", IsLocked: false },
            ],
        };
        var signoffList;
        var signOffData = localStorage.getItem("SignOff");
        if ((signOffData == null) || (signOffData == "")) {
            signoffList = signoffTemplateList;
        }
        else {
            signoffList = JSON.parse(signOffData);
        }
        $.each(signoffList.Phases, function (index, signoffPhase) {
            if (signoffPhase.Id == phase.Id) {
                signoffPhase.State = phaseStates.PendingInitiate;
                return;
            }
        });
        localStorage.setItem("SignOff", JSON.stringify(signoffList));
    }
    function insertTapeoutSignOff(newPhasePositionIndex, currentPhase, newPhase) {
        var signOffData = localStorage.getItem("SignOff");
        if ((signOffData != null) && (signOffData != "")) {
            var signoffList = JSON.parse(signOffData);
            $.each(signoffList.Phases, function (index, signoffPhase) {
                if (signoffPhase.Id == currentPhase.Id) {
                    var approvers = new Array();
                    $.each(signoffPhase.Approvers, function (index, approver) {
                        approvers.push(approver);
                    });
                    var newSignoffPhase = {
                        SequenceNumber: newPhase.SequenceNumber,
                        Id: newPhase.Id,
                        Title: newPhase.Title,
                        Approvers: approvers,
                        State: phaseStates.PendingInitiate,
                        IsLocked: false
                    };
                    signoffList.Phases.splice(newPhasePositionIndex, 0, newSignoffPhase);
                }
            });
            localStorage.setItem("SignOff", JSON.stringify(signoffList));
        }

    }
    function deleteTapeoutSignOff(phaseIndex) {
        var signOffData = localStorage.getItem("SignOff");
        if ((signOffData != null) && (signOffData != "")) {
            var signoffList = JSON.parse(signOffData);
            signoffList.Phases.splice(phaseIndex, 1);
        }
        localStorage.setItem("SignOff", JSON.stringify(signoffList));
    }
    function disableToolBar() {
        $("#initiatePhaseBtn").hide();
        $("#editBtn").hide();
        $("#moveUpBtn").hide();
        $("#moveDownBtn").hide();
        $("#addBtn").hide();
        $("#deleteBtn").hide();
        $("#reloadBtn").hide();
        $("#initiateTapeoutBtn").hide();
        $("#initiatePhaseDisabledBtn").hide();
        $("#editDisabledBtn").hide();
        $("#moveUpDisabledBtn").hide();
        $("#moveDownDisabledBtn").hide();
        $("#addDisabledBtn").hide();
        $("#deleteDisabledBtn").hide();
        $("#reloadDisabledBtn").hide();

        if (role == "admin") {
            $("#initiatePhaseDisabledBtn").show();
            $("#editDisabledBtn").show();
            $("#moveUpDisabledBtn").show();
            $("#moveDownDisabledBtn").show();
            $("#addDisabledBtn").show();
            $("#deleteDisabledBtn").show();
            $("#reloadDisabledBtn").show();
        }
        $("#initiateTapeoutDisableBtn").show();
    }
    function enablePhaseToolBar(phaseId) {
        if (role == "admin") {
            $("#initiatePhaseBtn").show();
            $("#addBtn").show();
            $("#reloadBtn").show();
        }
        $("#editBtn").hide();
        $("#moveUpBtn").hide();
        $("#moveDownBtn").hide();
        $("#deleteBtn").hide();

        $("#initiatePhaseDisabledBtn").hide();
        $("#addDisabledBtn").hide();
        $("#reloadDisabledBtn").hide();

        if (role == "admin") {
            $("#deleteDisabledBtn").show();
            $("#editDisabledBtn").show();
            $("#moveUpDisabledBtn").show();
            $("#moveDownDisabledBtn").show();
            var phase = $.grep(viewModel.data.Phases, function (item) {
                return item.Id == phaseId;
            })[0];
            if (phase != null)
                if (phase.IsTapeoutPhase && phase.State != phaseStates.Complete) {
                    $("#deleteBtn").show();
                    $("#deleteDisabledBtn").hide();
                }
        }
    }
    function enableTaskToolBar(phaseId, taskId) {
        if (role == "admin") {
            $("#editBtn").show();
            $("#moveUpBtn").show();
            $("#moveDownBtn").show();
            $("#deleteBtn").show();
        }
        $("#initiatePhaseBtn").hide();
        $("#addBtn").hide();
        $("#reloadBtn").hide();

        $("#editDisabledBtn").hide();
        $("#moveUpDisabledBtn").hide();
        $("#moveDownDisabledBtn").hide();
        $("#deleteDisabledBtn").hide();
        if (role == "admin") {
            $("#addDisabledBtn").show();
            $("#initiatePhaseDisabledBtn").show();
            $("#reloadDisabledBtn").show();
            var phase = $.grep(viewModel.data.Phases, function (item) {
                return item.Id == phaseId;
            })[0];
            if (phase != null) {
                $.each(phase.Tasks, function (index, task) {
                    if (task.Id == taskId && task.Type == taskTypes.Template) {
                        $("#moveUpBtn").hide();
                        $("#moveDownBtn").hide();
                        $("#deleteBtn").hide();
                        $("#moveUpDisabledBtn").show();
                        $("#moveDownDisabledBtn").show();
                        $("#deleteDisabledBtn").show();
                        return;
                    }
                });
            }
        }
    }

    /* render */
    function render() {
        renderHeader();
        renderPanelBar();
        renderGrids();
        renderPopups();
        renderTemplate();

        setTimeout(function () {
            var currentPhase = viewModel.data.Phases[currentPhaseIndex];
            if (currentPhase != null && currentPhase.State == phaseStates.PendingInitiate) {
                $("#phaseEditPopup").data("kendoWindow").center().open();
            }

        }, 500);
        $("#alertSection").hide();
    }
    function renderHeader() {
        $("#genericName").text(currentGeneric);
    }
    function renderPanelBar() {
        kendo.bind($("#panelbar"), viewModel.data);
    }
    function renderPopups() {
        renderPopup("taskPopup", "Edit Phase Tasks", "600px");
        renderPopup("addTaskPopup", "Add Task", "600px");
        renderPopup("phaseEditPopup", "Initiate Task CheckList", "600px");
        renderPopup("emailTaskPopup", "Email Task", "900px");
        renderPopup("taskSignOffPopup", "SignOff Task", "600px");
        renderPopup("taskSnapshotPopup", "Task Snapshot View", "1000px");
    }
    function renderPopup(popupId, title, width) {
        var window1 = $("#" + popupId);
        if (!window1.data("kendoWindow")) {
            window1.kendoWindow({
                width: width,
                title: title,
                modal: true
            });
        }
        window1.data("kendoWindow").close();
    }
    function renderGrids() {
        $.each(viewModel.data.Phases, function (index, phase) {
            renderTaskGrid(gridIds[index], phase.Id, phase.Tasks, phase.Id + 'HeaderRow');
        });
        var ds = new kendo.data.DataSource({
            data: psd2tasks,
            filter: { field: "Status", operator: "neq", value: "Deleted" }
        });
        renderSnapshotGrid(ds);
        renderLinksGrid();
    }
    function renderTaskGrid(gridId, phaseId, tasks, headerRowId) {
        var ds = new kendo.data.DataSource({
            data: tasks,
            filter: { field: "Status", operator: "neq", value: "Deleted" }
        });
        var grid = $("#" + gridId).kendoGrid({
            dataSource: ds,
            columns: [
                {
                    field: "blank",
                    title: "Edit",
                    width: "6%",
                    template: function (item) {
                        if (currentPhaseLocked) {
                            return ("<div style='text-align:center;' />");
                        }
                        else {
                            if (item.Type == taskTypes.Template) {
                                return ("<div style='text-align:center;'>" +
                                "<span id='" + item.Id + "' href='##' onclick='return openEmailPopup(\"" + gridId + "\"," + phaseId + "," + item.Id + ");' class='icon_16 mail'></span>&nbsp;" +
                                "<a href='##' class='icon_16 edit' onclick='return editTask(\"" + gridId + "\"," + phaseId + "," + item.Id + ");'></a>&nbsp;" +
                                "<a href='##' class='icon_16'></a></div>"
                                );
                            }
                            else {
                                return ("<div style='text-align:center;'>" +
                                "<span id='" + item.Id + "' href='##' class='icon_16 move'></span>&nbsp;" +
                                "<a href='##' class='icon_16 edit' onclick='return editTask(\"" + gridId + "\"," + phaseId + "," + item.Id + ");'></a>&nbsp;" +
                                "<a href='##' class='icon_16 delete' title='User Task' onclick='return deleteItem(\"" + gridId + "\"," + phaseId + "," + item.Id + ");'></a></div>"
                               );
                            }
                        }
                    },
                    headerAttributes: {
                        style: "display:none"
                    }
                },
                {
                    field: "Title",
                    title: "Title",
                    width: "50%",
                    template: "<div>#=Title#</div>",
                    headerAttributes: {
                        style: "display:none"
                    }
                },
                {
                    field: "blank",
                    title: "Role",
                    template: function (item) { return renderRole(item); },
                    width: "20%",
                    headerAttributes: {
                        style: "display:none"
                    }
                },
                {
                    field: "Approver",
                    title: "Approver",
                    template: function (item) { return renderApprover(item); },
                    width: "10%",
                    headerAttributes: {
                        style: "display:none"
                    }
                },
                {
                    field: "Date",
                    title: "Approved On",
                    template: function (item) { return renderApprovalDate(item); },
                    width: "10%",
                    headerAttributes: {
                        style: "display:none"
                    }
                },
                {
                    field: "blank",
                    title: " ",
                    width: "5%",
                    template: function (item) { return renderStatus(gridId, phaseId, item); },
                    headerAttributes: {
                        style: "display:none"
                    }
                }

            ],
            selectable: "row",
            change: function (e) {
                var selectedRows = this.select();
                if (selectedRows.length > 0) {
                    var dataItem = this.dataItem(selectedRows[0]);
                    onRowSelect('Task', phaseId, dataItem.Id, headerRowId, gridId);
                }
            }
        }).data("kendoGrid");
        grid.dataSource.sort({ field: "SequenceNumber", dir: "asc" })
        grid.table.kendoDraggable({
            filter: "tbody > tr span",
            group: "gridGroup",
            cursorOffset: { top: 10, left: 10 },
            hint: function (e) {
                var html = '';
                var id = (e.attr('Id'));
                var item = $.grep(tasks, function (item) {
                    return item.Id == id;
                })[0];
                html = "<td id= '" + item.Id + "'>" + item.Title + "</td>";
                return $('<div  class="k-grid k-widget" style="cursor:pointer;opacity:1.100;border:1px solid  #f06f1c;filter:alpha(opacity=100);z-index:99;font-size:x-small;font-weight:600;z-index:9999"><table><tbody><tr>' + html + '</tr></tbody></table></div>');
                return (html);
            }
        });
        grid.table.kendoDropTarget({
            group: "gridGroup",
            drop: function (e) {
                var source = $.grep(tasks, function (item) {
                    return item.Id == ($(e.draggable.currentTarget).attr("id"));
                })[0];
                var targetElement = ($(e.target).find("span").length == 0) ? $(e.target) : $(e.target).find("span");
                var target = $.grep(tasks, function (item) {
                    return item.Id == (targetElement.attr("id"));
                })[0];
                if ((source == null) || (target == null) || (source.IsApproved) || (target.IsApproved))
                { return false; }

                //not on same item
                if (source.get("Id") !== target.get("Id")) {
                    //reorder the items
                    var seqNum = source.get("SequenceNumber");
                    //Swap the SequenceNumber of source and target.
                    source.set("SequenceNumber", target.get("SequenceNumber"));
                    target.set("SequenceNumber", seqNum);

                    source.set("Status", (source.Status == EntityStatus.UnChanged) ? EntityStatus.Updated : source.Status);
                    target.set("Status", (target.Status == EntityStatus.UnChanged) ? EntityStatus.Updated : target.Status);
                    ds.sort({ field: "SequenceNumber", dir: "asc" });
                }
            }
        });

        if (tasks.length == 0) {
            // $("#" + gridId).hide();
        }
        else {
            // $("#" + gridId).show();
        }
    }
    function renderSnapshotGrid(ds) {
        $("#snapshotGrid").kendoGrid({
            dataSource: ds,
            columns: [
                {
                    field: "Title",
                    title: "Title",
                    width: "20%",
                    template: "<div>#=Title#</div>",
                },
                {
                    field: "Role",
                    title: "Role",
                    template: function (item) { return renderRole(item); },
                    width: "15%",
                },
                {
                    field: "Approver",
                    title: "Approver",
                    template: function (item) { return renderApprover(item); },
                    width: "10%",
                },
                {
                    field: "Date",
                    title: "Approved On",
                    template: function (item) { return renderApprovalDate(item); },
                    width: "10%",
                },
                {
                    field: "Comments",
                    title: "Comments",
                    width: "20%",
                    template: function (item) { return renderComments(item); },
                }

            ]
        });
    }
    function renderLinksGrid() {
        var ds = new kendo.data.DataSource({
            data: linksList,
        });
        $("#linksGrid").kendoGrid({
            dataSource: ds,
            columns: [
                {
                    field: "Title",
                    title: "Title",
                    template: "<span onclick='return deleteLinkItem(\"#=Title#\");' class='icon_16 delete'></span>&nbsp;&nbsp;<a href='#=Link#' target='_blank'>#=Title#</a>",
                    headerAttributes: {
                        style: "display:none"
                    }
                }
            ]
        });
        if (linksList.length == 0) {
            $("#linksSection").hide();
        }
    }
    function renderRole(item) {
        var htmlContents = "<div>";
        for (var i = 0; i < item.SignOff.length; i++) {
            htmlContents += (item.SignOff[i].Role + "</br>");
        }
        htmlContents += "</div>";
        return (htmlContents);
    }
    function renderApprover(item) {
        var htmlContents = "<div>";
        for (var i = 0; i < item.SignOff.length; i++) {
            htmlContents += (item.SignOff[i].User + "</br>");
        }
        htmlContents += "</div>";
        return (htmlContents);
    }
    function renderApprovalDate(item) {
        var htmlContents = "<div>";
        for (var i = 0; i < item.SignOff.length; i++) {
            htmlContents += (item.SignOff[i].Date + "</br>");
        }
        htmlContents += "</div>";
        return (htmlContents);
    }
    function renderComments(item) {
        var htmlContents = "<div class='description'>";
        for (var i = 0; i < item.SignOff.length; i++) {
            htmlContents += (item.SignOff[i].Comments + "</br>");
        }
        htmlContents += "</div>";
        return (htmlContents);
    }
    function renderStatus(gridId, phaseId, item) {
        var htmlContents = "<div style='text-align:center';>";
        for (var i = 0; i < item.SignOff.length; i++) {
            htmlContents += "<a href='##' class='icon_16 attachment' onclick='return openSignOffPopup(\"" + gridId + "\"," + phaseId + "," + item.Id + ");'></a>&nbsp;";
            if (item.SignOff[i].IsSignedOff) {
                var styleClass = (item.SignOff[i].IsNa) ? "icon_16 na" : "icon_16 signedoff";
                htmlContents += "<a href='##' title='" + item.SignOff[i].Comments + "' class= '" + styleClass + "' onclick='return openSignOffPopup(\"" + gridId + "\"," + phaseId + "," + item.Id + ");'></a>";
            }
            else {
                htmlContents += "<a href='##' class= 'icon_16 pending' onclick='return openSignOffPopup(\"" + gridId + "\"," + phaseId + "," + item.Id + ");'></a>";
            }
        }
        htmlContents += "</div>";
        return (htmlContents);
    }
    function renderTemplate() {
        var ds = new kendo.data.DataSource({
            data: templates.data
        });

        $("#selectTemplateList").kendoListView({
            dataSource: ds,
            template: kendo.template($("#newTaskTemplate").html())
        });
        $("#selectTemplateList").on("mouseenter", ".product", function (e) {
            kendo.fx($(e.currentTarget).find(".product-description")).expand("vertical").stop().play();
        }).on("mouseleave", ".product", function (e) {
            kendo.fx($(e.currentTarget).find(".product-description")).expand("vertical").stop().reverse();
        });

    }

    /* initialize */
    function initialize() {
        setCurrentPhase();
        setCurrentTemplate();
        setNewIds();
        initializeGlobalVariables();
        var currentPhase = viewModel.data.Phases[currentPhaseIndex];
        if (currentPhase != null) {
            initializePhaseEditPopup(currentPhase);
            loadGenericMasterList(currentPhase);
        }
        loadMultiSelectDropDowns();
        setLockedField();

        enableDisableControls();
    }
    function setNewIds() {
        $.each(viewModel.data.Phases, function (index, phase) {
            if (phase.IsTapeoutPhase) {
                tapeoutSequenceNumber++;
            }
        });
    }
    function setCurrentPhase() {
        $.each(viewModel.data.Phases, function (index, phase) {
            if ((phase.State == phaseStates.PendingInitiate) || (phase.State == phaseStates.InProgress)) {
                currentPhaseIndex = index;
                selectedPhaseId = phase.Id;
                selectedGridId = "psd" + phase.SequenceNumber + "Grid";
            }
        });
    }
    function setCurrentTemplate() {
        $.each(templates.data, function (index, template) {
            if (template.Name == template.SelectedTemplate) {
                template.IsSelected = true;
            }
        });
    }
    function initializePhaseEditPopup(phase) {
        var template = kendo.template($("#phaseEditTemplate").html());
        $("#phaseEditPopupContainer").html(template(phase));
    }
    function initializeGlobalVariables() {
        $.each(viewModel.data.Phases, function (index, phase) {
            gridIds.push('psd' + phase.SequenceNumber + 'Grid');
            genericLinks.push('psd' + phase.SequenceNumber + 'GenericLinks');
        });
    }
    function enableDisableControls() {
        disableToolBar();
        $("#initiateTapeoutBtn").hide();
        $("#initiateTapeoutDisableBtn").show();

        var psd2Phase = viewModel.data.Phases[1];
        var psd3Phase = viewModel.data.Phases[(viewModel.data.Phases.length - 1)];
        if (psd2Phase.State == phaseStates.Complete) {
            var activeTapeoutTasks = $.grep(viewModel.data.Phases,
                function (phase) { return ((phase.IsTapeoutPhase) && (phase.State != phaseStates.Complete)); });
            if ((activeTapeoutTasks.length == 0) && ((psd3Phase.State == phaseStates.PendingInitiate))) {
                $("#initiateTapeoutBtn").show();
                $("#initiateTapeoutDisableBtn").hide();
            }
        }
        else if (psd3Phase.State == phaseStates.Complete) {
            $("#editCurrentPhaseBtn").attr("disabled", "disabled");
            $("#saveTasksBtn").attr("disabled", "disabled");
        }
        $("#approvePhaseBtn").hide();
        var currentPhase = viewModel.data.Phases[currentPhaseIndex];
        if (currentPhase != null) {
            if ((currentPhase.State == phaseStates.PendingInitiate) && (currentPhase.CanApprove)) {
                $("#approvePhaseBtn").show();
            }
        }
    }

    /* load */
    function loadGenericMasterList(currentPhase) {
        /*$.each(genericMapping, function (j, mapping) {
            if (mapping != null) {
                var selectedGenerics = new Array();
                $.each(mapping.Generics, function (index, generic) {
                    if (generic != currentGeneric) {
                        selectedGenerics.push(generic);
                    }
                });
                loadGenericsMultiSelect((currentPhase.State == phaseStates.InProgress) ? selectedGenerics : genericList, selectedGenerics, j);
                $("#" + genericLinks[j]).text(selectedGenerics);
                return;
            }
        });*/
        loadGenericsMultiSelect(genericList, []);
        //$("#" + genericLinks[j]).text(selectedGenerics);
    }
    function loadGenericsMultiSelect(generics, selectedGenerics, currentIndex) {
        $.each(generics, function (index, generic) {
            if (generic != currentGeneric) {
                $('#generics').append('<option value="' + generic + '">' + generic + '</option>');
            }
        });
        if (true) {
            var ms = $("#generics").kendoMultiSelect().data("kendoMultiSelect");
            ms.value(selectedGenerics);
        }
    }
    function loadMultiSelectDropDowns() {
        $.each(genericList, function (index, generic) {
            if (generic != currentGeneric) {
                $('#newGenerics').append('<option value="' + generic + '">' + generic + '</option>');
            }
        });
        var ms = $("#newGenerics").kendoMultiSelect().data("kendoMultiSelect");
        $("#newGenericsSection").hide();

        $("#emailTeamMembers").kendoMultiSelect({
            dataSource: teamMembers
        });

    }

    /* phase event handlers */
    function editPhase(reset) {
        var phase = findPhase(selectedPhaseId);
        if (reset) {
            isReset = true;
            $('#genericsSection').hide();
            $('#newGenericsSection').show();
            $('.selectSection').show();
        }
        else {
            if (phase.State == phaseStates.InProgress) {
                $('.selectSection').hide();
            }
            $('#genericsSection').show();
            $('#newGenericsSection').hide();
        }
        $("#phaseEditPopup").data("kendoWindow").center().open();
        return false;
    }
    function saveEditPhase() {
        var key = getGenericMappingKey();
        var selectedGenerics = (isReset) ? $("#newGenerics").data("kendoMultiSelect").value() : $("#generics").data("kendoMultiSelect").value();
        selectedGenerics.push(currentGeneric);
        genericMapping[currentPhaseIndex] = ({ Id: key, Generics: selectedGenerics, Lock: currentGeneric });
        localStorage.setItem(key, JSON.stringify(genericMapping));
        var genericsDisplay = new Array();
        $.each(selectedGenerics, function (index, selectedGeneric) {
            if (selectedGeneric != currentGeneric) {
                genericsDisplay.push(selectedGeneric);
            }
        });
        $("#" + genericLinks[currentPhaseIndex]).text(genericsDisplay);

        var phase = findPhase(selectedPhaseId);
        if (phase != null) {
            phase.State = phaseStates.InProgress;

            gridIds.push('psd' + phase.SequenceNumber + 'Grid');
            genericLinks.push('psd' + phase.SequenceNumber + 'GenericLinks');

            $.each(taskList[(phase.SequenceNumber - 1)], function (j, task) {
                var currentTask = findTask(phase, task.Id);
                if (currentTask == null) {
                    phase.Tasks.push(task);
                }
                else if (!currentTask.SignOff[0].IsSignedOff) {
                    phase.Tasks.splice(j, 1, task);
                }
            });
            isReset = false;
            setPhaseState(phase);
            saveCheckList(true, true);
        }
        $("#phaseEditPopup").data("kendoWindow").close();
    }
    function cancelEditPhase() {
        isReset = false;
        $("#phaseEditPopup").data("kendoWindow").close();
    }
    function cancelPhase() {
        window.location = window.location.search;
    }
    function approveEditPhase() {
        var phase = findPhase(selectedPhaseId);
        phase.IsNa = true;
        phase.State = phaseStates.Complete;

        var nextPhase = viewModel.data.Phases[currentPhaseIndex + 1];
        if (nextPhase != null) {
            phase.SignOffState = phaseStates.PendingInitiate;
            saveSignOff(phase);
        }
        setPhaseState(phase);
        saveCheckList(true, true);
        currentPhaseIndex++;
        $("#phaseEditPopup").data("kendoWindow").close();
    }

    /* task event handlers */
    function openAddTaskPopup() {
        $("#addTaskPopup").data("kendoWindow").center().open();
        return false;
    }
    function addNewTask() {
        var task = {
            SequenceNumber: newTaskId, Id: newTaskId, Type: taskTypes.User,
            Title: $("#newTitle").val(), Description: $("#newTaskDescription").val(),
            Template: [$("#newTemplate").val()],
            SignOff: [{ Role: $("#newApproverRoles").val(), User: "", Date: "", Comments: "" }],
            IsLocked: false, Status: EntityStatus.Added, Type: taskTypes.User, Links: []
        };
        viewModel.addTask(selectedPhaseId, task);
        newTaskId++;

        closeAddTaskPopup();
        return (false);
    }
    function editTask() {
        var phase = findPhase(selectedPhaseId);
        var task = findTask(phase, selectedTaskId);

        $("#taskTitle").val(task.Title);
        $("#taskDescription").val(task.Description);
        $("#taskTemplate").val((task.Template.length > 0) ? task.Template[0] : "http://goo.gl/66TH9");
        $("#taskRole").val(task.SignOff[0].Role);
        $("#taskApprover").text(task.SignOff[0].User);
        $("#taskApprovedOn").text(task.SignOff[0].Date);
        $("#taskComments").text(task.SignOff[0].Comments);

        if ((task.Type == taskTypes.Template) || (task.SignOff[0].IsSignedOff)) {
            $("#taskTitle").attr("disabled", "disabled");
            $("#taskDescription").attr("disabled", "disabled");
            $("#taskTemplate").attr("disabled", "disabled");
            $("#taskRole").data("kendoDropDownList").enable(false);
        } else {
            $("#taskTitle").removeAttr("disabled");
            $("#taskDescription").removeAttr("disabled");
            $("#taskTemplate").removeAttr("disabled");
            $("#taskRole").data("kendoDropDownList").enable(true);
        }

        $("#taskPopup").data("kendoWindow").center().open();
        return false;
    }
    function openEmailPopup() {
        var title = "Generic : " + currentGeneric + " - CheckList Email Notification.";
        $("#emailTaskTitle").text(title);

        $("#emailTaskPopup").data("kendoWindow").center().open();
    }
    function emailTask() {
        cancelEmailTask();
    }
    function cancelEmailTask() {
        $("#emailTaskPopup").data("kendoWindow").close();
    }
    function deleteItem() {
        var currentPhase = findPhase(selectedPhaseId);
        if (selectedItemType == "Phase") {
            $.each(viewModel.data.Phases, function (index, phase) {
                if (phase != null && currentPhase.Id == phase.Id) {
                    viewModel.data.Phases.splice(index, 1);
                    deleteTapeoutSignOff(index);
                    return;
                }
            });
        }
        else {
            var task = findTask(currentPhase, selectedTaskId);
            task.Status = "Deleted";
            $("#" + selectedGridId).data("kendoGrid").dataSource.filter({ field: "Status", operator: "neq", value: "Deleted" });
            $("#" + selectedGridId).data("kendoGrid").refresh();
        }
        closeSignOffPopup();
    }
    function saveEditTask() {
        var phase = findPhase(selectedPhaseId);
        var task = findTask(phase, selectedTaskId);

        task.Title = $("#taskTitle").val();
        task.Description = $("#taskDescription").val();
        task.Template[0] = $("#taskTemplate").val();
        task.SignOff[0].Role = $("#taskRole").val();

        $("#" + selectedGridId).data("kendoGrid").refresh();
        $("#taskPopup").data("kendoWindow").close();
    }
    function cancelEditTask() {
        $("#taskPopup").data("kendoWindow").close();
    }
    function closeAddTaskPopup() {
        $("#addTaskPopup").data("kendoWindow").close();
        return (false);
    }
    function openSignOffPopup(gridId, phaseId, taskId) {
        selectedGridId = gridId;
        selectedPhaseId = phaseId;
        selectedTaskId = taskId;

        var phase = findPhase(selectedPhaseId);
        var task = findTask(phase, selectedTaskId);
        if (task != null) {
            $("#signOffComments").val(task.SignOff[0].Comments);
        }
        if (phase.State != phaseStates.Complete) {
            if (task.SignOff[0].IsSignedOff) {
                $("#signOffPopupSaveBtn").text("Undo SignOff");
            }
            else {
                $("#signOffPopupSaveBtn").text("SignOff");
            }

            linksList.splice(0, linksList.length);
            $.each(task.Links, function (index, link) {
                linksList.push(link);
            });

            $("#taskSignOffPopup").data("kendoWindow").center().open();
        }
        return false;
    }
    function closeSignOffPopup() {
        $("#taskSignOffPopup").data("kendoWindow").close();
    }
    function signOffTask(isSignedOff, isNa) {
        var phase = findPhase(selectedPhaseId);
        var task = findTask(phase, selectedTaskId);

        if ($("#signOffPopupSaveBtn").text() == "Undo SignOff") {
            task.SignOff[0].User = "";
            task.SignOff[0].Date = "";
            task.SignOff[0].Comments = "";
            task.SignOff[0].IsSignedOff = false;
        }
        else {

            task.SignOff[0].Comments = $("#signOffComments").val();
            if (isSignedOff || isNa) {
                task.SignOff[0].User = "bmarti2";
                task.SignOff[0].Date = "07/09/2013";
                task.SignOff[0].IsSignedOff = true;
                if (isNa) {
                    task.SignOff[0].IsNa = true;
                }
            }
        }
        var unsignedTasks = $.grep(phase.Tasks, function (task) { return ((!task.SignOff[0].IsSignedOff) && (task.Status != EntityStatus.Deleted)); });
        if (unsignedTasks.length == 0) {
            showHideEditSection(false);

            phase.State = phaseStates.Complete;
            currentPhaseIndex++;
            var nextPhase = viewModel.data.Phases[currentPhaseIndex];
            if (nextPhase != null) {
                if (nextPhase.SequenceNumber == 2) {
                    $("#approvePhaseBtn").show();
                }
                phase.SignOffState = phaseStates.PendingInitiate;
                saveSignOff(phase);
            }
            setPhaseState(phase);
            saveCheckList(true, true);
        }
        else {
            saveCheckList(false, false);
        }
        $("#" + selectedGridId).data("kendoGrid").dataSource.filter({ field: "Status", operator: "neq", value: "Deleted" });
        $("#" + selectedGridId).data("kendoGrid").refresh();

        closeSignOffPopup();
    }
    function addLinkAttachment() {
        $("#linksSection").show();
        linksList.push({ Title: $("#linkAttachmentTitle").val(), Link: $("#linkAttachmentUrl").val() });
    }
    function deleteLinkItem(linkTitle) {
        var phase = findPhase(selectedPhaseId);
        var task = findTask(phase, selectedTaskId);

        $.each(linksList, function (index, link) {
            if (link.Title == linkTitle) {
                linksList.splice(index, 1);
            }
        });
        if (linksList.length == 0) {
            $("#linksSection").hide();
        }
        return (false);
    }

    /* global event handlers */
    function onRowSelect(selectType, phaseId, taskId, headerRowId, gridId) {
        if (selectType == "Phase") {
            $("#" + selectedHeaderRowId).removeClass("k-state-selected");
            $("#" + headerRowId).addClass("k-state-selected");
            $("#" + gridId).data("kendoGrid").clearSelection();

            enablePhaseToolBar(phaseId);
        }
        else {
            $("#" + headerRowId).removeClass("k-state-selected");
            $("#" + selectedHeaderRowId).removeClass("k-state-selected");

            enableTaskToolBar(phaseId, taskId);
        }
        selectedItemType = selectType;
        selectedPhaseId = phaseId;
        selectedTaskId = taskId;
        selectedHeaderRowId = headerRowId;
        selectedGridId = gridId;
    }
    function editCheckList(showFlag) {
        showHideEditSection(showFlag);
        genericMapping[currentPhaseIndex].Lock = currentGeneric;
        localStorage.setItem(getGenericMappingKey(), JSON.stringify(genericMapping));
        $("#alertMsg").text("please click on save to commit the changes...");
        return (false);
    }
    function saveCheckList(releaseLock, performEdit) {
        var serializedInfo = JSON.stringify(viewModel.data);
        var key = getGenericMappingKey();

        $.each(genericMapping, function (i, mapItem) {
            if (releaseLock && mapItem.Id == key) {
                mapItem.Lock = "";
            }
            $.each(mapItem.Generics, function (j, generic) {
                localStorage.setItem(generic, serializedInfo);
            });
        });
        localStorage.setItem(key, JSON.stringify(genericMapping));
        if (performEdit) {
            showHideEditSection(false);
            enableDisableControls();
        }
    }
    function showHideEditSection(showFlag) {
        currentPhaseLocked = (!showFlag);
        viewModel.updateTask(showFlag);
        var phase = viewModel.data.Phases[currentPhaseIndex];
        if (phase != null) {
            if (showFlag) {
                $("#psd" + phase.SequenceNumber + "AddTaskLink").show();
                $("#psd" + phase.SequenceNumber + "EditPhaseLink").show();
                $("#psd" + phase.SequenceNumber + "LockLink").hide();
            }
            else {
                $("#psd" + phase.SequenceNumber + "AddTaskLink").hide();
                $("#psd" + phase.SequenceNumber + "EditPhaseLink").hide();
                $("#psd" + phase.SequenceNumber + "LockLink").hide();
            }
            if ($("#psd" + phase.SequenceNumber + "Grid").data("kendoGrid") != null) {
                $("#psd" + phase.SequenceNumber + "Grid").data("kendoGrid").refresh();
            }
        }
    }
    function resetCheckList() {
        var serializedInfo = JSON.stringify(templatePhaseList);
        localStorage.setItem(currentGeneric, serializedInfo);
        localStorage.setItem(getGenericMappingKey(), JSON.stringify(genericMappingList));
        window.location = window.location.search;
    }
    function initiateTapeout() {
        var newPhase = psd25Phase;
        var currentPhase = viewModel.data.Phases[currentPhaseIndex];
        newPhase.SequenceNumber = (tapeoutSequenceNumber + 3);
        newPhase.State = phaseStates.InProgress;
        newPhase.Id = newPhaseId++;
        newPhase.Title = "PSD 2." + tapeoutSequenceNumber + ": Approval for Iterations";
        var gridId = "psd" + newPhase.SequenceNumber + "Grid";
        var newPhasePositionIndex = (tapeoutSequenceNumber + 1);
        viewModel.data.Phases.splice(newPhasePositionIndex, 0, newPhase);
        gridIds.splice(newPhasePositionIndex, 0, gridId);
        genericMapping.splice(newPhasePositionIndex, 0, { Id: 1, Generics: [], Lock: "" });
        renderTaskGrid(gridId, newPhase.Id, viewModel.data.Phases[currentPhaseIndex].Tasks);
        tapeoutSequenceNumber++;

        insertTapeoutSignOff(newPhasePositionIndex, currentPhase, newPhase);
        var nextPhase = viewModel.data.Phases[currentPhaseIndex + 1];
        if (nextPhase != null) {
            nextPhase.State = phaseStates.Initial;
            setPhaseState(nextPhase);
        }
        saveCheckList(true, true);
    }
    function moveUp() { }
    function moveDown() { }
    function navigateToGeneric() {
        window.location = "GenericMgmt.html?generic=" + currentGeneric;
    }
    function navigateToSignOff() {
        window.location = "SignOffMgmt.html?generic=" + currentGeneric;
    }
    function manageGenericMapping() {
        alert("work in progress");
    }

    /* utils */
    function getGenericMappingKey() {
        return (currentPhaseIndex);
    }
    function getQueryParamValue(param) {
        hu = window.location.search.substring(1);
        gy = hu.split("&");
        for (i = 0; i < gy.length; i++) {
            ft = gy[i].split("=");
            if (ft[0] == param) {
                return ft[1];
            }
        }
    }
    function findPhase(id) {
        var phaseInstance = null;
        $.each(viewModel.data.Phases, function (index, phase) {
            if (phase.Id == id) {
                phaseInstance = phase;
            }
        });
        return (phaseInstance);
    }
    function findTaskByTitle(phase, title) {
        var taskInstance = null;
        $.each(phase.Tasks, function (index, task) {
            if (task.Title == title) {
                taskInstance = task;
            }
        });
        return (taskInstance);
    }
    function findTask(phase, taskId) {
        var taskInstance = null;
        $.each(phase.Tasks, function (index, task) {
            if (task.Id == taskId) {
                taskInstance = task;
            }
        });
        return (taskInstance);
    }
    function setPhaseState(phase) {
        var state = (phase.IsNa) ? "icon_16 na" :
                        (phase.State == "PendingInitiate") ? "icon_16 warning" :
                        (phase.State == "InProgress") ? "icon_16 inprocess" :
                        (phase.State == "Complete") ? "icon_16 signedoff" : "";
        $("#psd" + phase.SequenceNumber + "PhaseStatusLink").attr("class", "icon_16 " + state);
        $("#psd" + phase.SequenceNumber + "PhaseStatusLink").attr("title", phase.State);
        $("#psd" + phase.SequenceNumber + "Footer").hide();
        if (phase.State == phaseStates.InProgress) {
            //$("#psd" + phase.SequenceNumber + "ResetPhaseLink").show();
        }
        else {
            //$("#psd" + phase.SequenceNumber + "ResetPhaseLink").hide();
        }
        if (phase.State == phaseStates.Complete) {
            $("#psd" + phase.SequenceNumber + "Footer").show();
            $("#psd" + phase.SequenceNumber + "SignOffLink").text("SignOff [Status - PendingInitiate]");
            $("#psd" + phase.SequenceNumber + "SignOffStatus").attr("class", "icon_16 warning");
        }
    }
});

