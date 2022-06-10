package com.ez.herb.product.model;

import java.util.List;

public interface ProductService {
	List<ProductVO> selectEventList(String eventName);
	List<ProductVO> selectByCategory(int categoryNo);
	ProductVO selectByProductNo(int productNo);
}
