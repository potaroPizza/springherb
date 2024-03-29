package com.ez.herb.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
//public class LoginInterceptor implements HandlerInterceptor{
public class LoginInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger
	= LoggerFactory.getLogger(IndexController.class);

	@Override
	public boolean preHandle(HttpServletRequest request, 
			HttpServletResponse response, Object handler)
			throws Exception {
		logger.info("preHandle() 실행! - 컨트롤러 실행 전");
		
		String userid = (String)request.getSession().getAttribute("userid");
		logger.info("userid = {}", userid);
		
		//로그인이 안된 경우
		if(userid == null || userid.isEmpty()) {
			request.setAttribute("msg", "먼저 로그인하세요.");
			request.setAttribute("url", "/login/login");
			
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('먼저 로그인하세요.');");
			out.print("location.href='" + request.getContextPath()
				+ "/login/login';");
			out.print("</script>");
			
			return false;	// 수행을 막음
		}
		
		return true;	// 다음 컨트롤러가 수행됨
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle() 실행! - 컨트롤러 실행 후");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.info("afterCompletion() 실행! - 뷰페이지 생성 후");
	}

	
	
	
}
