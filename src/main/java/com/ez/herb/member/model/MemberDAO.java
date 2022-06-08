package com.ez.herb.member.model;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
	public int insertMember(MemberVO vo);
	public int duplicateId(String userid);
	String selectPwd(String userid);
	MemberVO selectByUserid(String userid);
	int updateMember(MemberVO vo);
	int deleteMember(String userid);
	
	/*
	 
	
	public int deleteMember(String userid) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		
		try {
			con = pool.getConnection();
			
			String sql = "update member "
					+ "set outdate = sysdate "
					+ "where userid = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, userid);
	
			int cnt = ps.executeUpdate();
			
			System.out.println("회원탈퇴 결과 cnt = " + cnt
					+ ", 매개변수 userid = " + userid);
			
			return cnt;
			
		} finally {
			pool.dbClose(ps, con);
		}
	}
	  
	 */
	
}
