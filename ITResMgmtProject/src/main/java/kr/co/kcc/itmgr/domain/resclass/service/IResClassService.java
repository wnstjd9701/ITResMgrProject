package kr.co.kcc.itmgr.domain.resclass.service;

import java.util.List;
import java.util.Map;

import kr.co.kcc.itmgr.domain.resclass.model.ResClass;


public interface IResClassService {
	List<ResClass> selectAllResClass();
	List<Map<Object, Object>> numberOfResByResClass();
	List<ResClass> selectResClass(int level);
}
