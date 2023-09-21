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
	public int countAddItem() {
		return addItemRepository.countAddItem();
	}

	@Override
	public List<AddItem> selectSearchAddItem(String useYN, String searchText) {
		return addItemRepository.selectSearchAddItem(useYN, searchText);
	}
}
