package kr.co.kcc.itmgr.domain.commoncode.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCode;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;

public interface ICommonCodeService {
	List<CommonCode> selectAllCommonCode(); // 모든 공통 코드 가져오기
	List<CommonCodeDetail> selectAllCommonCodeDetail(); // 모든 상세 코드 가져오기
	
	int checkIfCodeGroupIdExists(String codeGroupId); // 코드 그룹 존재하는지 확인
	
	List<CommonCode> selectCommonCodeBySearch(String useYn, String keyword); // 공통 코드 검색
	List<CommonCodeDetail> selectCommonCodeDetailBySearch(String useYn, String keyword); // 상세 코드 검색
	
	List<CommonCodeDetail> selectCommonCodeDetailByCodeGroupId(String codeGroupId); // 공통 코드 그룹 ID로 상세 코드 가져오기
}
