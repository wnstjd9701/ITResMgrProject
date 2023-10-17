package kr.co.kcc.itmgr.domain.ipinfo.model;

import lombok.Data;

@Data
public class IpRes {
	// resipmapping
	private int ipSn;
	private String resSerialId;
	private String ipTypeCode;
	private String ipTypeCodeName;
	
	// resInfo
	private String resName;
	private String resStatusCodeName;
	private String mgmtDeptName;
	private String rackInfo;
	private String resClassName;
	
	// installPlace
	private String installPlaceName;
	private String detailAddress;
	
	private int rn;
}
