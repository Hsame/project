package com.yedam.app.anonymous;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yedam.app.common.DAO;
import com.yedam.app.free.Freeboardcomment;
import com.yedam.app.noticeboard.Noticeboard;

public class AnonymousboardDAO extends DAO{
	
	// 싱글톤
	private static AnonymousboardDAO dao = null;

	private AnonymousboardDAO() {
	}

	public static AnonymousboardDAO getInstance() {
		if (dao == null) {
			dao = new AnonymousboardDAO();
		}
		return dao;
	}
	
	public void insert(Anonymousboard anonymousboard) {

		try {
			connect();
			String sql = "INSERT INTO anonymous VALUES (anonymous_next_seq.NEXTVAL, ?, ?, ?,SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anonymousboard.getTitle());
			pstmt.setString(2, anonymousboard.getContent());
			pstmt.setString(3, anonymousboard.getId());

			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("정상적으로 게시되었습니다.");
			} else {
				System.out.println("게시물 등록에 실패하였습니다");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void chboard(Anonymousboard anonymousboard) {
		try {
			connect();
			String sql = "UPDATE anonymous SET title = ?, content = ? WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anonymousboard.getTitle());
			pstmt.setString(2, anonymousboard.getContent());
			pstmt.setInt(3, anonymousboard.getNo());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("정상적으로 변경되었습니다.");
			} else {
				System.out.println("변경에 실패하였습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public Anonymousboard selectOne(int no) {
		Anonymousboard anonymousboard = null;
		try {
			connect();
			String sql = "SELECT * FROM anonymous WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				anonymousboard = new Anonymousboard();
				anonymousboard.setNo(rs.getInt("no"));
				anonymousboard.setTitle(rs.getString("title"));
				anonymousboard.setContent(rs.getString("content"));
				anonymousboard.setId(rs.getString("id"));
				anonymousboard.setRegdate(rs.getString("regdate"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return anonymousboard;
	}
	
	public void delete(int no) {
		try {
			connect();
			String sql = "DELETE FROM anonymous "
					+ "WHERE no = "+ "'" + no + "'"; 
			stmt = conn.createStatement();
			
			int result = stmt.executeUpdate(sql);
			
			if(result > 0) {
				System.out.println("게시물이 삭제되었습니다");
			} else {
				System.out.println("게시물이 삭제되지않았습니다.");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public List<Anonymousboard> selectAll() {
		List<Anonymousboard> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM anonymous");
			while (rs.next()) {
				Anonymousboard anonymousboard = new Anonymousboard();
				anonymousboard.setNo(rs.getInt("no"));
				anonymousboard.setTitle(rs.getString("title"));
				anonymousboard.setId(rs.getString("id"));
				anonymousboard.setRegdate(rs.getString("regdate"));
				
				list.add(anonymousboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	public List<Anonymousboard> searchboard(Anonymousboard anonymousboard) {
		List<Anonymousboard> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM notice WHERE title LIKE " + "'%" + anonymousboard.getTitle() + "%'");
			if (rs.next()) {
				anonymousboard = new Anonymousboard();
				anonymousboard.setNo(rs.getInt("no"));
				anonymousboard.setTitle(rs.getString("title"));
				anonymousboard.setContent(rs.getString("content"));
				anonymousboard.setId(rs.getString("id"));
				anonymousboard.setRegdate(rs.getString("regdate"));
				
				list.add(anonymousboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	public void insertcomment(Anonymousboardcomment anonymousboardcomment) {

		try {
			connect();
			String sql = "INSERT INTO comments (nono,comm, id, no) VALUES (anonycom_next_seq.NEXTVAL,?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(2, anonymousboardcomment.getId());
			pstmt.setString(1, anonymousboardcomment.getComment());
			pstmt.setInt(3, anonymousboardcomment.getNo());
			
			int result = pstmt.executeUpdate();

			if (result > 0) {
				System.out.println("정상적으로 게시되었습니다.\n");
			} else {
				System.out.println("게시물 등록에 실패하였습니다\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public Anonymousboardcomment selectOnecomment(int no) {
		Anonymousboardcomment anonymousboardcomment = null;
		try {
			connect();
			String sql = "SELECT * FROM comments WHERE nono = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();		
			if(rs.next()) {
				anonymousboardcomment = new Anonymousboardcomment();
				anonymousboardcomment.setNo(rs.getInt("no"));
				anonymousboardcomment.setId(rs.getString("id"));
				anonymousboardcomment.setNono(rs.getInt("nono"));
				anonymousboardcomment.setComment(rs.getString("comm"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return anonymousboardcomment;
	}

	public void chcomment(Anonymousboardcomment anonymousboardcomment) {
		try {
			connect();
			String sql = "UPDATE comments SET comm = ? WHERE nono = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, anonymousboardcomment.getComment());
			pstmt.setInt(2, anonymousboardcomment.getNono());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("정상적으로 변경되었습니다.\n");
			} else {
				System.out.println("변경에 실패하였습니다.\n");
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
				System.out.println("게시물이 삭제되었습니다\n");
			} else {
				System.out.println("게시물이 삭제되지않았습니다.\n");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	public List<Anonymousboardcomment> comment(int no) {
		List<Anonymousboardcomment> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM comments WHERE no = " + "'" + no +"'");
			while (rs.next()) {
				Anonymousboardcomment anonymousboardcomment = new Anonymousboardcomment();	
				anonymousboardcomment = new Anonymousboardcomment();
				anonymousboardcomment.setNo(rs.getInt("no"));
				anonymousboardcomment.setId(rs.getString("id"));
				anonymousboardcomment.setNono(rs.getInt("nono"));
				anonymousboardcomment.setComment(rs.getString("comm"));
				
				list.add(anonymousboardcomment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}


}