package com.ez.herb.reboard.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ez.herb.common.ConstUtil;
import com.ez.herb.common.FileUploadUtil;
import com.ez.herb.common.PaginationInfo;
import com.ez.herb.common.SearchVO;
import com.ez.herb.reboard.model.ReBoardService;
import com.ez.herb.reboard.model.ReBoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reBoard")
@RequiredArgsConstructor
public class ReBoardController {
	private static final Logger logger
	= LoggerFactory.getLogger(ReBoardController.class);
	
	private final ReBoardService reBoardService;
	private final FileUploadUtil fileUploadUtil;

	@GetMapping("/write.do")
	public String write_get() {
		logger.info("글쓰기 화면");

		return "/reBoard/write";
	}

	@PostMapping("/write.do")
	public String write_post(@ModelAttribute ReBoardVO vo,
			HttpServletRequest request) {
		logger.info("글쓰기 처리, 파라미터 vo={}", vo);
		
		//파일 업로드 처리
		String fileName = "", originalFileName = "";
		long fileSize = 0;	// 초기값, 업로드를 안했을때, 파일업로드 정보는 한개도없는것
		try {
			List<Map<String, Object>> fileList
				= fileUploadUtil.fileUpload(request, ConstUtil.UPLOAD_FILE_FLAG);
			
			for(Map<String, Object> fileMap : fileList) {
				//다중 파일 업로드 처리 해야함 (나중에)
				
				originalFileName = (String)fileMap.get("originalFileName");
				fileName = (String)fileMap.get("fileName");
				fileSize = (long)fileMap.get("fileSize");
			}
			
			logger.info("파일 업로드 성공, fileName = {},  fileSize = {}",
					fileName, fileSize);
			
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		vo.setFileName(fileName);
		vo.setOriginalFileName(originalFileName);
		vo.setFileSize(fileSize);
		
		int cnt=reBoardService.insertReBoard(vo);
		logger.info("글쓰기 처리 결과, cnt={}", cnt);

		return "redirect:/reBoard/list.do";

		/*
	 write.do => ReBoardWriteController (write_post)
	 => ReBoardService (인터페이스) => 자식인 ReBoardServiceImpl의 메서드(insertReBoard)호출
	 => ReBoardDAO (인터페이스) => 자식인 ReBoardDAOMybatis의 메서드(insertReBoard)호출
		 */
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

		List<ReBoardVO> list=reBoardService.selectAll(searchVo);
		logger.info("글 목록 조회 결과, list.size={}", list.size());
		
		//totalRecord개수 구하기
		int totalRecord=reBoardService.getTotalRecord(searchVo);
		logger.info("글목록 totalRecord={}", totalRecord);
		
		pagingInfo.setTotalRecord(totalRecord);
		
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "/reBoard/list";
		//=> http://localhost:9091/herb/reBoard/list.do
	}
	
	@RequestMapping("/countUpdate.do")
	public String countUpdate(@RequestParam(defaultValue = "0") int no,
			Model model) {
		logger.info("조회수 증가, 파라미터 no={}",no);

		if(no==0) {
			model.addAttribute("msg", "잘못된 url!");
			model.addAttribute("url", "/reBoard/list.do");
			return "/common/message";
		}

		int cnt=reBoardService.updateCount(no);
		logger.info("조회수 증가 결과, cnt={}", cnt);

		return "redirect:/reBoard/detail.do?no="+no;
	}

	@RequestMapping("/detail.do")
	public String detail(@RequestParam(defaultValue = "0") int no,
			 HttpServletRequest request, Model model) {
		logger.info("상세보기 파라미터 no={}", no);

		if(no==0) {
			model.addAttribute("msg", "잘못된 url!");
			model.addAttribute("url", "/reBoard/list.do");
			return "/common/message";
		}

		ReBoardVO vo=reBoardService.selectByNo(no);
		logger.info("상세보기 결과, vo={}", vo);
		
		// 업로드된 파일 정보
		String fileInfo = fileUploadUtil.getFileInfo(vo, request);

		model.addAttribute("vo", vo);
		model.addAttribute("fileInfo", fileInfo);

		return "/reBoard/detail";
	}

	@RequestMapping(value = "/edit.do", method = RequestMethod.GET)
	public String edit_get(@RequestParam(defaultValue = "0") int no,
			Model model) {
		logger.info("글 수정 페이지, 파라미터 no = {}", no);

		if(no == 0) {
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");
			return "/common/message";
		}

		ReBoardVO vo = reBoardService.selectByNo(no);
		logger.info("수정할 글 상세보기 결과 vo = {}", vo);

		model.addAttribute("vo", vo);

		return "/reBoard/edit";
	}

	@RequestMapping(value="/edit.do", method=RequestMethod.POST)
	public String edit_post(@ModelAttribute ReBoardVO vo,
			@RequestParam String oldFileName,	// 얘는 vo에 못들어가니 따로 받아와야함
			HttpServletRequest request, Model model) {
		logger.info("수정 처리, 파라미터 vo={}", vo);
		
		String msg="비밀번호 체크 실패!", url="/reBoard/edit.do?no="+vo.getNo();
		if(reBoardService.checkPwd(vo.getNo(), vo.getPwd())) {
			//파일 업로드 처리
			String fileName = "", originalFileName = "";
			long fileSize = 0;
			List<Map<String, Object>> fileList = null;
			
			try {
				fileList = fileUploadUtil.fileUpload(request, ConstUtil.UPLOAD_FILE_FLAG);
				
				for(Map<String, Object> fileMap : fileList) {
					fileName = (String)fileMap.get("fileName");
					originalFileName = (String)fileMap.get("originalFileName");
					fileSize = (long)fileMap.get("fileSize");				
				}//for
				
				logger.info("글수정-파일 업로드 성공");
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			vo.setFileName(fileName);
			vo.setOriginalFileName(originalFileName);
			vo.setFileSize(fileSize);
			
			int cnt=reBoardService.updateReBoard(vo);
			logger.info("글 수정 처리 결과, cnt={}", cnt);
			
			if(cnt>0) {
				msg="글 수정되었습니다.";
				url="/reBoard/detail.do?no="+vo.getNo();
				
				// 새로 파일을 첨부했을때, 기존에 첨부된파일이 이미 있다면, 기존파일 삭제처리
				if(!fileList.isEmpty()){	// 새로 파일을 첨부한 경우
					if(oldFileName != null && !oldFileName.isEmpty()) { 
						// 기존에 파일첨부가 되어있는경우
						String uploadPath = fileUploadUtil.getUploadPath(request,
								ConstUtil.UPLOAD_FILE_FLAG);
						File oldFile = new File(uploadPath, oldFileName);
						if(oldFile.exists()) {
							boolean bool = oldFile.delete();
							logger.info("글수정-파일 삭제여부 : {}", bool);
						}
					}
				}
			}else {
				msg="글 수정 실패";
			}
		}else {
			msg="비밀번호 불일치!";			
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping(value="/delete.do", method=RequestMethod.GET)
	public String delete_get(@RequestParam(defaultValue = "0") int no,
			@ModelAttribute ReBoardVO vo,
			Model model) {
		logger.info("삭제 처리 화면 보기, 파라미터 no={}, vo={}",no, vo);

		if(no==0) {
			model.addAttribute("msg", "잘못된 url");
			model.addAttribute("url", "/reBoard/list.do");
			return "/common/message";
		}

		return "/reBoard/delete";
	}

	@RequestMapping(value="/delete.do", method=RequestMethod.POST)
	public String delete_post(@ModelAttribute ReBoardVO vo, 
			HttpServletRequest request, Model model) {
		logger.info("삭제 처리, 파라미터 vo = {}", vo);

		String msg="비밀번호 체크 실패",url="/reBoard/delete.do?no="+vo.getNo()
			+ "&groupNo=" + vo.getGroupNo() + "&step=" + vo.getStep()
			+ "&fileName=" + vo.getFileName(); 
		if(reBoardService.checkPwd(vo.getNo(), vo.getPwd())) {
			Map<String, String> map = new HashMap<>();
			map.put("no", vo.getNo()+"");	// ""를 붙인 이유는 String으로 형변환 하기위해
			map.put("groupNo", vo.getGroupNo()+"");
			map.put("step", vo.getStep()+"");
			
			reBoardService.deleteReBoard(map);

			msg="글 삭제 완료";
			url="/reBoard/list.do";
				
			// 게시물 삭제 성공했을때, 웹에 저장된 파일까지 삭제하는 기능
			String uploadPath = fileUploadUtil.getUploadPath(request, 
					ConstUtil.UPLOAD_FILE_FLAG);
			File delFile = new File(uploadPath, vo.getFileName());
			if(delFile.exists()) {
				// 파일을 삭제하는, File클래스의 delete()메소드
				boolean bool = delFile.delete();
				logger.info("파일 삭제 여부 : {}", bool);
			}

		}else {
			msg="비밀번호 불일치";
		}
		
		model.addAttribute("msg",msg);
		model.addAttribute("url",url);

		return "common/message";
	}
	
	@RequestMapping("/download.do")
	public ModelAndView download(@RequestParam(defaultValue = "0") int no,
				@RequestParam String fileName, HttpServletRequest request) {
		//DB에서 다운로드 수 증가시키고, 다운로드 창을 띄우는 뷰 페이지로 넘긴다.
		logger.info("다운로드 처리, 파라미터 no = {}, fileName = {}", no, fileName);
		
		int cnt = reBoardService.updateDownCount(no);
		logger.info("다운로드 수 증가 결과, cnt = {}", cnt);
		
		//다운로드할 File 객체 만들어서 ModelAndView에 저장해서 리턴
		Map<String, Object> map = new HashMap<>();
		String uploadPath = fileUploadUtil.getUploadPath(request,
				ConstUtil.UPLOAD_FILE_FLAG);
		File file = new File(uploadPath, fileName);
		map.put("file", file);
		
		ModelAndView mav = new ModelAndView("reBoardDownloadView", map);
		//=> ModelAndView(String viewName, Map<Strimg, 0> model)
		
		return mav;
	}
	
	@GetMapping("/reply.do")
	public String reply_get(@RequestParam(defaultValue = "0") int no,
			Model model) {
		logger.info("답변하기 페이지, 파라미터 no = {}", no);
		
		if(no == 0) {
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");
			return "/common/message";
		}
		
		ReBoardVO vo = reBoardService.selectByNo(no);
		logger.info("답변하기 페이지 원본글 정보 불러오기, 파라미터 vo = {}", vo);
		
		model.addAttribute("vo", vo);
		
		return "/reBoard/reply";
	}
	
	@PostMapping("/reply.do")
	public String reply_post(@ModelAttribute ReBoardVO vo
			) {
		logger.info("답변 처리, 파라미터 vo = {}", vo);
		
		int cnt = reBoardService.reply(vo);
		logger.info("답변 처리 결과, cnt = {}", cnt);
		
		
		return "redirect:/reBoard/list.do";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
