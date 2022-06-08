package com.ez.herb.member.model;

public interface MemberService {
	
	//아이디 중복확인에서 사용하는 상수
	public static final int USABLE_ID=1;  //사용가능한 아이디
	public static final int UNUSABLE_ID=2; //이미 존재해서 사용불가능한 아이디
	
	//로그인 체크에서 사용하는 상수
	public static final int LOGIN_OK=1;  //로그인 성공
	public static final int DISAGREE_PWD=2;  //비번 불일치
	int NONE_USERID=3;  //해당 아이디 없다
	//=> 당연히, 인터페이스에서는 변수의 종류는 단 한개 이므로
	//=> public static final 생략가능 (컴파일 과정에서 자동으로 붙는다는 의미)
	
	//=> 마찬가지로, 당연히 인터페이스의 메소드 종류는 단 하나 이므로
	//=> public abstract 생략가능(컴파일 과정에서 자동으로 붙는다는 의미)
	public int insertMember(MemberVO vo);
	public int duplicateId(String userid);
	public int checkLogin(String userid, String pwd);
	public MemberVO selectByUserid(String userid);
	int updateMember(MemberVO vo);
	int deleteMember(String userid);
}