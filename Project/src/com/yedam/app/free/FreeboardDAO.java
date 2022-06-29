package com.yedam.app.free;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public void chboard(Freeboard freeboard) {
		try {
			connect();
			String sql = "UPDATE free SET title = ?, content = ? WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, freeboard.getTitle());
			pstmt.setString(2, freeboard.getContent());
			pstmt.setInt(3, freeboard.getNo());

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

	public Freeboard selectOne(int no) {
		Freeboard freeboard = null;
		try {
			connect();
			String sql = "SELECT * FROM free WHERE no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				freeboard = new Freeboard();
				freeboard.setNo(rs.getInt("no"));
				freeboard.setTitle(rs.getString("title"));
				freeboard.setContent(rs.getString("content"));
				freeboard.setId(rs.getString("id"));
				freeboard.setRegdate(rs.getString("regdate"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return freeboard;
	}

	public List<Freeboard> searchboard(Freeboard freeboard) {
		List<Freeboard> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM free WHERE title LIKE " + "'%" + freeboard.getTitle() + "%'");
			while (rs.next()) {
				freeboard = new Freeboard();
				freeboard.setNo(rs.getInt("no"));
				freeboard.setTitle(rs.getString("title"));
				freeboard.setContent(rs.getString("content"));
				freeboard.setId(rs.getString("id"));
				freeboard.setRegdate(rs.getString("regdate"));
				
				list.add(freeboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	public List<Freeboard> five() {
		List<Freeboard> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM free WHERE ROWNUM < 6");
			while (rs.next()) {
				Freeboard freeboard = new Freeboard();
				freeboard.setNo(rs.getInt("no"));
				freeboard.setTitle(rs.getString("title"));
				freeboard.setId(rs.getString("id"));
				freeboard.setRegdate(rs.getString("regdate"));

				list.add(freeboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}
	
	public List<Freeboard> next(int firstPage, int lastPage) {
		List<Freeboard> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM (SELECT ROWNUM num, no,title,content,id,regdate FROM FREE) "
					+ "WHERE num BETWEEN " + firstPage+" and "+lastPage + 
					"ORDER BY ROWNUM");
			while (rs.next()) {
				Freeboard freeboard = new Freeboard();
				freeboard.setNo(rs.getInt("no"));
				freeboard.setTitle(rs.getString("title"));
				freeboard.setId(rs.getString("id"));
				freeboard.setRegdate(rs.getString("regdate"));

				list.add(freeboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public void delete(int no) {
		try {
			connect();
			String sql = "DELETE FROM free " + "WHERE no = " + "'" + no + "'";
			stmt = conn.createStatement();

			int result = stmt.executeUpdate(sql);

			if (result > 0) {
				System.out.println("게시물이 삭제되었습니다\n");
			} else {
				System.out.println("게시물이 삭제되지않았습니다.\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public List<Freeboard> selectAll() {
		List<Freeboard> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM free ORDER BY ROWNUM");
			while (rs.next()) {
				Freeboard freeboard = new Freeboard();
				freeboard.setNo(rs.getInt("no"));
				freeboard.setTitle(rs.getString("title"));
				freeboard.setId(rs.getString("id"));
				freeboard.setRegdate(rs.getString("regdate"));

				list.add(freeboard);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public void insertcomment(Freeboardcomment freeboardcomment) {

		try {
			connect();
			String sql = "INSERT INTO comments (nono,comm, id, no) VALUES (freecom_next_seq.NEXTVAL,?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(2, freeboardcomment.getId());
			pstmt.setString(1, freeboardcomment.getComment());
			pstmt.setInt(3, freeboardcomment.getNo());

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

	public Freeboardcomment selectOnecomment(int no) {
		Freeboardcomment freeboardcomment = null;
		try {
			connect();
			String sql = "SELECT * FROM comments WHERE nono = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				freeboardcomment = new Freeboardcomment();
				freeboardcomment.setNo(rs.getInt("no"));
				freeboardcomment.setId(rs.getString("id"));
				freeboardcomment.setNono(rs.getInt("nono"));
				freeboardcomment.setComment(rs.getString("comm"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return freeboardcomment;
	}

	public void chcomment(Freeboardcomment freeboardcomment) {
		try {
			connect();
			String sql = "UPDATE comments SET comm = ? WHERE nono = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, freeboardcomment.getComment());
			pstmt.setInt(2, freeboardcomment.getNono());

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
			String sql = "DELETE FROM comments " + "WHERE nono = " + "'" + no + "'";
			stmt = conn.createStatement();

			int result = stmt.executeUpdate(sql);

			if (result > 0) {
				System.out.println("댓글이 삭제되었습니다\n");
			} else {
				System.out.println("댓글이 삭제되지않았습니다.\n");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public List<Freeboardcomment> comment(int no) {
		List<Freeboardcomment> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM comments WHERE no = " + "'" + no + "'");
			while (rs.next()) {
				Freeboardcomment freeboardcomment = new Freeboardcomment();
				freeboardcomment = new Freeboardcomment();
				freeboardcomment.setNo(rs.getInt("no"));
				freeboardcomment.setId(rs.getString("id"));
				freeboardcomment.setNono(rs.getInt("nono"));
				freeboardcomment.setComment(rs.getString("comm"));

				list.add(freeboardcomment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

}
