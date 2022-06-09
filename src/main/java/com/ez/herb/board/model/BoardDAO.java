package com.ez.herb.board.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ez.herb.common.SearchVO;

@Mapper
public interface BoardDAO {
	public int insertBoard(BoardVO vo);
	int getTotalRecord(SearchVO searchVo);
	public List<BoardVO> selectAll(SearchVO searchVo);
	public BoardVO selectByNo(int no);
	public int updateCount(int no);
	String selectPwd(int no);
	public int updateBoard(BoardVO vo);
	public int deleteBoard(int no);
	List<BoardVO> selectMainNotice();
}
