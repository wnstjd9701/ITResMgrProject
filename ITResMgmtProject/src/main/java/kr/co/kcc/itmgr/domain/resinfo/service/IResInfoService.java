package kr.co.kcc.itmgr.domain.resinfo.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;

public interface IResInfoService {
	List<ResInfo> selectAllResInfo();
	List<ResInfo> searchResInfoByResClass();
	List<ResInfo> searchResInfo(ResInfo resInfo); //검색결과조회
}
