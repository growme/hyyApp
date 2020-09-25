function DataListGetter(url, serverObjName, indexField, pageSize, fillLatestFunc, fillHistoryFunc) {
    this.pullUrl = url;
    this.serverObjName = "";
    if (serverObjName != null && serverObjName != "") this.serverObjName = serverObjName + ".";
    this.indexField = indexField;
    this.pageSize = pageSize;
    this.startIndex = 0;
    this.latestIndex = 0;
    this.fillLatestFunc = fillLatestFunc;
    this.fillHistoryFunc = fillHistoryFunc;
}

DataListGetter.prototype.pullLatest = function () {
    var appendParaStr = "<form>";
    appendParaStr += "<input name=\"" + this.serverObjName + "queryType\" value=\"latest\" />";
    appendParaStr += "<input name=\"" + this.serverObjName + "startIndex\" value=\"" + this.startIndex + "\" />";
    appendParaStr += "<input name=\"" + this.serverObjName + "latestIndex\" value=\"" + this.latestIndex + "\" />";
    appendParaStr += "<input name=\"" + this.serverObjName + "pageSize\" value=\"" + this.pageSize + "\" />";
    appendParaStr += "</form>";

    var formObj = $(appendParaStr);

    var appendPara = formObj.serialize();

    $.post(this.pullUrl, appendPara, function (dlg) {
        return function (data) {
            if (data != null && data.length > 0) {
                dlg.latestIndex = eval("data[data.length - 1]." + dlg.indexField);
                if (dlg.startIndex == 0) dlg.startIndex = eval("data[0]." + dlg.indexField);
            }
            dlg.fillLatestFunc(data);
        }
    }(this), "json");
};
DataListGetter.prototype.pullHistory = function () {
    var appendParaStr = "<form>";
    appendParaStr += "<input name=\"" + this.serverObjName + "queryType\" value=\"latest\" />";
    appendParaStr += "<input name=\"" + this.serverObjName + "startIndex\" value=\"" + this.startIndex + "\" />";
    appendParaStr += "<input name=\"" + this.serverObjName + "latestIndex\" value=\"" + this.latestIndex + "\" />";
    appendParaStr += "<input name=\"" + this.serverObjName + "pageSize\" value=\"" + this.pageSize + "\" />";
    appendParaStr += "</form>";

    var formObj = $(appendParaStr);

    var appendPara = formObj.serialize();

    $.post(this.pullUrl, appendPara, function (dlg) {
        return function (data) {
            if (data != null && data.length > 0) {
                dlg.startIndex = eval("data[data.length - 1]." + dlg.indexField);
                if (dlg.latestIndex == 0) dlg.latestIndex = eval("data[0]." + dlg.indexField);
            }
            dlg.fillHistoryFunc(data);
        }
    }(this), "json");
};