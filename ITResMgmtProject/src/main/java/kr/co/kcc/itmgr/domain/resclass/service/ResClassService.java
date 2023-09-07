package kr.co.kcc.itmgr.domain.resclass.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co.kcc.itmgr.domain.resclass.dao.IResClassRepository;
import kr.co.kcc.itmgr.domain.resclass.model.ResClass;
import kr.co.kcc.itmgr.domain.resinfo.model.ResInfo;
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
	public List<ResInfo> numberOfResByResClass() {
		return IResClassRepository.numberOfResByResClass();
	}




	


}
