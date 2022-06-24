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
			} else {
				System.out.println("게시물 등록에 실패하였습니다");
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
			} else {
				System.out.println("변경에 실패하였습니다.");
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
			} else {
				System.out.println("게시물이 삭제되지않았습니다.");
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


}
