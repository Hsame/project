package com.yedam.app.common;

import java.util.Scanner;

import com.yedam.app.member.Member;
import com.yedam.app.member.MembersDAO;

public class LoginControl {
	Scanner sc = new Scanner(System.in);
	protected MembersDAO mDAO = MembersDAO.getInstance();
	public static Member loginInfo = null;

	public static Member getLoginInfo() {
		return loginInfo;
	}

	public LoginControl() {
		while (true) {
			menuPrint();
			System.out.print("\n메뉴 선택 > ");
			int menuNo = menuSelect();

			if (menuNo == 1) {
				// 로그인
				login();
			} else if (menuNo == 2) {
				// 회원가입
				signup();
			} else if (menuNo == 3) {
				// 종료
				exit();
				break;
			} else {
				showInputError();
			}
		}
	}

	private void menuPrint() {
		System.out.println("----------------------------------------------------------------------------");
		System.out.println();
		System.out.println("                       1.로그인  ::  2.회원가입  ::  3.종료");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------");
	}

	private int menuSelect() {
		int menuNo = 0;
		try {
			menuNo = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("메뉴를 형식을 제대로 입력해주세요");
		}
		return menuNo;

	}

	private void exit() {
		System.out.println("프로그램을 종료합니다.");
	}

	private void showInputError() {
		System.out.println("메뉴를 확인해주세요");
	}

	private void login() {
		Member inputInfo = inputMember();
		loginInfo = MembersDAO.getInstance().selectOne(inputInfo);
		if (loginInfo == null)
			return;
		new Management().run(loginInfo);
	}

	private Member inputMember() {
		Member info = new Member();
			System.out.print("아이디 > ");
			info.setMemberId(sc.nextLine());
			System.out.print("비밀번호 > ");
			info.setMemberPassword(sc.nextLine());
		return info;
	}

	private void signup() {
		Member member = inputMember();
		// DB에 저장
		mDAO.insert(member);
	}
}
