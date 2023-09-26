var rowCount = 0;

//행추가 버튼
function addItemAddRow() {
	var table = document.getElementById("addItemTable");
	var newRow = table.insertRow();
	newRow.setAttribute("data-row-id", rowCount);

	var newCell0 = newRow.insertCell(0);
	newCell0.innerHTML = "<input type='checkbox' name='insertCheckBox'>";
	
	var newCell1 = newRow.insertCell(1);
	newCell1.innerHTML = "<span name='status'>I</span>";

	var newCell2 = newRow.insertCell(2);
	/*	newCell1.innerHTML = "<input type='text' id='addItemSn" + rowCount + "' size='5'>";*/

	var newCell3 = newRow.insertCell(3);
	newCell3.innerHTML = "<input type='text' id='insertName" + rowCount + "' size='15'>";

	var newCell4 = newRow.insertCell(4);
	newCell4.innerHTML = "<input type='text' id='addItemDesc" + rowCount + "' size='40'>";

	var newCell5 = newRow.insertCell(5);
/*	newCell5.innerHTML = "<select id='addItemUseYN" + rowCount + "'>" +
		"<option value='Y'>Y</option>" +
		"<option value='N'>N</option>" +
		"</select>";*/
		
	newCell5.innerHTML = "<span>Y</span>";

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
		url: '/search/additem?' + $.param(searchAddItemData),
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
						"<td><span name='status'>E</span></td>" +
						"<td name='addItemSn'>" + response[i].addItemSn + "</td>" +
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

//행삭제 버튼 (행숨김)
function addItemHideRow() {
	const selectedCheckboxes = document.querySelectorAll('input[name="checkBox"]:checked');
	//const hiddenBox = document.querySelector("span[name='status']");

	selectedCheckboxes.forEach(function(checkbox) {
		var row = checkbox.closest('tr');
		//row.style.display = 'none';
		row.querySelector("span[name='status']").textContent = "D";
	});
	
	//행추가 후 행삭제
	const insertCheckboxes = document.querySelectorAll('input[name="insertCheckBox"]:checked');
	insertCheckboxes.forEach(function(checkbox) {
		const row = checkbox.closest('tr');
		row.remove();
		rowCount--;
	});

}

//부가항목설명 클릭 시 수정가능
function handleClick(element) {
	// 부가항목설명 클릭 시 상태 U로 변경
	const addItemStatus = element.closest('tr').querySelector("span[name='status']");
	addItemStatus.textContent = 'U';

	//입력필드 생성
	const inputField = document.createElement('input');
	inputField.type = 'text';
	inputField.value = element.textContent;
	inputField.classList.add('edit-field');

	//스팬을 입력 필드로 대체
	element.textContent = '';
	element.appendChild(inputField);
	
	inputField.focus();

	//입력 필드가 포커스를 잃을 때
	inputField.addEventListener('blur', function() {
		//입력 필드에서 새로운 값 가져오기
		const newValue = inputField.value;
		element.textContent = newValue;
	});
}


//저장버튼
function addItemSaveAll() {
	//Insert
	if (rowCount > 0) {
		var insertAddItems = new Array();
		for (var i = 0; i < rowCount; i++) {
			var addItemName = document.getElementById('insertName' + i).value;
			console.log("addItemName", addItemName);
			var addItemDesc = document.getElementById('addItemDesc' + i).value;
			//var addItemUseYN = document.getElementById('addItemUseYN' + i).value;

			if (addItemName) {
				var addItemValue = {
					addItemName: addItemName,
					addItemDesc: addItemDesc
					//useYN: addItemUseYN
				};
				insertAddItems.push(addItemValue);
			}
		}
		console.log("insertAddItem", insertAddItems);
	}
	
	//Delete
	var deletedAddItems = [];

	var spanFields = document.getElementsByName("status");

	for (var i = 0; i < spanFields.length; i++) {
		var spanValue = spanFields[i].textContent;
		if (spanValue === "D") {
			// 해당 숨겨진 필드의 부모 행을 찾아서 employeeId 값을 가져옵니다.
			var row = spanFields[i].closest("tr");
			var addItemSn = row.querySelector('[name="addItemSn"]').textContent;
			console.log("addItemSn : ", addItemSn);
			deletedAddItems.push(addItemSn);
		}
	}
	
	//Update
	var spanFields2 = document.getElementsByName("status");
	var updateAddItems = [];
	for(var i=0; i<spanFields2.length; i++){
		var spanFields2Value = spanFields2[i].textContent;
		if(spanFields2Value === 'U'){
			var row = spanFields2[i].closest("tr");
			var u_addItemSn = row.querySelector('[name="addItemSn"]').textContent;
			var u_addItemDesc = row.querySelector('[name="addItemDesc"]').textContent;
			
		}
		
		var updateAddItem = {
			addItemSn : u_addItemSn,
			addItemDesc : u_addItemDesc
		};	
	}
	updateAddItems.push(updateAddItem);
	console.log("updateAddItems",updateAddItems);
	
	
	var requestData = {
		insertAddItems: insertAddItems,
		deletedAddItems: deletedAddItems,
		updateAddItems : updateAddItems
	};
	
	
		$.ajax({
		url: '/save/additem',
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
						"<td><span name='status'>E</span></td>" +
						"<td name='addItemSn'>" + response[i].addItemSn + "</td>" +
						"<td name='addItemName'>" + response[i].addItemName + "</td>" +
						"<td name='addItemDesc'>" + response[i].addItemDesc + "</td>" +
						"<td name='useYN'>" + response[i].useYN + "</td>" +
						"</tr>";


					$('#addItemTable > tbody').append(addTableRow);
				}
			}
			return;
		}
	});
	

}