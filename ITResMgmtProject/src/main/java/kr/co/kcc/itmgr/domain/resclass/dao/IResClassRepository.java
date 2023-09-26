package kr.co.kcc.itmgr.domain.resclass.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.additem.model.AddItem;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;


@Repository
@Mapper
public interface IResClassRepository {
	List<ResClass> selectAllResClass(); //메뉴트리
	List<Map<Object, Object>> numberOfResByResClass(); //자원분류별 자원의 개수
	List<ResClass> selectResClassByResClassName(String resClassName);//자원상세보기
	
	List<ResClass> selectResClassByLevel(); //lv별 자원분류조회
	
	void insertResClass(ResClass resClass);//자원분류 등록

	List<AddItem> selectAddItemInResClass(); //부가정보 조회
	void insertAddItemToResClass(@Param("resClassId")String resClassId, @Param("addItemSn") int addItemSn); //부가정보매핑

	
}
