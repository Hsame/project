package com.yedam.app.free;

import java.util.List;

import com.yedam.app.common.Management;
import com.yedam.app.member.Member;

public class FreeboardManagement extends Management {

	protected FreeboardDAO fDAO = FreeboardDAO.getInstance();
	private Member linfo;

	public FreeboardManagement(Member loginInfo) {
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
			} else if (menuNo == 2) {
				// 글 작성
				create();
			} else if (menuNo == 3) {
				// 글 수정 -- 번호로(no)
				update();
			} else if (menuNo == 4) {
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
		System.out.println("----------------------------------------------------------------------------");
		System.out.println();
		System.out.println("        1.글 조회       2.글 작성       3.글 수정       4.글 삭제       5.뒤로가기");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------\n");
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
		Freeboard freeboard = chcontent(linfo);
		int no = freeboard.getNo();
		Freeboard f1 = fDAO.selectOne(no);
//		System.out.println(linfo.getMemberId()); // 현재 접속한 아이디
//		System.out.println(f1.getId()); // 게시물 작성한 아이디

		if (linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
			fDAO.chboard(freeboard);
		} else {
			System.out.println("변경에 실패하였습니다.");
		}
	}

	private Freeboard chcontent(Member loginInfo) {
		Freeboard info = new Freeboard();
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
		Freeboard freeboard = fDAO.selectOne(no);
		while (true) {
			if (freeboard != null) {
				System.out.println("작성자 : " + freeboard.getId());
				System.out.println("작성일자 : " + freeboard.getRegdate());
				System.out.println("제목 : " + freeboard.getTitle());
				System.out.println("내용 : " + freeboard.getContent() + "\n\n");
				
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
	
	//게시물 선택
	private int inputboardno() {
		System.out.print("게시물 선택(번호로 선택해주세요) > ");
		return Integer.parseInt(sc.nextLine());
	}

	//삭제 게시물 선택
	private Freeboard inputboarddelno(Member loginInfo) {
		Freeboard info = new Freeboard();
		System.out.print("삭제할 게시물을 선택해주세요 > ");
		info.setNo(Integer.parseInt(sc.nextLine()));
		return info;
	}
	
	//삭제
	private void deletemember(Member loginInfo) {
		// 수정정보 입력
		Freeboard freeboard = inputboarddelno(linfo);
		int no = freeboard.getNo();
		Freeboard f1 = fDAO.selectOne(no);

		// DB 전달
		if (linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
			fDAO.delete(no);
		} else {
			System.out.println("삭제에 실패하였습니다");
		}
	}

	private void boardAll() {
		List<Freeboard> list = fDAO.selectAll();
		for (Freeboard freeboard : list) {
			System.out.println(freeboard);
		}
	}

	private void back() {
		System.out.println("메인으로 돌아갑니다");
	}

	private void backboard() {
		System.out.println("메인 게시판으로 돌아갑니다.");
	}
	
	//댓글 메뉴 출력
	private void commentPrint() {
		System.out.println("1.댓글 입력 2.댓글 수정 3.댓글 삭제 4.뒤로가기");
		System.out.print("메뉴 선택 > ");
	}
	
	// 댓글 입력
	private Freeboardcomment inputComment(Member loginInfo, int no) {
		Freeboardcomment info = new Freeboardcomment();
		System.out.print("댓글 입력 > ");
		info.setComment(sc.nextLine());
		info.setId(loginInfo.getMemberId());
		info.setNo(no);
		return info;
	}
	
	// 댓글 입력 insert
	private void createcomment(int no) {
		Freeboardcomment freeboardcomment = inputComment(linfo, no);

		fDAO.insertcomment(freeboardcomment);
	}

	private void updatecomment(Member loginInfo) {
		Freeboardcomment freeboardcomment = chcomment(linfo);
		int no = freeboardcomment.getNono();
		Freeboardcomment f1 = fDAO.selectOnecomment(no);
//		System.out.println(linfo.getMemberId()); // 현재 접속한 아이디
//		System.out.println(f1.getId()); // 게시물 작성한 아이디

		if (linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
			fDAO.chcomment(freeboardcomment);
		} else {
			System.out.println("변경에 실패하였습니다.");
		}
	}
	
	private Freeboardcomment chcomment(Member loginInfo) {
		Freeboardcomment info = new Freeboardcomment();
		System.out.print("어떤 댓글을 수정? > ");
		info.setNono(Integer.parseInt(sc.nextLine()));
		System.out.print("바꿀 댓글을 입력해주세요 > ");
		info.setComment(sc.nextLine());
		return info;
	}
	
	//삭제 게시물 선택
	private Freeboardcomment inputdelcomment(Member loginInfo) {
		Freeboardcomment info = new Freeboardcomment();
		System.out.print("삭제할 댓글을 선택해주세요 > ");
		info.setNono(Integer.parseInt(sc.nextLine()));
		return info;
	}
	
	//삭제
	private void deletecomment(Member loginInfo) {
		// 삭제정보 입력
		Freeboardcomment freeboardcomment = inputdelcomment(linfo);
		int no = freeboardcomment.getNono();
		Freeboardcomment f1 = fDAO.selectOnecomment(no);

		// DB 전달
		if (linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
			fDAO.deletecomment(no);
			System.out.println("댓글을 삭제하였습니다");
		} else {
			System.out.println("권한이 없습니다.");
		}
	}
	
	private void comment(int no) {
		List<Freeboardcomment> list = fDAO.comment(no);
		for (Freeboardcomment freeboardcomment : list) {
			System.out.println(freeboardcomment);
		}
	}

}
