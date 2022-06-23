package com.yedam.app.free;

import java.sql.SQLException;

import com.yedam.app.common.DAO;

public class FreeboardDAO extends DAO {
	// 싱글톤
	private static FreeboardDAO dao = null;

	private FreeboardDAO() {
	}

	public static FreeboardDAO getInstance() {
		if (dao == null) {
			dao = new FreeboardDAO();
		}
		return dao;
	}

	public void insert(Freeboard freeboard) {

		try {
			connect();
			String sql = "INSERT INTO free VALUES (free_next_seq.NEXTVAL, ?, ?, ?,SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, freeboard.getTitle());
			pstmt.setString(2, freeboard.getContent());
			pstmt.setString(3, freeboard.getId());

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

	public void chboard(Freeboard freeboard) {
		try {
			connect();
			String sql = "UPDATE free SET title = ?, content = ? WHERE no = ?;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, freeboard.getTitle());
			pstmt.setString(2, freeboard.getContent());
			pstmt.setInt(3, freeboard.getNo());

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
	
	public Freeboard selectOne(int no) {
		Freeboard freeboard = null;
		try {
			connect();
			String sql = "SELECT * FROM free WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				freeboard = new Freeboard();
				freeboard.setNo(rs.getInt("no"));
				freeboard.setTitle(rs.getString("title"));
				freeboard.setContent(rs.getString("content"));
				freeboard.setId(rs.getString("id"));
				freeboard.setRegdate(rs.getString("regdate"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return freeboard;
	}
	
	public void delete(int no) {
		try {
			connect();
			String sql = "DELETE FROM free "
					+ "WHERE no = "+ "'" + no + "'"; 
			stmt = conn.createStatement();
			
			int result = stmt.executeUpdate(sql);
			
			if(result > 0) {
				System.out.println("회원정보가 삭제되었습니다");
			} else {
				System.out.println("회원정보가 삭제되지않았습니다.");
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

}
