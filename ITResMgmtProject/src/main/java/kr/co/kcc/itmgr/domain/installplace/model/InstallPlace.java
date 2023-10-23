package kr.co.kcc.itmgr.domain.installplace.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class InstallPlace {
	private int installPlaceSn;
	private String installPlaceName;
	private String installPlacePostno;
	private String installPlaceAddress;
	private String installPlaceDetailAddress;
	private double longitude;
	private double latitude;
	
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
	
	// 페이징 rnum
	private int rn;
	
	// 자원 매핑 개수
	private int resCount;
	
	//도 이름
	private String doName;
	private double doLatitude;
	private double doLongitude;
}
