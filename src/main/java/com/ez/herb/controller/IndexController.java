package com.ez.herb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	private static final Logger logger
		= LoggerFactory.getLogger(IndexController.class);
	
	
	@RequestMapping("/")
	public String index() {
		logger.info("index 페이지");
		
		return "/index";
	}
	
	@RequestMapping("/kakaoLogin")
	public String logout() {
		logger.info("카카오 로그인 페이지");
		
		return "/kakaoLogin";
	}
}
