package kr.co.kcc.itmgr.domain.resclass.model;

import java.util.List;

import lombok.Data;

@Data
public class ResClassAddItem {

	private List<Integer> addItemSn;
	private String resClassId;
}
