package com.yedam.app.noticeboard;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Noticeboard {
	private int no;
	private String title;
	private String content;
	private String id;
	private String regdate;
	
	@Override
	public String toString() {	
		return "[" +"번호 : "+ no + ", 제목 : " + title + ", 게시자 : " + id + ", 작성일자 : " + regdate + "]";
	}
}
