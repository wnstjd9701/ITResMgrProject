package kr.co.kcc.itmgr.domain.resclass.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import kr.co.kcc.itmgr.domain.additem.model.AddItem;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resclass.model.ResClassDTO;

public interface IResClassService {
	List<ResClass> selectAllResClass(); //메뉴트리
	List<Map<Object, Object>> numberOfResByResClass(); //자원분류별 자원의 개수
	List<Map<Object, Object>> numberOfResByResClass2(); //자원분류별 자원의 개수
	List<Map<Object, Object>> numberOfResByResClass3(); //자원분류별 자원의 개수
	List<ResClass> selectResClassByResClassName(String resClassName);//자원상세보기
	
	List<ResClass> selectResClassByLevel(); //lv별 자원분류조회

	List<ResClass> selectResClassAndAddItemList();

	void insertResClass(ResClass resClass);
	
	int countOfAddItemList();
	
	List<AddItem> selectAddItemInResClass(int page); //부가정보 조회
	int insertAddItemToResClass(List<ResClass> resclass);
	
	int updateResClass(ResClass resclass);	//자원분류 업데이트
	int deleteAddItemInResClass(ResClass resclass);	//매핑된 부가항목 삭제
	
//	List<AddItem> setFlagAddItemList(List<AddItem> addItem); //부가항목리스트 flag설정
	List<ResClass> setFlagResClassList(List<ResClass> resClass); //자원분류 flag설정	
	<T extends ResClassDTO> List<T> setFlag(List<T> code);
}
