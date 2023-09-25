let rowCount = 0;

//행추가 버튼
function addRow() {
	const table = document.getElementById("empTable");
	const newRow = table.insertRow();
	newRow.setAttribute("data-row-id", rowCount);

	const newCell0 = newRow.insertCell(0);
	newCell0.innerHTML = "<input type='checkbox' name='addCheckbox'>";

	const newCell1 = newRow.insertCell(1);
	newCell1.innerHTML = "<span name='hiddenBox'>I</span>";

	const newCell2 = newRow.insertCell(2);
	newCell2.innerHTML = "<input type='text' id='empId" + rowCount + "'>";

	const newCell3 = newRow.insertCell(3);
	newCell3.innerHTML = "<input type='text' id='empName" + rowCount + "'>";

	//<select>에서 전체옵션 뺀 나머지 옵션
	const newCell4 = newRow.insertCell(4);
	const clonedSelect = document.createElement('select');
	clonedSelect.id = 'empTypeCode' + rowCount;
	clonedSelect.name = 'empTypeList'; // 선택 목록의 name 속성 설정

	document.querySelectorAll('#searchEmpTypeList option:not(:first-child)').forEach((option) => {
		const clonedOption = document.createElement('option');
		clonedOption.value = option.value;
		clonedOption.textContent = option.textContent;
		clonedSelect.appendChild(clonedOption);
	});

	newCell4.appendChild(clonedSelect);

	const newCell5 = newRow.insertCell(5);
	const clonedSelect2 = document.createElement('select');
	clonedSelect2.id = 'empTypeStatus' + rowCount;
	clonedSelect2.name = 'empStatusList';

	document.querySelectorAll('#searchEmpStatusList option:not(:first-child)').forEach((option) => {
		const clonedOption2 = document.createElement('option');
		clonedOption2.value = option.value;
		clonedOption2.textContent = option.textContent;
		clonedSelect2.appendChild(clonedOption2);
	});

	newCell5.appendChild(clonedSelect2);


	rowCount++;
}



//행삭제 버튼 (행숨김)
function hideRow() {
	//원래 있던 사원 삭제
	const selectedCheckboxes = document.querySelectorAll('input[name="empCheckbox"]:checked');
	const hiddenBox = document.querySelector("span[name='hiddenBox']").textContent;
	console.log("hiddenBox", hiddenBox);

	selectedCheckboxes.forEach(function(checkbox) {
		const row = checkbox.closest('tr');
		//row.style.display = 'none';
		row.querySelector("span[name='hiddenBox']").textContent = "d";
	});

	//console.log("hiddenBox.value", hiddenBox.value);

	//행추가 후 행삭제
	const insertCheckboxes = document.querySelectorAll('input[name="addCheckbox"]:checked');
	insertCheckboxes.forEach(function(checkbox) {
		const row = checkbox.closest('tr');
		row.remove();
		rowCount--;
	});
}



