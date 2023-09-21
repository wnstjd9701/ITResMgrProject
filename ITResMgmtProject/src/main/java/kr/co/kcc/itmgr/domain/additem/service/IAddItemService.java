package kr.co.kcc.itmgr.domain.additem.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.additem.model.AddItem;

public interface IAddItemService {
	//부가정보 전체조회
	List<AddItem> selectAllAddItem();

	//부가정보 총개수
	int countAddItem();

	//검색
	List<AddItem> selectSearchAddItem(String useYN, String searchText);
}
