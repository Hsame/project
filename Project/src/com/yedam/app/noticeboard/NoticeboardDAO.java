package com.yedam.app.noticeboard;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.app.common.DAO;

public class NoticeboardDAO extends DAO{
	
	// 싱글톤
	private static NoticeboardDAO dao = null;

	private NoticeboardDAO() {
	}

	public static NoticeboardDAO getInstance() {
		if (dao == null) {
			dao = new NoticeboardDAO();
		}
		return dao;
	}
	
	public void insert(Noticeboard noticeboard) {

		try {
			connect();
			String sql = "INSERT INTO notice VALUES (notice_next_seq.NEXTVAL, ?, ?, ?,SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticeboard.getTitle());
			pstmt.setString(2, noticeboard.getContent());
			pstmt.setString(3, noticeboard.getId());

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("정상적으로 게시되었습니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
			} else {
				System.out.println("게시물 등록에 실패하였습니다");
				System.out.println("----------------------------------------------------------------------------\n\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void chboard(Noticeboard noticeboard) {
		try {
			connect();
			String sql = "UPDATE notice SET title = ?, content = ? WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticeboard.getTitle());
			pstmt.setString(2, noticeboard.getContent());
			pstmt.setInt(3, noticeboard.getNo());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("정상적으로 변경되었습니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
			} else {
				System.out.println("변경에 실패하였습니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public Noticeboard selectOne(int no) {
		Noticeboard noticeboard = null;
		try {
			connect();
			String sql = "SELECT * FROM notice WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				noticeboard = new Noticeboard();
				noticeboard.setNo(rs.getInt("no"));
				noticeboard.setTitle(rs.getString("title"));
				noticeboard.setContent(rs.getString("content"));
				noticeboard.setId(rs.getString("id"));
				noticeboard.setRegdate(rs.getString("regdate"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return noticeboard;
	}
	
	public void delete(int no) {
		try {
			connect();
			String sql = "DELETE FROM notice "
					+ "WHERE no = "+ "'" + no + "'"; 
			stmt = conn.createStatement();
			
			int result = stmt.executeUpdate(sql);
			
			if(result > 0) {
				System.out.println("게시물이 삭제되었습니다");
				System.out.println("----------------------------------------------------------------------------\n\n");
			} else {
				System.out.println("게시물이 삭제되지않았습니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public List<Noticeboard> selectAll() {
		List<Noticeboard> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM notice");
			while (rs.next()) {
				Noticeboard noticeboard = new Noticeboard();
				noticeboard.setNo(rs.getInt("no"));
				noticeboard.setTitle(rs.getString("title"));
				noticeboard.setId(rs.getString("id"));
				noticeboard.setRegdate(rs.getString("regdate"));
				
				list.add(noticeboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	public void insertcomment(Noticeboardcomment noticeboardcomment) {

		try {
			connect();
			String sql = "INSERT INTO comments (nono,comm, id, no) VALUES (noticecom_next_seq.NEXTVAL,?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(2, noticeboardcomment.getId());
			pstmt.setString(1, noticeboardcomment.getComment());
			pstmt.setInt(3, noticeboardcomment.getNo());
			
			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("정상적으로 게시되었습니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
			} else {
				System.out.println("게시물 등록에 실패하였습니다");
				System.out.println("----------------------------------------------------------------------------\n\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public Noticeboardcomment selectOnecomment(int no) {
		Noticeboardcomment noticeboardcomment = null;
		try {
			connect();
			String sql = "SELECT * FROM comments WHERE nono = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();		
			if(rs.next()) {
				noticeboardcomment = new Noticeboardcomment();
				noticeboardcomment.setNo(rs.getInt("no"));
				noticeboardcomment.setId(rs.getString("id"));
				noticeboardcomment.setNono(rs.getInt("nono"));
				noticeboardcomment.setComment(rs.getString("comm"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return noticeboardcomment;
	}

	public void chcomment(Noticeboardcomment noticeboardcomment) {
		try {
			connect();
			String sql = "UPDATE comments SET comm = ? WHERE nono = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, noticeboardcomment.getComment());
			pstmt.setInt(2, noticeboardcomment.getNono());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("정상적으로 변경되었습니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
			} else {
				System.out.println("변경에 실패하였습니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public void deletecomment(int no) {
		try {
			connect();
			String sql = "DELETE FROM comments "
					+ "WHERE nono = "+ "'" + no + "'"; 
			stmt = conn.createStatement();
			
			int result = stmt.executeUpdate(sql);
			
			if(result > 0) {
				System.out.println("댓글이 삭제되었습니다");
				System.out.println("----------------------------------------------------------------------------\n\n");
			} else {
				System.out.println("댓글이 삭제되지않았습니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public List<Noticeboardcomment> comment(int no) {
		List<Noticeboardcomment> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM comments WHERE no = " + "'" + no +"'");
			while (rs.next()) {
				Noticeboardcomment noticeboardcomment = new Noticeboardcomment();	
				noticeboardcomment = new Noticeboardcomment();
				noticeboardcomment.setNo(rs.getInt("no"));
				noticeboardcomment.setId(rs.getString("id"));
				noticeboardcomment.setNono(rs.getInt("nono"));
				noticeboardcomment.setComment(rs.getString("comm"));
				
				list.add(noticeboardcomment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	public List<Noticeboard> searchboard(Noticeboard noticeboard) {
		List<Noticeboard> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM notice WHERE title LIKE " + "'%" + noticeboard.getTitle() + "%'");
			while (rs.next()) {
				noticeboard = new Noticeboard();
				noticeboard.setNo(rs.getInt("no"));
				noticeboard.setTitle(rs.getString("title"));
				noticeboard.setContent(rs.getString("content"));
				noticeboard.setId(rs.getString("id"));
				noticeboard.setRegdate(rs.getString("regdate"));
				
				list.add(noticeboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	public List<Noticeboard> next(int firstPage, int lastPage) {
		List<Noticeboard> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM (SELECT ROWNUM num, no,title,content,id,regdate FROM notice) WHERE num BETWEEN " + firstPage+" and "+lastPage + "ORDER BY ROWNUM");
			while (rs.next()) {
				Noticeboard noticeboard = new Noticeboard();
				noticeboard.setNo(rs.getInt("no"));
				noticeboard.setTitle(rs.getString("title"));
				noticeboard.setId(rs.getString("id"));
				noticeboard.setRegdate(rs.getString("regdate"));

				list.add(noticeboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}


}
