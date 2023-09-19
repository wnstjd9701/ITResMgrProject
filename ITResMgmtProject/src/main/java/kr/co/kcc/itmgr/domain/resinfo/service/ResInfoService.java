package kr.co.kcc.itmgr.domain.resinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.resclass.dao.IResClassRepository;
import kr.co.kcc.itmgr.domain.resinfo.dao.IResInfoRepository;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResInfoService implements IResInfoService {
	
	private final IResInfoRepository resInfoRepository;
	
	@Override
	public List<ResInfo> selectAllResInfo() {
		return resInfoRepository.selectAllResInfo();
	}

	@Override
	public List<ResInfo> searchResInfoByResClass() {
		return resInfoRepository.searchResInfoByResClass();
	}

	@Override
	public List<ResInfo> searchResInfo(ResInfo resInfo) {
		return resInfoRepository.searchResInfo(resInfo);
	}

}
