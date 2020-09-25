/**
 * js表格操作 wgp
 */
var currRow = "0";
var currCell = "0";
function getSum(obj, objName) {
	var sum = 0;
	for (var i = 1; i < obj.rows.length - 1; i++) {
		for (var j = 1; j < obj.rows[i].cells.length; j++) {
			sum += parseInt(obj.rows[i].cells[j].innerText);
		}
	}
	document.getElementsByName(objName)[0].value = sum;
}

function ascCell(obj) {
	if (currCell >= 1 && currRow >= 1) {
		var len = obj.rows[0].cells.length;
		for (var j = 0; j < len; j++) {
			var order = 0;
			for (var i = 1; i < len - 2; i++) {
				if (parseInt(obj.rows[currRow].cells[i].innerText) > parseInt(obj.rows[currRow].cells[i
						+ 1].innerText)) {
					for (var k = 0; k < obj.rows.length; k++) {
						var tmp = obj.rows[k].cells[i].innerHTML;
						obj.rows[k].cells[i].innerHTML = obj.rows[k].cells[i
								+ 1].innerHTML;
						obj.rows[k].cells[i + 1].innerHTML = tmp;
					}
					order++;
				}
			}
			len--;
			if (order == 0) {
				break;
			}
		}
	}
	clear();
}

function descCell(obj) {
	if (currCell >= 1 && currRow >= 1) {
		var len = obj.rows[0].cells.length;
		for (var j = 0; j < len; j++) {
			var order = 0;
			for (var i = 1; i < len - 2; i++) {
				if (parseInt(obj.rows[currRow].cells[i].innerText) < parseInt(obj.rows[currRow].cells[i
						+ 1].innerText)) {
					for (var k = 0; k < obj.rows.length; k++) {
						var tmp = obj.rows[k].cells[i].innerHTML;
						obj.rows[k].cells[i].innerHTML = obj.rows[k].cells[i
								+ 1].innerHTML;
						obj.rows[k].cells[i + 1].innerHTML = tmp;
					}
					order++;
				}
			}
			len--;
			if (order == 0) {
				break;
			}
		}
	}
	clear();
}

function ascRow(obj) {
	if (currCell >= 1) {
		var rows = obj.rows;
		var len = rows.length - 2;
		for (var j = 0; j <= rows.length - 2; j++) {
			var order = 0;
			for (var i = 1; i < len; i++) {
				if (parseInt(rows[i].cells[currCell].innerText) > parseInt(rows[i
						+ 1].cells[currCell].innerText)) {
					obj.moveRow(i, i + 1);
					order++;
				}
			}
			len--;
			if (order == 0) {
				break;
			}
		}
	}
	clear();
}

function descRow(obj) {
	if (currCell >= 1) {
		var rows = obj.rows;
		var len = rows.length - 2;
		for (var j = 0; j < rows.length - 2; j++) {
			var order = 0;
			for (var i = 1; i < len; i++) {
				if (parseInt(rows[i].cells[currCell].innerText) < parseInt(rows[i
						+ 1].cells[currCell].innerText)) {
					obj.moveRow(i, i + 1);
					order++;
				}
			}
			len--;
			if (order == 0) {
				break;
			}
		}
	}
	clear();
}

var cur_row = null;
var cur_cell = null;
function deleteRow(obj, currow) {
	cur_row = currow;
	if (obj.rows.length == 1)
		return;
	var the_row;
	the_row = (cur_row == null || cur_row == 0) ? -1 : cur_row;
	// alert(the_row);
	try {
		obj.deleteRow(the_row);
	} catch (e) {
		obj.deleteRow(-1);
	}
	cur_row = null;
	cur_cell = null;
	// 清除文本框的值
	$VD("tr_obj").value = "";
	return true;
}

function del_row(the_table) {
	if (the_table.rows.length == 1)
		return;
	var the_row;
	the_row = (cur_row == null || cur_row == 0) ? -1 : cur_row;
	the_table.deleteRow(the_row);
	cur_row = null;
	cur_cell = null;
}
function deleteCell(obj) {
	// if(currCell>=1&&currCell<obj.rows[0].cells.length-1){
	for (var i = 0; i < obj.rows.length; i++) {
		obj.rows[i].deleteCell(currCell);
		// }
	}
	clear();
	averCol(obj);
}

function averCol(obj) {
	var wh = obj.width;
	var cells = obj.rows[0].cells;
	for (var i = 0; i < cells.length; i++) {
		cells[i].width = Math.ceil(wh / cells.length);
	}
}

