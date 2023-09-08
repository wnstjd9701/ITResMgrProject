package kr.co.kcc.itmgr.domain.commoncode.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommonCode {
	private String codeGroupId;
	private String codeGroupName;
	private String useYn;
	
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
}
