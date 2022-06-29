package com.yedam.app.common;

import java.util.List;
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
	
	private Member equal(String id) {
		List<Member> list = mDAO.selectAll();
		for (Member member : list) {
			if(member.getMemberId().equals(id)) {
				return member;
			}
		}
		return null;
	}

    private boolean idCheck(String id) {
        boolean check = true;
        Member member = equal(id);
        if(member == null) {
            check = false;
        }
        return check;
    }
    
	private void login() {
		Member info = new Member();
		System.out.print("아이디 (0을 입력하면 종료)> ");
		info.setMemberId(sc.nextLine());	
		if (info.getMemberId().equals("0")) {
			System.out.println("이전으로 돌아갑니다 \n\n");
			return;
		}
		if (info.getMemberId() == null || info.getMemberId().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		System.out.print("비밀번호 (0을 입력하면 종료)> ");
		info.setMemberPassword(sc.nextLine());
		if (info.getMemberPassword().equals("0")) {
			System.out.println("이전으로 돌아갑니다 \n\n");
			return;
		}
		if (info.getMemberPassword() == null || info.getMemberPassword().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		loginInfo = MembersDAO.getInstance().selectOne(info);
		if (loginInfo == null)
			return;
		new Management().run(loginInfo);
	}

	private void signup() {
		Member info = new Member();
			System.out.print("아이디 (0을 입력하면 종료)> ");
			info.setMemberId(sc.nextLine());
	        if(idCheck(info.getMemberId())) {
	            System.out.println("중복된 ID입니다.");
	            return;
	        }
			if (info.getMemberId().equals("0")) {
				System.out.println("이전으로 돌아갑니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
				return;
			}
			if (info.getMemberId() == null || info.getMemberId().trim().length() == 0) {
				System.out.println("제대로 입력해주세요!");
				System.out.println("----------------------------------------------------------------------------\n\n");
				return;
			}

			System.out.print("비밀번호 (0을 입력하면 종료)> ");
			info.setMemberPassword(sc.nextLine());
			if (info.getMemberPassword().equals("0")) {
				System.out.println("이전으로 돌아갑니다\n\n");
				return;
			}
			if (info.getMemberId() == null || info.getMemberId().trim().length() == 0) {
				System.out.println("제대로 입력해주세요!");
				System.out.println("----------------------------------------------------------------------------\n\n");
				return;
			}
			// DB에 저장
			mDAO.insert(info);
		}
	
	}
