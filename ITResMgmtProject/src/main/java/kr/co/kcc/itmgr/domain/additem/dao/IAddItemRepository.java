package kr.co.kcc.itmgr.domain.additem.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.additem.model.AddItem;

@Repository
@Mapper
public interface IAddItemRepository {
	//부가정보 전체조회
	List<AddItem> selectAllAddItem();

	//검색
	List<AddItem> selectSearchAddItem(String useYN, String searchText);
	
	//부가정보 등록
	void insertAddItem(AddItem addItem);
	
	//부가정보 등록(엑셀파일)
	void insertAddItem(String addItemName, String addItemDesc);

	//부가정보 삭제(사용여부:N)
	void deleteAddItemByUseYN(int addItemSn);

	//부가정보 수정
	void updateAddItemDesc(List<AddItem> updateAddItems);
	

}
