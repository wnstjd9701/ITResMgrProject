package kr.co.kcc.itmgr.domain.commoncode.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCode;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonDTO;

public interface ICommonCodeService {
	List<CommonCode> selectAllCommonCode(); // 모든 공통 코드 가져오기
	List<CommonCodeDetail> selectAllCommonCodeDetail(); // 모든 상세 코드 가져오기
	
	int checkIfCodeGroupIdExists(String codeGroupId); // 코드 그룹 존재하는지 확인
	int checkIfDetailCodeNameExists(String codeGroupId, String detailCode); // 같은 코드 그룹 내에 상세 코드명 중복 체크
	
	List<CommonCode> setFlagCommonCode(List<CommonCode> commonCode); // 공통 코드 Flag 생성
	List<CommonCodeDetail> setFlagCommonCodeDetail(List<CommonCodeDetail> commonCodeDetail); // 상세 코드 Flag 생성
	<T extends CommonDTO> List<T> setFlag(List<T> code);
	
	List<CommonCode> selectCommonCodeBySearch(String useYn, String keyword); // 공통 코드 검색
	List<CommonCodeDetail> selectCommonCodeDetailBySearch(String useYn, String keyword); // 상세 코드 검색
	
	List<CommonCodeDetail> selectCommonCodeDetailByCodeGroupId(String codeGroupId); // 공통 코드 그룹 ID로 상세 코드 가져오기
	
	// 공통 코드 저장
	int insertCommonCode(List<CommonCode> commonCode); // 공통 코드 생성
	int updateCommonCode(CommonCode commonCode); // 공통 코드 수정
	
	// 상세 코드 저장
	int insertCommonCodeDetail(List<CommonCodeDetail> commonCodeDetail); // 상세 코드 생성
	int updateCommonCodeDetail(CommonCodeDetail commonCodeDetail); // 상세 코드 수정, 삭제
}
