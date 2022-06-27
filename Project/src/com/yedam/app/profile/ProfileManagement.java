package com.yedam.app.profile;

import java.util.List;
import com.yedam.app.common.Management;
import com.yedam.app.member.Member;

public class ProfileManagement extends Management {
	protected ProfileDAO pDAO = ProfileDAO.getInstance();

	public ProfileManagement() {
		boolean role = selectRole();
		while (true) {
			menuPrint(role);
			System.out.print("메뉴 선택 > ");
			int menuNo = menuSelect();
			if (menuNo == 1) {
				// 회원 전체조회
				memberAll();
			} else if (menuNo == 2) {
				// 회원 부분 조회
				memberSelect();
			} else if (menuNo == 3) {
				// 회원 정보 수정 - 비밀번호
				changePasswd();
			} else if (menuNo == 4) {
				// 회원 정보 삭제
				deletemember();
			} else if (menuNo == 5) {
				back();
				break;
			} else {
				showInputError();
			}
		}
	}

	@Override
	protected void menuPrint(boolean role) {
		System.out.println("----------------------------------------------------------------------------");
		System.out.println();
		System.out.println("1.회원 전체조회  ::  2.회원 부분조회  ::  3.회원 비밀번호 수정  ::  4.회원 삭제  ::  5.뒤로가기");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------\n");
	}

	private void memberAll() {
		List<Member> list = pDAO.selectAll();
		for (Member member : list) {
			System.out.println(member);
		}
	}

	private void memberSelect() {
		Member info = new Member();
		System.out.print("0 : 관리자 1 : 일반회원 3 : 종료> ");
		info.setMemberRole(Integer.parseInt(sc.nextLine()));
		if (info.getMemberRole() == 3) {
			System.out.println("이전으로 돌아갑니다");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		List<Member> list = pDAO.selectOne(info);
		for (Member member : list) {
			System.out.println(member);
		}
	}

	private String inputmemberid() {
		System.out.print("회원 아이디 입력(0을 입력시 종료) > ");
		return sc.nextLine();
	}

	private void changePasswd() {
		// 수정정보 입력
		Member member = new Member();
		System.out.print("아이디(0을 입력시 종료) > ");
		member.setMemberId(sc.nextLine());
		if (member.getMemberId().equals("0")) {
			return;
		}
		if (member.getMemberId() == null || member.getMemberId().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		System.out.print("변경할 비밀번호(0을 입력시 종료) > ");
		member.setMemberPassword(sc.nextLine());
		if (member.getMemberPassword().equals("0")) {
			return;
		}
		if (member.getMemberPassword() == null || member.getMemberPassword().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		// DB 전달
		pDAO.chprofile(member);
	}

	private void deletemember() {
		// 수정정보 입력
		String member = inputmemberid();
		if (member.equals("0")) {
			return;
		} else {
			// DB 전달
			pDAO.delete(member);
		}
	}

	private void back() {
		System.out.println("이전 메뉴로 돌아갑니다");
	}

}
