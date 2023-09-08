package kr.co.kcc.itmgr.domain.commoncode.service;

import java.util.List;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCode;

public interface ICommonCodeService {
	List<CommonCode> selectAllCommonCode(); // 모든 공통 코드 가져오기
}
