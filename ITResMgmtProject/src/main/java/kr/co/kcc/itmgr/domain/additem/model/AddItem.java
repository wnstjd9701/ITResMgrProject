package kr.co.kcc.itmgr.domain.additem.model;

import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class AddItem {
	private int addItemSn;
	private String addItemName;
	private String addItemDesc;
	private String  useYN;
	private Timestamp createDate;
	private String createrId;
	private Timestamp updateDate;
	private String updaterId;
	
	private String searchText;
	private List<AddItem> insertAddItems;
	private List<Integer> deletedAddItems;
	private List<AddItem> updateAddItems;

}
