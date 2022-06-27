package com.yedam.app.profile;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.yedam.app.common.DAO;
import com.yedam.app.member.Member;

public class ProfileDAO extends DAO {
	// 싱글톤
	private static ProfileDAO profileDAO = null;

	private ProfileDAO() {
	}

	public static ProfileDAO getInstance() {
		if (profileDAO == null) {
			profileDAO = new ProfileDAO();
		}
		return profileDAO;
	}

	// 전체 조회
	public List<Member> selectAll() {
		List<Member> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT * FROM members ORDER BY member_id";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Member member = new Member();
				member.setMemberId(rs.getString("member_id"));
				member.setMemberPassword(rs.getString("member_passwd"));
				member.setMemberRole(rs.getInt("member_role"));

				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

//	public Member selectOne(String memberId) {
//		Member member = null;
//		try {
//			connect();
//			String sql = "SELECT * FROM members WHERE member_role = ?";
//			pstmt = conn.prepareStatement(sql);
//			pstmt.setString(1, memberId);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				member = new Member();
//				member.setMemberId(rs.getString("member_id"));
//				member.setMemberPassword(rs.getString("member_passwd"));
//				member.setMemberRole(rs.getInt("member_role"));
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			disconnect();
//		}
//		return member;
//	}
	
	public List<Member> selectOne(Member member) {
		List<Member> list = new ArrayList<>();
		try {
			connect();
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * FROM members WHERE member_role =" + member.getMemberRole());
			
			while (rs.next()) {
			member = new Member();
			member.setMemberId(rs.getString("member_id"));
			member.setMemberPassword(rs.getString("member_passwd"));
			member.setMemberRole(rs.getInt("member_role"));
				
				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return list;
	}

	public void chprofile(Member member) {
		try {
			connect();
			String sql = "UPDATE members SET member_passwd = ? WHERE member_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getMemberPassword());
			pstmt.setString(2, member.getMemberId());
			
			int result = pstmt.executeUpdate();
			
				if (result > 0) {
					System.out.println("정상적으로 변경되었습니다.");
				} else {
					System.out.println("변경이 실패하였습니다.");
				}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	public void delete(String memberrole) {
		try {
			connect();
			String sql = "DELETE FROM members " + "WHERE member_id = " + "'" + memberrole + "'";
			stmt = conn.createStatement();

			int result = stmt.executeUpdate(sql);

			if (result > 0) {
				System.out.println("회원정보가 삭제되었습니다");
			} else {
				System.out.println("등록된 회원정보가 없습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

}
