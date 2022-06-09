package com.ez.herb.board.model;

import java.util.List;

import com.ez.herb.common.SearchVO;

public interface BoardService {
	
	public int insertBoard(BoardVO vo);
	int getTotalRecord(SearchVO searchVo);
	public List<BoardVO> selectAll(SearchVO searchVo);
	public BoardVO selectByNo(int no);
	public int updateCount(int no);
	public boolean checkPwd(int no, String pwd);
	public int updateBoard(BoardVO vo);
	public int deleteBoard(int no);
	List<BoardVO> selectMainNotice();
}
