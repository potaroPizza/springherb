package com.ez.herb.category.model;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	
	private final CategoryDAO categoryDao;

	@Override
	public List<CategoryVO> selectAll() {
		return categoryDao.selectAll();
	}

}
