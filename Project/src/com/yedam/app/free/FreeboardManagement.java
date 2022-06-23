package com.yedam.app.free;

import com.yedam.app.common.Management;
import com.yedam.app.member.Member;

public class FreeboardManagement extends Management {

	protected FreeboardDAO fDAO = FreeboardDAO.getInstance();
	private Member linfo;
	
	public FreeboardManagement(Member loginInfo) {
		linfo = loginInfo;
		boolean role = selectRole();
		while (true) {
			menuPrint(role);
			int menuNo = menuSelect();
			if (menuNo == 1) {
				// 글 작성
				create();
			} else if (menuNo == 2) {
				// 글 수정 -- 번호로(no)
				update();
			} else if (menuNo == 3) {
				// 글 조회 -- 번호로(no)
				boardSelect();
			} else if (menuNo == 4) {
				// 글 삭제 -- 번호로(no) 아이디 같을때
				deletemember();
			} else {
				showInputError();
			}
		}
	}

	@Override
	protected void menuPrint(boolean role) {
		System.out.println("==!==!==!==!==!==!==!==!==!====!==!==!==!==!==!==");
		System.out.println();
		System.out.println("1.글 작성 2.글 수정 3.글 조회 4.글 삭제 5.전체 조회");
		System.out.println();
		System.out.println("==!==!==!==!==!==!==!==!==!====!==!==!==!==!==!==");
	}

	private void create() {
		Freeboard freeboard = inputboard(linfo);
		
		fDAO.insert(freeboard);
	}
	
	private Freeboard inputboard(Member loginInfo) {
		Freeboard info = new Freeboard();
		System.out.print("글 제목 > ");
		info.setTitle(sc.nextLine());
		System.out.print("글 내용 > ");
		info.setContent(sc.nextLine());
		info.setId(loginInfo.getMemberId());
		return info;
	}
	
	private void update() {
		Freeboard freeboard = chcontent();
		if (freeboard.getId().equals(linfo.getMemberId()) || freeboard.getId().equals("admin")) {
			fDAO.chboard(freeboard);		
		} else {
			System.out.println("변경에 실패하였습니다");
		}
	}
	private Freeboard chcontent() {
		Freeboard info = new Freeboard();
		System.out.print("어떤 게시물을 수정? >");
		info.setNo(Integer.parseInt(sc.nextLine()));
		System.out.print("바꿀 글 제목 > ");
		info.setTitle(sc.nextLine());
		System.out.print("바꿀 글 내용 > ");
		info.setContent(sc.nextLine());		
		return info;
	}
	
	private void boardSelect() {
		int no = inputboardno();
		Freeboard freeboard = fDAO.selectOne(no);
		if (freeboard != null) {
			System.out.println("작성자 : " + freeboard.getId());
			System.out.println("작성일자 : " + freeboard.getRegdate());
			System.out.println("제목 : " + freeboard.getTitle());
			System.out.println("내용 : " + freeboard.getContent());
		} else {
			System.out.println("번호를 다시 확인해주세요");
		}
	}
	
	private int inputboardno() {
		System.out.println("게시물 선택 > ");
		return Integer.parseInt(sc.nextLine());
	}
	
	private void deletemember() {
		// 수정정보 입력
		int no = inputboardno();
		// DB 전달
		fDAO.delete(no);
	}

}
