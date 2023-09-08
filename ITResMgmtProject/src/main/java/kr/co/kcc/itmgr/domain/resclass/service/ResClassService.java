package kr.co.kcc.itmgr.domain.resclass.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.resclass.dao.IResClassRepository;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resclass.model.ResClassCount;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResClassService implements IResClassService {

	private final IResClassRepository IResClassRepository;

	@Override
	public List<ResClass> selectAllResClass() {
		return IResClassRepository.selectAllResClass();
	}

	@Override
	public List<Map<Object, Object>> numberOfResByResClass() {
		return IResClassRepository.numberOfResByResClass();
	}








}
