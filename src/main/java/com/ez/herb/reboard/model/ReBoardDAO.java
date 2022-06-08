package com.ez.herb.reboard.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ez.herb.common.SearchVO;

@Mapper
public interface ReBoardDAO {
	// 인터페이스는 앞에 생략해도 언제나 같은 설정임
	public int insertReBoard(ReBoardVO vo);	
	int getTotalRecord(SearchVO searchVo);
	public List<ReBoardVO> selectAll(SearchVO searchVo);
	public ReBoardVO selectByNo(int no);
	public int updateCount(int no);
	String selectPwd(int no);
	public int updateReBoard(ReBoardVO vo);
	public void deleteReBoard(Map<String, String> map);
	int updateDownCount(int no);
	int updateSortNo(ReBoardVO vo);
	int reply(ReBoardVO vo);
	
/*
	public int insertReReBoard(ReReBoardVO vo) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;

		try {
			//1,2
			con=pool.getConnection();

			//3
			String sql="insert into reReBoard(no, name, pwd, title, email, "
					+ "content, groupNo, filename, originalFilename,filesize)"
					+ "values(reReBoard_seq.nextval, ?,?,?,?,?"
					+ ",reReBoard_seq.nextval,?,?,?)";
			ps=con.prepareStatement(sql);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getPwd());
			ps.setString(3, vo.getTitle());
			ps.setString(4, vo.getEmail());
			ps.setString(5, vo.getContent());
			ps.setString(6, vo.getFileName());
			ps.setString(7, vo.getOriginalFileName());
			ps.setLong(8, vo.getFileSize());

			//4
			int cnt=ps.executeUpdate();
			System.out.println("글등록 결과 cnt="+cnt+", 매개변수 vo="+vo);

			return cnt;
		}finally {
			pool.dbClose(ps, con);
		}
	}

	public List<ReReBoardVO> selectAll(String condition, String keyword) 
				throws SQLException {
		
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		List<ReReBoardVO> list=new ArrayList<ReReBoardVO>();
		try {
			//1,2
			con=pool.getConnection();

			//3
			String sql="select * from reReBoard";
			if(keyword!=null && !keyword.isEmpty()) {
				sql+=" where "+condition+" like '%' || ? || '%'";
			}
			sql+=" order by groupno desc, sortno";
			ps=con.prepareStatement(sql);
				
			if(keyword!=null && !keyword.isEmpty()) {
				ps.setString(1, keyword);
			}
			
			//4
			rs=ps.executeQuery();
			while(rs.next()) {
				int no=rs.getInt("no");
				int readcount=rs.getInt("readcount");
				String name=rs.getString("name");
				String pwd=rs.getString("pwd");
				String title=rs.getString("title");
				String email=rs.getString("email");
				String content=rs.getString("content");
				Timestamp regdate=rs.getTimestamp("regdate");

				//
				int groupNo=rs.getInt("groupno");
				int step=rs.getInt("step");
				int sortNo=rs.getInt("sortno");
				String delflag=rs.getString("delflag");
				
				//
				String filename=rs.getString("filename");
				String originalFilename=rs.getString("originalFilename");
				long fileSize=rs.getLong("filesize");
				int downcount=rs.getInt("downcount");
				
				ReReBoardVO vo = new ReReBoardVO(no, name, pwd, title, email, 
						regdate, readcount, content, groupNo, step, 
						sortNo, delflag, filename, fileSize, 
						downcount, originalFilename);
				
				list.add(vo);
			}

			System.out.println("전체 조회 결과 list.size="+list.size()
				+", 매개변수 condition="+condition+", keyword="
				+ keyword);
			
			return list;
		}finally {
			pool.dbClose(rs, ps, con);
		}
	}

	public ReReBoardVO selectByNo(int no) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		ReReBoardVO vo = new ReReBoardVO();
		try {
			//1,2
			con=pool.getConnection();

			//3
			String sql="select * from reReBoard where no=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, no);

			//4
			rs=ps.executeQuery();
			if(rs.next()) {
				vo.setNo(no);
				vo.setTitle(rs.getString("title"));
				vo.setContent(rs.getString("content"));
				vo.setName(rs.getString("name"));
				vo.setEmail(rs.getString("email"));
				vo.setPwd(rs.getString("pwd"));
				vo.setRegdate(rs.getTimestamp("regdate"));
				vo.setReadcount(rs.getInt("readcount"));
				
				//
				vo.setGroupNo(rs.getInt("groupno"));
				vo.setStep(rs.getInt("step"));
				vo.setSortNo(rs.getInt("sortno"));
				vo.setDelFlag(rs.getString("delflag"));	
				
				//
				vo.setFileName(rs.getString("filename"));
				vo.setOriginalFileName(rs.getString("originalFilename"));
				vo.setFileSize(rs.getLong("filesize"));
				vo.setDownCount(rs.getInt("downcount"));
			}

			System.out.println("글 상세보기 결과 vo="+vo+", 매개변수 no="+no);

			return vo;
		}finally {
			pool.dbClose(rs, ps, con);
		}
	}

	public int updateReReBoard(ReReBoardVO vo) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;

		try {
			//1,2
			con=pool.getConnection();

			//3
			String sql="update reReBoard"
					+ " set name=?, title=?, email=?, content=?";
			
			if(vo.getFileName()!=null &&!vo.getFileName().isEmpty()) {
				sql+=", filename=?, filesize=?, originalFilename=?";
			}
			sql+= " where no=?";
			ps=con.prepareStatement(sql);
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getTitle());
			ps.setString(3, vo.getEmail());
			ps.setString(4, vo.getContent());
			
			if(vo.getFileName()!=null &&!vo.getFileName().isEmpty()) {
				ps.setString(5, vo.getFileName());
				ps.setLong(6, vo.getFileSize());
				ps.setString(7, vo.getOriginalFileName());
				ps.setInt(8, vo.getNo());
			}else {
				ps.setInt(5, vo.getNo());			
			}
			
			//4
			int cnt=ps.executeUpdate();
			System.out.println("글수정 결과 cnt="+cnt+", 매개변수 vo="+vo);

			return cnt;
		}finally {
			pool.dbClose(ps, con);
		}
	}

	public void deleteReReBoard(ReReBoardVO vo) throws SQLException {
		Connection con=null;
		CallableStatement ps=null;

		try {
			con=pool.getConnection();

			String sql="call rereBoardDelete(?,?,?)";
			ps=con.prepareCall(sql);
			ps.setInt(1, vo.getGroupNo());
			ps.setInt(2, vo.getStep());
			ps.setInt(3, vo.getNo());

			boolean bool=ps.execute();
			System.out.println("글 삭제 결과 bool="+bool+", 매개변수 vo="+vo);
		}finally {
			pool.dbClose(ps, con);
		}
	}
	
	public int updateCount(int no) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;

		try {
			con=pool.getConnection();

			String sql="update reReBoard set readcount=readcount+1"
		               + " where no=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, no);

			int cnt=ps.executeUpdate();
			System.out.println("조회수 증가 결과 cnt="+cnt+", 매개변수 no="+no);

			return cnt;
		}finally {
			pool.dbClose(ps, con);
		}
	}
	
	public int reply(ReReBoardVO vo) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		int cnt=0;
		
		try {
			//1,2
			con=pool.getConnection();

			//트랜잭션 시작
			con.setAutoCommit(false);
			
			String sql="update rereBoard"
					+ " set sortno=sortno+1"
					+ " where groupno=? and sortno>?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, vo.getGroupNo());
			ps.setInt(2, vo.getSortNo());
			
			cnt=ps.executeUpdate();
			System.out.println("sortNo update 결과 cnt="+cnt);
			
			//3
			sql="insert into reReBoard(no, name, pwd, title, email, "
					+ " content, groupNo, step, sortno)"
					+ " values(reReBoard_seq.nextval, ?,?,?,?,?,?,?,?)";
			ps=con.prepareStatement(sql);
			
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getPwd());
			ps.setString(3, vo.getTitle());
			ps.setString(4, vo.getEmail());
			ps.setString(5, vo.getContent());
			ps.setInt(6, vo.getGroupNo());
			ps.setInt(7, vo.getStep()+1);
			ps.setInt(8, vo.getSortNo()+1);
			
			//4
			cnt=ps.executeUpdate();
			System.out.println("답변등록 결과 cnt="+cnt+", 매개변수 vo="+vo);
			
			//커밋
			con.commit();
		}catch (SQLException e) {
			//롤백
			con.rollback();
			e.printStackTrace();
		}finally {
			con.setAutoCommit(true);
			
			pool.dbClose(ps, con);
		}
	
		return cnt;
	}
	
	public boolean checkPwd(int no, String pwd) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;

		boolean bool=false;
		try {
			//1,2
			con=pool.getConnection();

			//3
			String sql="select pwd from reReBoard where no=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, no);

			//4
			rs=ps.executeQuery();
			if(rs.next()) {
				String dbPwd=rs.getString(1);
				if(pwd.equals(dbPwd)) {
					bool=true; //비번 일치
				}
			}

			System.out.println("비번 체크 결과 bool="+bool
					+", 매개변수 no="+no+", pwd="+pwd);

			return bool;
		}finally {
			pool.dbClose(rs, ps, con);
		}
		
	}
	
	public int updateDownCount(int no) throws SQLException {
		Connection con=null;
		PreparedStatement ps=null;

		try {
			con=pool.getConnection();

			String sql="update reReBoard set downcount=downcount+1"
		               + " where no=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, no);

			int cnt=ps.executeUpdate();
			System.out.println("다운로드수 증가 결과 cnt="+cnt+", 매개변수 no="+no);

			return cnt;
		}finally {
			pool.dbClose(ps, con);
		}
	}
	*/
	
}





