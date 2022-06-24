package com.yedam.app.anonymous;

import java.util.List;

import com.yedam.app.common.Management;
import com.yedam.app.member.Member;

public class AnonymousboardManagement extends Management {

	protected AnonymousboardDAO aDAO = AnonymousboardDAO.getInstance();
	private Member linfo;

	public AnonymousboardManagement(Member loginInfo) {
		linfo = loginInfo;
		boolean role = selectRole();
		while (true) {
			boardAll();
			menuPrint(role);
			System.out.print("메뉴 선택 > ");
			int menuNo = menuSelect();
			if (menuNo == 1) {
				// 글 조회 -- 번호로(no)
				boardSelect();
			} else if (menuNo == 2) {
				// 글 작성
				create();
			} else if (menuNo == 3 && role) {
				// 글 수정 -- 번호로(no)
				update();
			} else if (menuNo == 4 && role) {
				// 글 삭제 -- 번호로(no) 아이디 같을때
				deletemember(loginInfo);
			} else if (menuNo == 5) {
				// 뒤로가기
				back();
				break;
			} else {				
				showInputError();
			}
		}
	}

	@Override
	protected void menuPrint(boolean role) {
		String menu = "";
		menu += "1.글 조회 2.글 작성 ";
		if (role) {
			menu += "3.글 수정 4.글 삭제 ";
		}
		menu += "5.뒤로가기";
		System.out.println("==!==!==!==!==!==!==!==!==!====!==!==!==!==!==!==");
		System.out.println();
		System.out.println(menu);
		System.out.println();
		System.out.println("==!==!==!==!==!==!==!==!==!====!==!==!==!==!==!==\n");
	}
	
	private void create() {
		Anonymousboard anonymousboard = inputboard(linfo);
		
		aDAO.insert(anonymousboard);
	}
	
	private Anonymousboard inputboard(Member loginInfo) {
		Anonymousboard info = new Anonymousboard();
		System.out.print("글 제목 > ");
		info.setTitle(sc.nextLine());
		System.out.print("글 내용 > ");
		info.setContent(sc.nextLine());
		info.setId(loginInfo.getMemberId());
		return info;
	}
	
	private void update() {
		Anonymousboard anonymousboard = chcontent(linfo);
		int no = anonymousboard.getNo();
		Anonymousboard f1 = aDAO.selectOne(no);
		System.out.println(linfo.getMemberId()); //현재 접속한 아이디
		System.out.println(f1.getId()); // 게시물 작성한 아이디
		
		if(linfo.getMemberId().equals("admin")) {
		aDAO.chboard(anonymousboard);
		} else {
			System.out.println("변경에 실패하였습니다.");
		}
	}
	
	private Anonymousboard chcontent(Member loginInfo) {
		Anonymousboard info = new Anonymousboard();
		System.out.print("어떤 게시물을 수정? > ");
		info.setNo(Integer.parseInt(sc.nextLine()));
		System.out.print("바꿀 글 제목 > ");
		info.setTitle(sc.nextLine());
		System.out.print("바꿀 글 내용 > ");
		info.setContent(sc.nextLine());	
		return info;
	}
	
	private void boardSelect() {
		int no = inputboardno();
		Anonymousboard anonymousboard = aDAO.selectOne(no);
		if (anonymousboard != null) {
			System.out.println("작성자 : " + "*****");
			System.out.println("작성일자 : " + anonymousboard.getRegdate());
			System.out.println("제목 : " + anonymousboard.getTitle());
			System.out.println("내용 : " + anonymousboard.getContent());
		} else {
			System.out.println("번호를 다시 확인해주세요");
		}
	}
	
	private int inputboardno() {
		System.out.print("게시물 선택 > ");
		return Integer.parseInt(sc.nextLine());
	}
	
	private Anonymousboard inputboarddelno(Member loginInfo) {
		Anonymousboard info = new Anonymousboard();
		System.out.print("삭제할 게시물을 선택해주세요 > ");
		info.setNo(Integer.parseInt(sc.nextLine()));
		return info;
	}
	
	
	private void deletemember(Member loginInfo) {
		// 수정정보 입력
		Anonymousboard anonymousboard = inputboarddelno(linfo);
		int no = anonymousboard.getNo();
		Anonymousboard f1 = aDAO.selectOne(no);
		
		// DB 전달
		if(linfo.getMemberId().equals("admin")) {
		aDAO.delete(no);
		} else {
			System.out.println("삭제에 실패하였습니다");
		}
	}
	
	private void boardAll() {
		List<Anonymousboard> list = aDAO.selectAll();
		for(Anonymousboard anonymousboard : list) {
			System.out.println(anonymousboard);
		}
	}
	
	private void back() {
		System.out.println("메인으로 돌아갑니다");
	}

	
	
}
