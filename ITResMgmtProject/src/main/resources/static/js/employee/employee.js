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

//행 저장
function saveList() {
    for (var i = 0; i < rowCount; i++) {
        var employeeId = document.getElementById('empId' + i).value;
        var employeeName = document.getElementById('empName' + i).value;
        var employeeStatusCode = document.getElementById('empStatusCode' + i).value;
        var employeeTypeCode = document.getElementById('empTypeCode' + i).value;
}
        $.ajax({
            url: '/employeeview',
            type: 'POST',
            data: {
                employeeId: employeeId,
                employeeName: employeeName,
                employeeStatusCode: employeeStatusCode,
                employeeTypeCode: employeeTypeCode
            },
            success: function () {
                window.location.href = '/employeeview';
            },
            error: function (request, error) {
                alert("메시지:" + request.responseText + "\n" + "에러:" + error);
            }
        });
    }


// 행삭제 버튼
function hideRow(){
	 var table = document.getElementById("empTable");

    var rows = table.getElementsByTagName('tr');
    for (var i = 1; i < rows.length; i++) {
        var row = rows[i];
        var checkbox = row.getElementsByTagName('input')[0];

        if (checkbox.checked) {
            row.style.display = 'none';
        }
    }
}
