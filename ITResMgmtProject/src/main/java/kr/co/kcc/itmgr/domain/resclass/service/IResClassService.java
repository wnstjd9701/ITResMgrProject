package kr.co.kcc.itmgr.domain.resclass.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.kcc.itmgr.domain.resclass.model.ResClass;

public interface IResClassService {
	List<Map<String, String>> selectAllResClass();
}
