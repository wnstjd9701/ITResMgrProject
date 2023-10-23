package kr.co.kcc.itmgr.domain.commoncode.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommonCodeDetail {
	private String detailCode;
	private String codeGroupId;
	private String detailCodeName;
	private String useYn;
	
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
	
	// Flag - Create / Update / Delete인지 확인하는 변수
	private String flag;
	
	private String resStatusCode;
}
