package kr.co.kcc.itmgr.domain.commoncode.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCode;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;

@Repository
@Mapper
public interface ICommonCodeRepository {
	List<CommonCode> selectAllCommonCode(); // 모든 공통 코드 가져오기
	List<CommonCodeDetail> selectAllCommonCodeDetail(); // 모든 상세 코드 가져오기
	
	int checkIfCodeGroupIdExists(String codeGroupId); // 코드 그룹 존재하는지 확인
	
	List<CommonCode> selectCommonCodeBySearch(String useYn, String keyword); // 공통 코드 검색
	List<CommonCodeDetail> selectCommonCodeDetailBySearch(String useYn, String keyword); // 상세 코드 검색
	
	List<CommonCodeDetail> selectCommonCodeDetailByCodeGroupId(String codeGroupId); // 공통 코드 그룹 ID로 상세 코드 가져오기
	
	int insertCommonCode(List<CommonCode> commonCode); // 공통 코드 생성
	int updateCommonCode(CommonCode commonCode); // 공통 코드 업데이트
}
