package com.ez.herb.reboard.model;

import java.util.List;
import java.util.Map;

import com.ez.herb.common.SearchVO;

public interface ReBoardService {
	// 인터페이스는 추상메소드와, 상수 밖에 가질수 없으므로
	// 메소드에는 public abstract가 자동으로 붙고
	// 멤버변수에는 public static final이 자동으로 붙음
	public int insertReBoard(ReBoardVO vo);
	int getTotalRecord(SearchVO searchVo);
	public List<ReBoardVO> selectAll(SearchVO searchVo);
	public ReBoardVO selectByNo(int no);
	public int updateCount(int no);
	public boolean checkPwd(int no, String pwd);
	public int updateReBoard(ReBoardVO vo);
	void deleteReBoard(Map<String, String> map); 
	int updateDownCount(int no);
	int reply(ReBoardVO vo);
}
