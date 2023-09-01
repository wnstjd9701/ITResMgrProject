package kr.co.kcc.itmgr.domain.resclass.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ResClass {
	private String resClassId;
	private String upperResClassId;
	private String resClassName;
	private String useYn;
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
	
	private int level;
}
