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

	//부가정보 개수
	int countAddItem();
	
	//검색
	List<AddItem> selectSearchAddItem(String useYN, String searchText);

}
