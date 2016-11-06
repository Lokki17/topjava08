var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        "url": ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: updateTableByData
    });
    return false;
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl + 'filter',
            type: "POST",
            "dataSrc": "",
            data: $('#filter').serialize()
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (data, type, row) {
                    if (type == 'display') {
                        return '<span>' + data.replace("T", " ").substring(0, 16) + '</span>';
                    }
                    return data;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],

        "createdRow": function (row, data, dataIndex) {
            $(row).addClass(data.exceed ? 'exceeded' : 'normal');
        },
        "initComplete": makeEditable
    });

    $('.date-field').datetimepicker({
        datepicker: true,
        timepicker: false,
        format:'Y-m-d',
        lang:'ru'
    });

    $('.time-field').datetimepicker({
        datepicker: false,
        timepicker: true,
        format: 'H:i',
        lang: 'ru',
        step: 30
    });
    $('.date-time-field').datetimepicker({
        datepicker: true,
        timepicker: true,
        format: "Y-m-d H:i",
        lang: 'ru',
        step: 30
    });
});
