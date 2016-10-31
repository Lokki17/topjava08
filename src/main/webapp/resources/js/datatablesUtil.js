function makeEditable() {
/*    $('.delete').click(function () {
        deleteRow($(this).attr("id"));
    });*/

    $('#detailsForm').submit(function () {
        save();
        return false;
    });

    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(event, jqXHR, options, jsExc);
    });
}

function add() {
    $('#id').val(null);
    $('#editRow').modal();
}

function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: 'DELETE',
        success: function () {
            updateThisTable();
            successNoty('Deleted');
        }
    });
}

/*function updateTable() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "between",
        data: $("#filter").serialize(),
        success: function (data) {
            updateTableWithData(data);
        }
    });
    return false;
}*/

function updateTableWithData(data) {
    datatableApi.clear();
    datatableApi.rows.add(data).draw();
}

function save() {
    var form = $('#detailsForm');
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $('#editRow').modal('hide');
            updateThisTable();
            successNoty('Saved');
        }
    });
}

function enableUser(check, id){
    var checked = check.is(":checked");
    $.ajax({
        type: "POST",
        url: ajaxUrl + "enable/" + id + "/" + checked,
        success: function(){
            successNoty(checked == true ? "Enables" : "Disabled");
        }
    })
}

var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    noty({
        text: text,
        type: 'success',
        layout: 'bottomRight',
        timeout: true
    });
}

function failNoty(event, jqXHR, options, jsExc) {
    closeNoty();
    failedNote = noty({
        text: 'Failed: ' + jqXHR.statusText + "<br>",
        type: 'error',
        layout: 'bottomRight'
    });
}