//저장버튼
function saveList() {
	//Insert
	if (rowCount > 0) {
		var employee = new Array();
		for (var i = 0; i < rowCount; i++) {
			const empId = document.getElementById('empId' + i).value;
			const empName = document.getElementById('empName' + i).value;

			const empTypeSelect = document.getElementById('empTypeCode' + i);
			const empTypeCode = empTypeSelect.value;

			const empStatusSelect = document.getElementById('empTypeStatus' + i);
			const empStatusCode = empStatusSelect.value;

			if (empId && empName && empStatusCode && empTypeCode) {
				const empValue = {
					employeeId: empId,
					employeeName: empName,
					employeeStatusCode: empStatusCode,
					employeeTypeCode: empTypeCode
				};
				employee.push(empValue);
			}
		}
		console.log("employee", employee);
	}

	//Delete
	var deletedEmployeeIds = [];

	var hiddenFields = document.getElementsByName("hiddenBox");

	for (var i = 0; i < hiddenFields.length; i++) {
		var hiddenValue = hiddenFields[i].textContent;
		if (hiddenValue === "d") {
			// 해당 숨겨진 필드의 부모 행을 찾아서 employeeId 값을 가져옵니다.
			var row = hiddenFields[i].closest("tr");
			var employeeId = row.querySelector('[name="employeeId"]').textContent;
			console.log("employeeId : ", employeeId);
			deletedEmployeeIds.push(employeeId);
		}
	}

	//Update
	var hiddenFields = document.getElementsByName("hiddenBox");
	var updatedEmployeeInfo = [];
	for (var i = 0; i < hiddenFields.length; i++) {
		var hiddenValue = hiddenFields[i].textContent;
		if (hiddenValue === "u") {
			// 해당 숨겨진 필드의 부모 행을 찾아서 employeeId 값을 가져옵니다.
			var row = hiddenFields[i].closest("tr");
			var u_empId = row.querySelector('[name="employeeId"]').textContent;
			var u_empName = row.querySelector('[name="employeeName"]').textContent;
			var u_empType = null;
			var u_empStatus = null;

			//name="empType"인 <span> text와 일치하는 <select><option> value값 가져오기
			//span text값 가지고오기
			var empTypeElement = row.querySelector('span.text[name="empType"]');
			var empTypetext = empTypeElement.textContent;

			//empTypeList <select> 가지고 오기	
			var selectElement = row.querySelector('select[name="empTypeList"]');

			//empTypeList <select>의 <option> 가지고 오기
			for (var j = 0; j < selectElement.options.length; j++) {
				var option = selectElement.options[j];
				//console.log("Option " + i + ": " + option.value + " - " + option.textContent);
				if (empTypetext === option.textContent) {
					u_empType = option.value;
					break;
				}
			}

			//name="empStatus"인 <span> text와 일치하는 <select><option> value값 가져오기
			//span text값 가지고오기
			var empStatusElement = row.querySelector('span.text[name="empStatus"]');
			var empStatustext = empStatusElement.textContent;

			//empTypeList <select> 가지고 오기	
			var typeSelectElement = row.querySelector('select[name="empStatusList"]');

			//empTypeList <select>의 <option> 가지고 오기
			for (var k = 0; k < typeSelectElement.options.length; k++) {
				var tpyeOption = typeSelectElement.options[k];
				//console.log("Option " + i + ": " + option.value + " - " + option.textContent);
				if (empStatustext === tpyeOption.textContent) {
					u_empStatus = tpyeOption.value;
					break;
				}
			}
			//console.log("u_empStatus", u_empStatus);
			var updateEmployee = {
				employeeId: u_empId,
				employeeName: u_empName,
				employeeTypeCode: u_empType,
				employeeStatusCode: u_empStatus
			};

			updatedEmployeeInfo.push(updateEmployee)
		}
	}
	console.log("updatedEmployeeInfo", updatedEmployeeInfo)


	var requestData = {
		employee: employee,
		deletedEmployeeIds: deletedEmployeeIds,
		updatedEmployeeInfo: updatedEmployeeInfo
	};


	$.ajax({
		url: '/save/employee',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(requestData),
		success: function(response) {

			console.log("response", response);
			rowCount = 0;


			$('#empTable > tbody').empty();

			for (var i = 0; i < response.length; i++) {
				var addTableRow = "<tr>" +
					"<td><input type='checkbox' name='empCheckbox'></td>" +
					"<td><span name='hiddenBox'>E</span></td>" +
					"<td name='employeeId'>" + response[i].employeeId + "</td>" +
					"<td name='employeeName' contenteditable='true' onclick='handleClick(this)'>" + response[i].employeeName + "</td>" +
					"<td name='employeeType' onclick='handleClick(this)'>" + "<span class='text' name='empType'>" + response[i].employeeType + "</span>" +
					"<select name='empTypeList' class='select' style='display: none;'>" +
					"<option value='EMT002'>IT자원관리자</option>" +
					"<option value='EMT001'>시스템관리자</option>" +
					"</select>" + "</td>" +
					"<td name='employeeStatus' onclick='handleClick(this)'>" + "<span class='text' name='empStatus'>" + response[i].employeeStatus + "</span>" +
					"<select name='empStatusList' class='select' style='display: none;'>" +
					"<option value='EMS001'>재직중</option>" +
					"<option value='EMS002'>휴직중</option>" +
					"<option value='EMS003'>퇴직</option>" +
					"</select>" + "</td>" +
					"</tr>";


				/*	var addTableRow = "<tr id='empList'>" +
						"<td><input type='checkbox' name='empCheckbox'></td>" +
						"<td><span name='hiddenBox'>E</span></td>" +
						"<td name='employeeId'>" + response[i].employeeId + "</td>" +
						"<td name='employeeName' contenteditable='true' onclick='handleClick(this)'>" + response[i].employeeName + "</td>" +
						"<td onclick='handleClick(this)' name='employeeType'>" +
						"<span class='text' name='empType'>" + response[i].employeeType + "</span>" +
						"<select name='empTypeList' class='select' style='display: none;'>";
	
					//console.log("commonCodeTypeList", commonCodeTypeList);
					// employeeType 선택 목록 추가
					for (var j = 0; j < commonCodeTypeList.length; j++) {
						var commonCode = commonCodeTypeList[j];
						addTableRow += "<option value='" + commonCode.key + "'>" + commonCode.value + "</option>";
					}
	
					addTableRow += "</select></td>" +
						"<td onclick='handleClick(this)' name='employeeStatus'>" +
						"<span class='text' name='empStatus'>" + response[i].employeeStatus + "</span>" +
						"<select name='empStatusList' class='select' style='display: none;'>";
	
					// employeeStatus 선택 목록 추가
					for (var k = 0; k < commonCodeStatusList.length; k++) {
						var commonCode2 = commonCodeStatusList[k];
						addTableRow += "<option value='" + commonCode2.key + "'>" + commonCode2.value + "</option>";
					}
	
					addTableRow += "</select></td></tr>";*/



				$('#empTable > tbody').append(addTableRow);
			}
			return;
		},
		error: function(request, error) {
			alert("메시지:" + request.responseText + "\n" + "에러:" + error);
		}
	});
}

