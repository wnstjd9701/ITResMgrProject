package kr.co.kcc.itmgr.domain.additem.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.additem.dao.IAddItemRepository;
import kr.co.kcc.itmgr.domain.additem.model.AddItem;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddItemService implements IAddItemService{
	
	private final IAddItemRepository addItemRepository;

	@Override
	public List<AddItem> selectAllAddItem() {
		return addItemRepository.selectAllAddItem();
	}
	
	@Override
	public List<AddItem> selectUseYAddItem() {
		return addItemRepository.selectUseYAddItem();
	}

	@Override
	public List<AddItem> selectSearchAddItem(String useYN, String searchText) {
		return addItemRepository.selectSearchAddItem(useYN, searchText);
	}
	
	@Override
	public void insertAddItem(List<AddItem> insertAddItems) {
		addItemRepository.insertAddItem(insertAddItems);
	}

	@Override
	public void deleteAddItemByUseYN(int addItemSn) {
		addItemRepository.deleteAddItemByUseYN(addItemSn);
	}

	@Override
	public void updateAddItemDesc(List<AddItem> updateAddItems) {
		addItemRepository.updateAddItemDesc(updateAddItems);
	}

	@Override
	public void insertAddItemExcel(String addItemName, String addItemDesc) {
		addItemRepository.insertAddItemExcel(addItemName, addItemDesc);
	}
}
