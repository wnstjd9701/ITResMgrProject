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
}
