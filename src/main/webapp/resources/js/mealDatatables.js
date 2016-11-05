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
                        return '<span>' + data.substring(0, 10) + '</span>';
                    }
                    return data;
                }
            },
            {
                "data": "description",
                "render": function (data, type, row) {
                    return data;
                }
            },
            {
                "data": "calories",
                "render": function (data, type, row) {
                    return data;
                }
            },
            {
                "data": "exceed",
/*                "columnDefs": function (data, type, row){
                    if(type = "display"){
                        var tmp = data == true ? '.exceeded' : '.normal';
                        row.id.addClass('.exceeded');
                }*/
/*                "render": function (data, type, row){
                    if(type = "display"){
                        var tmp = data == true ? '.exceeded' : '.normal';
                        //row.id.addClass('.exceeded');
                        return tmp;
                    }
                    return "example";
                }*/
            }
/*            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }*/
            /*/!*            {
             "data": "enabled",
             "render": function (data, type, row) {
             if (type == 'display') {
             return '<input type="checkbox" ' + (data ? 'checked' : '') + ' onclick="enable($(this),' + row.id + ');"/>';
             }
             return data;
             }
             },*!/
             {
             "data": "registered",
             "render": function (date, type, row) {
             if (type == 'display') {
             return '<span>' + date.substring(0, 10) + '</span>';
             }
             return date;
             }
             },*/
            /*            {
             "orderable": false,
             "defaultContent": "",
             "render": renderEditBtn
             },*/
            /*            {
             "orderable": false,
             "defaultContent": "",
             "render": renderDeleteBtn
             }*/
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ],
        /*        "createdRow": function (row, data, dataIndex) {
         if (!data.enabled) {
         $(row).css("opacity", 0.3);
         }
         },*/
        "initComplete": makeEditable
    });
});
