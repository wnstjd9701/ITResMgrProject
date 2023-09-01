package kr.co.kcc.itmgr.domain.resclass.model;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ResClass {
	private String resClassId;
	private String upperResClassId;
	private String resClassName;
	private char useYN;
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
}
