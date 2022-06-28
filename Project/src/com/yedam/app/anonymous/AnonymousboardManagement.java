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
			int a = 1;
			int b = 5;
			int c = 1;
			System.out.println("   번호                제목                 게시자                   작성일자");
			System.out.println("----------------------------------------------------------------------------");
			next(a, b);
			System.out.println("\n\t\t\t\t\t" + c + "/" + pageAll());
			System.out.println("\n\n\n");
			menuPrint(role);
			System.out.print("메뉴 선택 > ");
			int menuNo = menuSelect();
			if (role) {
				if (menuNo == 1) {
					// 글 조회 -- 번호로(no)
					boardSelect();
				} else if (menuNo == 2) {
					// 글 작성
					create(loginInfo);
				} else if (menuNo == 3) {
					// 글 검색
					search();
				} else if (menuNo == 4) {
					// 글 수정 -- 번호로(no)
					update(loginInfo);
				} else if (menuNo == 5) {
					// 글 삭제 -- 번호로(no) 아이디 같을때
					deletemember(loginInfo);
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
			if (!role) {
				if (menuNo == 1) {
					// 글 조회 -- 번호로(no)
					boardSelect();
				} else if (menuNo == 2) {
					// 글 작성
					create(loginInfo);
				} else if (menuNo == 3) {
					// 글 검색
					search();
				} else if (menuNo == 4) {
					if (c < pageAll()) {
						a += 5;
						b += 5;
						c++;
						} else {
							System.out.println("다음 페이지가 없습니다");
						}
				} else if (menuNo == 5) {
					if (c > 1) {
					a -= 5;
					b -= 5;
					c--;
					} else {
						System.out.println("이전 페이지가 없습니다");
					}
				} else if (menuNo == 6) {
					// 뒤로가기
					back();
					break;
				} else {
					showInputError();
				}
			}
		}
	}

	@Override
	protected void menuPrint(boolean role) {
		String menu = "";
		if (role) {
			menu = "         1.글 조회  ::  2.글 작성  ::  3.글 수정  ::  4.글 삭제  ::  5. 글 검색\n\n"
					+ "              6. 다음 페이지  ::  7. 이전 페이지  ::  8. 뒤로가기";
		} else {
			menu = "   1.글 조회  ::  2. 글 작성  ::  3. 글 검색  ::  4. 다음 페이지  ::  5. 이전페이지  ::  6.뒤로가기   ";
		}
		System.out.println("============================================================================");
		System.out.println();
		System.out.println(menu);
		System.out.println();
		System.out.println("============================================================================\n");
	}

	private void create(Member loginInfo) {
		Anonymousboard info = new Anonymousboard();
		System.out.print("글 제목 (0 입력시 종료) > ");
		info.setTitle(sc.nextLine());
		if (info.getTitle().equals("0")) {
			System.out.println("이전으로 돌아갑니다.");
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

		aDAO.insert(info);
	}

	private void next(int firstPage, int lastPage) {
		List<Anonymousboard> list = aDAO.next(firstPage, lastPage);
		for (Anonymousboard anonymousboard : list) {
			System.out.println(anonymousboard);
		}
	}

	private int pageAll() {
		List<Anonymousboard> list = aDAO.selectAll();
		int page = list.size() / 5;
		if (list.size() % 5 != 0) {
			page += 1;
		}
		return page;
	}

	private void update(Member loginInfo) {
		Anonymousboard info = new Anonymousboard();
		try {
			System.out.print("어떤 게시물을 수정? (0 입력시 종료) > ");
			info.setNo(Integer.parseInt(sc.nextLine()));
			if (info.getNo() == 0) {
				System.out.println("이전으로 돌아갑니다.");
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
			aDAO.selectOne(no);
//		System.out.println(linfo.getMemberId()); // 현재 접속한 아이디
//		System.out.println(f1.getId()); // 게시물 작성한 아이디

			if (linfo.getMemberId().equals("admin")) {
				aDAO.chboard(info);
			} else {
				System.out.println("변경에 실패하였습니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력해주세요!!");
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
			Anonymousboard anonymousboard = aDAO.selectOne(no);
			while (true) {
				if (anonymousboard != null) {
					System.out.println("작성자 : " + "*****");
					System.out.println("작성일자 : " + anonymousboard.getRegdate());
					System.out.println("제목 : " + anonymousboard.getTitle());
					System.out.println("내용 : " + anonymousboard.getContent() + "\n\n");

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
			System.out.println("숫자만 입력해주세요!!");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
	}

	private int inputboardno() {
		System.out.print("게시물 선택 (0 입력시 종료) > ");
		return Integer.parseInt(sc.nextLine());
	}

	private void deletemember(Member loginInfo) {
		// 수정정보 입력
		Anonymousboard info = new Anonymousboard();
		try {
			System.out.print("삭제할 게시물을 선택해주세요 (0 입력시 종료)> ");
			info.setNo(Integer.parseInt(sc.nextLine()));
			if (info.getNo() == 0) {
				System.out.println("이전으로 돌아갑니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
		int no = info.getNo();
		aDAO.selectOne(no);

		// DB 전달
		if (linfo.getMemberId().equals("admin")) {
			aDAO.delete(no);
		} else {
			System.out.println("삭제에 실패하였습니다");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
	}

	private void search() {
		Anonymousboard info = new Anonymousboard();
		System.out.print("검색 (0 입력시 종료)> ");
		info.setTitle(sc.nextLine());
		if (info.getTitle().equals("0")) {
			System.out.println("이전으로 돌아갑니다");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		if (info.getTitle() == null || info.getTitle().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		List<Anonymousboard> list = aDAO.searchboard(info);
		System.out.println("검색 결과");
		System.out.println("   번호              제목                 게시자                     작성일자");
		System.out.println("----------------------------------------------------------------------------");
		for (Anonymousboard anonymousboard : list) {
			System.out.println(anonymousboard);
		}
		System.out.println("\n\n\n\n");
	}

	private void back() {
		System.out.println("메인으로 돌아갑니다");
		System.out.println("----------------------------------------------------------------------------\n\n");
	}

	private void backboard() {
		System.out.println("메인 게시판으로 돌아갑니다");
		System.out.println("----------------------------------------------------------------------------\n\n");
	}

	// ------------------------------------------------------------------------
	// 댓글 메뉴 출력
	private void commentPrint() {
		System.out.println("1.댓글 입력 2.댓글 수정 3.댓글 삭제 4.뒤로가기");
		System.out.print("메뉴 선택 > ");
	}

	// 댓글 입력 insert
	private void createcomment(int no, Member loginInfo) {
		Anonymousboardcomment info = new Anonymousboardcomment();
		System.out.print("댓글 입력 > ");
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

		aDAO.insertcomment(info);
	}

	private void updatecomment(Member loginInfo) {
		Anonymousboardcomment info = new Anonymousboardcomment();
		try {
			System.out.print("어떤 댓글을 수정? > ");
			info.setNono(Integer.parseInt(sc.nextLine()));
			if (info.getNono() == 0) {
				System.out.println("이전으로 돌아갑니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력해주세요!!");
		}
		System.out.print("바꿀 댓글을 입력해주세요 > ");
		info.setComment(sc.nextLine());
		if (info.getComment() == null || info.getComment().trim().length() == 0) {
			System.out.println("제대로 입력해주세요!");
			System.out.println("----------------------------------------------------------------------------\n\n");
			return;
		}
		int no = info.getNono();
		aDAO.selectOnecomment(no);
//		System.out.println(linfo.getMemberId()); // 현재 접속한 아이디
//		System.out.println(f1.getId()); // 게시물 작성한 아이디

		if (linfo.getMemberId().equals("admin")) {
			aDAO.chcomment(info);
		} else {
			System.out.println("변경에 실패하였습니다.");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
	}

	// 삭제
	private void deletecomment(Member loginInfo) {
		// 삭제정보 입력
		Anonymousboardcomment info = new Anonymousboardcomment();
		try {
			System.out.print("삭제할 댓글을 선택해주세요 > ");
			info.setNono(Integer.parseInt(sc.nextLine()));
			if (info.getNono() == 0) {
				System.out.println("이전으로 돌아갑니다.");
				System.out.println("----------------------------------------------------------------------------\n\n");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력해주세요!!");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
		int no = info.getNono();
		aDAO.selectOnecomment(no);

		// DB 전달
		if (linfo.getMemberId().equals("admin")) {
			aDAO.deletecomment(no);
			System.out.println("댓글을 삭제하였습니다");
			System.out.println("----------------------------------------------------------------------------\n\n");
		} else {
			System.out.println("권한이 없습니다.");
			System.out.println("----------------------------------------------------------------------------\n\n");
		}
	}

	private void comment(int no) {
		List<Anonymousboardcomment> list = aDAO.comment(no);
		for (Anonymousboardcomment anonymousboardcomment : list) {
			System.out.println(anonymousboardcomment);
		}
	}
}