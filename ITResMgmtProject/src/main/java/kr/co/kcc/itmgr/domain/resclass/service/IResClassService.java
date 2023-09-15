package kr.co.kcc.itmgr.domain.resclass.service;

import java.util.List;
import java.util.Map;

import kr.co.kcc.itmgr.domain.resclass.model.ResClass;


public interface IResClassService {
	List<ResClass> selectAllResClass(); //메뉴트리
	List<Map<Object, Object>> numberOfResByResClass(); //자원분류별 자원의 개수
	List<ResClass> selectResClassByResClassName(String resClassName);//자원상세보기
}
