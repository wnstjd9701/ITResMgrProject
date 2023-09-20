var rowCount = 0;

//행추가 버튼
function addRow() {
	var table = document.getElementById("empTable");
	var newRow = table.insertRow();
	newRow.setAttribute("data-row-id", rowCount);
	
	var newCell0 = newRow.insertCell(0);
	newCell0.innerHTML = "<input type='checkBox' name='addCheckbox'>";

	var newCell1 = newRow.insertCell(1);
	newCell1.innerHTML = "<input type='text' id='empId" + rowCount + "' size='10'>";

	var newCell2 = newRow.insertCell(2);
	newCell2.innerHTML = "<input type='text' id='empName" + rowCount + "' size='10'>";

	var newCell3 = newRow.insertCell(3);
	var selectEmpType = document.querySelector('select[name="searchEmpTypeList"]');
	var clonedSelect = selectEmpType.cloneNode(true);
	clonedSelect.id = 'empTypeCode' + rowCount;
	newCell3.appendChild(clonedSelect);

	var newCell4 = newRow.insertCell(4);
	var selectEmpStatus = document.querySelector('select[name="searchEmpStatusList"]');  
	var clonedSelect2 = selectEmpStatus.cloneNode(true);
	clonedSelect2.id = 'empTypeStatus' + rowCount;
	newCell4.appendChild(clonedSelect2);

	rowCount++;
}


//행삭제 버튼 (행숨김)
function hideRow() {
	const selectedCheckboxes = document.querySelectorAll('input[name="emp_checkbox"]:checked');
	const hiddenBox = document.querySelector("input[name='hiddenBox']");

	selectedCheckboxes.forEach(function(checkbox) {
		var row = checkbox.closest('tr');
		row.style.display = 'none';
		row.querySelector("input[name='hiddenBox']").value = "d";
	});

	console.log("hiddenBox.value", hiddenBox.value)
}



//저장버튼
function saveList() {
	//Insert
	if (rowCount > 0) {
		var employee = new Array();
		for (var i = 0; i < rowCount; i++) {
			var empId = document.getElementById('empId' + i).value;
			var empName = document.getElementById('empName' + i).value;

			var empTypeSelect = document.getElementById('empTypeCode' + i);
			var empTypeCode = empTypeSelect.value;

			var empStatusSelect = document.getElementById('empTypeStatus' + i);
			var empStatusCode = empStatusSelect.value;

			if (empId && empName && empStatusCode && empTypeCode) {
				var empValue = {
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
		var hiddenValue = hiddenFields[i].value;
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
		var hiddenValue = hiddenFields[i].value;
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
			console.log("response", response)
			rowCount = 0;


			$('#empTable > tbody').empty();

			for (var i = 0; i < response.length; i++) {
				var addTableRow = "<tr>" +
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
					"<td><input type='hidden' name='hiddenBox'></td>" + "</tr>";


				$('#empTable > tbody').append(addTableRow);
			}
			return;
		},
		error: function(request, error) {
			alert("메시지:" + request.responseText + "\n" + "에러:" + error);
		}
	});
}



// 모든 셀을 클릭 이벤트 핸들러에 연결
const cells = document.querySelectorAll('td[name="employeeName"], td[name="employeeType"], td[name="employeeStauts"]');

cells.forEach((cell) => {
	cell.addEventListener('click', function() {
		handleClick(this);
	});
});


// name, 유형, 상태를 클릭하면 'u' 값을 hiddenBox에 넣기
function handleClick(element) {
	const hiddenBox = element.closest('tr').querySelector("input[name='hiddenBox']");
	hiddenBox.value = 'u';

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
}


//검색
function searchList() {
	var employeeTypeCode = document.getElementById('searchEmpTypeList').value;
	var employeeStatusCode = document.getElementById('searchEmpStatusList').value;
	var searchText = document.getElementById('searchText').value;

	console.log("employeeTypeCode", employeeTypeCode)
	console.log("employeeStatusCode", employeeStatusCode)

	var searchData = {
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
						"<td><input type='hidden' name='hiddenBox'></td>" + "</tr>";


					$('#empTable > tbody').append(addTableRow);
				}
			}
			return;
		}
	});
}