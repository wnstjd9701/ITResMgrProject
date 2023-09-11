package kr.co.kcc.itmgr.domain.commoncode.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.commoncode.dao.ICommonCodeRepository;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCode;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonCodeService implements ICommonCodeService{

	private final ICommonCodeRepository commonCodeRepository;
	
	// 모든 공통 코드 가져오기
	@Override
	public List<CommonCode> selectAllCommonCode() {
		return commonCodeRepository.selectAllCommonCode();
	}

	// 모든 상세 코드 가져오기
	@Override
	public List<CommonCodeDetail> selectAllCommonCodeDetail() {
		return commonCodeRepository.selectAllCommonCodeDetail();
	}

	// 코드 그룹 존재하는지 확인
	@Override
	public int checkIfCodeGroupIdExists(String codeGroupId) {
		return commonCodeRepository.checkIfCodeGroupIdExists(codeGroupId);
	}

	// 공통 코드 검색 
	@Override
	public List<CommonCode> selectCommonCodeBySearch(String useYn, String keyword) {
		return commonCodeRepository.selectCommonCodeBySearch(useYn, keyword);
	}
	
	// 상세 코드 검색
	@Override
	public List<CommonCodeDetail> selectCommonCodeDetailBySearch(String useYn, String keyword) {
		return commonCodeRepository.selectCommonCodeDetailBySearch(useYn, keyword);
	}
	
	// 공통 코드 그룹 ID로 상세 코드 가져오기
	@Override
	public List<CommonCodeDetail> selectCommonCodeDetailByCodeGroupId(String codeGroupId) {
		return commonCodeRepository.selectCommonCodeDetailByCodeGroupId(codeGroupId);
	}

}
