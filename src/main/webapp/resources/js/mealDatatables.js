var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $('#datatable').DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
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
});
