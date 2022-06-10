package com.ez.herb.product.model;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ez.herb.product.controller.ProductController;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private static final Logger logger
		= LoggerFactory.getLogger(ProductController.class);
	
	private final ProductDAO productDao;

	@Override
	public List<ProductVO> selectEventList(String eventName) {
		return productDao.selectEventList(eventName);
	}

	@Override
	public List<ProductVO> selectByCategory(int categoryNo) {
		return productDao.selectByCategory(categoryNo);
	}

	@Override
	public ProductVO selectByProductNo(int productNo) {
		return productDao.selectByProductNo(productNo);
	}
	
	
}
