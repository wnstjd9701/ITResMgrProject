package kr.co.kcc.itmgr.domain.resinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resinfo.dao.IResInfoRepository;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfoDetailDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResInfoService implements IResInfoService {
	
	private final IResInfoRepository resInfoRepository;
	
	@Override
	public List<ResInfo> selectAllResInfo(int page) {
		int start = (page-1)*10+1;
		return resInfoRepository.selectAllResInfo(start,start+9);
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
	public List<InstallPlace> selectResInstallPlace(int page) {
		int start = (page-1)*10+1;
		return resInfoRepository.selectResInstallPlace(start,start+9);
	}

	@Override
	public List<ResClass> selectAllResClass() {
		return resInfoRepository.selectAllResClass();
	}

	@Override
	public ResInfo selectResInfoDetail(String resName) {
		return resInfoRepository.selectResInfoDetail(resName);
	}

	@Override
	public List<ResInfo> selectMappingAddItem(String resClassId) {
		return resInfoRepository.selectMappingAddItem(resClassId);
	}

	@Override
	public int countOfResInfo() {
		return resInfoRepository.countOfResInfo();
	}

	@Override
	@Transactional
	public void insertAddItemValueInResInfo(List<String> resSerialIdList, List<String> addItemSnList, List<String> resDetailValueList) {
		for(int i=0; i<resSerialIdList.size(); i++) {
			resInfoRepository.insertAddItemValueInResInfo(resSerialIdList.get(i), addItemSnList.get(i), resDetailValueList.get(i));
		}
	}

	@Override
	public List<ResInfo> selectAddItemValueInResInfo(String resSerialId) {
		return resInfoRepository.selectAddItemValueInResInfo(resSerialId);
	}

	@Override
	public void updateResInfo(ResInfo resInfo) {
		resInfoRepository.updateResInfo(resInfo);
		
	}

	@Override
	public void deleteAddItemValueInResInfo(String resSerialId) {
		resInfoRepository.deleteAddItemValueInResInfo(resSerialId);
	}

	@Override
	public int CountOfAddItemValueInResInfo(String resSerialId) {
		return resInfoRepository.CountOfAddItemValueInResInfo(resSerialId);
	}

	@Override
	public int countOfInstallPlace() {
		return resInfoRepository.countOfInstallPlace();
	}
	

}
