package com.ez.herb.zipcode.model;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ez.herb.common.SearchVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ZipcodeServiceImpl implements ZipcodeService {
	
	private final ZipcodeDAO zipcodeDAO; 

	@Override
	public List<ZipcodeVO> selectAll(SearchVO searchVo) {
		return zipcodeDAO.selectAll(searchVo);
	}

	@Override
	public int getTotalRecord(SearchVO searchVo) {
		return zipcodeDAO.getTotalRecord(searchVo);
	}

}
