package com.yedam.app.noticeboard;

import java.util.List;

import com.yedam.app.common.Management;

import com.yedam.app.member.Member;

public class NoticeboardManagement extends Management {

	protected NoticeboardDAO nDAO = NoticeboardDAO.getInstance();
	private Member linfo;

	public NoticeboardManagement(Member loginInfo) {
		linfo = loginInfo;
		boolean role = selectRole();
		while (true) {
			System.out.println("   번호              제목                 게시자                     작성일자");
			System.out.println("----------------------------------------------------------------------------");
			boardAll();
			menuPrint(role);
			System.out.print("메뉴 선택 > ");
			int menuNo = menuSelect();
			if (menuNo == 1) {
				// 글 조회 -- 번호로(no)
				boardSelect();
			} else if (menuNo == 2 && role) {
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
		menu += "       1.글 조회 ";
		if (role) {
			menu += "       2.글 작성       3.글 수정       4.글 삭제 ";
		}
		menu += "       5.뒤로가기";
		System.out.println("----------------------------------------------------------------------------");
		System.out.println();
		System.out.println(menu);
		System.out.println();
		System.out.println("----------------------------------------------------------------------------\n");
	}
	
	private void create() {
		Noticeboard noticeboard = inputboard(linfo);
		
		nDAO.insert(noticeboard);
	}
	
	private Noticeboard inputboard(Member loginInfo) {
		Noticeboard info = new Noticeboard();
		System.out.print("글 제목 > ");
		info.setTitle(sc.nextLine());
		System.out.print("글 내용 > ");
		info.setContent(sc.nextLine());
		info.setId(loginInfo.getMemberId());
		return info;
	}
	
	private void update() {
		Noticeboard noticeboard = chcontent(linfo);
		int no = noticeboard.getNo();
		Noticeboard f1 = nDAO.selectOne(no);
		System.out.println(linfo.getMemberId()); //현재 접속한 아이디
		System.out.println(f1.getId()); // 게시물 작성한 아이디
		
		if(linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
		nDAO.chboard(noticeboard);
		} else {
			System.out.println("변경에 실패하였습니다.");
		}
	}
	
	private Noticeboard chcontent(Member loginInfo) {
		Noticeboard info = new Noticeboard();
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
		Noticeboard noticeboard = nDAO.selectOne(no);
		while (true) {
			if (noticeboard != null) {
				System.out.println("작성자 : " + noticeboard.getId());
				System.out.println("작성일자 : " + noticeboard.getRegdate());
				System.out.println("제목 : " + noticeboard.getTitle());
				System.out.println("내용 : " + noticeboard.getContent() + "\n\n");
				
				System.out.println("댓글");			
				comment(no);
				commentPrint();
				int menuNo = menuSelect();
				if (menuNo == 1) {
					// 1. 댓글 입력
					createcomment(no);
				} else if (menuNo == 2) {
					// 2. 댓글 수정
					updatecomment(linfo);
				} else if (menuNo == 3) {
					// 3. 댓글 삭제
					deletecomment(linfo);
				} else if (menuNo == 4) {
					backboard();
					break;
				} else {
					showInputError();
				}
			} else {
				System.out.println("번호를 다시 확인해주세요\n");
				break;
			}
		}

	}
	
	private void backboard() {
		System.out.println("메인 게시판으로 돌아갑니다.");
	}
	
	private int inputboardno() {
		System.out.print("게시물 선택 > ");
		return Integer.parseInt(sc.nextLine());
	}
	
	private Noticeboard inputboarddelno(Member loginInfo) {
		Noticeboard info = new Noticeboard();
		System.out.print("삭제할 게시물을 선택해주세요 > ");
		info.setNo(Integer.parseInt(sc.nextLine()));
		return info;
	}
	
	
	private void deletemember(Member loginInfo) {
		// 수정정보 입력
		Noticeboard noticeboard = inputboarddelno(linfo);
		int no = noticeboard.getNo();
		Noticeboard f1 = nDAO.selectOne(no);
		
		// DB 전달
		if(linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
		nDAO.delete(no);
		} else {
			System.out.println("삭제에 실패하였습니다");
		}
	}
	
	private void boardAll() {
		List<Noticeboard> list = nDAO.selectAll();
		for(Noticeboard noticeboard : list) {
			System.out.println(noticeboard);
		}
	}
	
	private void back() {
		System.out.println("메인으로 돌아갑니다");
	}
	
	//------------------------------------------------------------------------
	//댓글 메뉴 출력
	private void commentPrint() {
		System.out.println("1.댓글 입력 2.댓글 수정 3.댓글 삭제 4.뒤로가기");
		System.out.print("메뉴 선택 > ");
	}
	
	// 댓글 입력
	private Noticeboardcomment inputComment(Member loginInfo, int no) {
		Noticeboardcomment info = new Noticeboardcomment();
		System.out.print("댓글 입력 > ");
		info.setComment(sc.nextLine());
		info.setId(loginInfo.getMemberId());
		info.setNo(no);
		return info;
	}
	
	// 댓글 입력 insert
	private void createcomment(int no) {
		Noticeboardcomment noticeboardcomment = inputComment(linfo, no);

		nDAO.insertcomment(noticeboardcomment);
	}

	private void updatecomment(Member loginInfo) {
		Noticeboardcomment noticeboardcomment = chcomment(linfo);
		int no = noticeboardcomment.getNono();
		Noticeboardcomment f1 = nDAO.selectOnecomment(no);
//		System.out.println(linfo.getMemberId()); // 현재 접속한 아이디
//		System.out.println(f1.getId()); // 게시물 작성한 아이디

		if (linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
			nDAO.chcomment(noticeboardcomment);
		} else {
			System.out.println("변경에 실패하였습니다.");
		}
	}
	
	private Noticeboardcomment chcomment(Member loginInfo) {
		Noticeboardcomment info = new Noticeboardcomment();
		System.out.print("어떤 댓글을 수정? > ");
		info.setNono(Integer.parseInt(sc.nextLine()));
		System.out.print("바꿀 댓글을 입력해주세요 > ");
		info.setComment(sc.nextLine());
		return info;
	}
	
	//삭제 게시물 선택
	private Noticeboardcomment inputdelcomment(Member loginInfo) {
		Noticeboardcomment info = new Noticeboardcomment();
		System.out.print("삭제할 댓글을 선택해주세요 > ");
		info.setNono(Integer.parseInt(sc.nextLine()));
		return info;
	}
	
	//삭제
	private void deletecomment(Member loginInfo) {
		// 삭제정보 입력
		Noticeboardcomment noticeboardcomment = inputdelcomment(linfo);
		int no = noticeboardcomment.getNono();
		Noticeboardcomment f1 = nDAO.selectOnecomment(no);

		// DB 전달
		if (linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
			nDAO.deletecomment(no);
			System.out.println("댓글을 삭제하였습니다");
		} else {
			System.out.println("권한이 없습니다.");
		}
	}
	
	private void comment(int no) {
		List<Noticeboardcomment> list = nDAO.comment(no);
		for (Noticeboardcomment noticeboardcomment : list) {
			System.out.println(noticeboardcomment);
		}
	}

	
	
}
