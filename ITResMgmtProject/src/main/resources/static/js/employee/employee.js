let rowCount = 0;

//행추가 버튼
function addRow() {
	const table = document.getElementById("empTable");
	const newRow = table.insertRow();
	newRow.setAttribute("data-row-id", rowCount);

	const newCellCheckBox = newRow.insertCell(0);
	newCellCheckBox.innerHTML = "<input type='checkbox' name='addCheckbox'>";

	const newCellStatus = newRow.insertCell(1);
	newCellStatus.innerHTML = "<span name='empStatus'>I</span>";

	const newCellEmpId = newRow.insertCell(2);
	newCellEmpId.innerHTML = "<input type='text' style='text-align:center;' id='empId" + rowCount + "'>";

	const newCellEmpPwd = newRow.insertCell(3);
	newCellEmpPwd.innerHTML = "<input type='password' style='text-align:center;' id='empPwd" + rowCount + "'>";



	const newCellEmpName = newRow.insertCell(4);
	newCellEmpName.innerHTML = "<input type='text' style='width:100px; text-align:center;' id='empName" + rowCount + "'>";

	//<select>에서 전체옵션 뺀 나머지 옵션
	const newCellEmpType = newRow.insertCell(5);
	const empTypeSelect = document.createElement('select');
	empTypeSelect.id = 'empTypeCode' + rowCount;
	empTypeSelect.name = 'empTypeList'; // 선택 목록의 name 속성 설정

	document.querySelectorAll('#searchEmpTypeList option:not(:first-child)').forEach((option) => {
		const empTypeSelectOption = document.createElement('option');
		empTypeSelectOption.value = option.value;
		empTypeSelectOption.textContent = option.textContent;
		empTypeSelect.appendChild(empTypeSelectOption);
	});

	newCellEmpType.appendChild(empTypeSelect);

	const newCellEmpStatus = newRow.insertCell(6);
	const empStatusSelect = document.createElement('select');
	empStatusSelect.id = 'empTypeStatus' + rowCount;
	empStatusSelect.name = 'empStatusList';

	document.querySelectorAll('#searchEmpStatusList option:not(:first-child)').forEach((option) => {
		const empStatusSelectOption = document.createElement('option');
		empStatusSelectOption.value = option.value;
		empStatusSelectOption.textContent = option.textContent;
		empStatusSelect.appendChild(empStatusSelectOption);
	});

	newCellEmpStatus.appendChild(empStatusSelect);

	rowCount++;

	const tableContainer = document.querySelector(".table-container");
	tableContainer.scrollTop = tableContainer.scrollHeight; // 맨 아래로 스크롤 이동

}

