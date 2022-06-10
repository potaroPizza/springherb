package com.ez.herb.category.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ez.herb.board.controller.BoardController;
import com.ez.herb.category.model.CategoryService;
import com.ez.herb.category.model.CategoryVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CategoryController {
	
	private static final Logger logger 
		= LoggerFactory.getLogger(BoardController.class);
	
	private final CategoryService categoryService; 
	
	
	@RequestMapping("/inc/categoryList")
	public String categoryList(Model model) {
		logger.info("카테고리리스트 페이지");
		
		List<CategoryVO> list = categoryService.selectAll();
		logger.info("카테고리 조회 결과, list.size={}", list.size());
		
		model.addAttribute("list", list);
		
		return "/inc/categoryList";
	}
}
