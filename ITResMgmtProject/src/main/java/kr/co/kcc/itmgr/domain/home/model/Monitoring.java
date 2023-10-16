package kr.co.kcc.itmgr.domain.home.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class Monitoring {
	// 자원 정보
	private String resSerialId; // 자원 시리얼 아이디
	private String resName; // 자원명
	private String resSn; // 자원 시리얼 번호
	private String mgmtId; // 관리자 ID
	private String resStatusCode; // 자원 상태 코드 (사용중 ..)
	private String introductionDate; // 도입일자 
	private String resUseYn; // 자원 사용여부
	private String monitoringYn; // 모니터링 여부
	
	// 자원 분류
	private int level;
	private String resClassId; // 자원 분류 아이디
	private String resClassName; // 자원 분류 이름
	private String resClassPath; // 자원분류 경로
	private String useYn; // 자원분류 사용여부
	
	// 설치 장소
	private int installPlaceSn; // 설치장소 번호
	private String installPlaceName; // 설치장소명
	private String installPlaceFullAddress; // 설치장소 주소 + 설치장소 상세 주소
	private String installPlaceAddress; // 설치장소 주소
	private String installPlaceDetailAddress; // 설치장소 상세 주소
	
	// IP Mapping
	private int ip_sn; // IP 시리얼 번호
	private String ipTypeCode; // IP 종류코드
	
	// IP
	private String ip; // IP 번호
	private String ipDesc; // IP 설명
	private boolean ipStatus;
	
	// 검색을 위한 칼럼
	private String midResClass;
	private String bottomResClass;
}
