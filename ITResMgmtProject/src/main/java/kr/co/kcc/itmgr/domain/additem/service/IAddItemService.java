package kr.co.kcc.itmgr.domain.additem.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.additem.model.AddItem;

public interface IAddItemService {
	//부가정보 전체조회
	List<AddItem> selectAllAddItem();
	
	//부가정보 사용여부:Y 전체조회
	List<AddItem> selectUseYAddItem();

	//검색
	List<AddItem> selectSearchAddItem(String useYN, String searchText);

	//부가정보 등록
	void insertAddItem(List<AddItem> insertAddItems);

	//부가정보 삭제(사용여부:N)
	void deleteAddItemByUseYN(int addItemSn);
	
	//부가정보 수정
	void updateAddItemDesc(List<AddItem> updateAddItems);

	//부가정보 엑셀파일 등록
	void insertAddItemExcel(String addItemName, String addItemDesc);
}
