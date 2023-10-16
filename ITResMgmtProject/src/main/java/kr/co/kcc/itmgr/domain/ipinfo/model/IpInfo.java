package kr.co.kcc.itmgr.domain.ipinfo.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class IpInfo {
	// ipInfo
	private int ipSn;
	private String ip;
	private String ipDesc;
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
	
	// commoncode
    private String codeGroupId;
	private String detailCode;
	private String detailCodeName;
	
	private int rn;
}
