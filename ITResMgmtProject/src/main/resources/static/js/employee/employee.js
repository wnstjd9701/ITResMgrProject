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
	newCell3.innerHTML = "<select id='empStatusCode" + rowCount + "'>"
		+ "<option value=''>선택</option>"
		+ "<option value='EMS001'>재직중</option>"
		+ "<option value='EMS002'>휴직중</option>"
		+ "<option value='EMS003'>퇴직</option>"
		+ "</select>";

	var newCell4 = newRow.insertCell(4);
	newCell4.innerHTML = "<select id='empTypeCode" + rowCount + "'>"
		+ "<option value=''>선택</option>"
		+ "<option value='EMT002'>IT자원관리자</option>"
		+ "<option value='EMT001'>시스템관리자</option>"
		+ "</select>";
	rowCount++;
}
// 행삭제 버튼
function hideRow() {
	var table = document.getElementById("empTable");
	var rows = table.getElementsByTagName('tr');


	var employeeIdArray = [];
	//var employeeIdArray = new Array();

	for (var i = 1; i < rows.length; i++) {
		var empIdArr = new Object();
		var row = rows[i];
		var checkboxes = row.querySelectorAll('[name="emp_chekbox"]');

		if (checkboxes.length > 0) {
			var checkbox = checkboxes[0];
			if (checkbox.checked) {
				row.style.display = 'none';
				var employeeIdCell = row.querySelector("#employeeId");
				console.log(employeeIdCell)
				//var employeeIdValue = employeeIdCell.textContent;
				empIdArr = employeeIdCell.textContent;

				employeeIdArray.push(empIdArr);
				//employeeIdArray.push(employeeIdValue);
				//console.log("employeeId 값: " + employeeIdValue);
			}
			console.log("employeeIdArray : " + employeeIdArray);
		}
	}
	
	
	return employeeIdArray;
}


//행 저장
function saveList() {
	var employeeIdArray = hideRow();
	var employee = new Array();


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
			//alert("성공	");

			console.log("response", response)
			rowCount = 0;

			$('#empTable > tbody').empty();

			for (var i = 0; i < response.length; i++) {
				// table 행 추가
				var addTableRow = "<tr>" +
					"<td><input type='checkbox' name='emp_chekbox'></td>" +
					"<td>" + response[i].employeeId + "</td>" +
					"<td>" + response[i].employeeName + "</td>" +
					"<td>" + response[i].employeeStatus + "</td>" +
					"<td>" + response[i].employeeType + "</td>" +
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


}