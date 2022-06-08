package com.ez.herb.reboard.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

@Component
public class ReBoardDownloadView extends AbstractView {
	public static final Logger logger
		= LoggerFactory.getLogger(ReBoardDownloadView.class);

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		File file = (File)model.get("file");
		if(file == null || !file.exists() || !file.canRead()){
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>alert('파일이 존재하지 않거나 손상되었습니다.');history.back();</script>");
			return;
		}
		
		logger.info("fileName = {}", file.getName());
		
		String fileName = new String(file.getName().getBytes("euc-kr"),
				"8859_1");
		
		//컨텐트 타입을 딱히 지정하지않는것, 모든 컨텍트 타입을 받아들이겠다는 의미
		response.setContentType("application/octet-stream");
		//다운로드 처리하는 헤더 
		response.setHeader("Content-disposition", "attachment;filename="
				+ fileName);
		
		// 파일을 출력하기위해, 스트림 두개 생성
		// 밖으로 내보내는 스트림
		OutputStream os = response.getOutputStream();
		// 파일을 가져오는 스트림
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			// 파일을 가져와서, 밖으로 출력
			FileCopyUtils.copy(fis, os);
		} finally {
			if(fis != null) fis.close();
		}
		
	}
	
	

}
