package kr.co.kcc.itmgr.domain.resinfo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpInfo;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfoDTO;

public interface IResInfoService {
	List<ResInfo> selectAllResInfo(int page,ResInfo resInfo);
	int countOfResInfo(ResInfo resInfo);//자원갯수
	int countOfInstallPlace();
	
	List<ResInfo> searchResInfoByResClass();
	List<ResInfo> searchResInfo(ResInfo resInfo); //검색결과조회
	
	List<ResClass> selectAllResClass(); //자원분류리스트 조회
	
	void insertResInfo(ResInfo resInfo); //자원입력
	void updateResInfo(ResInfo resInfo);
	
	List<CommonCodeDetail> selectResStatusCode(String codeGroupId); //자원상태코드 리스트 불러오기
	List<InstallPlace> selectResInstallPlace(int page); //자원설치장소 리스트 불러오기
	
	ResInfo selectResInfoDetail(String resName);
	
	List<ResInfo> selectMappingAddItem(String resClassId);
	
	void insertAddItemValueInResInfo(List<String> resSerialIdList, List<String> addItemSnList, List<String> resDetailValueList); //매핑된 부가항목 값 넣기
	List<ResInfo> selectAddItemValueInResInfo(String resSerialId);
	void deleteAddItemValueInResInfo(String resSerialId);
	
	List<ResInfo> selectIpInResInfo(String resSerialId);
	void insertIpInResInfo(List<String> resSerialIdList, List<Integer> ipSnList, List<String> ipTypeCodeList);
	void updateIpInResInfo(List<String> resSerialIdList,List<Integer> ipSnList,List<String> ipTypeCodeList);
	void deleteIpInResInfo(int ipSn);
	List<IpInfo> selectAllIpInfoList(int page);
	List<ResInfo> selectIpMappingInResInfo(String resSerialId);
	
	int CountOfAddItemValueInResInfo(String resSerialId);
	int CountOfIpList();
	
	List<ResInfo> setFlagResInfoList(List<ResInfo> resInfo); //자원분류 flag설정
	<T extends ResInfoDTO> List<T> setFlag(List<T> code);
	
	List<Integer> existingIpSnList(String resSerialId);
}
