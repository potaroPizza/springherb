package com.ez.herb.reboard.model;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ez.herb.common.SearchVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReBoardServiceImpl implements ReBoardService{
	private static final Logger logger
		=LoggerFactory.getLogger(ReBoardServiceImpl.class);
	
	//생성자를 통한 주입
	private final ReBoardDAO reBoardDao;

	public int insertReBoard(ReBoardVO vo) {
		return reBoardDao.insertReBoard(vo);
	}

	@Override
	public int getTotalRecord(SearchVO searchVo) {
		return reBoardDao.getTotalRecord(searchVo);
	}

	@Override
	public List<ReBoardVO> selectAll(SearchVO searchVo) {
		return reBoardDao.selectAll(searchVo);
	}

	public ReBoardVO selectByNo(int no) {
		return reBoardDao.selectByNo(no);
	}

	public int updateCount(int no){
		return reBoardDao.updateCount(no);
	}

	public int updateReBoard(ReBoardVO vo) {
		return reBoardDao.updateReBoard(vo);
	}

	public boolean checkPwd(int no, String pwd) {
		String dbPwd= reBoardDao.selectPwd(no);

		boolean result=false;
		if(dbPwd.equals(pwd)) {
			result=true;  //비밀번호 일치
		}

		return result;
	}

	@Override
	public int updateDownCount(int no) {
		return reBoardDao.updateDownCount(no);
	}

	@Override
	public void deleteReBoard(Map<String, String> map) {
		reBoardDao.deleteReBoard(map);
	}

	@Override
	@Transactional	// 트렌잭션을 관리하고 싶을때 사용하는 어노테이션
	public int reply(ReBoardVO vo) {
		int cnt = reBoardDao.updateSortNo(vo);
		
		cnt = reBoardDao.reply(vo);
		
		
		return cnt;
	}
}
