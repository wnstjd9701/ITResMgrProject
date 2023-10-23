package kr.co.kcc.itmgr.domain.installplace.model;

import lombok.Data;

@Data
public class InstallRes {
	private String resSerialId;
	private String resName;
	private String resStatusCodeName;
	private String mgmtDeptName;
	private String rackInfo;
	private String resClassName;
	private int ipSn;
	private String ip;
	private String ipDesc;
	private String ipTypeCodeName;
	private int resCount;
	
	private int rn;
}
