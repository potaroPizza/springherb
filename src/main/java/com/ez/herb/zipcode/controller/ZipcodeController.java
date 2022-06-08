package com.ez.herb.zipcode.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.common.ConstUtil;
import com.ez.herb.common.PaginationInfo;
import com.ez.herb.common.SearchVO;
import com.ez.herb.controller.IndexController;
import com.ez.herb.zipcode.model.ZipcodeService;
import com.ez.herb.zipcode.model.ZipcodeVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/zipcode")
@RequiredArgsConstructor
public class ZipcodeController {
	
	private static final Logger logger
	= LoggerFactory.getLogger(IndexController.class);
	
	private final ZipcodeService zipcodeService;
	
	@RequestMapping("/zipcode")
	public String zipcode(@RequestParam(required = false) String dong,
			@ModelAttribute SearchVO searchVo, Model model) {
		logger.info("우편번호 찾기 페이지, 파라미터 dong={}", dong);
		
		searchVo.setSearchKeyword(dong);
		logger.info("searchVo={}", searchVo);
		
		//[1] PaginationInfo 생성
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(ConstUtil.BLOCKSIZE);
		pagingInfo.setRecordCountPerPage(ConstUtil.RECORD_COUNT);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		//[2] searchVo에 페이징 처리 관련 변수의 값 셋팅
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		searchVo.setRecordCountPerPage(ConstUtil.RECORD_COUNT);

		
		List<ZipcodeVO> list = null;
		
		if(dong != null && !dong.isEmpty()) {
			list = zipcodeService.selectAll(searchVo);
			
			//totalRecord개수 구하기
			int totalRecord=zipcodeService.getTotalRecord(searchVo);
			logger.info("글목록 totalRecord={}", totalRecord);
			
			pagingInfo.setTotalRecord(totalRecord);
		}
		
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "/zipcode/zipcode";
	}
	
}
