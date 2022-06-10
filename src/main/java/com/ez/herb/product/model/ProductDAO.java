package com.ez.herb.product.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductDAO {
	List<ProductVO> selectEventList(String eventName);
	List<ProductVO> selectByCategory(int categoryNo);
	ProductVO selectByProductNo(int productNo);
}
