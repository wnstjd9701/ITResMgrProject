package kr.co.kcc.itmgr.domain.resclass.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;

public interface IResClassService {
	List<ResClass> selectAllResClass();
	List<ResInfo> numberOfResByResClass();
}
