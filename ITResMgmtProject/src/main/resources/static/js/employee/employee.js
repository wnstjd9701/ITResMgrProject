var rowCount = 0;


//행추가 버튼
function addRow() {
	var table = document.getElementById("empTable");
	var newRow = table.insertRow();
	newRow.setAttribute("data-row-id", rowCount);

	var newCell0 = newRow.insertCell(0);
	newCell0.innerHTML = "<input type='checkbox'>";

	var newCell1 = newRow.insertCell(1);
	newCell1.innerHTML = "<input type='text' id='empId" + rowCount + "' size='10'>";

	var newCell2 = newRow.insertCell(2);
	newCell2.innerHTML = "<input type='text' id='empName" + rowCount + "' size='10'>";

	var newCell3 = newRow.insertCell(3);
	newCell3.innerHTML = "<select id='empTypeCode" + rowCount + "'>"
		+ "<option value=''>선택</option>"
		+ "<option value='EMT001'>시스템관리자</option>"
		+ "<option value='EMT002'>IT자원관리자</option>"
		+ "</select>";


	var newCell4 = newRow.insertCell(4);
	newCell4.innerHTML = "<select id='empStatusCode" + rowCount + "'>"
		+ "<option value=''>선택</option>"
		+ "<option value='EMS001'>재직중</option>"
		+ "<option value='EMS002'>휴직중</option>"
		+ "<option value='EMS003'>퇴직</option>"
		+ "</select>";
	rowCount++;
}

//행추가저장 버튼
function addEmp() {
	var employee = new Array();
	for (var i = 0; i < rowCount; i++) {
		var empId = document.getElementById('empId' + i).value;
		var empName = document.getElementById('empName' + i).value;
		var empStatusCode = document.getElementById('empStatusCode' + i).value;
		var empTypeCode = document.getElementById('empTypeCode' + i).value;

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
	$.ajax({
		url: '/employeeview',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(employee),
		success: function(response) {
			console.log("response", response)
			rowCount = 0;

			$('#empTable > tbody').empty();

			for (var i = 0; i < response.length; i++) {
				var addTableRow = "<tr>" +
					"<td><input type='checkbox' name='emp_checkbox'></td>" +
					"<td>" + response[i].employeeId + "</td>" +
					"<td>" + response[i].employeeName + "</td>" +
					"<td>" + response[i].employeeType + "</td>" +
					"<td>" + response[i].employeeStatus + "</td>" +
					"</tr>";
					
				$('#empTable > tbody').append(addTableRow);
			}
			return;
		},
		error: function(request, error) {
			alert("메시지:" + request.responseText + "\n" + "에러:" + error);
		}
	});
}

// 행바로삭제 버튼(바로 사용여부 : N)
function deleteRow() {
	var employeeIdList = new Array();

	$('input[name="emp_checkbox"]:checked').each(function() {
		var empId = $(this).closest('tr').find('td:eq(1)').text();
		employeeIdList.push(empId);
	});
	
	console.log("employeeId", employeeIdList);

	$.ajax({
		url: '/employeeview',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(employeeIdList),
		success: function(response) {
			console.log("response", response)
			rowCount = 0;

			$('#empTable > tbody').empty();

			for (var i = 0; i < response.length; i++) {
				var addTableRow = "<tr>" +
					"<td><input type='checkbox' name='emp_checkbox'></td>" +
					"<td>" + response[i].employeeId + "</td>" +
					"<td>" + response[i].employeeName + "</td>" +
					"<td>" + response[i].employeeType + "</td>" +
					"<td>" + response[i].employeeStatus + "</td>" +
					"</tr>";

				$('#empTable > tbody').append(addTableRow);
			}
			return;
		},
		error: function(request, error) {
			alert("메시지:" + request.responseText + "\n" + "에러:" + error);
		}
	});
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

function updateRow(){
	var updateEmployee = [];
	
	const tbody = document.querySelector("#empTable tbody");

	// tbody 내의 모든 행을 선택합니다.
	const rows = tbody.querySelectorAll("tr");

	// 각 행에 대해 반복합니다.
	rows.forEach(function(row) {
    // 행 내의 각 셀에 접근하려면 다음과 같이 사용할 수 있습니다:
    const cells = row.querySelectorAll("td");
	for (var i = 0; i < rows; i++) {
		var empId = document.getElementById('empId' + i).value;
		var empName = document.getElementById('empName' + i).value;
		var empStatusCode = document.getElementById('empStatusCode' + i).value;
		var empTypeCode = document.getElementById('empTypeCode' + i).value;
		
		if (empId && empName && empStatusCode && empTypeCode) {
			var updateEmpValue = {
				employeeId: empId,
				employeeName: empName,
				employeeStatusCode: empStatusCode,
				employeeTypeCode: empTypeCode
			};
			updateEmployee.push(updateEmpValue);
		}
	}
	
	
    // 각 셀에 대한 조작을 수행합니다.
    cells.forEach(function(cell) {
        // cell에 대한 조작을 수행합니다.
        // 예를 들어 cell.textContent를 사용하여 셀 내의 텍스트를 가져올 수 있습니다.
        const cellText = cell.textContent;
        
        // 추가적인 작업을 수행하거나 조작할 수 있습니다.
    });
});
	
}



/*function hideRow() {
	var table = document.getElementById("empTable");
	var rows = table.getElementsByTagName('tr');
	var employeeIdArray = [];


	for (var i = 1; i < rows.length; i++) {
		var empIdArr = new Object();
		var row = rows[i];
		var checkboxes = row.querySelectorAll('[name="emp_checkbox"]');

		if (checkboxes.length > 0) {
			var checkbox = checkboxes[0];
			if (checkbox.checked) {
				row.style.display = 'none';
				var employeeIdCell = row.querySelector("#employeeId");
				console.log(employeeIdCell)
				empIdArr = employeeIdCell.textContent;
				employeeIdArray.push(empIdArr);


			}
			console.log("employeeIdArray : " + employeeIdArray);
		}
	}

	return employeeIdArray;

}*/





//행 저장
/*function saveList() {
	var employeeIdArray = hideRow();
	var employee = new [];


	for (var i = 0; i < rowCount; i++) {
		var emp = new Object();
		emp.employeeId = document.getElementById('empId' + i).value;
		emp.employeeName = document.getElementById('empName' + i).value;
		emp.employeeStatusCode = document.getElementById('empStatusCode' + i).value;
		emp.employeeTypeCode = document.getElementById('empTypeCode' + i).value;

		employee.push(emp);
	}

	var temp = new Object();
	temp.employeeIdArray = employeeIdArray;
	employee.push(temp);

	console.log("employee", employee)

	$.ajax({
		url: '/employeeview',
		type: 'POST',
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(employee),
		success: function(response) {

			console.log("response", response)
			rowCount = 0;



			$('#empTable > tbody').empty();

			for (var i = 0; i < response.length; i++) {
				var addTableRow = "<tr>" +
					"<td><input type='checkbox' name='emp_checkbox'></td>" +
					"<td>" + response[i].employeeId + "</td>" +
					"<td>" + response[i].employeeName + "</td>" +
					"<td>" + response[i].employeeType + "</td>" +
					"<td>" + response[i].employeeStatus + "</td>" +
					"</tr>";

				$('#empTable > tbody').append(addTableRow);
			}
			console.log(" 결과 employee", employee)

			return;


		},
		error: function(request, error) {
			alert("메시지:" + request.responseText + "\n" + "에러:" + error);
		}
	});

}*/

 