package kr.co.kcc.itmgr.domain.resinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
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

	@Override
	public void insertResInfo(ResInfo resInfo) {
		resInfoRepository.insertResInfo(resInfo);
		
	}

	@Override
	public List<CommonCodeDetail> selectResStatusCode(String codeGroupId) {
		return resInfoRepository.selectResStatusCode(codeGroupId);
	}

	@Override
	public List<InstallPlace> selectResInstallPlace() {
		return resInfoRepository.selectResInstallPlace();
	}

	@Override
	public List<ResClass> selectAllResClass(String resClassName) {
		return resInfoRepository.selectAllResClass(resClassName);
	}

	@Override
	public ResInfo selectResInfoDetail(String resName) {
		return resInfoRepository.selectResInfoDetail(resName);
	}

}