/**
 * 动态插入一行
 * 
 * @param {}
 *            obj
 * @param {}
 *            k
 * @param {}
 *            vl
 * @return {Boolean}
 */
function insertRows(obj, k) {
	var rowsnumber = obj.rows.length;
	var cellsnumber = obj.rows[0].cells.length;
	var now_num = 0;
	if (rowsnumber <= 1) {
		rowsnumber = 1;
	} else {
		now_num = obj.rows[rowsnumber - 1].cells[0].innerText;
	}
	var count = parseInt(now_num) + 1;
	var row = obj.insertRow(rowsnumber);
	row.align = 'left';
	var idstr="";
	for (var i = 0; i < cellsnumber; i++) {
		row.id = k;
		cell = row.insertCell(i);
		cell.align='center';
		switch(i)
		{
		case 0:
			idstr = "skuName"+rowsnumber;
			placeholder="商品套餐（SKU）标题";
		  break;
		case 1:
			idstr = "skuCost"+rowsnumber;
			placeholder="整数或者小数";
		  break;
		case 2:
			idstr = "skuPrice"+rowsnumber;
			placeholder="整数或者小数";
		  break;
		case 3:
			idstr = "levelOnePmoney"+rowsnumber;
			placeholder="整数或者小数";
		  break;
		case 4:
			idstr = "levelTwoPmoney"+rowsnumber;
			placeholder="整数或者小数";
		  break;
		case 5:
			idstr = "levelThreePmoney"+rowsnumber;
			placeholder="整数或者小数";
		  break;
		case 6:
			idstr = "servicePmoney"+rowsnumber;
			placeholder="整数或者小数";
		  break;
		case 7:
			idstr = "freeMoney"+rowsnumber;
			placeholder="整数或者小数";
		  break;
		default:
		  
		}
		className="form-control"
		cell.innerHTML = '<input id="'+idstr+'" name="'+idstr+'" class="'+className+'" placeholder="'+placeholder+'" type="text" value="" />';
	}

	return true;
}

/**
 * 动态插入一行
 * 
 * @param {}
 *            obj
 * @param {}
 *            k
 * @param {}
 *            vl
 * @return {Boolean}
 */
function insertRows2(obj, k) {
	var rowsnumber = obj.rows.length;
	var cellsnumber = obj.rows[0].cells.length;
	var now_num = 0;
	if (rowsnumber <= 1) {
		rowsnumber = 1;
	} else {
		now_num = obj.rows[rowsnumber - 1].cells[0].innerText;
	}
	var count = parseInt(now_num) + 1;
	var row = obj.insertRow(rowsnumber);
	row.align = 'left';
	var idstr="";
	var className="form-control";
	for (var i = 0; i < cellsnumber; i++) {
		row.id = k;
		cell = row.insertCell(i);
		cell.align='left';
		switch(i)
		{
		case 0:
			cell.width=100;
			idstr = "attrName"+rowsnumber;
			placeholder="尺码、颜色";
		  break;
		case 1:
			idstr = "attrValue"+rowsnumber;
			placeholder="多个值请用竖线间隔‘|’ XL|XXL";
		  break;
		default:
			
		}
		cell.innerHTML = '<input id="'+idstr+'" name="'+idstr+'" class="'+className+'" placeholder="'+placeholder+'" type="text" value="" />';
	}

	return true;
}


/**
 * 动态插入一行
 * 
 * @param {}
 *            obj
 * @param {}
 *            k
 * @param {}
 *            vl
 * @return {Boolean}
 */
function insertRows3(obj, k) {
	var rowsnumber = obj.rows.length;
	var cellsnumber = obj.rows[0].cells.length;
	var now_num = 0;
	if (rowsnumber <= 1) {
		rowsnumber = 1;
	} else {
		now_num = obj.rows[rowsnumber - 1].cells[0].innerText;
	}
	var count = parseInt(now_num) + 1;
	var row = obj.insertRow(rowsnumber);
	row.align = 'left';
	var idstr="";
	var className="form-control";
	for (var i = 0; i < cellsnumber; i++) {
		row.id = k;
		cell = row.insertCell(i);
		cell.align='left';
		switch(i)
		{
		case 0:
			cell.width=70;
			cell.align='center';
			cell.innerHTML = '标题 '+rowsnumber+'：';
		  break;
		case 1:
			idstr = "avTitle_"+rowsnumber;
			placeholder="请输入广告标题";
			cell.innerHTML = '<input id="'+idstr+'" name="'+idstr+'" class="'+className+'" placeholder="'+placeholder+'" type="text" value="" />';
		  break;
		default:
			
		}
	}
	return true;
}


