package kr.co.kcc.itmgr.domain.resinfo.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;

public interface IResInfoService {
	List<ResInfo> selectAllResInfo();
	List<ResInfo> searchResInfoByResClass();
	List<ResInfo> searchResInfo(ResInfo resInfo); //검색결과조회
	
	void insertResInfo(ResInfo resInfo); //자원입력
	
	List<CommonCodeDetail> selectResStatusCode(String codeGroupId); //자원상태코드 리스트 불러오기
}
