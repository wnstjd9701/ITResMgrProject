package kr.co.kcc.itmgr.domain.commoncode.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.commoncode.dao.ICommonCodeRepository;
import kr.co.kcc.itmgr.domain.commoncode.model.CommonCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommonCodeService implements ICommonCodeService{

	private final ICommonCodeRepository commonCodeRepository;
	
	@Override
	public List<CommonCode> selectAllCommonCode() {
		return commonCodeRepository.selectAllCommonCode();
	}

}
