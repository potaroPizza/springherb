package com.ez.herb.zipcode.model;

import java.util.List;

import com.ez.herb.common.SearchVO;

public interface ZipcodeService {
	
	public List<ZipcodeVO> selectAll(SearchVO searchVo);
	int getTotalRecord(SearchVO searchVo);

}
