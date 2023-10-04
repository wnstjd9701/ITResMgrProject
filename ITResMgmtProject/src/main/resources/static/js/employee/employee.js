let rowCount=0;

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
	newCellEmpId.innerHTML = "<input type='text' id='empId" + rowCount + "'>";

	const newCellEmpName = newRow.insertCell(3);
	newCellEmpName.innerHTML = "<input type='text' id='empName" + rowCount + "'>";

	//<select>에서 전체옵션 뺀 나머지 옵션
	const newCellEmpType = newRow.insertCell(4);
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

	const newCellEmpStatus = newRow.insertCell(5);
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
	
	const newCellEmpPwd = newRow.insertCell(6);
	newCellEmpPwd.innerHTML = "<input type='text' id='empPwd" + rowCount + "' + placeholder='비밀번호를 입력하세요'>"; 

	rowCount++;

}



//행삭제 버튼
function hideRow() {
	//원래 있던 사원 삭제
	const selectedCheckboxes = document.querySelectorAll('input[name="empCheckbox"]:checked');
	//const hiddenBox = document.querySelector("span[name='empStatus']").textContent;
	//console.log("hiddenBox", hiddenBox);

	selectedCheckboxes.forEach(function(checkbox) {
		const row = checkbox.closest('tr');
		//row.style.display = 'none';
		row.querySelector("span[name='empStatus']").textContent = "D";
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

			const empPwd = document.getElementById('empPwd' + i).value;

			if (empId && empName && empStatusCode && empTypeCode && empPwd) {
				const empValue = {
					employeeId: empId,
					employeeName: empName,
					employeeStatusCode: empStatusCode,
					employeeTypeCode: empTypeCode,
					employeePwd: empPwd
				};
				employee.push(empValue);
				//duplicateTest(employee);
				duplicateCheck(employee);
			} else {
				alert("입력칸 모두 작성해 주세요");
				return rowCount;
			}
		}
		console.log("insert의 employee", employee);
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

 
	if ((rowCount === 0 || (rowCount > 0 && employee.length > 0)) && (deletedEmployeeIds.length >= 0 || updatedEmployeeInfo.length >= 0)) {
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

				//Insert 오류나고 Update나 Delete 동시실행시 response가 0일때 테이블 데이터 유지
//				if (response.length === 0) {
//					console.log("rowCount", rowCount);
//					return;
//				} else{
					//정상 실행 시
					rowCount = 0;

					var empTypeSelect = document.querySelector("select[name='empTypeList']");
					var empStatusSelect = document.querySelector("select[name='empStatusList']");

					$('#empTable > tbody').empty();

					for (var i = 0; i < response.length; i++) {
						var addTableRow = "<tr>" +
							"<td><input type='checkbox' name='empCheckbox'></td>" +
							"<td><span name='empStatus'>E</span></td>" +
							"<td name='employeeId'>" + response[i].employeeId + "</td>" +
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

						addTableRow += employeeTypeCell + employeeStatusCell + "<td></td>" + "</tr>";

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
}*/

function saveList() {

    // 새로 추가될 행의 사원 ID들을 배열에 저장
    const newEmpIds = [];
    for (let i = 0; i < rowCount; i++) {
        const empId = document.getElementById('empId' + i).value;
        newEmpIds.push(empId);
    }

    // 서버에 중복 검사 요청
    $.ajax({
        url: '/checkDuplicateEmployeeIds',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(newEmpIds), // 새로 추가될 행의 사원 ID 배열
        success: function(response) {
            // 중복된 ID 목록을 서버로부터 받아옵니다.
            const duplicateIds = response;

            if (duplicateIds.length > 0) {
                // 중복된 ID가 있으면 사용자에게 알림을 표시합니다.
                alert("다음 사원 ID들은 이미 사용 중입니다: " + duplicateIds.join(", "));
                return;
            } else {
                // 중복이 아닌 경우에만 행을 추가
                // 여기서부터 행 추가 코드를 작성합니다.

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
                            var empValue = {
                                employeeId: empId,
                                employeeName: empName,
                                employeeStatusCode: empStatusCode,
                                employeeTypeCode: empTypeCode,
                                employeePwd: empPwd
                            };
                            employee.push(empValue);
                        } else {
                            alert("입력칸 모두 작성해 주세요");
                            return rowCount;
                        }
                    }
                    console.log("insert의 employee", employee);
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
	
		if ((rowCount === 0 || (rowCount > 0 && employee.length > 0)) && (deletedEmployeeIds.length >= 0 || updatedEmployeeInfo.length >= 0)) {
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

				//Insert 오류나고 Update나 Delete 동시실행시 response가 0일때 테이블 데이터 유지
//				if (response.length === 0) {
//					console.log("rowCount", rowCount);
//					return;
//				} else{
					//정상 실행 시
					rowCount = 0;

					var empTypeSelect = document.querySelector("select[name='empTypeList']");
					var empStatusSelect = document.querySelector("select[name='empStatusList']");

					$('#empTable > tbody').empty();

					for (var i = 0; i < response.length; i++) {
						var addTableRow = "<tr>" +
							"<td><input type='checkbox' name='empCheckbox'></td>" +
							"<td><span name='empStatus'>E</span></td>" +
							"<td name='employeeId'>" + response[i].employeeId + "</td>" +
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

						addTableRow += employeeTypeCell + employeeStatusCell + "<td></td>" + "</tr>";

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


}




const cells = document.querySelectorAll('td[name="employeeName"], td[name="employeeType"], td[name="employeeStatus"]');

cells.forEach((cell) => {
	cell.addEventListener('click', function() {
		handleClick(this);
	});
});

function handleClick(element) {
	const hiddenBox = element.closest('tr').querySelector("span[name='empStatus']");
	hiddenBox.textContent = 'U';

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
						"<td><span name='empStatus'>E</span></td>" +
						"<td name='employeeId'>" + response[i].employeeId + "</td>" +
						"<td name='employeeName' contenteditable='true' onclick='handleClick(this)'>" + response[i].employeeName + "</td>"; 
 						
						//사원유형 <td>
               			var employeeTypeCell = "<td onclick='handleClick(this)' name='employeeType'>" +
                    							"<span class='text' name='empType'>" + response[i].employeeType + "</span>";
                		
                		employeeTypeCell += empTypeSelect.outerHTML; // 'empTypeSelect'를 새 셀에 추가합니다..
                		employeeTypeCell += "</td>";

                		//사원상태 <td>
                		var employeeStatusCell = "<td onclick='handleClick(this)' name='employeeStatus'>" +
                    							"<span class='text' name='empStatus'>" + response[i].employeeStatus + "</span>";
                		
                		employeeStatusCell += empStatusSelect.outerHTML; // 'empStatusSelect'를 새 셀에 추가합니다.
                		employeeStatusCell += "</td>";

						

                		addTableRow += employeeTypeCell + employeeStatusCell + "<td></td>" + "</tr>";

					$('#empTable > tbody').append(addTableRow);
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