package com.ez.herb.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.product.model.ProductService;
import com.ez.herb.product.model.ProductVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/shop/product")
@RequiredArgsConstructor
public class ProductController {
	private static final Logger logger
		= LoggerFactory.getLogger(ProductController.class);
	
	private final ProductService productService;
	
	@RequestMapping("/productEvent")
	public String productEvent(@RequestParam String eventName,
			Model model) {
		logger.info("이벤트 상품 페이지, 파라미터 eventName={}", eventName);
		
		List<ProductVO> list = productService.selectEventList(eventName);
		logger.info("이벤트 상품 조회 결과, list.size={}", list.size());
		
		model.addAttribute("list", list);
		
		return "/shop/product/productEvent";
	}
	
	@RequestMapping("/productByCategory")
	public String productByCategory(@RequestParam(defaultValue = "0") int categoryNo,
			@RequestParam String categoryName, Model model) {
		logger.info("카테고리별 상품 페이지, 파라미터 categoryNo={}, categoryNo={}",
				categoryNo, categoryName);
		
		List<ProductVO> list = productService.selectByCategory(categoryNo);
		
		model.addAttribute("list", list);
		
		return "/shop/product/productByCategory";
	}
	
	@RequestMapping("/productDetail")
	public String detail(@RequestParam(defaultValue = "0") int productNo,
			Model model) {
		logger.info("상품 상세 페이지, 파라미터 productNo={)", productNo);
		
		ProductVO vo = productService.selectByProductNo(productNo);
		logger.info("상품 상세 조회, vo={)", vo);
		
		model.addAttribute("vo", vo);
		
		return "/shop/product/productDetail";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
