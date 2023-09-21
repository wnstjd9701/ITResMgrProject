var rowCount = 0;

//행추가 버튼
function addItemAddRow() {
	var table = document.getElementById("addItemTable");
	var newRow = table.insertRow();
	newRow.setAttribute("data-row-id", rowCount);

	var newCell0 = newRow.insertCell(0);
	newCell0.innerHTML = "<input type='checkbox' name='checkBox'>";

	var newCell1 = newRow.insertCell(1);
	newCell1.innerHTML = "<input type='text' id='addItemSn" + rowCount + "' size='5'>";

	var newCell2 = newRow.insertCell(2);
	newCell2.innerHTML = "<input type='text' id='addItemName" + rowCount + "' size='15'>";

	var newCell3 = newRow.insertCell(3);
	newCell3.innerHTML = "<input type='text' id='addItemDesc" + rowCount + "' size='40'>";

	var newCell4 = newRow.insertCell(4);
	newCell4.innerHTML =  "<select id='addItemUseYN" + rowCount + "'>" +
        "<option value='Y'>Y</option>" +
        "<option value='N'>N</option>" +
        "</select>";

	rowCount++;
}

//검색
function addItemsearch(){
	var searchAddItemUseYN = document.getElementById('searchUseYN').value;
	var searchAddItemText = document.getElementById('addItemSearchText').value;

	console.log("searchAddItemUseYN", searchAddItemUseYN)
	console.log("searchAddItemText", searchAddItemText)

	var searchAddItemData = {
		searchAddItemUseYN: searchAddItemUseYN,
		searchAddItemText: searchAddItemText
	};
	
	console.log("searchAddItemData", searchAddItemData);
	
	$.ajax({
		url: '/search/addItem?' + $.param(searchAddItemData),
		type: 'GET',
		success: function(response) {
			console.log("response", response)

			$('#addItemTable > tbody').empty();

			if (response.length === 0) {
				// 검색 결과가 없는 경우
				var noResultRow = "<tr><td colspan='5'>검색결과가 없습니다</td></tr>";
				$('#addItemTable > tbody').append(noResultRow);
			} else {
				// 검색 결과가 있는 경우
				for (var i = 0; i < response.length; i++) {
					/*var addTableRow = "<tr>" +
					"<td><input type='checkbox' name='emp_checkbox'></td>" +
					"<td name='employeeId'>" + response[i].employeeId + "</td>" +
					"<td name='employeeName' contenteditable='true' onclick='handleClick(this)'>" + response[i].employeeName + "</td>" +
					"<td name='employeeType' onclick='handleClick(this)'>" + "<span class='text' name='empType'>" + response[i].employeeType + "</span>" +
					"<select name='empTypeList' class='select' style='display: none;'>" +
					"<option value=''>선택</option>" +
					"<option value='EMT002'>IT자원관리자</option>" +
					"<option value='EMT001'>시스템관리자</option>" +
					"</select>" + "</td>" +
					"<td name='employeeStatus' onclick='handleClick(this)'>" + "<span class='text' name='empStatus'>" + response[i].employeeStatus + "</span>" +
					"<select name='empStatusList' class='select' style='display: none;'>" +
					"<option value=''>선택</option>" +
					"<option value='EMS001'>재직중</option>" +
					"<option value='EMS002'>휴직중</option>" +
					"<option value='EMS003'>퇴직</option>" +
					"</select>" + "</td>" +
					"<td><input type='hidden' name='hiddenBox'></td>" + "</tr>";*/
					var addTableRow = "<tr>" +
					"<td><input type='checkbox' name='checkBox'></td>" +
					"<td name='addItemSn'>" + response[i].addItemSn  + "</td>" +
					"<td name='addItemName'>" + response[i].addItemName + "</td>" +
					"<td name='addItemDesc'>" + response[i].addItemDesc + "</td>" +
					"<td name='useYN'>" + response[i].useYN + "</td>" +
					+ "</tr>";


					$('#addItemTable > tbody').append(addTableRow);
				}
			}
			return;
		}
	});
}