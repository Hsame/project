package com.yedam.app.free;

import java.util.List;

import com.yedam.app.common.Management;
import com.yedam.app.member.Member;

public class FreeboardManagement extends Management {
	protected FreeboardDAO fDAO = FreeboardDAO.getInstance();
	private Member linfo;

	public FreeboardManagement(Member loginInfo) {
		int a = 1;
		int b = 5;
		int c = 1;
		linfo = loginInfo;
		boolean role = selectRole();
		while (true) {
			System.out.println("   번호                제목                  게시자                   작성일자");
			System.out.println("----------------------------------------------------------------------------");
			next(a, b);
			System.out.println("\n\t\t\t\t\t" + c + "/" + pageAll());
			// 페이징
			menuPrint(role);
			System.out.print("메뉴 선택 > ");
			int menuNo = menuSelect();
			if (menuNo == 1) {
				// 글 조회 -- 번호로(no)
				boardSelect();
			} else if (menuNo == 2) {
				// 글 작성
				create(loginInfo);
			} else if (menuNo == 3) {
				// 글 수정 -- 번호로(no)
				update(loginInfo);
			} else if (menuNo == 4) {
				// 글 삭제 -- 번호로(no) 아이디 같을때
				deletemember(loginInfo);
			} else if (menuNo == 5) {
				// 글검색 --글 제목으로 검색
				search();
			} else if (menuNo == 6) {
				if (c < pageAll()) {
					a += 5;
					b += 5;
					c++;
					} else {
						System.out.println("다음 페이지가 없습니다");
					}
			} else if (menuNo == 7) {
				if (c > 1) {
				a -= 5;
				b -= 5;
				c--;
				} else {
					System.out.println("이전 페이지가 없습니다");
				}
			} else if (menuNo == 8) {
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
		System.out.println("============================================================================");
		System.out.println();
		System.out.println("         1.글 조회  ::  2.글 작성  ::  3.글 수정  ::  4.글 삭제  ::  5. 글 검색\n\n"
				+ "              6. 다음 페이지  ::  7. 이전 페이지  ::  8. 뒤로가기");
		System.out.println();
		System.out.println("============================================================================");
	}

	private void create(Member loginInfo) {
		Freeboard info = new Freeboard();
		System.out.print("글 제목 (0을 입력시 종료) > ");
		info.setTitle(sc.nextLine());
		if (info.getTitle().equals("0")) {
			System.out.print("이전으로 돌아갑니다");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		if (info.getTitle() == null || info.getTitle().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		System.out.print("글 내용 > ");
		info.setContent(sc.nextLine());
		info.setId(loginInfo.getMemberId());
		fDAO.insert(info);
	}

	private void update(Member loginInfo) {
		Freeboard info = new Freeboard();
		try {
			System.out.print("어떤 게시물을 수정? (0 입력시 종료) > ");
			info.setNo(Integer.parseInt(sc.nextLine()));
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력해주세요.!!");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
		if (info.getNo() == 0) {
			System.out.println("이전으로 돌아갑니다");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		System.out.print("바꿀 글 제목 > ");
		info.setTitle(sc.nextLine());
		if (info.getTitle() == null || info.getTitle().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		System.out.print("바꿀 글 내용 > ");
		info.setContent(sc.nextLine());
		int no = info.getNo();
		Freeboard f1 = fDAO.selectOne(no);
//		System.out.println(linfo.getMemberId()); // 현재 접속한 아이디
//		System.out.println(f1.getId()); // 게시물 작성한 아이디

		if (linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
			fDAO.chboard(info);
		} else {
			System.out.println("변경에 실패하였습니다.");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
	}

	private void boardSelect() {
		try {
			int no = inputboardno();
			if (no == 0) {
				System.out.println("이전으로 돌아갑니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
				return;
			}
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
						createcomment(no, linfo);
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
					System.out.println("번호를 다시 확인해주세요");
					System.out.println(
							"----------------------------------------------------------------------------\n\n");
					break;
				}
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력해주세요");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}

	}

	// 게시물 선택
	private int inputboardno() {
		System.out.print("게시물 선택(번호로 선택해주세요 0 : 종료) > ");
		return Integer.parseInt(sc.nextLine());
	}

	// 삭제
	private void deletemember(Member loginInfo) {
		// 수정정보 입력
		Freeboard info = new Freeboard();
		try {
			System.out.print("삭제할 게시물을 선택해주세요 > ");
			info.setNo(Integer.parseInt(sc.nextLine()));
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력해주세요!!");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
		if (info.getNo() == 0) {
			System.out.println("이전으로 돌아갑니다.");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		int no = info.getNo();
		Freeboard f1 = fDAO.selectOne(no);

		// DB 전달
		if (linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
			fDAO.delete(no);
		} else {
			System.out.println("삭제에 실패하였습니다");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
	}

	private void next(int firstPage, int lastPage) {
		List<Freeboard> list = fDAO.next(firstPage, lastPage);
		if (list.size() != 0) {
			for (Freeboard freeboard : list) {
				System.out.println(freeboard);
			}
		} else { 
			System.out.println("정보가 없습니다!");
		}
	}

	private int pageAll() {
		List<Freeboard> list = fDAO.selectAll();
		int page = list.size() / 5;
		if (list.size() % 5 != 0) {
			page += 1;
		}
		return page;
	}

	private void search() {
		Freeboard title = inputtitle();
		if (title.getTitle().equals("0")) {
			System.out.println("이전으로 돌아갑니다.");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		if (title.getTitle() == null || title.getTitle().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		List<Freeboard> list = fDAO.searchboard(title);
		System.out.println("검색 결과");
		System.out.println("   번호              제목                 게시자                     작성일자");
		System.out.println("----------------------------------------------------------------------------");
		for (Freeboard freeboard : list) {
			System.out.println(freeboard);
		}
		System.out.println("\n\n\n\n");
	}

	private Freeboard inputtitle() {
		Freeboard info = new Freeboard();
		System.out.print("검색 > ");
		info.setTitle(sc.nextLine());
		return info;
	}

	private void back() {
		System.out.println("메인으로 돌아갑니다");
		System.out.println("----------------------------------------------------------------------------\n\n");
	}

	private void backboard() {
		System.out.println("메인 게시판으로 돌아갑니다.");
		System.out.println("----------------------------------------------------------------------------\n\n");
	}

	// 댓글 메뉴 출력
	private void commentPrint() {
		System.out.println("1.댓글 입력 2.댓글 수정 3.댓글 삭제 4.뒤로가기");
		System.out.print("메뉴 선택 > ");
	}

	// 댓글 입력 insert
	private void createcomment(int no, Member loginInfo) {
		Freeboardcomment info = new Freeboardcomment();
		System.out.print("댓글 입력 (0 입력시 종료) > ");
		info.setComment(sc.nextLine());
		if (info.getComment().equals("0")) {
			System.out.println("이전으로 돌아갑니다.");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		if (info.getComment() == null || info.getComment().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		info.setId(loginInfo.getMemberId());
		info.setNo(no);

		fDAO.insertcomment(info);
	}

	private void updatecomment(Member loginInfo) {
		Freeboardcomment info = new Freeboardcomment();
		try {
			System.out.print("어떤 댓글을 수정? (0 입력시 종료) > ");
			info.setNono(Integer.parseInt(sc.nextLine()));
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력해주세요!!");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
		if (info.getNono() == 0) {
			System.out.println("이전으로 돌아갑니다.");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		System.out.print("바꿀 댓글을 입력해주세요 > ");
		info.setComment(sc.nextLine());
		if (info.getComment() == null || info.getComment().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		int no = info.getNono();
		Freeboardcomment f1 = fDAO.selectOnecomment(no);
//		System.out.println(linfo.getMemberId()); // 현재 접속한 아이디
//		System.out.println(f1.getId()); // 게시물 작성한 아이디

		if (linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
			fDAO.chcomment(info);
		} else {
			System.out.println("변경에 실패하였습니다.");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
	}

	// 삭제
	private void deletecomment(Member loginInfo) {
		// 삭제정보 입력
		Freeboardcomment info = new Freeboardcomment();
		System.out.print("삭제할 댓글을 선택해주세요 > ");
		info.setNono(Integer.parseInt(sc.nextLine()));
		if (info.getNono() == 0) {
			System.out.println("이전으로 돌아갑니다.");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		int no = info.getNono();
		Freeboardcomment f1 = fDAO.selectOnecomment(no);

		// DB 전달
		if (linfo.getMemberId().equals(f1.getId()) || linfo.getMemberId().equals("admin")) {
			fDAO.deletecomment(no);
		} else {
			System.out.println("권한이 없습니다.");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
	}

	private void comment(int no) {
		List<Freeboardcomment> list = fDAO.comment(no);
		for (Freeboardcomment freeboardcomment : list) {
			System.out.println(freeboardcomment);
		}
	}
}
