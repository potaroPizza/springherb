package com.ez.herb.member.model;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberDAO memberDao;

	@Override
	public int insertMember(MemberVO vo) {
		return memberDao.insertMember(vo);
	}

	@Override
	public int duplicateId(String userid) {
		int count = memberDao.duplicateId(userid);
		int result = 0;
		
		if(count > 0) {	// 중복된 아이디 존재
			result = MemberService.UNUSABLE_ID;	// 사용불가
		} else {	// 중복된 아이디 없음
			result = MemberService.USABLE_ID;	// 사용가능
		}
		
		return result;
	}

	public int checkLogin(String userid, String pwd) {
		String dbPwd = memberDao.selectPwd(userid);
		
		int result = 0;
		
		if(dbPwd != null && !dbPwd.isEmpty()) {
			if(dbPwd.equals(pwd)) {
				result = MemberService.LOGIN_OK;
			} else {
				result = MemberService.DISAGREE_PWD;
			}
		} else {
			result = MemberService.NONE_USERID;
		}
		
		return result;
	}

	@Override
	public MemberVO selectByUserid(String userid) {
		return memberDao.selectByUserid(userid);
	}

	@Override
	public int updateMember(MemberVO vo) {
		return memberDao.updateMember(vo);
	}

	@Override
	public int deleteMember(String userid) {
		return memberDao.deleteMember(userid);
	}	
		
}