const cells = document.querySelectorAll('td[name="employeeName"], td[name="employeeType"], td[name="employeeStatus"]');

cells.forEach((cell) => {
	cell.addEventListener('click', function() {
		handleClick(this);
	});
});

function handleClick(element) {
	const hiddenBox = element.closest('tr').querySelector("span[name='hiddenBox']");
	hiddenBox.textContent = 'u';

	// 클릭한 셀이 'employeeType' 또는 'employeeStatus'인 경우에만 showSelectBox 함수 실행
	if (element.getAttribute('name') === 'employeeType' || element.getAttribute('name') === 'employeeStatus') {
		showSelectBox(element);
	}
}

function showSelectBox(listItem) {
	const textElement = listItem.querySelector(".text");
	textElement.style.display = "none";

	const selectElement = listItem.querySelector(".select");



	selectElement.style.display = "inline-block";

	selectElement.focus();

	selectElement.addEventListener("blur", function() {
		const selectedOption = selectElement.options[selectElement.selectedIndex];
		textElement.innerText = selectedOption.innerText;
		textElement.style.display = "inline-block";
		selectElement.style.display = "none";
	});

	// 현재 선택한 text 값
	const selectedText = textElement.textContent;

	// select 요소 내의 모든 option 요소를 가져옵니다.
	const options = Array.from(selectElement.options);

	// 선택한 셀의 값을 기반으로 첫 번째 옵션을 선택 상자에 표시합니다.
	for (let i = 0; i < options.length; i++) {
		if (options[i].textContent === selectedText) {
			selectElement.selectedIndex = i;
			break; // 찾았으므로 루프 종료
		}
	}
}


//검색
function searchList() {
	const employeeTypeCode = document.getElementById('searchEmpTypeList').value;
	const employeeStatusCode = document.getElementById('searchEmpStatusList').value;
	const searchText = document.getElementById('searchText').value;

	console.log("employeeTypeCode", employeeTypeCode)
	console.log("employeeStatusCode", employeeStatusCode)

	const searchData = {
		employeeTypeCode: employeeTypeCode,
		employeeStatusCode: employeeStatusCode,
		searchText: searchText
	};

	$.ajax({
		url: '/search/employee?' + $.param(searchData),
		type: 'GET',
		success: function(response) {
			console.log("response", response)

			$('#empTable > tbody').empty();

			if (response.length === 0) {
				// 검색 결과가 없는 경우
				var noResultRow = "<tr><td colspan='5'>검색결과가 없습니다</td></tr>";
				$('#empTable > tbody').append(noResultRow);
			} else {
				// 검색 결과가 있는 경우
				for (var i = 0; i < response.length; i++) {
					var addTableRow = "<tr>" +
						"<td><input type='checkbox' name='empCheckbox'></td>" +
						"<td><input type='' name='hiddenBox'></td>" +
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
						"</tr>";


					$('#empTable > tbody').append(addTableRow);
				}
			}
			return;
		},
		error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		}
	});
}