package kr.co.kcc.itmgr.domain.resclass.service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.kcc.itmgr.domain.additem.model.AddItem;
import kr.co.kcc.itmgr.domain.resclass.dao.IResClassRepository;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resclass.model.ResClassDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResClassService implements IResClassService {

	private final IResClassRepository resClassRepository;
	
	static final Logger logger = LoggerFactory.getLogger(ResClassService.class);

	@Override
	public List<ResClass> selectAllResClass() {
		return resClassRepository.selectAllResClass();
	}

	@Override
	public List<Map<Object, Object>> numberOfResByResClass() {
		return resClassRepository.numberOfResByResClass();
	}

	@Override
	public List<ResClass> selectResClassByResClassName(String resClassName) {
		return setFlagResClassList(resClassRepository.selectResClassByResClassName(resClassName));
	}

	@Override
	@Transactional
	public void insertResClass(ResClass resClass) {
		resClassRepository.insertResClass(resClass);
		
	}

	@Override
	public List<ResClass> selectResClassByLevel() {
		return resClassRepository.selectResClassByLevel();
	}

//	@Override
//	public List<AddItem> selectAddItemInResClass(int page) {
//		int start=(page-1)*5+1;
//		List<AddItem> addItemList = resClassRepository.selectAddItemInResClass(start, start+4);
//		return addItemList;
//	}

	@Override
	public int insertAddItemToResClass(List<ResClass> resclass) {
		 return resClassRepository.insertAddItemToResClass(resclass);
		
	}

	@Override
	public int countOfAddItemList() {
		return resClassRepository.countOfAddItemList();
	}

	@Override
	public List<AddItem> selectAddItemInResClass(int page) {
		int start = (page-1)*5+1;
		return resClassRepository.selectAddItemInResClass(start,start+9);
	}

	@Override
	public int updateResClass(ResClass resclass) {
		return resClassRepository.updateResClass(resclass);
	}

	@Override
	public int deleteAddItemInResClass(ResClass resclass) {
		return resClassRepository.deleteAddItemInResClass(resclass);
	}
	
//	// 부가항목 Flag 생성
//	@Override
//	public List<AddItem> setFlagAddItemList(List<AddItem> addItem) {
//		return addItem.stream()
//				.map(r -> {
//					r.setFlag("E");
//					return r;
//				})
//				.collect(Collectors.toList());
//	}
	
	// 자원분류 Flag 생성
	@Override
	public List<ResClass> setFlagResClassList(List<ResClass> resClassList) {
		return resClassList.stream()
				.map(r -> {
					r.setFlag("E");
					return r;
				})
				.collect(Collectors.toList());
	}

	/*
	 *  부가항목, 자원분류 Flag 생성
	 *  제너릭 타입으로 ResClassDTO 인터페이스를 상속 / setFlag를 사용하기 위해
	 */
	@Override
	public <T extends ResClassDTO> List<T> setFlag(List<T> code) {
		return code.stream()
				.map(r -> {
					r.setFlag("E");
					return r;
				})
				.collect(Collectors.toList());
	}

	@Override
	public List<ResClass> selectResClassAndAddItemList() {
		return resClassRepository.selectResClassAndAddItemList();
	}

	@Override
	public List<Map<Object, Object>> numberOfResByResClass2() {
		return resClassRepository.numberOfResByResClass2();
	}

	@Override
	public List<Map<Object, Object>> numberOfResByResClass3() {
		return resClassRepository.numberOfResByResClass3();
	}




}
