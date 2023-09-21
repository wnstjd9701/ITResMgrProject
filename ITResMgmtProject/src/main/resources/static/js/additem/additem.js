var rowCount = 0;

//행추가 버튼
function addItemAddRow() {
	var table = document.getElementById("addItemTable");
	var newRow = table.insertRow();
	newRow.setAttribute("data-row-id", rowCount);

	var newCell0 = newRow.insertCell(0);
	newCell0.innerHTML = "<input type='checkbox' name='checkBox'>";

	var newCell1 = newRow.insertCell(1);
	/*	newCell1.innerHTML = "<input type='text' id='addItemSn" + rowCount + "' size='5'>";*/

	var newCell2 = newRow.insertCell(2);
	newCell2.innerHTML = "<input type='text' id='insertName" + rowCount + "' size='15'>";

	var newCell3 = newRow.insertCell(3);
	newCell3.innerHTML = "<input type='text' id='addItemDesc" + rowCount + "' size='40'>";

	var newCell4 = newRow.insertCell(4);
	newCell4.innerHTML = "<select id='addItemUseYN" + rowCount + "'>" +
		"<option value='Y'>Y</option>" +
		"<option value='N'>N</option>" +
		"</select>";

	rowCount++;
}

//검색
function addItemsearch() {
	var searchAddItemUseYN = document.querySelector('input[name="searchUseYN"]:checked').value;
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
					var addTableRow = "<tr>" +
						"<td><input type='checkbox' name='checkBox'></td>" +
						"<td name='addItemSn'>" + response[i].addItemSn + "</td>" +
						"<td name='addItemName'>" + response[i].addItemName + "</td>" +
						"<td name='addItemDesc'>" + response[i].addItemDesc + "</td>" +
						"<td name='useYN'>" + response[i].useYN + "</td>" +
						"<td><input type='hidden' name='hiddenBox'></td>" +
						+ "</tr>";


					$('#addItemTable > tbody').append(addTableRow);
				}
			}
			return;
		}
	});
}

//행삭제 버튼 (행숨김)
function addItemHideRow() {
	const selectedCheckboxes = document.querySelectorAll('input[name="checkBox"]:checked');
	const hiddenBox = document.querySelector("input[name='hiddenBox']");

	selectedCheckboxes.forEach(function(checkbox) {
		var row = checkbox.closest('tr');
		row.style.display = 'none';
		row.querySelector("input[name='hiddenBox']").value = "d";
	});

	console.log("hiddenBox.value", hiddenBox.value)
}



//저장버튼
function addItemSaveAll() {
	//Insert
	if (rowCount > 0) {
		var insertAddItem = new Array();
		for (var i = 0; i < rowCount; i++) {
			var addItemName = document.getElementById('insertName' + i).value;
			console.log("addItemName", addItemName);
			var addItemDesc = document.getElementById('addItemDesc' + i).value;
			var addItemUseYN = document.getElementById('addItemUseYN' + i).value;

			if (addItemName && addItemUseYN) {
				var addItemValue = {
					addItemName: addItemName,
					addItemDesc: addItemDesc,
					useYN: addItemUseYN
				};
				insertAddItem.push(addItemValue);
			}
		}
		console.log("insertAddItem", insertAddItem);
	}
	
	//Delete
	var deletedAddItems = [];

	var hiddenFields = document.getElementsByName("hiddenBox");

	for (var i = 0; i < hiddenFields.length; i++) {
		var hiddenValue = hiddenFields[i].value;
		if (hiddenValue === "d") {
			// 해당 숨겨진 필드의 부모 행을 찾아서 employeeId 값을 가져옵니다.
			var row = hiddenFields[i].closest("tr");
			var addItemSn = row.querySelector('[name="addItemSn"]').textContent;
			console.log("addItemSn : ", addItemSn);
			deletedAddItems.push(addItemSn);
		}
	}
	
	var requestData = {
		insertAddItem: insertAddItem,
		deletedAddItems: deletedAddItems
	};
	
	
		$.ajax({
		url: '/save/addItem',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(requestData),
		success: function(response) {
			rowCount=0;
			console.log("response", response)

			$('#addItemTable > tbody').empty();

			if (response.length === 0) {
				$('#addItemTable > tbody').append(noResultRow);
			} else {
				// 검색 결과가 있는 경우
				for (var i = 0; i < response.length; i++) {
					var addTableRow = "<tr>" +
						"<td><input type='checkbox' name='checkBox'></td>" +
						"<td name='addItemSn'>" + response[i].addItemSn + "</td>" +
						"<td name='addItemName'>" + response[i].addItemName + "</td>" +
						"<td name='addItemDesc'>" + response[i].addItemDesc + "</td>" +
						"<td name='useYN'>" + response[i].useYN + "</td>" +
						"<td><input type='hidden' name='hiddenBox'></td>" +
						"</tr>";


					$('#addItemTable > tbody').append(addTableRow);
				}
			}
			return;
		}
	});
	

}