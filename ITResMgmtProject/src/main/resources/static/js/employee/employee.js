//행추가버튼
function addRow() {
	var table = document.getElementById("empTable");

	var newRow = table.insertRow();

	var newCell0 = newRow.insertCell(0);
	newCell0.innerHTML = "<input type='checkbox' id='cell_0'>";

	var newCell1 = newRow.insertCell(1);
	newCell1.innerHTML = "<input type='text' id='empId' size='10'>";

	var newCell2 = newRow.insertCell(2);
	newCell2.innerHTML = "<input type='text' id='empName' size='10'>";

	var newCell3 = newRow.insertCell(3);
	newCell3.innerHTML = "<select id='empStatusCode'> <option value=''>선택</option> <option value='EMS001'>재직중</option> <option value='EMS002'>휴직중</option> <option value='EMS003'>퇴직</option> </select>";

	var newCell4 = newRow.insertCell(4);
	newCell4.innerHTML = "<select id='empTypeCode'> <option value=''>선택</option> <option value='EMT002'>IT자원관리자</option> <option value='EMT001'>시스템관리자</option> </select>";

}

/*
<srcipt>

	
</srcipt>*/

//저장버튼S

//insert값 저장
function saveList() {
	var employeeId = document.getElementById('empId').value;
	var employeeName = document.getElementById('empName').value;
	var employeeStatusCode = document.getElementById('empStatusCode').value;
	var employeeTypeCode = document.getElementById('empTypeCode').value;
	console.log(employeeId);


	$.ajax({
		url: '/employeeview',
		type: 'POST',
		data:
		{
			employeeId: employeeId,
			employeeName: employeeName,
			employeeStatusCode: employeeStatusCode,
			employeeTypeCode: employeeTypeCode
		},
		success: function() {
			window.location.href = '/employeeview';
		},
		error: function(request, error) {
			alert("message:" + request.responseText + "\n" + "error:" + error);
		}
	});
}

