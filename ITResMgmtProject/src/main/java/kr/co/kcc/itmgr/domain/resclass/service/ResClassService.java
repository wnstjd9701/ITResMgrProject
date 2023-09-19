package kr.co.kcc.itmgr.domain.resclass.service;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.resclass.dao.IResClassRepository;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResClassService implements IResClassService {

	private final IResClassRepository resClassRepository;

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
		return resClassRepository.selectResClassByResClassName(resClassName);
	}

	@Override
	public void insertResClass(ResClass resClass) {
		resClassRepository.insertResClass(resClass);
		
	}

	@Override
	public List<ResClass> selectResClassByLevel() {
		return resClassRepository.selectResClassByLevel();
	}







}
