package kr.co.kcc.itmgr.domain.resinfo.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ResInfo {
	
	private String resSerialId;
	private String resClassId;
	private String resName;
	private String resSn;
	private String mgmtId;
	private String resStatusCode;
	private String menufactureCompanyName;
	private String modelName;
	private String mgmtDeptName;
	private String managerName;
	private String introdutionDate;
	private int introdutionPrice;
	private String purchaseCompanyName;
	private String useYn;
	private String monitoringYn;
	private String rackInfo;
	private String expirationDate;
	private String addInfo;
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
	private int installPlaceSn;
	
	
	//추가컬럼
	private String resClassName;
	private int mappingNumberOfRes;
}
