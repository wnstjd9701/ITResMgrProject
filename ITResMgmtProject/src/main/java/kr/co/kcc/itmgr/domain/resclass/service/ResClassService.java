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
	
	private final IResClassRepository IResClassRepository;

	@Override
	public List<Map<String, String>> selectAllResClass() {
		return IResClassRepository.selectAllResClass();
	}


	


}
