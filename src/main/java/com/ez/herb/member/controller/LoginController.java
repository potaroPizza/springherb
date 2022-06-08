package com.ez.herb.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ez.herb.controller.IndexController;
import com.ez.herb.member.model.MemberService;
import com.ez.herb.member.model.MemberVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
	
	private static final Logger logger
		= LoggerFactory.getLogger(IndexController.class);
	
	private final MemberService memberService;
	
	@GetMapping("/login")
	public String login_get() {
		logger.info("로그인 화면");
		
		return "/login/login";
	}
	
	@PostMapping("/login")
	public String login_post(@ModelAttribute MemberVO vo,
			@RequestParam(required = false) String chkSaveId, 
			HttpServletRequest request,
			HttpServletResponse response, Model model) {
		logger.info("로그인 처리, 파라미터 vo={}, chkSaveId={}", vo, chkSaveId);
		
		int result = memberService.checkLogin(vo.getUserid(), vo.getPwd());
		logger.info("로그인 처리 결과, result={}", result);
		
		String msg = "로그인을 실패했습니다.", url = "/login/login";
		
		if(result == MemberService.LOGIN_OK) {
			//회원정보 조회
			//덮어씌워도 되지만, 그냥 확실하게 하기위해서, 다른 vo객체를 선언함.
			MemberVO memVo = memberService.selectByUserid(vo.getUserid());
			logger.info("로그인 처리 - 회원정보 조회 결과, memVo={}", memVo);
			
			//[1] session에 저장
			HttpSession session = request.getSession();
	        session.setAttribute("userid", memVo.getUserid());
	        session.setAttribute("userName", memVo.getName());
			
			//[2] 쿠키에 저장
	        Cookie ck = new Cookie("ckUserid", memVo.getUserid());
	        ck.setPath("/");
	       
	        // 로그인시 '아이디저장 체크박스'를 체크한 경우
	        if(chkSaveId != null) {	// 체크박스를, 체크하면 "on", 안하면 null	
	        	ck.setMaxAge(1000*24*60*60);	// 쿠키 유효기간 : 1000일
	        	response.addCookie(ck);
	        } else {
	        	ck.setMaxAge(0);	// 쿠키 유효기간 : 0일 => 쿠키 제거
	        	response.addCookie(ck);
	        }
			
			msg = "로그인을 성공했습니다.";
			url = "/";
		} else if(result == MemberService.DISAGREE_PWD) {
			msg = "비밀번호가 일치하지 않습니다.";
		} else if(result == MemberService.NONE_USERID) {
			msg = "존재하지않는 아이디 입니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/common/message";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("로그아웃 처리 ");
		
		session.invalidate();	// 세션 소멸
		
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
