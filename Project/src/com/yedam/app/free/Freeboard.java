package com.yedam.app.free;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Freeboard {
	private int no;
	private String title;
	private String content;
	private String id;
	private String regdate;
	
	@Override
	public String toString() {	
		return "[no." + no + ", 제목 : " + title + ", 게시자 : " + id + ", 작성일자 : " + regdate + "]";
	}
}