/**
 * 动态插入一行
 * 
 * @param {}
 *            obj
 * @param {}
 *            k
 * @param {}
 *            vl
 * @return {Boolean}
 */
function insertRows4(obj, k) {
	var rowsnumber = obj.rows.length;
	var cellsnumber = obj.rows[0].cells.length;
	var now_num = 0;
	if (rowsnumber <= 1) {
		rowsnumber = 1;
	} else {
		now_num = obj.rows[rowsnumber - 1].cells[0].innerText;
	}
	var count = parseInt(now_num) + 1;
	var row = obj.insertRow(rowsnumber);
	row.align = 'left';
	var idstr="";
	var className="form-control";
	for (var i = 0; i < cellsnumber; i++) {
		row.id = k;
		cell = row.insertCell(i);
		cell.align='left';
		switch(i)
		{
		case 0:
			cell.width=70;
			cell.align='center';
			cell.innerHTML = '图片 '+rowsnumber+'：';
		  break;
		case 1:
			cell.width=120;
			cell.align='center';
			cell.innerHTML = '<img class="avImgPre" id="spic_'+rowsnumber+'" name="spic" src="'+ccnetpath+'/static/img/no_pic.png" >';
		  break;
		case 2:
			idstr = "avPic_"+rowsnumber;
			placeholder="请输入广告图片地址，建议将图片存放到外网云服务器上面，如阿里云CDN、新浪云等等";
			cell.innerHTML = '<input id="'+idstr+'" name="'+idstr+'" class="'+className+'" onchange="setPicPreview(this,\'spic_'+rowsnumber+'\',120,80);" placeholder="'+placeholder+'" type="text" value="" />';
		  break;
		default:
			
		}
	}
	return true;
}

/**
 * 动态插入一行
 * 
 * @param {}
 *            obj
 * @param {}
 *            k
 * @param {}
 *            vl
 * @return {Boolean}
 */
function insertRows5(obj, k) {
	var rowsnumber = obj.rows.length;
	var cellsnumber = obj.rows[0].cells.length;
	var now_num = 0;
	if (rowsnumber <= 1) {
		rowsnumber = 1;
	} else {
		now_num = obj.rows[rowsnumber - 1].cells[0].innerText;
	}
	var count = parseInt(now_num) + 1;
	var row = obj.insertRow(rowsnumber);
	row.align = 'left';
	var idstr="";
	var className="form-control";
	for (var i = 0; i < cellsnumber; i++) {
		row.id = k;
		cell = row.insertCell(i);
		cell.align='left';
		switch(i)
		{
		case 0:
			cell.width=70;
			cell.align='center';
			cell.innerHTML = '图片 '+rowsnumber+'：';
		  break;
		case 1:
			cell.width=400;
			cell.align='center';
			cell.innerHTML = '<img class="avGImgPre" id="spic_'+rowsnumber+'" name="spic" src="'+ccnetpath+'/static/img/lno_pic.png" >';
		  break;
		case 2:
			idstr = "avPic_"+rowsnumber;
			placeholder="请输入广告图片地址，建议将图片存放到外网云服务器上面，如阿里云CDN、新浪云等等";
			cell.innerHTML = '<input id="'+idstr+'" name="'+idstr+'" class="'+className+'" onchange="setPicPreview(this,\'spic_'+rowsnumber+'\',400,80);" placeholder="'+placeholder+'" type="text" value="" />';
		  break;
		default:
			
		}
	}
	return true;
}


