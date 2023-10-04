package kr.co.kcc.itmgr.domain.resclass.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.kcc.itmgr.domain.additem.model.AddItem;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resclass.model.ResClassAddItem;


@Repository
@Mapper
public interface IResClassRepository {
	List<ResClass> selectAllResClass(); //메뉴트리
	List<Map<Object, Object>> numberOfResByResClass(); //자원분류별 자원의 개수
	List<ResClass> selectResClassByResClassName(String resClassName);//자원상세보기
	
	List<ResClass> selectResClassByLevel(); //lv별 자원분류조회
	
	void insertResClass(ResClass resClass);//자원분류 등록
	int countOfAddItemList(AddItem addItem);
//	List<AddItem> selectAddItemInResClass(@Param("start")int start,@Param("end")int end); //부가정보 조회
	
	List<AddItem> selectAddItemInResClass(); //부가항목리스트 조회
	int insertAddItemToResClass(@Param("resClass")ResClass resClass); //부가항목 매핑
	
	int updateResClass(ResClass resclass);	//자원분류 업데이트
	int deleteAddItemInResClass(@Param("resClass")ResClass resClass);	//매핑된 부가항목 삭제

}
