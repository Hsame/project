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
			} else if (menuNo == 5){ 
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
		for (Member book : list) {
			System.out.println(book);
		}
	}

	private void memberSelect() {
		String memberId = inputmemberid();
		// DB 검색
		Member member = pDAO.selectOne(memberId);
		// 결과 출력
		if (member != null) {
			System.out.println(member);
		} else {
			System.out.println("원하는 정보가 존재하지 않습니다");
		}
	}

	private String inputmemberid() {
		System.out.print("회원 아이디 > ");
		return sc.nextLine();
	}

	private void changePasswd() {
		// 수정정보 입력
		Member member = inputUpdateInfo();
		// DB 전달
		pDAO.chprofile(member);
	}

	private Member inputUpdateInfo() {
		Member member = new Member();
		System.out.println("아이디 > ");
		member.setMemberId(sc.nextLine());
		System.out.println("변경할 비밀번호 > ");
		member.setMemberPassword(sc.nextLine());
		return member;
	}

	private void deletemember() {
		// 수정정보 입력
		String member = inputmemberid();
		// DB 전달
		pDAO.delete(member);
	}
	
	private void back() {
		System.out.println("이전 메뉴로 돌아갑니다");
	}

}