function getTrObj(obj) {
	var tr_obj = obj.parentNode.parentNode;
	var tr_id = tr_obj.id;
	document.getElementById('tr_obj').value = tr_id;
	// obj.value = "";
}
function insertCell(obj) {
	// if(currCell && currCell!=obj.rows[0].cells.length-1){
	for (var i = 0; i < obj.rows.length; i++) {
		obj.rows[i].insertCell(currCell + 1);
		obj.rows[i].cells[currCell + 1].innerHTML = '<input class="intext" id="keywordName" type="text"size="20" max="50" allownull="false"  onkeydown="verifyLength(this);"/>';
	}
	clear();
	// averCol(obj);
	// chnBgcolor(obj,currRow,"ffffff");
	// }
}
function check(obj) {
	var k = parseInt(obj.innerText);
	if (isNaN(k)) {
		obj.childNodes[0].innerText = 0;
		return 0;
	} else {
		obj.childNodes[0].innerText = k;
		return k;
	}
}
function sum(obj) {
	var o = event.srcElement;
	var total = 0;
	var tindex = obj.rows.length - 1;
	if (o.parentElement.tagName == "TD" && o.parentElement.cellIndex >= 1
			&& o.parentElement.parentElement.rowIndex >= 1) {
		var index = o.parentElement.cellIndex;
		var pindex = o.parentElement.parentElement.rowIndex;
		for (var i = 1; i < tindex; i++) {
			var num = check(obj.rows[i].cells[index]);
			total = parseInt(total) + num;
		}
		var total1 = 0;
		for (i = 1; i < obj.rows[pindex].cells.length - 1; i++) {
			total1 += check(obj.rows[pindex].cells[i]);
		}
		obj.rows[pindex].cells(obj.rows[pindex].cells.length - 1).innerText = total1;
		obj.rows[tindex].cells[index].innerText = total;
		obj.rows[tindex].cells(obj.rows[0].cells.length - 1).innerText = addAll(obj);
	}
	setPos(obj);
}
function chnBgcolor(obj, index, color) {
	for (var i = 0; i < obj.rows.length; i++) {
		if (i == index) {
			obj.rows[i].bgColor = color;
		} else {
			obj.rows[i].bgColor = "ffffff";
		}
	}
}
function setPos(obj) {
	var o = event.srcElement;
	if (o.tagName == "DIV") {
		currRow = o.parentElement.parentElement.rowIndex;
		currCell = o.parentElement.cellIndex;
		chnBgcolor(obj, currRow, "dedede");
	} else if (o.tagName == "TD") {
		currRow = o.parentElement.rowIndex;
		currCell = o.cellIndex;
	} else {
		currRow = obj.rows.length - 1;
		currCeil = obj.rows[0].cells.length - 1;
	}
}
function clear() {
	currRow = 0;
	currCell = 0;
}
function addAll(obj) {
	var total = 0;
	for (var i = 1; i < obj.rows[0].cells.length - 1; i++) {
		total += parseInt(obj.rows[obj.rows.length - 1].cells[i].innerText);
	}
	return total;
}

// hide a rol by id;
function hideCol(obj, col_id) {
	var tb = document.getElementById(obj);
	var trow = tb.rows;
	for (var i = 0; i < trow.length; i++) {
		trow[i].cells[col_id].style.display == '';
	}
}
// show a rol by id;
function showCol(obj, col_id) {
	var tb = document.getElementById(obj);
	var trow = tb.rows;
	for (var i = 0; i < trow.length; i++) {
		trow[i].cells[col_id].style.display == 'block';
	}
}

// /////////////////////////////////////////////
// 功能：合并表格
// 参数：tb－－需要合并的表格ID
// 参数：colLength－－需要对前几列进行合并，比如，
// 想合并前两列，后面的数据列忽略合并，colLength应为2
// 缺省表示对全部列合并
// data: 2012.03.29
// /////////////////////////////////////////////
function uniteTable(tb, objCol) {
	// 检查表格是否规整
	if (!checkTable(tb))
		return;
	var i = 0;
	var j = 0;
	var rowCount = tb.rows.length; // 行数
	var colCount = tb.rows[0].cells.length; // 列数
	var obj1 = null;
	var obj2 = null;
	// 为每个单元格命名
	for (i = 0; i < rowCount; i++) {
		for (j = 0; j < colCount; j++) {
			tb.rows[i].cells[j].id = "tb__" + i.toString() + "_" + j.toString();
		}
	}
	// 逐列检查合并
	for (t = 0; t < objCol.length; t++) {
		i = objCol[t];
		obj1 = document.getElementById("tb__0_" + i.toString())
		for (j = 1; j < rowCount; j++) {
			obj2 = document.getElementById("tb__" + j.toString() + "_"
					+ i.toString());
			if (obj1.innerHTML == obj2.innerHTML)// 修改了原来的innerText
			{
				obj1.rowSpan++;
				obj2.parentNode.removeChild(obj2);
			} else {
				obj1 = document.getElementById("tb__" + j.toString() + "_"
						+ i.toString());
			}
		}
	}
}

// ///////////////////////////////////////
// 功能：检查表格是否规整
// 参数：tb－－需要检查的表格ID
// data: 2016.03.29
// ///////////////////////////////////////
function checkTable(tb) {
	if (tb.rows.length == 0)
		return false;
	if (tb.rows[0].cells.length == 0)
		return false;
	for (var i = 0; i < tb.rows.length; i++) {
		if (tb.rows[0].cells.length != tb.rows[i].cells.length)
			return false;
	}
	return true;
}