function hideRow() {
	//원래 있던 사원 삭제
	const selectedCheckboxes = document.querySelectorAll('input[name="empCheckbox"]:checked');

	selectedCheckboxes.forEach(function(checkbox) {
		const row = checkbox.closest('tr');
		//row.style.display = 'none';
		row.querySelector("span[name='empStatus']").textContent = "D";
		row.querySelector("td[name='employeeName']").contentEditable = 'false';
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
/*function saveList() {
	// 새로 추가될 행의 사원 ID들을 배열에 저장
	const newEmpIds = [];
	for (let i = 0; i < rowCount; i++) {
		const empId = document.getElementById('empId' + i).value;
		if (empId !== null && empId !== '') { // empId가 null 또는 빈 문자열이 아닌 경우에만 배열에 추가
			newEmpIds.push(empId);
		}
	}
	console.log("newEmpIds", newEmpIds);

	// 서버에 중복 검사 요청
	$.ajax({
		url: '/checkDuplicateEmployeeIds',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(newEmpIds), // 새로 추가될 행의 사원 ID 배열
		success: function(response) {
			// 중복된 ID 목록을 서버로부터 받아옵니다.
			const duplicateIds = response;
			var confirmation = confirm("저장하시겠습니까?"); // 사용자에게 물어보기


			if (duplicateIds.length > 0) {
				// 중복된 ID가 있으면 사용자에게 알림을 표시합니다.
				alert("중복된 사원ID " + duplicateIds.join(", ") + "입니다. 다시 입력해 주세요.");
				return;
			} else {
				// 중복이 아닌 경우에만 행을 추가
				// 여기서부터 행 추가 코드를 작성합니다.
				if (confirmation) {
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

							var empPwd = document.getElementById('empPwd' + i).value;

							if (empId && empName && empStatusCode && empTypeCode && empPwd) {
								if (validation(empId, empName, empPwd)) {
									var empValue = {
										employeeId: empId,
										employeeName: empName,
										employeeStatusCode: empStatusCode,
										employeeTypeCode: empTypeCode,
										employeePwd: empPwd
									};
									employee.push(empValue);
								}
							} else {
								alert("입력칸 모두 작성해 주세요");
								return rowCount;
							}
						}
						console.log("insert의 employee", employee);
						console.log("insert의 employee길이", employee.length);
					}

					//Delete
					var deletedEmployeeIds = [];

					var hiddenFields = document.getElementsByName("empStatus");

					for (var i = 0; i < hiddenFields.length; i++) {
						var hiddenValue = hiddenFields[i].textContent;
						if (hiddenValue === "D") {
							// 해당 숨겨진 필드의 부모 행을 찾아서 employeeId 값을 가져옵니다.
							var row = hiddenFields[i].closest("tr");
							var employeeId = row.querySelector('[name="employeeId"]').textContent;
							console.log("employeeId : ", employeeId);
							deletedEmployeeIds.push(employeeId);
						}
					}

					//Update
					var hiddenFields = document.getElementsByName("empStatus");
					var updatedEmployeeInfo = [];
					for (var i = 0; i < hiddenFields.length; i++) {
						var hiddenValue = hiddenFields[i].textContent;
						if (hiddenValue === "U") {
							// 해당 숨겨진 필드의 부모 행을 찾아서 employeeId 값을 가져옵니다.
							var row = hiddenFields[i].closest("tr");
							var updateEmpId = row.querySelector('[name="employeeId"]').textContent;
							var updateEmpPwd = row.querySelector('[name="employeePwd"]').textContent;
							var updateEmpName = row.querySelector('[name="employeeName"]').textContent;
							var updateEmpType = null;
							var updateEmpStatus = null;

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
									updateEmpType = option.value;
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
									updateEmpStatus = tpyeOption.value;
									break;
								}
							}
							if (updateValidation(updateEmpName, updateEmpPwd)) {
								var updateEmployee = {
									employeeId: updateEmpId,
									employeePwd: updateEmpPwd,
									employeeName: updateEmpName,
									employeeTypeCode: updateEmpType,
									employeeStatusCode: updateEmpStatus
								};
								updatedEmployeeInfo.push(updateEmployee);
							} else {
								return;
							}
						}
					}
					console.log("updatedEmployeeInfo", updatedEmployeeInfo)

					if ((rowCount === 0 || (rowCount > 0 && employee.length > 0)) && (deletedEmployeeIds.length >= 0 || updatedEmployeeInfo.length >= 0)) {
						var requestData = {
							employee: employee,
							deletedEmployeeIds: deletedEmployeeIds,
							updatedEmployeeInfo: updatedEmployeeInfo
						};
						alert("저장되었습니다.");
					}
					else {
						return;
					}


					$.ajax({
						url: '/save/employee',
						type: 'POST',
						contentType: 'application/json',
						data: JSON.stringify(requestData),
						success: function(response) {
							console.log("response", response);
							rowCount = 0;

							var empTypeSelect = document.querySelector("select[name='empTypeList']");
							var empStatusSelect = document.querySelector("select[name='empStatusList']");

							$('#empTable > tbody').empty();

							for (var i = 0; i < response.length; i++) {
								var addTableRow = "<tr>" +
									"<td><input type='checkbox' name='empCheckbox'></td>" +
									"<td><span name='empStatus'>S</span></td>" +
									"<td name='employeeId'>" + response[i].employeeId + "</td>" +
									"<td name='employeePwd' onclick='showPasswordField(this)'>**********</td>" +
									"<td name='employeeName' contenteditable='true' onclick='handleClick(this)'>" + response[i].employeeName + "</td>";

								//사원유형 <td>
								var employeeTypeCell = "<td name='employeeType' onclick='handleClick(this)'>" +
									"<span class='text' name='empType'>" + response[i].employeeType + "</span>";

								employeeTypeCell += empTypeSelect.outerHTML; // 'empTypeSelect'를 새 셀에 추가합니다..
								employeeTypeCell += "</td>";

								//사원상태 <td>
								var employeeStatusCell = "<td name='employeeStatus' onclick='handleClick(this)'>" +
									"<span class='text' name='empStatus'>" + response[i].employeeStatus + "</span>";

								employeeStatusCell += empStatusSelect.outerHTML; // 'empStatusSelect'를 새 셀에 추가합니다.
								employeeStatusCell += "</td>";

								addTableRow += employeeTypeCell + employeeStatusCell + "</tr>";

								$('#empTable > tbody').append(addTableRow);
							}
							return;
							//}
						},

						error: function(request, error) {
							alert("메시지:" + request.responseText + "\n" + "에러:" + error);
						}
					});
				}
			}
		},
		error: function(request, error) {
			// 서버 요청 중 에러가 발생한 경우에 대한 처리
			alert("에러 발생: " + error);
		}
	});
}*/
/*function saveList() {
	//저장버튼 N번 시도 후 사원ID 중복 없을 시 <input> 스타일 초기화
	for (let i = 0; i < rowCount; i++) {
		const duplicateEmpIdInput = document.getElementById('empId' + i);
		console.log("중복된 id input", duplicateEmpIdInput);

		const duplicateEmpId = document.getElementById('empId' + i).value;
		console.log("중복된 id 값duplicateEmpId", duplicateEmpId);

		duplicateEmpIdInput.style.border = "";
		duplicateEmpIdInput.style.borderRadius = "";
	}

	// 새로 추가될 행의 사원 ID들을 배열에 저장
	const newEmpIds = [];
	for (let i = 0; i < rowCount; i++) {
		const empId = document.getElementById('empId' + i).value;
		if (empId !== null && empId !== '') { // empId가 null 또는 빈 문자열이 아닌 경우에만 배열에 추가
			newEmpIds.push(empId);
		}
	}
	console.log("newEmpIds", newEmpIds);

	// 서버에 중복 검사 요청
	$.ajax({
		url: '/checkDuplicateEmployeeIds',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(newEmpIds),
		success: function(response) {
			// 중복된 ID 목록을 서버로부터 받아옵니다.
			const duplicateIds = response;
			console.log("duplicateIds", duplicateIds);

			var confirmation = confirm("저장하시겠습니까?"); // 사용자에게 물어보기

			//중복검사 N번 시도 후 사원ID 중복 없을 시 <input> 스타일 초기화
			for (let i = 0; i < rowCount; i++) {
				const duplicateEmpIdInput = document.getElementById('empId' + i);
				console.log("중복된 id input", duplicateEmpIdInput);

				const duplicateEmpId = document.getElementById('empId' + i).value;
				console.log("중복된 id 값duplicateEmpId", duplicateEmpId);

				duplicateEmpIdInput.style.border = "";
				duplicateEmpIdInput.style.borderRadius = "";
			}

			// 중복된 ID가 있으면 사용자에게 알림을 표시합니다.
			if (duplicateIds.length > 0) {
				//alert("중복된 사원ID [" + duplicateIds.join(", ") + "] 입니다. 다시 입력해 주세요.");

				//중복된 ID가 있으면 <input> 테두리 빨간색으로 변경
				for (let i = 0; i < duplicateIds.length; i++) {
					console.log("duplicateIds.length", duplicateIds.length);

					var duplicateEmpIdInput = document.getElementById('empId' + i);
					console.log("중복된 id input", duplicateEmpIdInput);

					var duplicateEmpId = document.getElementById('empId' + i).value;
					console.log("중복된 id 값duplicateEmpId", duplicateEmpId);

					if (duplicateEmpId) {
						duplicateEmpIdInput.style.border = "2px solid red";
						duplicateEmpIdInput.style.borderRadius = "3px";
					}
				}

				return;
			} else {
				// 중복이 아닌 경우에만 행을 추가
				// 여기서부터 행 추가 코드를 작성합니다.
				if (confirmation) {
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

							var empPwd = document.getElementById('empPwd' + i).value;

							if (empId && empName && empStatusCode && empTypeCode && empPwd) {
								if (validation(empId, empName, empPwd)) {
									var empValue = {
										employeeId: empId,
										employeeName: empName,
										employeeStatusCode: empStatusCode,
										employeeTypeCode: empTypeCode,
										employeePwd: empPwd
									};
									employee.push(empValue);
								}
							} else {
								alert("입력칸 모두 작성해 주세요");
								return rowCount;
							}
						}
						console.log("insert의 employee", employee);
						console.log("insert의 employee길이", employee.length);
					}

					//Delete
					var deletedEmployeeIds = [];

					var hiddenFields = document.getElementsByName("empStatus");

					for (var i = 0; i < hiddenFields.length; i++) {
						var hiddenValue = hiddenFields[i].textContent;
						if (hiddenValue === "D") {
							// 해당 숨겨진 필드의 부모 행을 찾아서 employeeId 값을 가져옵니다.
							var row = hiddenFields[i].closest("tr");
							var employeeId = row.querySelector('[name="employeeId"]').textContent;
							console.log("employeeId : ", employeeId);
							deletedEmployeeIds.push(employeeId);
						}
					}

					//Update
					var hiddenFields = document.getElementsByName("empStatus");
					var updatedEmployeeInfo = [];
					for (var i = 0; i < hiddenFields.length; i++) {
						var hiddenValue = hiddenFields[i].textContent;
						if (hiddenValue === "U") {
							// 해당 숨겨진 필드의 부모 행을 찾아서 employeeId 값을 가져옵니다.
							var row = hiddenFields[i].closest("tr");
							var updateEmpId = row.querySelector('[name="employeeId"]').textContent;
							var updateEmpPwd = row.querySelector('[name="employeePwd"]').textContent;
							var updateEmpName = row.querySelector('[name="employeeName"]').textContent;
							var updateEmpType = null;
							var updateEmpStatus = null;

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
									updateEmpType = option.value;
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
									updateEmpStatus = tpyeOption.value;
									break;
								}
							}
							if (updateValidation(updateEmpName, updateEmpPwd)) {
								var updateEmployee = {
									employeeId: updateEmpId,
									employeePwd: updateEmpPwd,
									employeeName: updateEmpName,
									employeeTypeCode: updateEmpType,
									employeeStatusCode: updateEmpStatus
								};
								updatedEmployeeInfo.push(updateEmployee);
							} else {
								return;
							}
						}
					}
					console.log("updatedEmployeeInfo", updatedEmployeeInfo)

					if ((rowCount === 0 || (rowCount > 0 && employee.length > 0)) && (deletedEmployeeIds.length >= 0 || updatedEmployeeInfo.length >= 0)) {
						var requestData = {
							employee: employee,
							deletedEmployeeIds: deletedEmployeeIds,
							updatedEmployeeInfo: updatedEmployeeInfo
						};
						alert("저장되었습니다.");
					}
					else {
						return;
					}


					$.ajax({
						url: '/save/employee',
						type: 'POST',
						contentType: 'application/json',
						data: JSON.stringify(requestData),
						success: function(response) {
							console.log("response", response);
							rowCount = 0;

							var empTypeSelect = document.querySelector("select[name='empTypeList']");
							var empStatusSelect = document.querySelector("select[name='empStatusList']");

							$('#empTable > tbody').empty();

							for (var i = 0; i < response.length; i++) {
								var addTableRow = "<tr>" +
									"<td><input type='checkbox' name='empCheckbox'></td>" +
									"<td><span name='empStatus'>S</span></td>" +
									"<td name='employeeId'>" + response[i].employeeId + "</td>" +
									"<td name='employeePwd' onclick='showPasswordField(this)'>**********</td>" +
									"<td name='employeeName' contenteditable='true' onclick='handleClick(this)'>" + response[i].employeeName + "</td>";

								//사원유형 <td>
								var employeeTypeCell = "<td name='employeeType' onclick='handleClick(this)'>" +
									"<span class='text' name='empType'>" + response[i].employeeType + "</span>";

								employeeTypeCell += empTypeSelect.outerHTML; // 'empTypeSelect'를 새 셀에 추가합니다..
								employeeTypeCell += "</td>";

								//사원상태 <td>
								var employeeStatusCell = "<td name='employeeStatus' onclick='handleClick(this)'>" +
									"<span class='text' name='empStatus'>" + response[i].employeeStatus + "</span>";

								employeeStatusCell += empStatusSelect.outerHTML; // 'empStatusSelect'를 새 셀에 추가합니다.
								employeeStatusCell += "</td>";

								addTableRow += employeeTypeCell + employeeStatusCell + "</tr>";

								$('#empTable > tbody').append(addTableRow);
							}
							return;
							//}
						},

						error: function(request, error) {
							alert("메시지:" + request.responseText + "\n" + "에러:" + error);
						}
					});
				}
			}
		},
		error: function(request, error) {
			// 서버 요청 중 에러가 발생한 경우에 대한 처리
			alert("에러 발생: " + error);
		}
	});
}*/

function saveList() {
	if (rowCount > 0) {
		for (var i = 0; i < rowCount; i++) {
			var empId = document.getElementById('empId' + i).value;
			var empName = document.getElementById('empName' + i).value;
			var empTypeCode = document.getElementById('empTypeCode' + i).value;
			var empStatusCode = document.getElementById('empTypeStatus' + i).value;
			var empPwd = document.getElementById('empPwd' + i).value;

			if (empId && empName && empStatusCode && empTypeCode && empPwd) {
				//alert("입력 모두 작성완료");
				
				








			} else {
				alert("입력칸 모두 작성해 주세요");
				return rowCount;
			}
	}

}



}
//유효성 검사
function validation(empId, empName, empPwd) {

	console.log("empId", empId, "empName", empName, "empPwd", empPwd);


	// 사원ID 유효성 검사
	var pattern_id = /^[^~!@#$%^&*_+|<>?:{}()-,.=\p{Script=Hangul}\s]/u;
	var pattern_length = /^.{5,}$/;
	var pattern_whitespace = /^\S*$/;

	if (!pattern_length.test(empId) || !pattern_id.test(empId) || !pattern_whitespace.test(empId)) {
		alert("사원ID는 5글자 이상이며, 특수문자, 공백, 한글을 사용할 수 없습니다.");
		return false;
	}


	// 사원비밀번호 유효성 검사
	var pattern_pwd = /^(?=.*[!@#$%^&*_+|<>?:{}()-,.=])[^\s]{6,}$/;

	if (!pattern_pwd.test(empPwd)) {
		alert("사원비밀번호는 특수문자 포함 6글자 이상, 공백은 사용할 수 없습니다.");
		return false;
	}

	// 사원명 유효성 검사
	var pattern_name = /^[a-zA-Z가-힣]{2,}$/;

	if (!pattern_name.test(empName)) {
		alert("사원명은 2글자 이상, 숫자, 공백, 특수문자는 사용할 수 없습니다.");
		return false;
	}
	return true;
}

function updateValidation(updateEmpName, updateEmpPwd) {
	// 사원명 유효성 검사
	if (!/^[^\d\s!@#$%^&*(),.?":{}|<>]{2,}$/.test(updateEmpName)) {
		alert("수정된 사원명은 2글자 이상, 숫자, 공백, 특수문자는 사용할 수 없습니다.");
		return false;
	}

	if (!/^(?=.*[!@#$%^&*_+|<>?:{}()-,.=])[^\s]{6,}$/.test(updateEmpPwd)) {
		alert("사원비밀번호는 특수문자 포함 6글자 이상, 공백은 사용할 수 없습니다.");
		return false;
	}

	return true;
}


const cells = document.querySelectorAll('td[name="employeeName"], td[name="employeeType"], td[name="employeeStatus"]');

cells.forEach((cell) => {
	cell.addEventListener('click', function() {
		handleClick(this);
	});
});

function handleClick(element) {
	const empStatusSpan = element.closest('tr').querySelector("span[name='empStatus']");

	if (empStatusSpan.textContent === 'D') {
		return;
	} else {
		empStatusSpan.textContent = 'U';

		// 클릭한 셀이 'employeeType' 또는 'employeeStatus'인 경우에만 showSelectBox 함수 실행
		if (element.getAttribute('name') === 'employeeType' || element.getAttribute('name') === 'employeeStatus') {
			showSelectBox(element);
		}
	}
}

function showSelectBox(listItem) {
	const textElement = listItem.querySelector(".text");
	textElement.style.display = "none";

	const selectElement = listItem.querySelector(".select");
	selectElement.style.display = "inline-block";

	selectElement.focus();

	// 현재 선택한 text 값
	const selectedText = textElement.textContent;

	// select 요소 내의 모든 option 요소를 가져옵니다.
	const options = Array.from(selectElement.options);

	// 선택한 셀의 값을 기반으로 해당 값을 가진 옵션을 찾아 선택합니다.
	for (let i = 0; i < options.length; i++) {
		if (options[i].textContent === selectedText) {
			selectElement.selectedIndex = i;
			break; // 찾았으므로 루프 종료
		}
	}

	selectElement.addEventListener("change", function() {
		textElement.innerText = selectElement.options[selectElement.selectedIndex].textContent;
	});

	selectElement.addEventListener("blur", function() {
		textElement.style.display = "inline-block";
		selectElement.style.display = "none";
	});
}

//사원명 클릭했을 때 input박스 생성
function showNameField(element) {
	const empStatusSpan = element.closest('tr').querySelector("span[name='empStatus']");
	empStatusSpan.textContent = 'U';

	const updateName = document.createElement('input');
	updateName.type = 'text';
	updateName.value = element.textContent;
	updateName.classList.add('edit-field');

	//스팬을 입력 필드로 대체
	element.textContent = '';
	updateName.style.width = '100px'
	updateName.style.textAlign = 'center'; // 텍스트를 가운데 정렬
	element.appendChild(updateName);

	updateName.focus();

	//입력 필드가 포커스를 잃을 때
	updateName.addEventListener('blur', function() {
		//입력 필드에서 새로운 값 가져오기
		const updateNameValue = updateName.value;
		element.textContent = updateNameValue;
	});
}

//사원비밀번호 가져오기
function showPasswordField(element) {
	const empStatusSpan = element.closest('tr').querySelector("span[name='empStatus']");
	empStatusSpan.textContent = 'U';

	const employeeId = element.closest('tr').querySelector("td[name='employeeId']").textContent;
	console.log('선택된 사원의 ID: ' + employeeId);

	const updatePwd = document.createElement('input');
	updatePwd.type = 'text';

	$.ajax({
		url: '/findEmployeePwd',
		type: 'Post',
		data: { employeeId: employeeId },
		success: function(response) {

			// JSON 응답에서 employeePwd 필드의 값을 추출
			var employeePwd = response.employeePwd;
			console.log("employeePwd: " + employeePwd);

			updatePwd.value = employeePwd;
		},
		error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		}
	});

	updatePwd.classList.add('edit-field');
	// 스팬을 입력 필드로 대체
	element.textContent = '';
	updatePwd.style.width = '170px';
	updatePwd.style.textAlign = 'center'; // 텍스트를 가운데 정렬
	element.appendChild(updatePwd);

	updatePwd.focus();

	// 입력 필드가 포커스를 잃을 때
	updatePwd.addEventListener('blur', function() {
		// 입력 필드에서 새로운 값 가져오기
		const updateNameValue = updatePwd.value;
		element.textContent = updateNameValue;
	});
}





//조회
function searchList() {
	const employeeTypeCode = document.getElementById('searchEmpTypeList').value;
	const employeeStatusCode = document.getElementById('searchEmpStatusList').value;
	const searchText = document.getElementById('searchText').value;

	const searchData = {
		employeeTypeCode: employeeTypeCode,
		employeeStatusCode: employeeStatusCode,
		searchText: searchText
	};

	$.ajax({
		url: '/search/employee?' + $.param(searchData),
		type: 'GET',
		success: function(response) {
			//console.log("response", response)

			var empTypeSelect = document.querySelector("select[name='empTypeList']");
			var empStatusSelect = document.querySelector("select[name='empStatusList']");



			$('#empTable > tbody').empty();

			if (response.length === 0) {
				// 검색 결과가 없는 경우
				var noResultRow = "<tr><td colspan='7'>검색결과가 없습니다</td></tr>";
				$('#empTable > tbody').append(noResultRow);
			} else {
				// 검색 결과가 있는 경우
				for (var i = 0; i < response.length; i++) {
					var addTableRow = "<tr>" +
						"<td><input type='checkbox' name='empCheckbox'></td>" +
						"<td><span name='empStatus'>S</span></td>" +
						"<td name='employeeId'>" + response[i].employeeId + "</td>" +
						"<td name='employeePwd' onclick='showPasswordField(this)'>**********</td>" +
						"<td name='employeeName' contenteditable='true' onclick='handleClick(this)'>" + response[i].employeeName + "</td>";

					//사원유형 <td>
					var employeeTypeCell = "<td onclick='handleClick(this)' name='employeeType'>" +
						"<span class='text' name='empType'>" + response[i].employeeType + "</span>";

					employeeTypeCell += empTypeSelect.outerHTML; // 'empTypeSelect'를 새 셀에 추가합니다..
					//console.log("찐 employeeTypeCell", employeeTypeCell);
					employeeTypeCell += "</td>";

					//사원상태 <td>
					var employeeStatusCell = "<td onclick='handleClick(this)' name='employeeStatus'>" +
						"<span class='text' name='empStatus'>" + response[i].employeeStatus + "</span>";

					employeeStatusCell += empStatusSelect.outerHTML; // 'empStatusSelect'를 새 셀에 추가합니다.
					employeeStatusCell += "</td>";

					addTableRow += employeeTypeCell + employeeStatusCell + "</tr>";

					$('#empTable > tbody').append(addTableRow);

					/*									var addTableRow = "<tr>" +
											"<td><input type='checkbox' name='empCheckbox'></td>" +
											"<td><span name='empStatus'>S</span></td>" +
											"<td name='employeeId'>" + response[i].employeeId + "</td>" +
											"<td name='employeePwd' onclick='showPasswordField(this)'>**********</td>" +
											"<td name='employeeName' onclick='showNameField(this)'>" + response[i].employeeName + "</td>";
												
										
										
										
										var employeeTypeCell2 = "<td onclick='handleClick(this)' name='employeeType'>" +
											"<span class='text' name='empType'>" + response[i].employeeType + "</span>";
											 
											var empTypeSelect2 = document.querySelector("select[name='searchEmpTypeList']");
											console.log("empTypeSelect2",empTypeSelect2);
											
											
											//select 옵션 가지고 오기
												employeeTypeCell2 += empTypeSelect2.outerHTML; // 'empTypeSelect'를 새 셀에 추가합니다..
										//console.log("찐 employeeTypeCell", employeeTypeCell);
										employeeTypeCell2 += "</td>" ;
					
					
											addTableRow += employeeTypeCell2+ "</tr>";
											
											
						
					
										$('#empTable > tbody').append(addTableRow);*/


				}
				alert(response.length + "건이 조회되었습니다.");
			}
			return;
		},
		error: function(request, status, error) {
			alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
		}
	});
}

