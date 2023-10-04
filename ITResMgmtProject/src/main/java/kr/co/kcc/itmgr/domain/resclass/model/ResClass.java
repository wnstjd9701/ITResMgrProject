package kr.co.kcc.itmgr.domain.resclass.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
	
	//AddItem 매핑
	private String addItemName;
	private String addItemDesc;
	private String addItemUseYn;
	
	//페이징
	private int page;
	private int limit;
	private int listCount;
	private int maxPage;
	private int startPage;
	private int endPage;
	
	
	private String flag;
	
	private List<Integer> addItemSn;
	

	
}
