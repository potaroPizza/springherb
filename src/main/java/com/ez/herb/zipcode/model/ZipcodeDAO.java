package com.ez.herb.zipcode.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ez.herb.common.SearchVO;

@Mapper
public interface ZipcodeDAO {
	public List<ZipcodeVO> selectAll(SearchVO searchVo);
	int getTotalRecord(SearchVO searchVo);

}
