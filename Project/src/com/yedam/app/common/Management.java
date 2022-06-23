package com.yedam.app.common;

import java.util.Scanner;

import com.yedam.app.free.FreeboardManagement;
import com.yedam.app.member.Member;
import com.yedam.app.profile.ProfileManagement;


public class Management {
	//필드
	protected Scanner sc = new Scanner(System.in);
	
	
	public void run(Member loginInfo) {		
		boolean role = selectRole();
		while (true) {
			menuPrint(role);
			int menuNo = menuSelect();
			if(menuNo == 1) {
				// 1. 공지사항
			} else if (menuNo == 2 ) {
				// 2. 자유게시판
				new FreeboardManagement(loginInfo);
			} else if (menuNo == 3 ) {
				// 3. 익명게시판
			} else if (menuNo == 0 ) {
				// 0. 회원정보 열람
				new ProfileManagement();
			} else if (menuNo == 9 ) {
				// 종료
				exit();
				break;
			} else {
				showInputError();
			}
		}
	}
	
	protected void menuPrint(boolean role) {
		String menu = "";
		if(role) {
			menu += "0.회원정보 열람 ";
		}
		menu += "1.공지사항 2.자유게시판 3.익명게시판 9.종료";
		System.out.println("==!==!==!==!==!==!==!==!==!====!==!==!==!==!==!==");
		System.out.println();
		System.out.println(menu);
		System.out.println();
		System.out.println("==!==!==!==!==!==!==!==!==!====!==!==!==!==!==!==");
	}

	protected int menuSelect() {
		int menuNo = 0;
		try {
			menuNo = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력해주시기 바랍니다.");
		}
		return menuNo;
	}

	protected void exit() {
		System.out.println("프로그램을 종료합니다.");
	}

	protected void showInputError() {
		System.out.println("메뉴에서 입력해주시기 바랍니다.");
	}
	
	protected boolean selectRole() {
		int memberRole = LoginControl.getLoginInfo().getMemberRole();
		if (memberRole == 0) {
			return true;
		} else {
			return false;
		}
	}

	
}
