package kr.co.kcc.itmgr.domain.resinfo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import kr.co.kcc.itmgr.domain.commoncode.model.CommonCodeDetail;
import kr.co.kcc.itmgr.domain.installplace.model.InstallPlace;
import kr.co.kcc.itmgr.domain.ipinfo.model.IpInfo;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;

@Repository
@Mapper
public interface IResInfoRepository {
	
	List<ResInfo> selectAllResInfo(@Param("start")int start, @Param("end")int end); //자원정보 모두 찾아오기
	int countOfResInfo();//자원갯수
	int countOfInstallPlace();
	
	List<ResInfo> searchResInfoByResClass(); //자원분류 조회
	List<ResInfo> searchResInfo(ResInfo resInfo); //검색결과조회
	
	List<ResClass> selectAllResClass(); //자원분류리스트 조회
	
	void insertResInfo(ResInfo resInfo); //자원입력
	void updateResInfo(ResInfo resInfo); //자원수정
	void updateAddItemValueInResInfo(ResInfo resInfo);
	
	List<CommonCodeDetail> selectResStatusCode(String codeGroupId); //자원상태코드 리스트 불러오기
	List<InstallPlace> selectResInstallPlace(@Param("start")int start, @Param("end")int end); //자원설치장소 리스트 불러오기
	
	ResInfo selectResInfoDetail(String resName);
	
	List<ResInfo> selectMappingAddItem(String resClassId);
	
	void insertAddItemValueInResInfo(@Param("resSerialId") String resSerialId, @Param("addItemSn") String addItemSn, @Param("resDetailValue") String resDetailValue); //매핑된 부가항목 값 넣기
	void deleteAddItemValueInResInfo(String resSerialId);
	List<ResInfo> selectAddItemValueInResInfo(String resSerialId);
	
	List<ResInfo> selectIpInResInfo(String resSerialId);
	void insertIpInResInfo(@Param("resSerialId") String resSerialId,@Param("ipSn") int ipSn,@Param("ipTypeCode")String ipTypeCode);
	List<IpInfo> selectAllIpInfoList(@Param("start")int start , @Param("end")int end);
	
	int CountOfAddItemValueInResInfo(String resSerialId);
	int CountOfIpList();
	
}
