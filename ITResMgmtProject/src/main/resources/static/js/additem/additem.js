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
	newCell3.innerHTML = "<input type='text' id='insertName" + rowCount + "' size='15' placeholder='반드시 입력해주세요.'>";

	var newCell4 = newRow.insertCell(4);
	newCell4.innerHTML = "<input type='text' id='addItemDesc" + rowCount + "' size='40'>";

	var newCell5 = newRow.insertCell(5);
	newCell5.innerHTML = "<span>Y</span>";

	rowCount++;
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

	if (addItemStatus.textContent === 'D') {
		return;
	} else {
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

}

//저장버튼
function addItemSaveAll() {
	// 새로 추가될 행의 부가항목명들을 배열에 저장
	const newAddItemNames = [];
	for (let i = 0; i < rowCount; i++) {
		const newAddItemName = document.getElementById('insertName' + i).value;
		if (newAddItemName !== null && newAddItemName !== '') { // empId가 null 또는 빈 문자열이 아닌 경우에만 배열에 추가
			newAddItemNames.push(newAddItemName);
		}
	}
	console.log("newAddItemNames", newAddItemNames);

	$.ajax({
		url: '/checkDuplicateAddItemNames',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(newAddItemNames), // 새로 추가될 행의 사원 ID 배열
		success: function(response) {
			// 중복된 Name 목록을 서버로부터 받아옵니다.
			const duplicateNames = response;
			if (duplicateNames.length > 0) {
				// 중복된 ID가 있으면 사용자에게 알림을 표시합니다.
				alert("중복된 부가항목명 : " + duplicateNames.join(", ") + "입니다. 다시 입력해 주세요.");
				return;
			} else {
				//Insert
				if (rowCount > 0) {
					var insertAddItems = new Array();
					for (var i = 0; i < rowCount; i++) {
						var addItemName = document.getElementById('insertName' + i).value;
						console.log("addItemName", addItemName);
						var addItemDesc = document.getElementById('addItemDesc' + i).value;


						if (addItemName) {
							var addItemValue = {
								addItemName: addItemName,
								addItemDesc: addItemDesc
							};
							insertAddItems.push(addItemValue);
						} else {
							alert("입력칸 모두 작성해 주세요");
							return rowCount;
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
						// 해당 숨겨진 필드의 부모 행을 찾아서 addItemSn 값을 가져옵니다.
						var row = spanFields[i].closest("tr");
						var addItemSn = row.querySelector('[name="addItemSn"]').textContent;
						console.log("삭제할 addItemSn : ", addItemSn);
						deletedAddItems.push(addItemSn);
					}
				}

				//Update
				var spanFields2 = document.getElementsByName("status");
				var updateAddItems = [];
				for (var i = 0; i < spanFields2.length; i++) {
					var spanFields2Value = spanFields2[i].textContent;
					if (spanFields2Value === 'U') {
						var row = spanFields2[i].closest("tr");
						var u_addItemSn = row.querySelector('[name="addItemSn"]').textContent;
						var u_addItemDesc = row.querySelector('[name="addItemDesc"]').textContent;

						var updateAddItem = {
							addItemSn: u_addItemSn,
							addItemDesc: u_addItemDesc
						};
						updateAddItems.push(updateAddItem);
					}
				}
				console.log("updateAddItems", updateAddItems);

				var requestData = {
					insertAddItems: insertAddItems,
					deletedAddItems: deletedAddItems,
					updateAddItems: updateAddItems
				};

				console.log("requestData", requestData);

				$.ajax({
					url: '/save/additem',
					type: 'POST',
					contentType: 'application/json',
					data: JSON.stringify(requestData),
					success: function(response) {
						rowCount = 0;
						console.log("response", response)

						$('#addItemTable > tbody').empty();

						// 검색 결과가 있는 경우
						for (var i = 0; i < response.length; i++) {
							var addTableRow = "<tr>" +
								"<td><input type='checkbox' name='checkBox'></td>" +
								"<td name='addItemStatus'><span name='status'>E</span></td>" +
								"<td name='addItemSn'>" + response[i].addItemSn + "</td>" +
								"<td name='addItemName'>" + response[i].addItemName + "</td>" +
								"<td name='addItemDesc' onclick='handleClick(this)'>" + response[i].addItemDesc + "</td>" +
								"<td name='useYN'>Y</td>" +
								"</tr>";

							$('#addItemTable > tbody').append(addTableRow);
						}

						return;
					},
					error: function(xhr, status, error) {
						// 오류 발생 시 처리할 내용을 여기에 작성
						alert("오류가 발생했습니다. 다시 시도해주세요.");
					}
				});
			}
		}
	})
}


//조회
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
				var noResultRow = "<tr><td colspan='6'>검색결과가 없습니다</td></tr>";
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

function uploadFile() {
	// 파일 선택 필드를 클릭하여 파일 선택 대화 상자 열기
	document.getElementById('fileInput').click();
}

// 파일 선택 이벤트 처리
document.getElementById('fileInput').addEventListener('change', handleFileSelect);

function handleFileSelect(event) {
	const fileInput = event.target;
	const file = fileInput.files[0];
	if (file) {
		const formData = new FormData();
		formData.append('file', file);

		$.ajax({
			url: '/checkDuplicateAddItemNamesExcel',
			type: 'POST',
			contentType: false,
			processData: false,
			data: formData,
			success: function(response) {

				const duplicateNames = response;

				if (duplicateNames.length > 0) {
					// 중복된 ID가 있으면 사용자에게 알림을 표시합니다.
					alert("중복된 부가항목명 " + duplicateNames.join(", ") + "입니다. 다시 입력해 주세요.");
					return;
				} else {
					$.ajax({
						url: '/excel/upload',
						type: 'POST',
						contentType: false,
						processData: false,
						data: formData,
						success: function(response) {
							alert("엑셀 업로드가 완료되었습니다.");
							rowCount = 0;
							console.log("response", response)

							$('#addItemTable > tbody').empty();

							if (response.length === 0) {
								$('#addItemTable > tbody').append(noResultRow);
							} else {
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
						},
						error: function() {
							alert("엑셀 업로드에 실패했습니다. 파일을 다시 선택해주세요.");
						}
					});
				}


			},
			error: function() {
				alert("엑셀 업로드에 실패했습니다. 파일을 다시 선택해주세요.");
			}
		});

	}
}
