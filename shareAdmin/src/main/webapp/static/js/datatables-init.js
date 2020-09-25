var InitiateExpandableDataTable = function () {
    return {
        init: function () {
            function fnFormatDetails(oTable, nTr) {
                var aData = oTable.fnGetData(nTr);
                var sOut = '<table>';
                sOut += '<tr><td rowspan="5" style="padding:0 10px 0 0;"><img src="assets/img/avatars/' + aData[6] + '"/></td><td>Name:</td><td>' + aData[1] + '</td></tr>';
                sOut += '<tr><td>Family:</td><td>' + aData[2] + '</td></tr>';
                sOut += '<tr><td>Age:</td><td>' + aData[3] + '</td></tr>';
                sOut += '<tr><td>Positon:</td><td>' + aData[4] + '</td></tr>';
                sOut += '<tr><td>Others:</td><td><a href="">Personal WebSite</a></td></tr>';
                sOut += '</table>';
                return sOut;
            }
            
            $('body').on('click', '.dropdown-menu.hold-on-click', function (e) {
                e.stopPropagation();
            })
        }
    };
}();

