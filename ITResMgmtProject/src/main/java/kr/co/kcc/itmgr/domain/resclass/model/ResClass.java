package kr.co.kcc.itmgr.domain.resclass.model;

import java.sql.Timestamp;
import java.util.ArrayList;

import lombok.Data;

@Data
public class ResClass {
	private String resClassId;
	private String upperResClassId;
	private String resClassName;

	private char useYn;
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
	

	//추가컬럼
	private int level;
	private String resClassName2;
	
	private String topUpperResClassName;
	private String upperResClassName;
	private String bottomResClassName;

	
}
