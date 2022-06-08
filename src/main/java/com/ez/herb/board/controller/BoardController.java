package com.ez.herb.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.board.model.BoardService;
import com.ez.herb.board.model.BoardVO;
import com.ez.herb.common.PaginationInfo;
import com.ez.herb.common.SearchVO;
import com.ez.herb.common.ConstUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

	// @RequiredArgsConstructor 덕분에 final로 선언된 객체는 오토와이어링되고, 객체도 자동 주입됨
	private final BoardService boardService;

	@GetMapping("/write.do")
	public String write_get() {
		logger.info("글 등록 페이지");

		return "/board/write";
		// => http://localhost:9091/herb/board/write.do
	}

	@PostMapping("/write.do")
	public String write_post(@ModelAttribute BoardVO vo) {
		logger.info("글 등록 처리, 매개변수vo = {}", vo);

		int cnt = boardService.insertBoard(vo);
		logger.info("글 등록 처리 결과, cnt = {}", cnt);

		return "redirect:/board/list.do";
		// do => controller => service인터페이스 => 구현체service의 메소드 호출
		// =>dao인터페이스 => 구현체dao의 메소드호출
	}
	
	@RequestMapping("/list.do")
	public String list(@ModelAttribute SearchVO searchVo, Model model) {
		logger.info("글 목록, 파라미터 searchVo={}", searchVo);
		
		//[1] PaginationInfo 생성
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(ConstUtil.BLOCKSIZE);
		pagingInfo.setRecordCountPerPage(ConstUtil.RECORD_COUNT);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		//[2] searchVo에 페이징 처리 관련 변수의 값 셋팅
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		searchVo.setRecordCountPerPage(ConstUtil.RECORD_COUNT);

		List<BoardVO> list=boardService.selectAll(searchVo);
		logger.info("글 목록 조회 결과, list.size={}", list.size());
		
		//totalRecord개수 구하기
		int totalRecord=boardService.getTotalRecord(searchVo);
		logger.info("글목록 totalRecord={}", totalRecord);
		
		// 이거 빼먹으면, 다른거 계산이 안됨
		pagingInfo.setTotalRecord(totalRecord);
		
		// request객체, 모델앤뷰객체에 저장하는것과 똑같은 구조
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "/board/list";
	}
	
	@RequestMapping("/countUpdate.do")
	public String countUpdate(@RequestParam(defaultValue = "0") int no,
			Model model) {
		logger.info("조회수 증가, 파라미터 = {}", no);
		
		if(no == 0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/board/list.do");
			return "/common/message";
		}
		
		int cnt = boardService.updateCount(no);
		logger.info("조회수 증가 결과, cnt = {}", cnt);
		
		return "redirect:/board/detail.do?no="+no;
	}
	
	@RequestMapping("/detail.do")
	public String detail(@RequestParam(defaultValue = "0") int no,
			Model model) {
		logger.info("글 상세보기 파라미터 no = {}", no);
		
		if(no == 0) {
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/board/list.do");
			return "/common/message";
		}
		
		BoardVO vo = boardService.selectByNo(no);
		logger.info("글 상세보기 결과 vo = {}", vo);
		
		model.addAttribute("vo", vo);
		
		return "/board/detail";
	}
	
	@GetMapping("/edit.do")
	public String edit_get(@RequestParam(defaultValue = "0") int no,
			Model model) {
		logger.info("글 수정 페이지, 파라미터 no = {}", no);
		
		if(no == 0) {
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/board/list.do");
			return "/common/message";
		}
		
		BoardVO vo = boardService.selectByNo(no);
		logger.info("수정할 글 상세보기 결과 vo = {}", vo);
		
		model.addAttribute("vo", vo);
		
		return "/board/edit";
	}
	
	@PostMapping("/edit.do")
	public String edit_post(@ModelAttribute BoardVO vo,
			Model model) {
		logger.info("글 수정 처리, 파라미터 vo = {}", vo);
		
		//비밀번호 체크
		String msg = "비밀번호 체크 실패", url = "/board/edit.do?no="+vo.getNo();
		if(boardService.checkPwd(vo.getNo(), vo.getPwd())) {
			// 비밀번호가 일치하는 경우
			// 수정 처리
			int cnt = boardService.updateBoard(vo);
			logger.info("글 수정 처리 결과,cnt = {}", cnt);
			
			if(cnt > 0) {
				msg = "글 수정을 성공했습니다.";
				url = "/board/detail.do?no="+vo.getNo();
			} else {
				msg = "글 수정을 실패했습니다.";
			}
			
		} else {
			msg = "비밀번호가 일치하지 않습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/common/message";
	}
	
	@GetMapping("/delete.do")
	public String delete_get(@RequestParam(defaultValue = "0") int no,
			Model model) {
		logger.info("글 삭제 페이지, 파라미터 no={}", no);
		
		if(no == 0) {
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/board/list.do");
			return "/common/message";
		}
		
		return "/board/delete";
	}
	
	@PostMapping("/delete.do")
	public String delete_post(@ModelAttribute BoardVO vo,
			Model model) {
		logger.info("글 삭제 처리, 파라미터 no={}, pwd={}", vo.getNo(), vo.getPwd());
		
		//비밀번호 체크
		String msg = "비밀번호 체크 실패", url = "/board/delete.do?no="+vo.getNo();
		if(boardService.checkPwd(vo.getNo(), vo.getPwd())) {
			// 비밀번호가 일치하는 경우
			// 삭제처리
			int cnt = boardService.deleteBoard(vo.getNo());
			logger.info("글 삭제 처리 결과, no = {}", vo.getNo());
			if(cnt > 0) {
				msg = "글 삭제를 성공했습니다.";
				url = "/board/list.do";
			} else {
				msg = "글 삭제를 실패했습니다.";
			}
			
		} else {
			msg = "비밀번호가 일치하지 않습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/common/message";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
