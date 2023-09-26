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
	
	private Timestamp create_date;
	private String creater_id;
	private Timestamp update_date;
	private String updater_id;
	
	// 자원 매핑 개수
	private int resCount;
	
	//도 이름
	private String doName;
	private double doLatitude;
	private double doLongitude;
}
