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
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	
	private static final Logger logger
		= LoggerFactory.getLogger(IndexController.class);
	
	private final MemberService memberService;
	
	@GetMapping("/agreement")
	public void agreement() {
		logger.info("회원약관 페이지");
	}
	
	@RequestMapping("/register")
	public void register() {
		logger.info("회원가입 페이지");
	}
	
	@PostMapping("/join")
	public String join(@ModelAttribute MemberVO vo, 
			@RequestParam String email3, Model model) {
		logger.info("회원가입 처리, 파리미터 vo={}, email3={}", vo, email3);
		
		if(vo.getHp2() == null || vo.getHp2().isEmpty() ||
				vo.getHp3() == null || vo.getHp3().isEmpty()) {
			vo.setHp1("");
			vo.setHp2("");
			vo.setHp3("");
		}
		
		if(vo.getEmail1()  == null || vo.getEmail1().isEmpty()) {
			vo.setEmail1("");
			vo.setEmail2("");
		}
		
		if(vo.getEmail2().equals("etc")) {
			vo.setEmail2(email3);
		}
		
		int cnt = memberService.insertMember(vo);
		logger.info("회원가입 결과, cnt={}", cnt);
		
		String msg="회원가입 실패", url="/member/register";
		if(cnt>0) {
			msg="회원가입 성공";
			url="/";	// to index.jsp
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/common/message";
	}
	
	@RequestMapping("/checkUserid")
	public String checkUserid(@RequestParam String userid, Model model) {
		logger.info("아이디 중복확인 페이지, 파라미터 userid={}", userid);
		
		int result = 0;
		
		// 넘어온 아이디가 있을때만, DB작업
		if(userid != null && !userid.isEmpty()) {
			result = memberService.duplicateId(userid);
			logger.info("아이디 중복확인 결과, result={}", result);
		}
		
		model.addAttribute("result", result);
		model.addAttribute("USABLE_ID", MemberService.USABLE_ID);
		model.addAttribute("UNUSABLE_ID", MemberService.UNUSABLE_ID);
		
		return "/member/checkUserid";
	}
	
	@GetMapping("/memberEdit")
	public String edit_get(HttpSession session, Model model) {
		String userid = (String)session.getAttribute("userid");
		logger.info("회원정보 수정 페이지, userid={}", userid);
		
		//세션에 userid가 없는상태에서, 회원정보수정 페이지로 들어온다면 막아야한다.
		//막는방법은 크게 2가지가 있다.
		
		/* (1번방법, 기존방법)
		if(userid == null || userid.isEmpty()) {
			model.addAttribute("msg", "먼저 로그인하세요.");
			model.addAttribute("url", "/login/login");
			
			return "/common/message";
		}
		*/
		
		//2번방법 HandlerInterceptor사용 
		//컨트롤러가 수행되기 전에, 막고, 로그인으로 보내야하므로 preHandle()
		//인터셉터만들고, MvcConfiguration에 설정하면된다.
		
		
		
		
		
		MemberVO vo = memberService.selectByUserid(userid);
		logger.info("회원정보 조회 결과, vo={}", vo);
		
		model.addAttribute("vo", vo);
		
		return "/member/memberEdit";
	}
	
	@PostMapping("memberEdit")
	public String edit_pst(@ModelAttribute MemberVO vo, 
			@RequestParam String email3,
			HttpSession session, Model model) {
		logger.info("회원수정 처리, 파리미터 vo={}, email3={}", vo, email3);
		
		String userid = (String)session.getAttribute("userid");
		vo.setUserid(userid);
		
		if(vo.getHp2() == null || vo.getHp2().isEmpty() ||
				vo.getHp3() == null || vo.getHp3().isEmpty()) {
			vo.setHp1("");
			vo.setHp2("");
			vo.setHp3("");
		}
		
		if(vo.getEmail1()  == null || vo.getEmail1().isEmpty()) {
			vo.setEmail1("");
			vo.setEmail2("");
		}
		
		if(vo.getEmail2().equals("etc")) {
			vo.setEmail2(email3);
		}
		
		int result = memberService.checkLogin(vo.getUserid(), vo.getPwd());
		logger.info("비밀번호 체크, result={}", result);
		
		String msg = "비밀번호 체크 실패", url = "/member/memberEdit";
		
		if(result == MemberService.LOGIN_OK) {
			int cnt = memberService.updateMember(vo);
			logger.info("회원정보 수정 결과, cnt={}", cnt);
		
			if(cnt>0) {
				msg="회원정보 수정 성공";
				url="/";	// to index.jsp
			} else {
				msg="회원정보 수정 실패";
			}
			
		} else if(result == MemberService.DISAGREE_PWD) {
			msg = "비밀번호가 일치하지 않습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/common/message";
	}
	
	@GetMapping("/memberOut")
	public String delete_get() {
		logger.info("회원탈퇴 페이지");
		
		return "/member/memberOut";
	}
	
	@PostMapping("/memberOut")
	public String delete_post(@RequestParam String pwd,
			HttpSession session,
			HttpServletResponse response, Model model) {
		logger.info("회원탈퇴 처리, 파라미터 pwd={}", pwd);
		
		String userid = (String)session.getAttribute("userid");
		
		int result = memberService.checkLogin(userid, pwd);
		logger.info("비밀번호 체크, result={}", result);
		
		String msg = "비밀번호 체크 실패", url = "/member/memberOut";
		
		if(result == MemberService.LOGIN_OK) {
			int cnt = memberService.deleteMember(userid);
			logger.info("회원정보 삭제 결과, cnt={}", cnt);
		
			if(cnt>0) {
				session.invalidate();	// session 정보 제거
				
				// 쿠키 삭제
				Cookie ck = new Cookie("ckUserid", userid);
		        ck.setPath("/");	
		        //=> 만약에 setPath로 루트경로를 설정안해주면
		        //=> 쿠키를 만들어준 곳이나, 그 하위 디렉토리에서만 쿠키를 다룰수 있는데,
		        //=> 쿠키의 경로를 루트경로로 설정해주면, 어디에서건 쿠키를 다룰 수 있다.
		        //=> 따라서 쿠키의 경로를 꼭 설정해줘야, 다른곳에서도 쿠키를 사용할 수 있다.
		        ck.setMaxAge(0);	// 쿠키 유효기간 : 0일 => 쿠키 제거
	        	response.addCookie(ck);
				
				msg="회원탈퇴 성공";
				url="/";	// to index.jsp
			} else {
				msg="회원탈퇴 실패";
			}
			
		} else if(result == MemberService.DISAGREE_PWD) {
			msg = "비밀번호가 일치하지 않습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/common/message";
	}
	
	
	
	
	
	
	
	
	
	
}
